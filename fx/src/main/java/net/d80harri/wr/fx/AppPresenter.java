package net.d80harri.wr.fx;

import java.net.URL;
import java.util.ResourceBundle;

import net.d80harri.wr.fx.debug.DebugPresenter;
import net.d80harri.wr.fx.debug.DebugView;
import net.d80harri.wr.fx.task.TaskPresenter;
import net.d80harri.wr.fx.task.TaskView;
import net.d80harri.wr.fx.tasklist.TaskListPresenter;
import net.d80harri.wr.fx.tasklist.TaskListView;
import net.d80harri.wr.model.Task;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AppPresenter implements Initializable {
	//@formatter:off
	@FXML private AnchorPane ctlTask;
	@FXML private AnchorPane ctlDebug;
	//@formatter:on
	
	private TaskView viewTask;
	private DebugView viewDebug;
	
	private TaskPresenter presTask;
	private DebugPresenter presDebug;

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
	}

	private void initializeViews() {
		viewTask = new TaskView();
		viewDebug = new DebugView();
		
		presTask = (TaskPresenter) viewTask.getPresenter();
		presDebug = (DebugPresenter) viewDebug.getPresenter();
	}

	private void initializeControls() {
		ctlTask.getChildren().add(viewTask.getView());
		ctlDebug.getChildren().add(viewDebug.getView());
	}

	private void bindModelToControls() {
	}

	private void bindViewsToModel() {
	}

	private void bindModelPropertiesToModel() {
	}
	
}
