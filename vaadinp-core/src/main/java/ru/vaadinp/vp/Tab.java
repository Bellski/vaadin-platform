package ru.vaadinp.vp;

import com.vaadin.server.Resource;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by oem on 10/13/16.
 */
public class Tab {
	private final String caption;
	private final Resource icon;

	private final Panel tabPanel = new Panel(); {
		tabPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
		tabPanel.setSizeFull();
	}

	public Tab(String caption) {
		this(caption, null);
	}

	public Tab(String caption, Resource icon) {
		this.caption = caption;
		this.icon = icon;
	}

	public String getCaption() {
		return caption;
	}

	public Resource getIcon() {
		return icon;
	}

	public Panel getTabPanel() {
		return tabPanel;
	}
}
