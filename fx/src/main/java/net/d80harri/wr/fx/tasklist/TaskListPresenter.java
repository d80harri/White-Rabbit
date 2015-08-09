package net.d80harri.wr.fx.tasklist;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.d80harri.wr.fx.task.TaskView;
import net.d80harri.wr.fx.utils.MappedList;
import net.d80harri.wr.model.Task;

public class TaskListPresenter implements Initializable {
	@FXML
	private VBox items;
	
	private ObservableList<TaskView> taskList = FXCollections.observableArrayList();
	private ObservableList<Node> mappedList = new MappedList<Node, TaskView>(taskList, i -> i.getView());
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		EasyBind.listBind(items.getChildren(), mappedList);
	}
	
	public List<TaskView> getTaskViews() {
		return taskList;
	}
	
	public void setAll(List<TaskView> tasks) {
		taskList.clear();
		taskList.addAll(tasks);
	}
	
	public void replaceTasks(List<Task> task) {
		setAll(task.stream().map(this::convertTask2TaskView).collect(Collectors.toList()));
	}
	
	public void add(TaskView task) {
		taskList.add(task);
	}

	public void add(Task newTask) {
		this.add(convertTask2TaskView(newTask));
	}
	
	private TaskView convertTask2TaskView(Task task) {
		TaskView view = new TaskView();
		view.getPresenter().setTask(task);
		return view;
	}
	
	@FXML
	private void debug() {
		items.getChildren().add(new Text("TEST"));
	}



}
