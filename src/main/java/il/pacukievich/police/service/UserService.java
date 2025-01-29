package il.pacukievich.police.service;

import il.pacukievich.police.entities.User;
import il.pacukievich.police.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

		private final UserRepository userRepository;

		private final PasswordEncoder passwordEncoder;

		@Autowired
		public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
				this.userRepository = userRepository;
				this.passwordEncoder = passwordEncoder;
		}

		public List<User> getAllUsers() {
				return userRepository.findAll();
		}


		public Optional<User> getUserById(Long id) {
				return userRepository.findById(id);
		}

		public User createUser(User user) {

				String encodedPassword = passwordEncoder.encode(user.getPassword());
				user.setPassword(encodedPassword);

				return userRepository.save(user);
		}

		public User updateUser(Long id, User user) {
				return userRepository.findById(id).map(existingUser -> {
						existingUser.setUsername(user.getUsername());
						existingUser.setPassword(user.getPassword());
						existingUser.setEmail(user.getEmail());
						existingUser.setRole(user.getRole());
						existingUser.setActive(user.isActive());
						return userRepository.save(existingUser);
				}).orElseThrow(() -> new RuntimeException("User not found with id " + id));
		}

		public void deleteUser(Long id) {
				if (userRepository.existsById(id)) {
						userRepository.deleteById(id);
				} else {
						throw new RuntimeException("User not found with id " + id);
				}
		}
}
