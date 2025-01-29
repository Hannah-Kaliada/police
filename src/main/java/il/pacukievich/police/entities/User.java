package il.pacukievich.police.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@NotBlank(message = "Username cannot be blank")
		@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
		private String username;

		@NotBlank(message = "Password cannot be blank")
		@Size(min = 6, message = "Password must be at least 6 characters")
		private String password;

		@Email(message = "Email should be valid")
		private String email;

		@NotBlank(message = "Role cannot be blank")
		private String role; // USER, EMPLOYEE, ADMIN

		private boolean active;

		public Long getId() {
				return id;
		}

		public String getUsername() {
				return username;
		}

		public String getPassword() {
				return password;
		}

		public String getEmail() {
				return email;
		}

		public String getRole() {
				return role;
		}

		public boolean isActive() {
				return active;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public void setUsername(String username) {
				this.username = username;
		}

		public void setPassword(String password) {
				this.password = password;
		}

		public void setEmail(String email) {
				this.email = email;
		}

		public void setRole(String role) {
				this.role = role;
		}

		public void setActive(boolean active) {
				this.active = active;
		}
}

