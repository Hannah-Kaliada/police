package il.pacukievich.police.service;

import il.pacukievich.police.entities.TypeOfCrime;
import il.pacukievich.police.entities.dto.CrimeHotspot;
import il.pacukievich.police.entities.utilities.TypeOfCrimeConverter;
import il.pacukievich.police.repository.CrimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrimeAnalysisService {

		@Autowired
		private CrimeRepository crimeRepository;

		public List<CrimeHotspot> getCrimeHotspots(double gridSize) {
				List<Object[]> results = crimeRepository.findCrimeHotspots(gridSize);
				List<CrimeHotspot> hotspots = new ArrayList<>();

				for (Object[] row : results) {
						double centerLongitude = ((Number) row[0]).doubleValue();
						double centerLatitude = ((Number) row[1]).doubleValue();
						String dominantCrimeTypeStr = (String) row[2];
						TypeOfCrimeConverter converter = new TypeOfCrimeConverter();
						TypeOfCrime dominantCrimeType = converter.convertToEntityAttribute(dominantCrimeTypeStr);
						long crimeCount = ((Number) row[3]).longValue();

						hotspots.add(new CrimeHotspot(centerLatitude, centerLongitude, dominantCrimeType, crimeCount));
				}
				return hotspots;
		}

}
