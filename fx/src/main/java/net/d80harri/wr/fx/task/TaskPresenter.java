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
import net.d80harri.wr.fx.tasklist.TaskListPresenter;
import net.d80harri.wr.fx.tasklist.TaskListView;
import net.d80harri.wr.model.Task;

public class TaskPresenter implements Initializable {

	private StringProperty title = new SimpleStringProperty();
	private ObservableList<Task> subTasks = FXCollections.observableArrayList();
	
	@FXML
	private TextField taskTitle;
	@FXML
	private AnchorPane subTaskList;
	
	private Task task = new Task();
	private TaskListView taskListView = new TaskListView();
	private TaskListPresenter taskListPresenter;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		taskListPresenter = (TaskListPresenter) taskListView.getPresenter();
		
		this.title.bind(taskTitle.textProperty());
		taskListPresenter.bind(subTasks);
		
		subTaskList.getChildren().add(taskListView.getView());
		installTaskTitleListener();
	}
	
	public Task getTask() {
		return task;
	}

	private void installTaskTitleListener() {
		title.addListener((obs, ov, nv) -> task.setTitle(nv));
	}
	
	@FXML
	private void debug(ActionEvent evt) {
		System.out.println(task.getTitle());
		for (Task t : task.getTask()) {
			System.out.println(" " + t.getTitle());
		}
	}
	
	@FXML
	private void addChild(ActionEvent evt) {
		Task newTask = new Task();
		task.getTask().add(newTask);
		subTasks.add(newTask);
	}

}
