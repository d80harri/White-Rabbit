package net.d80harri.wr.fx;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import com.google.common.base.Charsets;

import net.d80harri.wr.fx.debug.DebugBus;
import net.d80harri.wr.fx.debug.DebugEvent;
import net.d80harri.wr.fx.debug.DebugPresenter;
import net.d80harri.wr.fx.debug.DebugView;
import net.d80harri.wr.fx.task.TaskPresenter;
import net.d80harri.wr.fx.task.TaskView;
import net.d80harri.wr.model.Task;
import net.d80harri.wr.model.WRFile;

public class AppPresenter implements Initializable {
	private static final WRFile DEFAULT_MODEL;

	static {
		DEFAULT_MODEL = new WRFile();
		DEFAULT_MODEL.setTask(new Task());
	}

	//@formatter:off
	@FXML private AnchorPane ctlTask;
	@FXML private AnchorPane ctlDebug;
	//@formatter:on

	private FileChooser fileChooser;

	private TaskView viewTask;
	private DebugView viewDebug;

	private TaskPresenter presTask;
	private DebugPresenter presDebug;

	private Optional<WRFile> model;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeModel();
		initializeViews();
		initializeControls();

		bindModelToControls();
		bindViewsToModel();
		bindModelPropertiesToModel();
		this.setModel(DEFAULT_MODEL);
	}

	public WRFile getModel() {
		return model.orElse(null);
	}

	public void setModel(WRFile model) {
		this.model = Optional.ofNullable(model);

		this.presTask.setTask(this.model.orElse(DEFAULT_MODEL).getTask());
	}

	private void initializeModel() {
	}

	private void initializeViews() {
		viewTask = new TaskView();
		viewDebug = new DebugView();

		presTask = (TaskPresenter) viewTask.getPresenter();
		presDebug = (DebugPresenter) viewDebug.getPresenter();
	}

	private void initializeControls() {
		this.fileChooser = new FileChooser();

		ctlTask.getChildren().add(viewTask.getView());
		ctlDebug.getChildren().add(viewDebug.getView());
	}

	private void bindModelToControls() {
	}

	private void bindViewsToModel() {
	}

	private void bindModelPropertiesToModel() {
	}

	@FXML
	private void saveFile(ActionEvent evt) throws JAXBException, IOException {
		File file = fileChooser.showSaveDialog(ctlTask.getScene().getWindow());
		if (file != null) { // TODO: Externalize -> ServiceMethod
			JAXBContext context = JAXBContext.newInstance(WRFile.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			if (!file.exists()) {
				file.createNewFile();
			}
			marshaller.marshal(new JAXBElement<>(new QName("WhiteRabbit"), WRFile.class, model.get()), file);
			
			DebugBus.getInstance().fireDebugEvent(new DebugEvent("Wrote to file -> "));
			DebugBus.getInstance().fireDebugEvent(new DebugEvent(new String(Files.readAllBytes(Paths.get(file.toURI())))));
		}
	}

	@FXML
	private void debug(ActionEvent evt) { // TODO: Externalize -> DebugUtils
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(bos);
		encoder.writeObject(model.orElse(null));
		encoder.close();
		DebugBus.getInstance().fireDebugEvent(new DebugEvent(new String(bos.toByteArray(), StandardCharsets.UTF_8)));
	}
}
