package net.d80harri.wr.fx.task;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

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
		
		Scene scene = new Scene(view.getView(), 800, 600);
        stage.setScene(scene);
        stage.show();
	}
	
	@Test
    public void should_drag_file_into_trashcan() {
		clickOn("#title");
		
		write("My new Task");
		Assert.assertEquals("My new Task", presenter.getTask().getTitle());
		
		push(KeyCode.CONTROL, KeyCode.A);
	    push(KeyCode.DELETE);
		write("My very new Task");
		Assert.assertEquals("My very new Task", presenter.getTask().getTitle());
    }

}
