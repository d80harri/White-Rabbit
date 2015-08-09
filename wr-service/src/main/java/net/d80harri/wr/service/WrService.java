package net.d80harri.wr.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import net.d80harri.wr.model.Task;
import net.d80harri.wr.model.WRFile;

public class WrService {
	private Optional<WRFile> data = Optional.of(new WRFile());
	private Long maxId = null;

	public Optional<WRFile> getData() {
		return data;
	}
	
	public void load(File file) throws JAXBException {
		if (file.exists()) {
			JAXBContext context = JAXBContext.newInstance(WRFile.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			data = Optional.of(unmarshaller.unmarshal(new StreamSource(file),
					WRFile.class).getValue());
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void store(File file) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(WRFile.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		if (!file.exists()) {
			file.createNewFile();
		}
		marshaller.marshal(new JAXBElement<>(new QName("WhiteRabbit"), WRFile.class, data.get()), file);
	}

	public void moveTaskBefore(long toMoveId, long targetId) {
		Task toMove = this.selectTask(toMoveId).get();
		Task target = this.selectTask(targetId).get();
		Task parentOfToMove = this.selectParentTaskOf(toMove.getId()).get();
		Task parentOfTarget = this.selectParentTaskOf(target.getId()).get();
		
		int idxOfTarget = target.getTask().indexOf(target);
		
		parentOfToMove.getTask().remove(toMove);
		parentOfTarget.getTask().add(idxOfTarget, toMove);
	}

	public void moveTaskAfter(long toMoveId, long targetId) {
		Task toMove = this.selectTask(toMoveId).get();
		Task target = this.selectTask(targetId).get();
		Task parentOfToMove = this.selectParentTaskOf(toMove.getId()).get();
		Task parentOfTarget = this.selectParentTaskOf(target.getId()).get();
		
		int idxOfTarget = target.getTask().indexOf(target);
		
		parentOfToMove.getTask().remove(toMove);
		parentOfTarget.getTask().add(idxOfTarget+1, toMove);
	}

	public void moveTaskToChildren(long toMoveId, long targetId) {
		Task toMove = this.selectTask(toMoveId).get();
		Task target = this.selectTask(targetId).get();
		Task parentOfToMove = this.selectParentTaskOf(toMove.getId()).get();
		
		parentOfToMove.getTask().remove(toMove);
		target.getTask().add(toMove);
	}

	private Optional<Task> selectParentTaskOf(long id) {
		return Optional.ofNullable(selectParentTaskOf(this.data.get().getTask(), id));
	}

	public Optional<Task> selectTask(long id) {
		return Optional.ofNullable(selectTask(this.data.get().getTask(), id));
	}
	
	private Task selectParentTaskOf(Task node, long id) {
		if(node.getTask().stream().filter(i -> i.getId() == id).count() >= 1) {
			return node;
		} else {
			for (Task t : node.getTask()) {
				Task subResult = selectParentTaskOf(t, id);
				if (subResult != null)
					return  subResult;
			}
		}
		return null;
	}
	
	private Task selectTask(Task node, long id) {
		if (node.getId() == id) {
			return node;
		} else {
			for (Task t : node.getTask()) {
				Task subResult = selectTask(t, id);
				if (subResult != null) {
					return  subResult;
				}
			}
		}
		return null;
	}

	public Task insert(Task newTask) {
		newTask.setId(getMaxId());
		return newTask;
	}
	
	private long getMaxId() {
		if (this.data.get().getTask() == null) return 0;
		return getMaxId(this.data.get().getTask(), 0);
	}
	
	private long getMaxId(Task task, long currentMax) {
		long result = Math.max(task.getId(), currentMax);
		for (Task sub : task.getTask()) {
			result = getMaxId(sub, currentMax);
		}
		return result;
	}

}
