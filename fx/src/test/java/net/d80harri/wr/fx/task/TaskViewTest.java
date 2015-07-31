package net.d80harri.wr.fx.task;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class TaskViewTest extends ApplicationTest {
	
	
	private TaskView view;
	private TaskPresenter presenter;

	@Override
	public void start(Stage stage) throws Exception {
		view = new TaskView();
		presenter = (TaskPresenter) view.getPresenter();
		
		Scene scene = new Scene(view.getView(), 1000, 600);
        stage.setScene(scene);
        stage.show();
	}
	
	@Test
    public void writeToTextfieldUpdatesModel() {
		clickOn("#title");
		
		write("My new Task");
		Assertions.assertThat(presenter.getTask().getTitle()).isEqualTo("My new Task");
		
		push(KeyCode.CONTROL, KeyCode.A);
	    push(KeyCode.DELETE);
		write("My very new Task");
		Assertions.assertThat(presenter.getTask().getTitle()).isEqualTo("My very new Task");
    }
	
	@Test
	public void addingAChild() {
		clickOn("#itemMenu");
		clickOn("#addChild");
		Assertions.assertThat(presenter.getTask().getTask()).hasSize(1);
		
		clickOn("#itemMenu");
		clickOn("#addChild");
		Assertions.assertThat(presenter.getTask().getTask()).hasSize(2);
	}

}
