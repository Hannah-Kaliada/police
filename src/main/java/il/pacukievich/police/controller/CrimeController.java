package il.pacukievich.police.controller;

import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.service.CrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/free")
public class CrimeController {

		private final CrimeService crimeService;

		@Autowired
		public CrimeController(CrimeService crimeService) {
				this.crimeService = crimeService;
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