package com.shadowninja108.main;

import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class OnDocumentChangedListener implements DocumentListener {

	private static boolean handledAction = false;

	private Consumer<DocumentEvent> action;

	public OnDocumentChangedListener(Consumer<DocumentEvent> action) {
		this.action = action;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		handle(e);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		handle(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		handle(e);
	}

	private void handle(DocumentEvent e) {
		// an action as a result of another action should be ignored
		if (!handledAction) {
			handledAction = true;
			action.accept(e);
			handledAction = false;
		}
	}

}
