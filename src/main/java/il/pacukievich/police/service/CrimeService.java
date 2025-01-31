package il.pacukievich.police.service;

import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.repository.CrimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CrimeService {

		private final CrimeRepository crimeRepository;

		@Autowired
		public CrimeService(CrimeRepository crimeRepository) {
				this.crimeRepository = crimeRepository;
		}

		// Создать новое преступление
		public Crime createCrime(Crime crime) {
				return crimeRepository.save(crime);
		}

		// Получить все преступления
		public List<Crime> getAllCrimes() {
				return crimeRepository.findAll();
		}

		// Получить преступление по ID
		public Crime getCrimeById(Long id) {
				return crimeRepository.findById(id)
								.orElseThrow(() -> new NullPointerException("Преступление не найдено: id=" + id));
		}

		// Обновить преступление
		public Crime updateCrime(Long id, Crime updatedCrime) {
				return crimeRepository.findById(id)
								.map(crime -> {
										crime.setType(updatedCrime.getType());
										crime.setCrimeDate(updatedCrime.getCrimeDate());
										crime.setLocation(updatedCrime.getLocation());
										crime.setDescription(updatedCrime.getDescription());
										crime.setStatus(updatedCrime.getStatus());
										return crimeRepository.save(crime);
								})
								.orElseThrow(() -> new NullPointerException("Преступление не найдено: id=" + id));
		}

		// Удалить преступление
		public void deleteCrime(Long id) {
				if (!crimeRepository.existsById(id)) {
						throw new NullPointerException("Преступление не найдено: id=" + id);
				}
				crimeRepository.deleteById(id);
		}
}