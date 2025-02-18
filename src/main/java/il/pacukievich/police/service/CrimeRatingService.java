package il.pacukievich.police.service;

import il.pacukievich.police.ML.CrimeRatingModel;
import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.entities.Location;
import il.pacukievich.police.utils.CrimeUtils;
import il.pacukievich.police.utils.LocationUtils;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class CrimeRatingService {

		public static void main(String[] args) {

				MultiLayerNetwork model = null;
				try {
						model = MultiLayerNetwork.load(new File("crime_rating_model.zip"), true);
						System.out.println("Модель успешно загружена!");
				} catch (Exception e) {
						System.err.println("Ошибка при загрузке модели: " + e.getMessage());

						model = CrimeRatingModel.createModel();
						System.out.println("Создана новая модель!");
				}

				Crime crime = new Crime();
				crime.setLocation(new Location(55.7558, 37.6173, "Moscow"));
				crime.setSenderLocation(new Location(55.7558, 37.6177, "Moscow"));
				crime.setDescription("Михаил Иванович Иванов +375(44)456-78-90");
				crime.setReportDate(LocalDateTime.now().minusMinutes(1));

				double distance = LocationUtils.calculateDistance(
								crime.getLocation().getLatitude(),
								crime.getLocation().getLongitude(),
								crime.getSenderLocation().getLatitude(),
								crime.getSenderLocation().getLongitude()
				);


				boolean hasPhone = CrimeUtils.containsPhoneNumber(crime.getDescription());
				boolean hasName = CrimeUtils.containsValidName(crime.getDescription());

				double minutesSinceReport = Duration.between(crime.getReportDate(), LocalDateTime.now()).toMinutes();

				double rating = CrimeRatingModel.predict(model, distance, hasPhone, hasName);
				System.out.println("Рейтинг заявления: " + rating);
		}
}