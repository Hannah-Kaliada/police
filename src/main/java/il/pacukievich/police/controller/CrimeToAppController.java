package il.pacukievich.police.controller;

import il.pacukievich.police.entities.dto.CrimeToApp;
import il.pacukievich.police.service.CrimeToAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}