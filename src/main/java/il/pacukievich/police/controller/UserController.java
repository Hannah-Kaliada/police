package il.pacukievich.police.controller;

import il.pacukievich.police.entities.User;
import il.pacukievich.police.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/users")
public class UserController {

		private final UserService userService;

		@Autowired
		public UserController(UserService userService) {
				this.userService = userService;
		}

		@GetMapping
		public ResponseEntity<List<User>> getAllUsers() {
				List<User> users = userService.getAllUsers();
				if (users.isEmpty()) {
						return ResponseEntity.noContent().build();
				}
				return ResponseEntity.ok(users);
		}

		@GetMapping("/{id}")
		public ResponseEntity<User> getUserById(@PathVariable Long id) {
				return userService.getUserById(id)
								.map(ResponseEntity::ok)
								.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		}


		@PostMapping
		public ResponseEntity<User> createUser(@RequestBody User user) {
				try {
						User createdUser = userService.createUser(user);
						return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
				} catch (Exception e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				}
		}


		@PutMapping("/{id}")
		public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
				try {
						User updatedUser = userService.updateUser(id, user);
						return ResponseEntity.ok(updatedUser);
				} catch (RuntimeException e) {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				} catch (Exception e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				}
		}


		@DeleteMapping("/{id}")
		public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
				try {
						userService.deleteUser(id);
						return ResponseEntity.noContent().build();
				} catch (RuntimeException e) {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				} catch (Exception e) {
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				}
		}

}
