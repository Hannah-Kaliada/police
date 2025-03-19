package il.pacukievich.police.entities;

import il.pacukievich.police.entities.utilities.InvestigationStatusConverter;
import il.pacukievich.police.entities.utilities.TypeOfCrimeConverter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "crimes")
public class Crime {
		private static final GeometryFactory geometryFactory = new GeometryFactory();

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Convert(converter = TypeOfCrimeConverter.class)
		@Column(nullable = false)
		private TypeOfCrime type;

		@Column(nullable = false)
		private LocalDateTime reportDate;

		@Embedded
		@AttributeOverrides({
						@AttributeOverride(name = "latitude", column = @Column(name = "location_latitude")),
						@AttributeOverride(name = "longitude", column = @Column(name = "location_longitude")),
						@AttributeOverride(name = "address", column = @Column(name = "location_address"))
		})
		private Location location;

		@Embedded
		@AttributeOverrides({
						@AttributeOverride(name = "latitude", column = @Column(name = "sender_location_latitude")),
						@AttributeOverride(name = "longitude", column = @Column(name = "sender_location_longitude")),
						@AttributeOverride(name = "address", column = @Column(name = "sender_location_address"))
		})
		private Location senderLocation;

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

		public LocalDateTime getReportDate() {
				return reportDate;
		}

		public void setReportDate(LocalDateTime crimeDate) {
				this.reportDate = crimeDate;
		}

		public Location getLocation() {
				return location;
		}

		public void setSenderLocation(Location location) {
				this.senderLocation = location;
		}
		public Location getSenderLocation() {
				return senderLocation;
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