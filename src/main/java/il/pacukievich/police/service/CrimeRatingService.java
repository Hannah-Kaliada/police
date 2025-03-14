package il.pacukievich.police.service;

import il.pacukievich.police.ML.CrimeRatingModel;
import il.pacukievich.police.entities.Crime;
import il.pacukievich.police.utils.CrimeUtils;
import il.pacukievich.police.utils.LocationUtils;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.File;

public class CrimeRatingService {

		private static MultiLayerNetwork model;

		static {
				try {
						model = MultiLayerNetwork.load(new File("crime_rating_model.zip"), true);
						System.out.println("Модель успешно загружена!");
				} catch (Exception e) {
						System.err.println("Ошибка при загрузке модели: " + e.getMessage());
						model = CrimeRatingModel.createModel();
						System.out.println("Создана новая модель!");
				}
		}

		public static double evaluateCrimeRating(Crime crime) {
				double distance = LocationUtils.calculateDistance(
								crime.getLocation().getLatitude(),
								crime.getLocation().getLongitude(),
								crime.getSenderLocation().getLatitude(),
								crime.getSenderLocation().getLongitude()
				);

				boolean hasPhone = CrimeUtils.containsPhoneNumber(crime.getDescription());
				boolean hasName = CrimeUtils.containsValidName(crime.getDescription());

				return CrimeRatingModel.predict(model, distance, hasPhone, hasName);
		}
}
