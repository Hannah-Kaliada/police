package il.pacukievich.police.service;

import il.pacukievich.police.entities.CustomUserDetails;
import il.pacukievich.police.entities.User;
import il.pacukievich.police.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

		private final UserRepository userRepository;

		public CustomUserDetailsService(UserRepository userRepository) {
				this.userRepository = userRepository;
		}

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

				User user = userRepository.findByUsername(username)
								.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

				return new CustomUserDetails(user);
		}
}

