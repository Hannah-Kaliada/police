package il.pacukievich.police.entities.utilities;

import il.pacukievich.police.entities.TypeOfCrime;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeOfCrimeConverter implements AttributeConverter<TypeOfCrime, String> {

		@Override
		public String convertToDatabaseColumn(TypeOfCrime attribute) {
				return attribute != null ? attribute.getRussianName() : null;
		}

		@Override
		public TypeOfCrime convertToEntityAttribute(String dbData) {
				if (dbData == null) {
						return null;
				}
				for (TypeOfCrime crime : TypeOfCrime.values()) {
						if (crime.getRussianName().equals(dbData)) {
								return crime;
						}
				}
				throw new IllegalArgumentException("Неизвестное значение: " + dbData);
		}
}