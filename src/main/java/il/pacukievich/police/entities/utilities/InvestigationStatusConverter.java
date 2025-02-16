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
				return switch (attribute) {
						case OPEN -> "Открыто";
						case UNDER_REVIEW -> "На рассмотрении";
						case CLOSED -> "Закрыто";
						case SUSPENDED -> "Приостановлено";
						case APPROVED -> "Подтверждено";
						default -> throw new IllegalArgumentException("Неизвестный статус: " + attribute);
				};
		}

		@Override
		public InvestigationStatus convertToEntityAttribute(String dbData) {
				if (dbData == null) {
						return null;
				}
				return switch (dbData) {
						case "Открыто" -> InvestigationStatus.OPEN;
						case "На рассмотрении" -> InvestigationStatus.UNDER_REVIEW;
						case "Закрыто" -> InvestigationStatus.CLOSED;
						case "Приостановлено" -> InvestigationStatus.SUSPENDED;
						case "Подтверждено" -> InvestigationStatus.APPROVED;
						default -> throw new IllegalArgumentException("Неизвестное значение: " + dbData);
				};
		}
}