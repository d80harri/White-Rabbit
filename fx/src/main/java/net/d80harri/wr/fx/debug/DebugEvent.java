package net.d80harri.wr.fx.debug;

import java.util.Date;

public class DebugEvent {
	private final Date date;
	private final String message;
	
	public DebugEvent(String message) {
		this.date = new Date();
		this.message = message;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "DebugEvent [date=" + date + ", message=" + message + "]";
	}
	
	
}
