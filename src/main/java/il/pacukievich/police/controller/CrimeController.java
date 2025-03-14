package il.pacukievich.police.controller;

import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.entities.InvestigationStatus;
import il.pacukievich.police.entities.Location;
import il.pacukievich.police.entities.TypeOfCrime;
import il.pacukievich.police.service.CrimeRatingService;
import il.pacukievich.police.service.CrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static il.pacukievich.police.service.CrimeRatingService.evaluateCrimeRating;

@RestController
@RequestMapping("/submit-report")
public class CrimeController {

		private final CrimeService crimeService;

		@Autowired
		public CrimeController(CrimeService crimeService) {
				this.crimeService = crimeService;
		}
		@PostMapping
		public String submitCrimeReport(@RequestParam String crimeType,
		                                @RequestParam String description,
		                                @RequestParam String coordinates,
		                                @RequestParam(required = false) String contact,
		                                @RequestParam(required = false) String senderCoordinates) { // Добавлен новый параметр

				TypeOfCrime type = TypeOfCrime.valueOf(crimeType);

				// Обработка основных координат
				String[] coords = coordinates.split(",");
				Double latitude = Double.parseDouble(coords[0].trim());
				Double longitude = Double.parseDouble(coords[1].trim());

				// Создание объекта Crime
				Crime crime = new Crime();
				crime.setType(type);
				crime.setReportDate(LocalDateTime.now());
				crime.setDescription(description);
				crime.setLocation(new Location(latitude, longitude, "Unknown"));
				crime.setStatus(InvestigationStatus.UNDER_REVIEW);

				// Обработка senderCoordinates, если они переданы
				if (senderCoordinates != null && !senderCoordinates.isEmpty()) {
						String[] senderCoords = senderCoordinates.split(",");
						Double senderLat = Double.parseDouble(senderCoords[0].trim());
						Double senderLon = Double.parseDouble(senderCoords[1].trim());
						crime.setSenderLocation(new Location(senderLat, senderLon, "Unknown"));
				}

				crimeService.createCrime(crime);

				return "Crime report submitted successfully!";
		}


		@PostMapping("/crimes")
		public ResponseEntity<Crime> createCrime(@RequestBody Crime crime) {
				Crime createdCrime = crimeService.createCrime(crime);
				return new ResponseEntity<>(createdCrime, HttpStatus.CREATED);
		}


		@GetMapping("/getAll")
		public ResponseEntity<List<Crime>> getAllCrimes() {
				List<Crime> crimes = crimeService.getAllCrimes();
				return new ResponseEntity<>(crimes, HttpStatus.OK);
		}


		@GetMapping("/{id}")
		public ResponseEntity<Crime> getCrimeById(@PathVariable Long id) {
				Crime crime = crimeService.getCrimeById(id);
				return new ResponseEntity<>(crime, HttpStatus.OK);
		}
		@GetMapping("/{id}/rating")
		public ResponseEntity<Double> getCrimeRating(@PathVariable Long id) {
				Crime crime = crimeService.getCrimeById(id);
				if (crime == null) {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				double rating = CrimeRatingService.evaluateCrimeRating(crime);
				return new ResponseEntity<>(rating, HttpStatus.OK);
		}
		@PutMapping("/{id}")
		public ResponseEntity<Crime> updateCrime(
						@PathVariable Long id,
						@RequestBody Crime updatedCrime
		) {
				Crime crime = crimeService.updateCrime(id, updatedCrime);
				return new ResponseEntity<>(crime, HttpStatus.OK);
		}


		@DeleteMapping("/{id}")
		public ResponseEntity<Void> deleteCrime(@PathVariable Long id) {
				crimeService.deleteCrime(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}


		@ExceptionHandler(NullPointerException.class)
		public ResponseEntity<String> handleCrimeNotFound(NullPointerException ex) {
				return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
}