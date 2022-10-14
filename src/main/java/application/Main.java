package application;


import java.io.IOException;


import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	private static Scene mainScene;

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));

			ScrollPane scrollPane = loader.load();

			scrollPane.getLayoutBounds();
			scrollPane.getViewportBounds();
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);

			mainScene = new Scene(scrollPane);

			primaryStage.setResizable(true);
			
			//primaryStage.setMaximized(true);

			primaryStage.setScene(mainScene);
			primaryStage.setResizable(true);
			primaryStage.setTitle("Cálculo Numérico");
			primaryStage.show();
			FadeTransition ft = new FadeTransition(Duration.millis(200), scrollPane);
			ft.setFromValue(0.0);
			ft.setToValue(1.0);
			ft.play();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		launch(args);

	}

	public static Scene getMainScene() {
		return mainScene;
	}

}
