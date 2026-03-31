package com.justicegov.demo.service;

import java.util.List;

import com.justicegov.demo.dto.AuthRequest;
import com.justicegov.demo.dto.AuthResponse;
import com.justicegov.demo.dto.UserRegistrationReq;
import com.justicegov.demo.dto.UserResponse;
import com.justicegov.demo.model.AuditLog;
import com.justicegov.demo.model.User;

public interface IdentityService {

	/**
	 * Registers a new user, encodes their password, and creates an initial audit log entry.
	 * @param req The registration request containing user details (name, email, phone, raw password, role).
	 * @return UserResponse A DTO containing the saved user's safe details (excluding the password).
	 */
	UserResponse registerUser(UserRegistrationReq req);

	/**
	 * Authenticates a user based on their email and password.
	 * If successful, generates a JWT token and logs the login action in the database.
	 * @param req The authentication request containing the email and raw password.
	 * @return AuthResponse containing the JWT token and basic user details.
	 * @throws RuntimeException if credentials are bad, user is not found, or user status is INACTIVE.
	 */
	AuthResponse authenticate(AuthRequest req);

	/**
	 * Creates and saves an audit log entry for a specific user action.
	 * @param user The user performing the action.
	 * @param action A string description of the action (e.g., "LOGIN_SUCCESS").
	 * @param resource The module or resource being accessed (e.g., "IDENTITY").
	 */
	void logAction(User user, String action, String resource);

	/**
	 * Retrieves a list of all system audit logs. This action is restricted to Administrators.
	 * @param adminId The ID of the user requesting the logs.
	 * @return List of AuditLog entities.
	 * @throws RuntimeException if the user is not found or does not have the ADMIN role.
	 */
	List<AuditLog> getAllLogs(Long adminId);

	/**
	 * Suspends a user account by changing their status to INACTIVE.
	 * @param email The email address of the user to suspend.
	 * @return A success message confirming the suspension.
	 * @throws RuntimeException if the user is not found in the database.
	 */
	String suspendUser(String email);

	/**
	 * Reactivates a suspended user account by changing their status back to ACTIVE.
	 * @param email The email address of the user to reactivate.
	 * @return A success message confirming the reactivation, or a notification if already active.
	 * @throws RuntimeException if the user is not found in the database.
	 */
	String reactivateUser(String email);
}