package ru.vaadinp.uri;

import com.vaadin.server.Page;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author bellski
 *
 * Реализация BaseUriFragmentSource. В качестве источника uri fragment использует {@link Page}
 */
@Singleton
public class PageUriFragmentSource extends BaseUriFragmentSource implements Page.UriFragmentChangedListener {
	private final Page page;

	@Inject
	public PageUriFragmentSource(Page page) {
		this.page = page;
		page.addUriFragmentChangedListener(this);
		setCurrentUriFragment(page.getUriFragment(), false);
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
