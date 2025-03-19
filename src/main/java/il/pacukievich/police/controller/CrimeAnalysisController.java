package il.pacukievich.police.controller;

import il.pacukievich.police.entities.dto.CrimeHotspot;
import il.pacukievich.police.service.CrimeAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inapi/crime-analysis")
public class CrimeAnalysisController {

		@Autowired
		private CrimeAnalysisService crimeAnalysisService;

		// Пример запроса: GET /api/crime-analysis/hotspots?gridSize=0.01
		@GetMapping("/hotspots")
		public ResponseEntity<List<CrimeHotspot>> getCrimeHotspots(@RequestParam double gridSize) {
				List<CrimeHotspot> hotspots = crimeAnalysisService.getCrimeHotspots(gridSize);
				return ResponseEntity.ok(hotspots);
		}
}
