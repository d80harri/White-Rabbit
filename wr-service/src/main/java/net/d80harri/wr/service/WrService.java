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

import net.d80harri.wr.model.WRFile;

public class WrService {
	private Optional<WRFile> data = Optional.empty();

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
}
