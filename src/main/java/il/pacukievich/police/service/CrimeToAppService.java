package il.pacukievich.police.service;

import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.entities.InvestigationStatus;
import il.pacukievich.police.entities.dto.CrimeToApp;
import il.pacukievich.police.repository.CrimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrimeToAppService {

		private final CrimeRepository crimeRepository;

		@Autowired
		public CrimeToAppService(CrimeRepository crimeRepository) {
				this.crimeRepository = crimeRepository;
		}

		public List<CrimeToApp> getAllCrimesToApp() {
				List<Crime> crimes = crimeRepository.findAll();
				return crimes.stream()
								.map(this::convertToCrimeToApp)
								.collect(Collectors.toList());
		}

		public List<CrimeToApp> getOpenOrApprovedCrimes() {
				List<Crime> crimes = crimeRepository.findAll();
				return crimes.stream()
								.filter(crime -> crime.getStatus() == InvestigationStatus.OPEN ||
												crime.getStatus() == InvestigationStatus.APPROVED)
								.map(this::convertToCrimeToApp)
								.collect(Collectors.toList());
		}

		public List<CrimeToApp> getCrimesBeforeDate(LocalDateTime date) {
				List<Crime> crimes = crimeRepository.findAll();
				return crimes.stream()
								.filter(crime -> crime.getReportDate().isBefore(date) || crime.getReportDate().isEqual(date))
								.map(this::convertToCrimeToApp)
								.collect(Collectors.toList());
		}

		private CrimeToApp convertToCrimeToApp(Crime crime) {
				CrimeToApp dto = new CrimeToApp();
				dto.setType(crime.getType());
				dto.setReportDate(crime.getReportDate());
				dto.setLocation(crime.getLocation());
				dto.setDescription(crime.getDescription());
				dto.setStatus(crime.getStatus());
				return dto;
		}
}