package net.d80harri.wr.fx.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DebugBus {
	private List<Consumer<DebugEvent>> consumers = new ArrayList<>();
	
	private DebugBus() {}
	
	public void fireDebugEvent(DebugEvent evt) {
		consumers.forEach(i -> i.accept(evt));
	}
	
	public void register(Consumer<DebugEvent> listener) {
		this.consumers.add(listener);
	}
	
	private static DebugBus INSTANCE = new DebugBus();
	public static DebugBus getInstance() {
		return INSTANCE;
	}
}
