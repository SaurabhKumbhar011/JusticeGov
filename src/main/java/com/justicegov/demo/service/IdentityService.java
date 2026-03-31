package com.justicegov.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justicegov.demo.config.JwtUtil;
import com.justicegov.demo.dto.AuthRequest;
import com.justicegov.demo.dto.AuthResponse;
import com.justicegov.demo.dto.UserRegistrationReq;
import com.justicegov.demo.dto.UserResponse;
import com.justicegov.demo.model.AuditLog;
import com.justicegov.demo.model.User;
import com.justicegov.demo.model.enums.GenericStatus;
import com.justicegov.demo.model.enums.Role;
import com.justicegov.demo.repository.AuditLogRepository;
import com.justicegov.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdentityService {

	private final UserRepository userRepository;
	private final AuditLogRepository auditLogRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Transactional
	public UserResponse registerUser(UserRegistrationReq req) {
		User user = new User();
		user.setName(req.getName());
		user.setEmail(req.getEmail());
		user.setPhone(req.getPhone());
		user.setPassword(passwordEncoder.encode(req.getPassword())); 
		user.setRole(req.getRole());
		user.setStatus(GenericStatus.ACTIVE); 

		User savedUser = userRepository.save(user);

		AuditLog log = new AuditLog();
		log.setUser(savedUser);
		log.setAction("REGISTER_USER");
		log.setResource("IDENTITY");
		log.setTimestamp(LocalDateTime.now());
		auditLogRepository.save(log);

		return mapToResponse(savedUser);
	}

	private UserResponse mapToResponse(User user) {
		UserResponse res = new UserResponse();
		res.setUserId(user.getId());
		res.setName(user.getName());
		res.setEmail(user.getEmail());
		res.setRole(user.getRole().name());
		res.setStatus(user.getStatus().name());
		return res;
	}

	public AuthResponse authenticate(AuthRequest req) {
	    authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
	    );
	    User user = userRepository.findByEmail(req.getEmail())
	            .orElseThrow(() -> new RuntimeException("User not found"));
	            
	    if (user.getStatus().name().equals("INACTIVE")) {
	        throw new RuntimeException("Access Denied: Your account has been deactivated by an Administrator.");
	    }
	    logAction(user, "LOGIN_SUCCESS", "IDENTITY");
	    
	    String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

	    return new AuthResponse(
	        token, 
	        user.getEmail(), 
	        user.getRole().name(), 
	        user.getStatus().name(), 
	        "Login Successful"
	    );
	}

	@Transactional
	public void logAction(User user, String action, String resource) {
		AuditLog log = new AuditLog();
		log.setUser(user);
		log.setAction(action);
		log.setResource(resource);
		log.setTimestamp(LocalDateTime.now());
		auditLogRepository.save(log);
	}

	public List<AuditLog> getAllLogs(Long adminId) {
		User admin = userRepository.findById(adminId).orElseThrow(() -> new RuntimeException("User not found"));

		if (admin.getRole() != Role.ADMIN) { 
			throw new RuntimeException("Access Denied: Admin role required");
		}
		return auditLogRepository.findAll();
	}

	public String suspendUser(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
		user.setStatus(GenericStatus.INACTIVE); 
		userRepository.save(user);
		return "User " + email + " has been successfully suspended.";
	}
	
	public String reactivateUser(String email) {
	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
	    if (user.getStatus().name().equals("ACTIVE")) {
	        return "User " + email + " is already active.";
	    }
	    user.setStatus(GenericStatus.ACTIVE); 
	    userRepository.save(user);
	    
	    return "User " + email + " has been successfully reactivated. They can now log in.";
	}
}