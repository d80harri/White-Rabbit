package net.d80harri.wr.fx.main;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.d80harri.wr.fx.core.PresenterBase;
import net.d80harri.wr.fx.main.view.IMainView;
import net.d80harri.wr.fx.task.TaskPresenter;
import net.d80harri.wr.model.ObjectFactory;
import net.d80harri.wr.model.WRFile;

public class MainPresenter extends PresenterBase<IMainView<?>, WRFile, MainPresenter> {
	
	public void init(File file) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		setModel(((JAXBElement<WRFile>) jaxbUnmarshaller.unmarshal(file)).getValue());
	}
	
	protected void modelChanged(IMainView view) {
		view.setTask(new TaskPresenter(getModel().getTask()));
	}
}//C:\Users\Harald\workspace\wr2\model\src\test\resources
