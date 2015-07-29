package net.d80harri.wr.fx.core;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class ViewBase<P extends PresenterBase<?, M, P>, M, ME extends ViewBase<P, M, ME>>
		extends BorderPane implements IView<P, M, ME> {
	private P presenter;

	public ViewBase() {
		String fxmlFile = this.getClass().getSimpleName() + ".fxml";
		URL fileLocation = getClass().getResource(
				fxmlFile);
		
		if (fileLocation == null) {
			throw new RuntimeException("FXML file " + fxmlFile + " not found.");
		}
		
		FXMLLoader fxmlLoader = new FXMLLoader(fileLocation);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public P getPresenter() {
		return presenter;
	}

	public void setPresenter(P presenter) {
		this.presenter = presenter;
	}
}
