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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j 
public class IdentityServiceImpl implements IdentityService {

	private final UserRepository userRepository;
	private final AuditLogRepository auditLogRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Override
	@Transactional
	public UserResponse registerUser(UserRegistrationReq req) {
		log.info("Attempting to register new user with email: {}", req.getEmail());
		
		User user = new User();
		user.setName(req.getName());
		user.setEmail(req.getEmail());
		user.setPhone(req.getPhone());
		user.setPassword(passwordEncoder.encode(req.getPassword())); 
		user.setRole(req.getRole());
		user.setStatus(GenericStatus.ACTIVE);

		User savedUser = userRepository.save(user);

		AuditLog logEntry = new AuditLog();
		logEntry.setUser(savedUser);
		logEntry.setAction("REGISTER_USER");
		logEntry.setResource("IDENTITY");
		logEntry.setTimestamp(LocalDateTime.now());
		auditLogRepository.save(logEntry);

		log.info("Successfully registered user: {}", savedUser.getEmail());
		return mapToResponse(savedUser);
	}

	@Override
	public AuthResponse authenticate(AuthRequest req) {
		log.info("Authentication requested for email: {}", req.getEmail());
		
	    authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
	    );
	    
	    User user = userRepository.findByEmail(req.getEmail())
	            .orElseThrow(() -> {
	            	log.error("Authentication failed: User {} not found in database", req.getEmail());
	            	return new RuntimeException("User not found");
	            });
	            
	    if (user.getStatus().name().equals("INACTIVE")) {
	    	log.warn("Blocked login attempt from suspended account: {}", req.getEmail());
	        throw new RuntimeException("Access Denied: Your account has been deactivated by an Administrator.");
	    }
	    
	    logAction(user, "LOGIN_SUCCESS", "IDENTITY");
	    String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

	    log.info("Authentication successful for: {}", req.getEmail());
	    return new AuthResponse(
	        token, 
	        user.getEmail(), 
	        user.getRole().name(), 
	        user.getStatus().name(), 
	        "Login Successful"
	    );
	}

	@Override
	@Transactional
	public void logAction(User user, String action, String resource) {
		log.debug("Recording audit log for user: {}, action: {}", user.getEmail(), action);
		AuditLog logEntry = new AuditLog();
		logEntry.setUser(user);
		logEntry.setAction(action);
		logEntry.setResource(resource);
		logEntry.setTimestamp(LocalDateTime.now());
		auditLogRepository.save(logEntry);
	}

	@Override
	public List<AuditLog> getAllLogs(Long adminId) {
		log.info("Audit logs requested by User ID: {}", adminId);
		
		User admin = userRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (admin.getRole() != Role.ADMIN) {
			log.warn("Unauthorized log access attempt blocked for User ID: {}", adminId);
			throw new RuntimeException("Access Denied: Admin role required");
		}
		
		log.info("Successfully fetched all audit logs for Admin ID: {}", adminId);
		return auditLogRepository.findAll();
	}

	@Override
	public String suspendUser(String email) {
		log.info("Attempting to suspend user account: {}", email);
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
				
		user.setStatus(GenericStatus.INACTIVE);
		userRepository.save(user);
		
		log.info("Successfully suspended user account: {}", email);
		return "User " + email + " has been successfully suspended.";
	}
	
	@Override
	public String reactivateUser(String email) {
		log.info("Attempting to reactivate user account: {}", email);
		
	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
	            
	    if (user.getStatus().name().equals("ACTIVE")) {
	    	log.info("Reactivation skipped. User {} is already ACTIVE.", email);
	        return "User " + email + " is already active.";
	    }
	    
	    user.setStatus(GenericStatus.ACTIVE);
	    userRepository.save(user);
	    
	    log.info("Successfully reactivated user account: {}", email);
	    return "User " + email + " has been successfully reactivated. They can now log in.";
	}

	/**
	 * Internal helper method to map a User entity to a secure UserResponse DTO.
	 * Note: This is private and not exposed in the interface.
	 */
	private UserResponse mapToResponse(User user) {
		UserResponse res = new UserResponse();
		res.setUserId(user.getId());
		res.setName(user.getName());
		res.setEmail(user.getEmail());
		res.setRole(user.getRole().name());
		res.setStatus(user.getStatus().name());
		return res;
	}
}