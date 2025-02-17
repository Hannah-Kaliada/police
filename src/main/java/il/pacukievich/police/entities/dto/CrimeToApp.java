package il.pacukievich.police.entities.dto;

import il.pacukievich.police.entities.InvestigationStatus;
import il.pacukievich.police.entities.Location;
import il.pacukievich.police.entities.TypeOfCrime;

import java.time.LocalDateTime;

public class CrimeToApp {

		private TypeOfCrime type;
		private LocalDateTime reportDate;
		private Location location;
		private String description;
		private InvestigationStatus status;

		public TypeOfCrime getType() {
				return type;
		}

		public void setType(TypeOfCrime type) {
				this.type = type;
		}

		public LocalDateTime getReportDate() {
				return reportDate;
		}

		public void setReportDate(LocalDateTime reportDate) {
				this.reportDate = reportDate;
		}

		public Location getLocation() {
				return location;
		}

		public void setLocation(Location location) {
				this.location = location;
		}

		public String getDescription() {
				return description;
		}

		public void setDescription(String description) {
				this.description = description;
		}

		public InvestigationStatus getStatus() {
				return status;
		}

		public void setStatus(InvestigationStatus status) {
				this.status = status;
		}
}