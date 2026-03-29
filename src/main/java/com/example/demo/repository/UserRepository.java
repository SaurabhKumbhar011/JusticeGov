<<<<<<< HEAD
	package com.example.demo.repository;
	
	import java.util.Optional;
	
	import org.springframework.data.jpa.repository.JpaRepository;
	
	import com.example.demo.model.User;
	
	public interface UserRepository extends JpaRepository<User, Long> {
		Optional<User> findByEmail(String email);
		boolean existsByPhone(String phone);
	}
=======
package com.example.demo.repository;

import com.example.demo.model.User; // Import your User entity
import org.springframework.data.jpa.repository.JpaRepository; // Import the JPA interface
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //adding a custom finder to search for Compliance Officers
    User findByRole(String role);
}
>>>>>>> origin/Vedasri
