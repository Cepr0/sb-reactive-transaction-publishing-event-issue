package io.github.cepr0.issue;

import lombok.Value;

@Value
public class ModelEvent {

	private final Model model;
	private Type type;

	public enum Type {
		NORMAL, WITH_EXCEPTION, WITH_TRANSACTIONAL, IN_TRANSACTIONAL_EVENT_LISTENER
	}

	public boolean normal() {
		return type == Type.NORMAL;
	}

	public boolean withException() {
		return type == Type.WITH_EXCEPTION;
	}

	public boolean withTransaction() {
		return type == Type.WITH_TRANSACTIONAL;
	}

	public boolean inTransactionEventListener() {
		return type == Type.IN_TRANSACTIONAL_EVENT_LISTENER;
	}
}
