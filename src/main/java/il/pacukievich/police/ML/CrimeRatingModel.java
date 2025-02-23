package il.pacukievich.police.ML;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class CrimeRatingModel {

		public static MultiLayerNetwork createModel() {
				NeuralNetConfiguration.ListBuilder config = new NeuralNetConfiguration.Builder()
								.seed(123)
								.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
								.updater(new Adam(0.0001))  // Adam optimizer with learning rate
								.l2(0.0001)  // L2 regularization to prevent overfitting
								.list();

				// First hidden layer with more neurons
				config.layer(0, new DenseLayer.Builder().nIn(3).nOut(64)
								.activation(Activation.RELU)
								.build());

				// Second hidden layer
				config.layer(1, new DenseLayer.Builder().nIn(64).nOut(32)
								.activation(Activation.RELU)
								.build());

				// Third hidden layer
				config.layer(2, new DenseLayer.Builder().nIn(32).nOut(16)
								.activation(Activation.RELU)
								.build());

				// Fourth hidden layer
				config.layer(3, new DenseLayer.Builder().nIn(16).nOut(8)
								.activation(Activation.RELU)
								.build());

				// Output layer with Mean Absolute Error loss function
				config.layer(4, new OutputLayer.Builder(LossFunctions.LossFunction.MEAN_ABSOLUTE_ERROR)
								.nIn(8).nOut(1)
								.activation(Activation.IDENTITY)
								.build());

				MultiLayerNetwork model = new MultiLayerNetwork(config.build());
				model.init();
				return model;
		}

		public static void trainModel(MultiLayerNetwork model, INDArray input, INDArray output) {
				int epochs = 5000;
				for (int i = 0; i < epochs; i++) {
						model.fit(input, output);
						if (i % 100 == 0) {
								double loss = model.score();
								System.out.println("Эпоха " + i + " - Ошибка: " + loss);
						}
				}
		}

		public static double predict(MultiLayerNetwork model, double distance, boolean hasPhone, boolean hasName) {
				INDArray input = Nd4j.create(new double[][]{{distance, hasPhone ? 1 : 0, hasName ? 1 : 0}});
				INDArray output = model.output(input);
				double rating = output.getDouble(0);

				// Постобработка для ограничения рейтинга в диапазоне от 0 до 10
				return postprocessRating(rating);
		}
		// Функция постобработки для ограничения значения рейтинга
		private static double postprocessRating(double rating) {
				// Ограничиваем результат в диапазоне от 0 до 10
				return Math.max(0, Math.min(rating, 10));
		}
}