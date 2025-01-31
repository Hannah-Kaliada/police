package il.pacukievich.police.entities;

import il.pacukievich.police.entities.utilities.InvestigationStatusConverter;
import il.pacukievich.police.entities.utilities.TypeOfCrimeConverter;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "crimes")
public class Crime {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Convert(converter = TypeOfCrimeConverter.class)
		@Column(nullable = false)
		private TypeOfCrime type;

		@Column(nullable = false)
		private LocalDateTime crimeDate;

		@Embedded
		private Location location;

		@Column(nullable = false, columnDefinition = "TEXT")
		private String description;

		@Convert(converter = InvestigationStatusConverter.class)
		@Column(nullable = false)
		private InvestigationStatus status;


		public Long getId() {
				return id;
		}

		public void setId(Long id) {
				this.id = id;
		}

		public TypeOfCrime getType() {
				return type;
		}

		public void setType(TypeOfCrime type) {
				this.type = type;
		}

		public LocalDateTime getCrimeDate() {
				return crimeDate;
		}

		public void setCrimeDate(LocalDateTime crimeDate) {
				this.crimeDate = crimeDate;
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