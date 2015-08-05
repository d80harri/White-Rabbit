package net.d80harri.wr.fx.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DragboardReferences {

	private Map<String, Object> values = new HashMap<String, Object>();

	private DragboardReferences() {
	}

	public String register(Object value) {
		String key = UUID.randomUUID().toString();
		values.put(key, value);
		return key;
	}
	
	public void unregister(String key) {
		values.remove(key);
	}
	
	public Object get(String key) {
		return values.get(key);
	}

	public static final DragboardReferences INSTANCE = new DragboardReferences();
}
