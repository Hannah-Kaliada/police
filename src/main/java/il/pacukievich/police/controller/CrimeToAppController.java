package il.pacukievich.police.controller;

import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.entities.dto.CrimeToApp;
import il.pacukievich.police.service.CrimeToAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/crimes")
public class CrimeToAppController {

		private final CrimeToAppService crimeToAppService;

		@Autowired
		public CrimeToAppController(CrimeToAppService crimeToAppService) {
				this.crimeToAppService = crimeToAppService;
		}

		@GetMapping
		public ResponseEntity<List<CrimeToApp>> getAllCrimes() {
				List<CrimeToApp> crimes = crimeToAppService.getAllCrimesToApp();
				return ResponseEntity.ok(crimes);
		}

		@GetMapping("/open-or-approved")
		public ResponseEntity<List<CrimeToApp>> getOpenOrApprovedCrimes() {
				List<CrimeToApp> crimes = crimeToAppService.getOpenOrApprovedCrimes();
				return ResponseEntity.ok(crimes);
		}

		@GetMapping("/before-date")
		public ResponseEntity<List<CrimeToApp>> getCrimesBeforeDate(@RequestParam("date") String date) {
				LocalDateTime parsedDate = LocalDateTime.parse(date);
				List<CrimeToApp> crimes = crimeToAppService.getCrimesBeforeDate(parsedDate);
				return ResponseEntity.ok(crimes);
		}

		@GetMapping("/within-radius")
		public ResponseEntity<List<CrimeToApp>> getCrimesWithinRadius(
						@RequestParam("latitude") double latitude,
						@RequestParam("longitude") double longitude,
						@RequestParam("radius") double radius) {
				List<CrimeToApp> crimes = crimeToAppService.getCrimesWithinRadius(latitude, longitude, radius);
				return ResponseEntity.ok(crimes);
		}


}