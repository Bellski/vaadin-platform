package ru.vaadinp.vp;

import com.vaadin.server.Resource;

/**
 * Created by oem on 10/26/16.
 */
public class MVPInfo {
	private final String caption;
	private final String title;
	private String historyToken;
	private final Resource icon;
	private final int priority;

	public MVPInfo(String title) {
		this(null, title, null, null, -1);
	}

	public MVPInfo(String title, String historyToken) {
		this(null, title, historyToken, null, -1);
	}

	public MVPInfo(String title, String historyToken, int priority) {
		this(null, title, historyToken, priority);
	}

	public MVPInfo(String caption, String title, String historyToken) {
		this(caption, title, historyToken, null, -1);
	}

	public MVPInfo(String caption, String title, String historyToken, int priority) {
		this(caption, title, historyToken, null, priority);
	}

	public MVPInfo(String caption, String title, String historyToken, Resource icon, int priority) {
		this.caption = caption;
		this.title = title;
		this.historyToken = historyToken;
		this.icon = icon;
		this.priority = priority;
	}

	public String getCaption() {
		return caption;
	}

	public String getTitle() {
		return title;
	}

	public String getHistoryToken() {
		return historyToken;
	}

	public Resource getIcon() {
		return icon;
	}

	public int getPriority() {
		return priority;
	}
}
