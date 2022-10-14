package gui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.auxiliar.ManipulateNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class InterpolacaoController implements Initializable {

	private Integer numeroLinhas;

	private Integer numeroColunas;

	ManipulateNode manipulateNode = new ManipulateNode();

	@FXML
	private WebView webView;
	
	private WebEngine webEngine;
	
	@FXML
	Text copyTextMessage = new Text();
	
	@FXML
	private TextField resultadoInterpolador = new TextField();

	@FXML
	private TextField expressaoField;

	@FXML
	private Button btCalcular;

	@FXML
	private ScrollPane dadosExperimentais;

	@FXML
	private Button addRow;

	@FXML
	private Button removeRow;

	@FXML
	private GridPane dadosExperimentaisGridPane;

	@FXML
	private GridPane resultadosGridPane;

	List<Double> dados = new ArrayList<Double>();

	List<Double> resultados = new ArrayList<Double>();

	public void initialize(java.net.URL location, ResourceBundle resources) {
		webView.setDisable(true);
		Tolltips();
		dadosExperimentais.getStyleClass().clear();
		numeroLinhas = 1;
		numeroColunas = 1;

		Text txt = new Text();
		txt.setText("X");
		dadosExperimentaisGridPane.add(txt, 0, 0);
		setConstraints();


	}

	@FXML
	private void atualizaFuncao() throws IOException {
		if (expressaoField.getText() != null && expressaoField.getText() != "") {
			Expression expressao = new ExpressionBuilder(expressaoField.getText().toString()).variables("x").build();
			for (int i = 1; i < numeroLinhas; i++) {

				if (((TextField) manipulateNode.returnNodeByRowColumn(i, 0, dadosExperimentaisGridPane)).getText()
						.toString() == ""
						|| ((TextField) manipulateNode.returnNodeByRowColumn(i, 0, dadosExperimentaisGridPane))
								.getText().toString() == null) {
					TextField newtextfield = new TextField();
					newtextfield.setAlignment(Pos.CENTER);

					newtextfield.setText("0");
					manipulateNode.replaceNodeByRowColumnIndex(i, 0, dadosExperimentaisGridPane, newtextfield);
				}

				TextField textfield = new TextField();
				textfield.setAlignment(Pos.CENTER);
				expressao.setVariable("x",
						Double.valueOf(
								((TextField) manipulateNode.returnNodeByRowColumn(i, 0, dadosExperimentaisGridPane))
										.getText()));
				Double valor = expressao.evaluate();
				textfield.setText(valor.toString());
				manipulateNode.replaceNodeByRowColumnIndex(i - 1, 0, resultadosGridPane, textfield);

			}
		}

	}

	@FXML
	private void addRowAction() {

		for (int i = 0; i < numeroColunas; i++) {

			TextField txt = new TextField();
			txt.setMaxHeight(30);
			txt.setMaxWidth(100);
			txt.setAlignment(Pos.CENTER);
			dadosExperimentaisGridPane.add(txt, i, numeroLinhas);

		}

		TextField txt = new TextField();
		txt.setAlignment(Pos.CENTER);
		txt.setMaxHeight(30);
		txt.setMaxWidth(100);
		resultadosGridPane.add(txt, 0, numeroLinhas - 1);

		numeroLinhas += 1;
		setConstraints();
	}

	@FXML
	private void removeRowAction() {

		if (numeroLinhas > 1) {
			for (int i = 0; i < numeroColunas; i++) {

				manipulateNode.removeNodeByRowColumnIndex(numeroLinhas - 1, i, dadosExperimentaisGridPane);
			}
			manipulateNode.removeNodeByRowColumnIndex(numeroLinhas - 2, 0, resultadosGridPane);
			numeroLinhas -= 1;
			setConstraints();
		}

	}

	@FXML
	private void btCalcularAction() {

		copyTextMessage.setText("");
		dados.clear();
		resultados.clear();
		for (int i = 1; i < numeroLinhas; i++) {
			if (((TextField) manipulateNode.returnNodeByRowColumn(i, 0, dadosExperimentaisGridPane)).getText()
					.toString() == ""
					|| ((TextField) manipulateNode.returnNodeByRowColumn(i, 0, dadosExperimentaisGridPane)).getText()
							.toString() == null) {
				TextField newtextfield = new TextField();
				newtextfield.setAlignment(Pos.CENTER);
				newtextfield.setText("0");
				manipulateNode.replaceNodeByRowColumnIndex(i, 0, dadosExperimentaisGridPane, newtextfield);

			}
			if (((TextField) manipulateNode.returnNodeByRowColumn(i - 1, 0, resultadosGridPane)).getText()
					.toString() == ""
					|| ((TextField) manipulateNode.returnNodeByRowColumn(i, 0, resultadosGridPane)).getText()
							.toString() == null) {
				TextField newtextfield = new TextField();
				newtextfield.setAlignment(Pos.CENTER);
				newtextfield.setText("0");
				manipulateNode.replaceNodeByRowColumnIndex(i - 1, 0, resultadosGridPane, newtextfield);

			}
		}

		for (int i = 1; i < numeroLinhas; i++) {
			dados.add(
					Double.valueOf(((TextField) manipulateNode.returnNodeByRowColumn(i, 0, dadosExperimentaisGridPane))
							.getText().toString()));
			resultados
					.add(Double.valueOf(((TextField) manipulateNode.returnNodeByRowColumn(i - 1, 0, resultadosGridPane))
							.getText().toString()));
		}

		List<String> listLagrange = new ArrayList<String>();
		for (int i = 0; i < dados.size(); i++) {
			String polinomio = "";
			for (int j = 0; j < dados.size(); j++) {
				if (i != j) {
					Double diferenca = dados.get(i) - dados.get(j);
					polinomio = polinomio + "((x-" + dados.get(j) + ")/" + diferenca + ")";
				}
			}
			listLagrange.add(polinomio);
		}

		String expressao = "";
		for (int i = 0; i < resultados.size(); i++) {
			expressao = expressao + resultados.get(i) + "*" + listLagrange.get(i);
			if (i != resultados.size() - 1) {
				expressao += "+";
			}
		}
		resultadoInterpolador.setAlignment(Pos.CENTER_LEFT);
		resultadoInterpolador.setText(expressao);
		
		if(webView.isDisabled()) {
			webView.setDisable(false);
		}
		
		webEngine = webView.getEngine();
		webEngine.load("https://www.geogebra.org/calculator/");
		
	}

	
	private void setConstraints() {

		dadosExperimentaisGridPane.getColumnConstraints().clear();
		dadosExperimentaisGridPane.getRowConstraints().clear();
		dadosExperimentaisGridPane.setAlignment(Pos.TOP_CENTER);

		resultadosGridPane.getColumnConstraints().clear();
		resultadosGridPane.getRowConstraints().clear();
		resultadosGridPane.setAlignment(Pos.TOP_CENTER);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setFillWidth(false);
		col1.setMinWidth(100);
		col1.setMaxWidth(100);
		col1.setHalignment(HPos.CENTER);

		for (int i = 0; i < numeroColunas; i++) {
			dadosExperimentaisGridPane.getColumnConstraints().add(col1);
		}

		ColumnConstraints colResultados = new ColumnConstraints();
		colResultados.setFillWidth(false);
		colResultados.setMinWidth(100);
		colResultados.setMaxWidth(100);
		colResultados.setHalignment(HPos.CENTER);
		resultadosGridPane.getColumnConstraints().add(colResultados);

		dadosExperimentaisGridPane.setHgap(20);
		dadosExperimentaisGridPane.setVgap(5);
		resultadosGridPane.setVgap(5);

	}

	private void Tolltips() {
		Tooltip hintExpressao = new Tooltip();
		hintExpressao.setText("Utilize x como variável\nPara potência utilize: ^\nPara funções utilize o nome em inglês");
		hintExpressao.setStyle("-fx-background-color: white; -fx-text-fill: black");
		Tooltip.install(expressaoField, hintExpressao);
	}


	
	@FXML
	private void copyPolinomio() {
		StringSelection stringSelection = new StringSelection (resultadoInterpolador.getText());
		java.awt.datatransfer.Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents(stringSelection, null);
		if(resultadoInterpolador.getText() != null && resultadoInterpolador.getText()!="") {
			copyTextMessage.setText("Polinômio Copiado");
		}
	}

}
