package il.pacukievich.police.controller;

import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.entities.InvestigationStatus;
import il.pacukievich.police.entities.Location;
import il.pacukievich.police.entities.TypeOfCrime;
import il.pacukievich.police.service.CrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
		                                @RequestParam(required = false) String contact) {

				TypeOfCrime type = TypeOfCrime.valueOf(crimeType);

				String[] coords = coordinates.split(",");
				Double latitude = Double.parseDouble(coords[0].trim());
				Double longitude = Double.parseDouble(coords[1].trim());

				Crime crime = new Crime();
				crime.setType(type);
				crime.setReportDate(LocalDateTime.now());
				crime.setDescription(description);
				crime.setLocation(new Location(latitude, longitude, "Unknown"));
				crime.setStatus(InvestigationStatus.UNDER_REVIEW);


				crimeService.createCrime(crime);

				return "Crime report submitted successfully!";
		}

		@PostMapping("/crimes")
		public ResponseEntity<Crime> createCrime(@RequestBody Crime crime) {
				Crime createdCrime = crimeService.createCrime(crime);
				return new ResponseEntity<>(createdCrime, HttpStatus.CREATED);
		}


		@GetMapping
		public ResponseEntity<List<Crime>> getAllCrimes() {
				List<Crime> crimes = crimeService.getAllCrimes();
				return new ResponseEntity<>(crimes, HttpStatus.OK);
		}


		@GetMapping("/{id}")
		public ResponseEntity<Crime> getCrimeById(@PathVariable Long id) {
				Crime crime = crimeService.getCrimeById(id);
				return new ResponseEntity<>(crime, HttpStatus.OK);
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