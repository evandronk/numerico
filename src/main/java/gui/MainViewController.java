package gui;

import java.io.IOException;
import java.util.function.Consumer;

import application.Main;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class MainViewController {

	
	
	
	
	@FXML
	private void loadInterpolacao() throws IOException {
		loadView("/gui/Interpolacao.fxml", (InterpolacaoController controller) -> {});
	}
	
	
	
	
	
	
	
	// O método loadView carrega a nova tela na tela principal, mantendo o menu
	// Indicar na absoluteName o caminho do FXML a ser carregado, ex: "/gui/Interpolcao.fxml"
	// Indicar na initializingAction o controller da tela, ex: (InterpolacaoController controller) -> {}
	// Dentro do {} pode ser indicados os métodos a serem carregados no início do carregamento, antes de inicializar o controller em si
	public synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {

		try {

			Scene mainScene = Main.getMainScene(); // Cria uma nova cena
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName)); // Carrega o FXML da nova tela pelo caminho indicado
			
			VBox newVBox = loader.load();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // Faz mainVBox = vBox da tela principal
			Node mainMenu = mainVBox.getChildren().get(0); // Faz mainMenu = menu da tela principal
			mainVBox.getChildren().clear(); // Limpa os elementos da tela principal
			mainVBox.getChildren().add(mainMenu); // Adiciona o mainMenu
			mainVBox.getChildren().addAll(newVBox.getChildren()); // Adiciona os novos elementos

			// Apenas uma animação no carregamento das telas
			SequentialTransition seq = new SequentialTransition();
			for (int i = 1; i < mainVBox.getChildren().size(); i++) {
				FadeTransition ft = new FadeTransition(Duration.millis(300), mainVBox.getChildren().get(i));
				ft.setFromValue(0.0);
				ft.setToValue(1.0);
				seq.getChildren().add(ft);
			}
			seq.play();

			// Carrega o controller indicado 
			T controller = loader.getController();
			initializingAction.accept(controller);

		} catch (IOException e) {
			System.out.println("Erro de Load");
		}
	}
}





	
