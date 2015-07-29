package net.d80harri.wr.fx.main.view;

import net.d80harri.wr.fx.main.MainPresenter;
import net.d80harri.wr.fx.task.TaskPresenter;
import net.d80harri.wr.fx.core.IView;
import net.d80harri.wr.model.WRFile;

public interface IMainView<ME extends IMainView<ME>> extends IView<MainPresenter, WRFile, ME> {

	void setTask(TaskPresenter taskPresenter);

}
