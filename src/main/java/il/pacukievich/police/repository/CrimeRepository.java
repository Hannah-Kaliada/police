package il.pacukievich.police.repository;

import il.pacukievich.police.entities.Crime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrimeRepository extends JpaRepository<Crime, Long> {
}