package net.d80harri.wr.fx.task;

import net.d80harri.wr.fx.core.PresenterBase;
import net.d80harri.wr.model.Task;

public class TaskPresenter extends PresenterBase<ITaskView<?>, Task, TaskPresenter> {

	public TaskPresenter(Task task) {
		super(task);
	}

	@Override
	protected void modelChanged(ITaskView view) {
		// TODO Auto-generated method stub
		
	}

}
