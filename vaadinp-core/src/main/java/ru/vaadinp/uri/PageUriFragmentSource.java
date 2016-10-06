package ru.vaadinp.uri;

import com.vaadin.server.Page;

/**
 * @author bellski
 *
 * Реализация BaseUriFragmentSource. В качестве источника uri fragment использует {@link Page}
 */
public class PageUriFragmentSource extends BaseUriFragmentSource implements Page.UriFragmentChangedListener {
	private final Page page;

	public PageUriFragmentSource(Page page) {
		this.page = page;
	}

	@Override
	public void setCurrentUriFragment(String currentUriFragment, boolean fireEvent) {
		page.setUriFragment(currentUriFragment, fireEvent);
		super.setCurrentUriFragment(currentUriFragment, fireEvent);
	}


	@Override
	public void uriFragmentChanged(Page.UriFragmentChangedEvent event) {
		super.fireUriFragmentChange(event.getUriFragment());
	}
}
