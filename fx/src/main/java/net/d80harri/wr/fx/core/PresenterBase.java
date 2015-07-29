package net.d80harri.wr.fx.core;

import java.util.ArrayList;
import java.util.List;

public abstract class PresenterBase<V extends IView<ME, M, ?>, M, ME extends PresenterBase<V, M, ME>> {
	private List<V> views = new ArrayList<>();
	private M model;

	public PresenterBase() {
		this.model = null;
	}
	
	public PresenterBase(M model) {
		this.model = model;
	}

	public M getModel() {
		return model;
	}
	
	public void setModel(M model) {
		this.model = model;
		views.stream().forEach(this::modelChanged);
	}
	
	protected abstract void modelChanged(V view) ;
}
