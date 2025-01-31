package il.pacukievich.police.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Location {

		@Column(precision = 9, scale = 6)
		private BigDecimal latitude;

		@Column(precision = 9, scale = 6)
		private BigDecimal longitude;

		private String address;

		public BigDecimal getLatitude() {
				return latitude;
		}

		public void setLatitude(BigDecimal latitude) {
				this.latitude = latitude;
		}

		public BigDecimal getLongitude() {
				return longitude;
		}

		public void setLongitude(BigDecimal longitude) {
				this.longitude = longitude;
		}

		public String getAddress() {
				return address;
		}

		public void setAddress(String address) {
				this.address = address;
		}
}