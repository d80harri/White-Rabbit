package net.d80harri.wr.fx.task;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import net.d80harri.wr.model.Task;

public class TaskPresenter implements Initializable {

	private StringProperty title = new SimpleStringProperty();
	
	@FXML
	private TextField taskTitle;
	
	private Task task = new Task();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.title.bind(taskTitle.textProperty());
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
	}

}
