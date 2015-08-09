package net.d80harri.wr.fx.task;

import com.airhacks.afterburner.views.FXMLView;

public class TaskView extends FXMLView  {

	public void setPresenter(TaskPresenter presenter) {
		presenterProperty.set(presenter);
	}
	
	public TaskPresenter getPresenter() {
		return (TaskPresenter) super.getPresenter();
	}
}
