package net.d80harri.wr.fx.task;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import net.d80harri.wr.fx.debug.DebugBus;
import net.d80harri.wr.fx.debug.DebugEvent;
import net.d80harri.wr.fx.tasklist.TaskListPresenter;
import net.d80harri.wr.fx.tasklist.TaskListView;
import net.d80harri.wr.model.Task;

public class TaskPresenter implements Initializable {
	// @formatter:off
	@FXML private TextField ctlTaskTitle;
	@FXML private AnchorPane ctlSubtaskList;
	// @formatter:on

	private TaskListView viewTaskList;
	private TaskListPresenter presTaskList;

	// ====== Model properties ======
	private Task model;
	private StringProperty modelTitle;
	private ObservableList<Task> modelSubtasks;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.model = new Task();

		initializeModel();
		initializeViews();
		initializeControls();

		bindModelToControls();
		bindViewsToModel();
		bindModelPropertiesToModel();
	}

	private void initializeModel() {
		this.modelTitle = new SimpleStringProperty();
		this.modelSubtasks = FXCollections.observableArrayList();
	}

	private void initializeViews() {
		viewTaskList = new TaskListView();
		presTaskList = (TaskListPresenter) viewTaskList.getPresenter();
	}

	private void initializeControls() {
		ctlSubtaskList.getChildren().add(viewTaskList.getView());
	}

	private void bindModelToControls() {
		this.modelTitle.bindBidirectional(ctlTaskTitle.textProperty());
	}

	private void bindViewsToModel() {
		presTaskList.bind(modelSubtasks);
	}

	private void bindModelPropertiesToModel() {
		modelTitle.addListener((obs, ov, nv) -> model.setTitle(nv));
	}

	public Task getTask() {
		return model;
	}

	public void setTask(Task task) {
		this.model = task;
		this.modelTitle.set(task.getTitle());
		this.modelSubtasks.setAll(task.getTask());
	}

	@FXML
	private void debug(ActionEvent evt) {
		DebugBus.getInstance().fireDebugEvent(
				new DebugEvent(model.hashCode() + " " + model.getTitle()));
		for (Task t : model.getTask()) {
			DebugBus.getInstance().fireDebugEvent(
					new DebugEvent(" " + t.hashCode() + " " + t.getTitle()));
		}
	}

	@FXML
	private void addChild(ActionEvent evt) {
		Task newTask = new Task();
		DebugBus.getInstance().fireDebugEvent(
				new DebugEvent("Adding task " + newTask.hashCode() + " to "
						+ model.hashCode()));
		model.getTask().add(newTask);
		modelSubtasks.add(newTask);
	}

}
