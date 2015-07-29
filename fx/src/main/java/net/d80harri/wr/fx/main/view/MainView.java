package net.d80harri.wr.fx.main.view;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import net.d80harri.wr.fx.core.ViewBase;
import net.d80harri.wr.fx.main.MainPresenter;
import net.d80harri.wr.fx.task.TaskPresenter;
import net.d80harri.wr.fx.task.view.TaskView;
import net.d80harri.wr.model.WRFile;

public class MainView extends ViewBase<MainPresenter, WRFile, MainView> {
	private FileChooser fileChooser = new FileChooser();

	@FXML
	private TaskView taskView;
	
	public MainView() {
		this.setPresenter(new MainPresenter());
	}
	
	public void openFile(ActionEvent event) throws Throwable {
        File file = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (file != null) {
        	getPresenter().init(file);
        }
    }

	public void setTask(TaskPresenter taskPresenter) {
		taskView.setPresenter(taskPresenter);
	}
}
