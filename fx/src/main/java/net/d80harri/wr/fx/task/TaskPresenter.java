package net.d80harri.wr.fx.task;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import net.d80harri.wr.fx.debug.DebugBus;
import net.d80harri.wr.fx.debug.DebugEvent;
import net.d80harri.wr.fx.tasklist.TaskListPresenter;
import net.d80harri.wr.fx.tasklist.TaskListView;
import net.d80harri.wr.fx.utils.DragboardReferences;
import net.d80harri.wr.model.Task;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

public class TaskPresenter implements Initializable {
	private static final Task DEFAULT_TASK = new Task();

	// @formatter:off
	@FXML private AnchorPane ctlContainer;
	@FXML private TextField ctlTaskTitle;
	@FXML private AnchorPane ctlSubtaskList;
	@FXML private Pane ctlPopupContent;
	@FXML private Button ctlMenuButton;
	// @formatter:on
	private PopOver ctlPopOver;

	private TaskListView viewTaskList;
	private TaskListPresenter presTaskList;

	// ====== Model properties ======
	private Optional<Task> model = Optional.empty();
	private StringProperty modelTitle;
	private ObservableList<Task> modelSubtasks;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.model = Optional.of(new Task());

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
		ctlPopOver = new PopOver(ctlPopupContent);
		ctlPopOver.setArrowLocation(ArrowLocation.TOP_LEFT);
		ctlSubtaskList.getChildren().add(viewTaskList.getView());
	}

	private void bindModelToControls() {
		this.modelTitle.bindBidirectional(ctlTaskTitle.textProperty());
	}

	private void bindViewsToModel() {
		presTaskList.bind(modelSubtasks);
	}

	private void bindModelPropertiesToModel() {
		modelTitle.addListener((obs, ov, nv) -> model.ifPresent(m -> m
				.setTitle(nv)));
	}

	public Task getTask() {
		return model.get();
	}

	public void setTask(Task task) {
		this.model = Optional.ofNullable(task);
		this.modelTitle.set(model.orElse(DEFAULT_TASK).getTitle());
		this.modelSubtasks.setAll(model.orElse(DEFAULT_TASK).getTask());
	}

	@FXML
	private void debug(ActionEvent evt) {
		model.ifPresent(m -> {
			DebugBus.getInstance().fireDebugEvent(
					new DebugEvent(model.hashCode() + " " + m.getTitle()));
			for (Task t : m.getTask()) {
				DebugBus.getInstance()
						.fireDebugEvent(
								new DebugEvent(" " + t.hashCode() + " "
										+ t.getTitle()));
			}
		});
	}

	@FXML
	private void addChild(ActionEvent evt) {
		Task newTask = new Task();
		DebugBus.getInstance().fireDebugEvent(
				new DebugEvent("Adding task " + newTask.hashCode() + " to "
						+ model.hashCode()));
		model.get().getTask().add(newTask);
		modelSubtasks.add(newTask);
	}

	@FXML
	private void showPopupMenu(ActionEvent evt) {
		this.ctlPopOver.show(this.ctlMenuButton);
	}

	@FXML
	private void taskDragDetected(MouseEvent evt) {
		/* drag was detected, start a drag-and-drop gesture */
		/* allow any transfer mode */
		Dragboard db = ctlContainer.startDragAndDrop(TransferMode.MOVE);

		/* Put a string on a dragboard */
		ClipboardContent content = new ClipboardContent();
		content.putString(DragboardReferences.INSTANCE.register(model.get()));
		db.setContent(content);

		evt.consume();
	}

	@FXML
	private void draggedOver(DragEvent evt) {
		/* data is dragged over the target */
		/*
		 * accept it only if it is not dragged from the same node and if it has
		 * a string data
		 */

		if (evt.getDragboard().hasString()) {
			Object content = DragboardReferences.INSTANCE.get(evt
					.getDragboard().getString());
			if (content instanceof Task) {
				/* allow for both copying and moving, whatever user chooses */
				evt.acceptTransferModes(TransferMode.MOVE);
			}
		}

		evt.consume();
	}

}
