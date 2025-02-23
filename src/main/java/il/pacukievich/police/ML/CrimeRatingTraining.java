package il.pacukievich.police.ML;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

public class CrimeRatingTraining {

		public static void main(String[] args) {

				double[][] features = {
								{10.5, 1, 1},   // Пример 1: расстояние = 10.5 м, есть телефон, есть ФИО
								{50.2, 1, 0},   // Пример 2: расстояние = 50.2 м, есть телефон, нет ФИО
								{5.8, 0, 1},    // Пример 3: расстояние = 5.8 м, нет телефона, есть ФИО
								{100.0, 0, 0},  // Пример 4: расстояние = 100 м, нет телефона, нет ФИО
								{2.0, 1, 1},    // Пример 5: очень близкое расстояние, все данные есть
								{75.0, 0, 1},   // Пример 6: большое расстояние, есть ФИО, нет телефона
								{20.0, 1, 1},
								{25.0, 1, 1},
								{2.0, 0, 1},
								{2.0, 0, 0},
								{20.0, 0, 0}
				};

				double[] labels = {7.0, 3.0, 9.0, 0.0, 10.0, 1.0, 5.0, 5.0, 9.89, 9.2, 4.6};

				INDArray input = Nd4j.create(features);
				INDArray output = Nd4j.create(labels, new int[]{labels.length, 1});

				MultiLayerNetwork model = CrimeRatingModel.createModel();

				CrimeRatingModel.trainModel(model, input, output);

				System.out.println("Обучение завершено!");

				try {
						model.save(new File("crime_rating_model.zip"));
						System.out.println("Модель успешно сохранена!");
				} catch (IOException e) {
						System.err.println("Ошибка при сохранении модели: " + e.getMessage());
				}

				double testDistance = 2.0;
				boolean testHasPhone = true;
				boolean testHasName = true;

				double rating = CrimeRatingModel.predict(model, testDistance, testHasPhone, testHasName);
				System.out.println("Прогнозируемый рейтинг: " + rating);
		}
}