package il.pacukievich.police.utils;

import java.math.BigDecimal;

public class LocationUtils {
		public static final double EARTH_RADIUS_KM = 6371;
		public static final double METERS_IN_KM = 1000;

		public static double calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
				double lat1Rad = Math.toRadians(lat1.doubleValue());
				double lon1Rad = Math.toRadians(lon1.doubleValue());
				double lat2Rad = Math.toRadians(lat2.doubleValue());
				double lon2Rad = Math.toRadians(lon2.doubleValue());

				double dlat = lat2Rad - lat1Rad;
				double dlon = lon2Rad - lon1Rad;

				double a = Math.sin(dlat / 2) * Math.sin(dlat / 2)
								+ Math.cos(lat1Rad) * Math.cos(lat2Rad)
								* Math.sin(dlon / 2) * Math.sin(dlon / 2);

				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

				return EARTH_RADIUS_KM * c * METERS_IN_KM;
		}
}