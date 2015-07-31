package net.d80harri.wr.fx.debug;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class DebugPresenter implements Initializable {
	//@formatter:off
	@FXML private TextArea ctlOutput;
	//@formatter:off

	private StringProperty model;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		initializeModel();
		initializeViews();
		initializeControls();

		bindModelToControls();
		bindViewsToModel();	
		bindModelPropertiesToModel();
	}
	
	private void initializeModel() {
		this.model = new SimpleStringProperty();
	}
	
	private void initializeViews() {
	}
	
	private void initializeControls() {
	}
	
	private void bindModelToControls() {
		model.bindBidirectional(ctlOutput.textProperty());
	}

	private void bindViewsToModel() {
	}

	private void bindModelPropertiesToModel() {
		DebugBus.getInstance().register(e -> model.set(model.get() + "\n" + e.toString()));
	}
	
	public String getModel() {
		return modelProperty().get();
	}

	public StringProperty modelProperty() {
		return model;
	}

	@FXML
	private void debug(ActionEvent evt) {
	}
}
