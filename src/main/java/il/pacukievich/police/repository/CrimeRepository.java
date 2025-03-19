package il.pacukievich.police.repository;

import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.entities.InvestigationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CrimeRepository extends JpaRepository<Crime, Long> {

		// Пример метода для поиска преступлений по радиусу (оставляем его, если необходимо)
		@Query(value = "SELECT * FROM crimes " +
						"WHERE (6371 * acos( cos(radians(:lat)) * cos(radians(location_latitude)) " +
						"* cos(radians(location_longitude) - radians(:lon)) + sin(radians(:lat)) " +
						"* sin(radians(location_latitude)) )) <= :radius", nativeQuery = true)
		List<Crime> findCrimesWithinRadius(@Param("lat") double lat,
		                                   @Param("lon") double lon,
		                                   @Param("radius") double radius);
		@Query(value =
						"SELECT (cell_lon + :gridSize/2) as centerLongitude, " +
										"       (cell_lat + :gridSize/2) as centerLatitude, " +
										"       dominant_crime as dominantCrimeType, " +
										"       crimeCount " +
										"FROM ( " +
										"    SELECT cell_lat, cell_lon, " +
										"           COUNT(*) as crimeCount, " +
										"           ( " +
										"             SELECT type " +
										"             FROM crimes c2 " +
										"             WHERE FLOOR(c2.location_latitude / :gridSize) * :gridSize = cell_lat " +
										"               AND FLOOR(c2.location_longitude / :gridSize) * :gridSize = cell_lon " +
										"             GROUP BY type " +
										"             ORDER BY COUNT(*) DESC " +
										"             LIMIT 1 " +
										"           ) as dominant_crime " +
										"    FROM ( " +
										"         SELECT FLOOR(location_latitude / :gridSize) * :gridSize AS cell_lat, " +
										"                FLOOR(location_longitude / :gridSize) * :gridSize AS cell_lon, " +
										"                type " +
										"         FROM crimes " +
										"    ) as sub " +
										"    GROUP BY cell_lat, cell_lon " +
										") as hotspots", nativeQuery = true)
		List<Object[]> findCrimeHotspots(@Param("gridSize") double gridSize);



		@Query(value = "SELECT id, description, status, type, location_latitude, location_longitude " +
						"FROM crimes " +
						"WHERE status = 'Открыто' OR status = 'Подтверждено'", nativeQuery = true)
		List<Object[]> findOpenOrApprovedCrimesNative();

		@Query(value = "SELECT id, description, status, type, location_latitude, location_longitude FROM crimes WHERE status = 'На рассмотрении'", nativeQuery = true)
		List<Object[]> findUnderReviewCrimesNative();

}
