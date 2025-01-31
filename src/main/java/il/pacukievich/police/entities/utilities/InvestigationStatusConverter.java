package il.pacukievich.police.entities.utilities;

import il.pacukievich.police.entities.InvestigationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class InvestigationStatusConverter implements AttributeConverter<InvestigationStatus, String> {

		@Override
		public String convertToDatabaseColumn(InvestigationStatus attribute) {
				if (attribute == null) {
						return null;
				}
				switch (attribute) {
						case OPEN:
								return "Открыто";
						case UNDER_REVIEW:
								return "На рассмотрении";
						case CLOSED:
								return "Закрыто";
						case SUSPENDED:
								return "Приостановлено";
						default:
								throw new IllegalArgumentException("Неизвестный статус: " + attribute);
				}
		}

		@Override
		public InvestigationStatus convertToEntityAttribute(String dbData) {
				if (dbData == null) {
						return null;
				}
				switch (dbData) {
						case "Открыто":
								return InvestigationStatus.OPEN;
						case "На рассмотрении":
								return InvestigationStatus.UNDER_REVIEW;
						case "Закрыто":
								return InvestigationStatus.CLOSED;
						case "Приостановлено":
								return InvestigationStatus.SUSPENDED;
						default:
								throw new IllegalArgumentException("Неизвестное значение: " + dbData);
				}
		}
}