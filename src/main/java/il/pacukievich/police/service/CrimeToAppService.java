package il.pacukievich.police.service;

import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.entities.InvestigationStatus;
import il.pacukievich.police.entities.Location;
import il.pacukievich.police.entities.TypeOfCrime;
import il.pacukievich.police.entities.dto.CrimeToApp;
import il.pacukievich.police.entities.utilities.InvestigationStatusConverter;
import il.pacukievich.police.entities.utilities.TypeOfCrimeConverter;
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

				List<Object[]> results = crimeRepository.findOpenOrApprovedCrimesNative();

				return results.stream()
								.map(result -> {
										CrimeToApp crimeToApp = new CrimeToApp();
										InvestigationStatusConverter statusConverter = new InvestigationStatusConverter();
										crimeToApp.setDescription((String) result[1]);
										String statusString = (String) result[2];
										InvestigationStatus status = statusConverter.convertToEntityAttribute(statusString);
										crimeToApp.setStatus(status);

										TypeOfCrimeConverter typeConverter = new TypeOfCrimeConverter();
										String typeString = (String) result[3];
										TypeOfCrime type = typeConverter.convertToEntityAttribute(typeString);
										crimeToApp.setType(type);

										crimeToApp.setLocation(new Location());


										BigDecimal latitude = (BigDecimal) result[4];
										BigDecimal longitude = (BigDecimal) result[5];

										crimeToApp.getLocation().setLatitude(latitude);
										crimeToApp.getLocation().setLongitude(longitude);

										return crimeToApp;
								})
								.collect(Collectors.toList());
		}

		public List<Crime> getUnderReviewCrimes() {
				List<Object[]> results = crimeRepository.findUnderReviewCrimesNative();

				return results.stream()
								.map(result -> {
										Crime crime = new Crime();
										InvestigationStatusConverter statusConverter = new InvestigationStatusConverter();

										crime.setId((Long) result[0]);
										TypeOfCrimeConverter typeConverter = new TypeOfCrimeConverter();
										String description = (String) result[1];
										crime.setDescription(description);
										String typeString = (String) result[3];
										TypeOfCrime type = typeConverter.convertToEntityAttribute(typeString);
										crime.setType(type);
										InvestigationStatus status = statusConverter.convertToEntityAttribute((String) result[2]);
										crime.setStatus(status);
										crime.setLocation(new Location());

										crime.getLocation().setLatitude((BigDecimal) result[4]);
										crime.getLocation().setLongitude((BigDecimal) result[5]);

										return crime;
								})
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