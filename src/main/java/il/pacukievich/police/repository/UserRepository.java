package il.pacukievich.police.repository;

import il.pacukievich.police.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
		Optional<User> findByUsername(String username);
}
