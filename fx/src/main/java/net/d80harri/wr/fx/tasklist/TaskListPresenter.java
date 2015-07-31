package net.d80harri.wr.fx.tasklist;

import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.d80harri.wr.fx.task.TaskPresenter;
import net.d80harri.wr.fx.task.TaskView;
import net.d80harri.wr.fx.utils.MappedList;
import net.d80harri.wr.model.Task;

public class TaskListPresenter implements Initializable {
	@FXML
	private VBox items;
	
	private ObservableList<Task> taskList = FXCollections.observableArrayList();
	private ObservableList<Node> taskViewList = new MappedList<Node, Task>(taskList, this::convert);
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void bind(ObservableList<Task> tasks) {
		EasyBind.listBind(taskList, tasks);
		EasyBind.listBind(items.getChildren(), taskViewList);
	}
	
	private Node convert(Task task) {
		TaskView view = new TaskView();
		((TaskPresenter)view.getPresenter()).setTask(task);
		return view.getView();
	}
	
	@FXML
	private void debug() {
		items.getChildren().add(new Text("TEST"));
	}
}
