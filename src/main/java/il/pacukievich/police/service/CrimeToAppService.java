package il.pacukievich.police.service;

import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.entities.InvestigationStatus;
import il.pacukievich.police.entities.dto.CrimeToApp;
import il.pacukievich.police.repository.CrimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
		public List<CrimeToApp> getCrimesWithinRadius(double latitude, double longitude, double radius) {
				List<Crime> crimes = crimeRepository.findAll();
				return crimes.stream()
								.filter(crime -> {
										BigDecimal lat = crime.getLocation().getLatitude();
										BigDecimal lon = crime.getLocation().getLongitude();
										return lat != null && lon != null &&
														calculateDistance(latitude, longitude, lat.doubleValue(), lon.doubleValue()) <= radius;
								})
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

		private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
				final int R = 6371000;
				double dLat = Math.toRadians(lat2 - lat1);
				double dLon = Math.toRadians(lon2 - lon1);
				double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
								Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
												Math.sin(dLon / 2) * Math.sin(dLon / 2);
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				return R * c;
		}
		public List<CrimeToApp> getCrimesBetweenDates(LocalDateTime fromDate, LocalDateTime toDate) {
				List<Crime> crimes = crimeRepository.findAll();
				return crimes.stream()
								.filter(crime -> (crime.getReportDate().isAfter(fromDate) || crime.getReportDate().isEqual(fromDate)) &&
												(crime.getReportDate().isBefore(toDate) || crime.getReportDate().isEqual(toDate)))
								.map(this::convertToCrimeToApp)
								.collect(Collectors.toList());
		}

}