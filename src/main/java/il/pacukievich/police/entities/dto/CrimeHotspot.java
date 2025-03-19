package il.pacukievich.police.entities.dto;

import il.pacukievich.police.entities.TypeOfCrime;

public class CrimeHotspot {
		private double centerLatitude;
		private double centerLongitude;
		private TypeOfCrime dominantCrimeType;
		private long crimeCount;

		public CrimeHotspot(double centerLatitude, double centerLongitude, TypeOfCrime dominantCrimeType, long crimeCount) {
				this.centerLatitude = centerLatitude;
				this.centerLongitude = centerLongitude;
				this.dominantCrimeType = dominantCrimeType;
				this.crimeCount = crimeCount;
		}

		// Геттеры и сеттеры

		public double getCenterLatitude() {
				return centerLatitude;
		}

		public void setCenterLatitude(double centerLatitude) {
				this.centerLatitude = centerLatitude;
		}

		public double getCenterLongitude() {
				return centerLongitude;
		}

		public void setCenterLongitude(double centerLongitude) {
				this.centerLongitude = centerLongitude;
		}

		public TypeOfCrime getDominantCrimeType() {
				return dominantCrimeType;
		}

		public void setDominantCrimeType(TypeOfCrime dominantCrimeType) {
				this.dominantCrimeType = dominantCrimeType;
		}

		public long getCrimeCount() {
				return crimeCount;
		}

		public void setCrimeCount(long crimeCount) {
				this.crimeCount = crimeCount;
		}
}
