package gui.auxiliar;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class ManipulateNode {

	public Node returnNodeByRowColumn(final int row, final int column, GridPane gridPane) {

		ObservableList<Node> childrens = gridPane.getChildren();
		Node nodeRC = childrens.get(0);
		for (Node node : childrens) {

			if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
				nodeRC = node;
				break;

			}
		}

		return nodeRC;

	}

	public void removeNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {

		ObservableList<Node> childrens = gridPane.getChildren();

		for (Node node : childrens) {

			if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {

				gridPane.getChildren().remove(node);

				break;
			}
		}
	}

	public void replaceNodeByRowColumnIndex(final int row, final int column, GridPane gridPane, Node replaceNode) {

		ObservableList<Node> childrens = gridPane.getChildren();

		for (Node node : childrens) {

			if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {

				removeNodeByRowColumnIndex(row, column, gridPane);

				gridPane.add(replaceNode, column, row);

				break;

			}
		}

		gridPane = new GridPane();

	}

}
