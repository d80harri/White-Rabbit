package net.d80harri.wr.fx.task;

import net.d80harri.wr.fx.core.IView;
import net.d80harri.wr.model.Task;

public interface ITaskView<ME extends ITaskView<ME>> extends IView<TaskPresenter, Task, ME>{

}
