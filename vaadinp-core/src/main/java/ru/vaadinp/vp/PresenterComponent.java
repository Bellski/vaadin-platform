package ru.vaadinp.vp;

import com.vaadin.ui.Component;
import ru.vaadinp.slot.*;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.api.MVP;
import ru.vaadinp.vp.api.Presenter;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by oem on 10/7/16.
 */
public class PresenterComponent<VIEW extends View> implements Presenter, HasSlots, HasPopupsSlot {
	private PresenterComponent<?> parent;

	private IsSlot<?> inSlot;

	private final VIEW view;

	private boolean visible;

	private final Set<PresenterComponent<?>> children = new HashSet<>(3);

	private final PopupSlot<PresenterComponent<? extends PopupView>> POPUP_SLOT = new PopupSlot<>();

	private MVP<? extends PresenterComponent<?>> mvp;

	public PresenterComponent(VIEW view) {
		this.view = view;
		view.bindPresenter(this);
	}


	public PresenterComponent<?> getParent() {
		return parent;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}


	<T extends PresenterComponent<?> & Presenter> void setParent(T parent) {
		this.parent = parent;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public IsSlot<?> getInSlot() {
		return inSlot;
	}

	public VIEW getView() {
		return view;
	}

	@Override
	public <T extends PresenterComponent<?> & Presenter> void addToSlot(MultiSlot<T> slot, T child) {
		if (child == null) {
			throw new NullPointerException("Child cannot be null");
		}

		if (child.getInSlot() != slot && child.getParent() != this) {
			bindChild(slot, child);
			if (!child.isPopup()) {
				getView().addToSlot(slot, child);
			}
			if (isVisible()) {
				child.internalReveal();
			}
		}
	}

	@Override
	public boolean isPopup() {
		return inSlot != null && inSlot.isPopup();
	}

	private <T extends PresenterComponent<?> & Presenter> void bindChild(IsSlot<T> slot, T child) {
		if (child.getParent() != this) {
			if (child.getParent() != null) {
				if (!child.getInSlot().isRemovable()) {
					throw new IllegalArgumentException("Cannot move a child of a permanent slot to another slot");
				}
				child.getParent().getChildren().remove(child);
			}
			child.setParent(this);
			children.add(child);
		}
		child.bindToSlot(slot);
	}

	void unBindChild() {
		if (inSlot != null) {
			if (!inSlot.isRemovable()) {
				throw new IllegalArgumentException("Cannot remove a child from a permanent slot");
			}
			if (parent != null) {
				internalHide();

				parent.children.remove(this);
				parent = null;
			}
			inSlot = null;
		}
	}

	<T extends PresenterComponent<?> & Presenter> void bindToSlot(IsSlot<T> slot) {
		this.inSlot = slot;
	}

	public void internalReset() {
		if (!isVisible()) {
			return;
		}
		onReset();
		// use new set to prevent concurrent modification
		for (PresenterComponent<?> child: new HashSet<PresenterComponent<?>>(getChildren())) {
			child.internalReset();
		}
	}

	protected void onReset() {

	}

	void internalReveal() {
		if (isVisible()) {
			return;
		}

		for (PresenterComponent<?> child : children) {
			child.internalReveal();
		}

		onReveal();
		visible = true;
	}

	void internalHide() {
		if (!isVisible()) {
			return;
		}

		for (PresenterComponent<?> child : children) {
			child.internalHide();
		}

		onHide();
		visible = false;
	}

	protected void onReveal() {
	}

	protected void onHide() {
	}

	@Override
	public void clearSlot(RemovableSlot<?> slot) {
		internalClearSlot(slot, null);
	}

	private void internalClearSlot(IsSlot<?> slot, PresenterComponent<?> dontRemove) {
		new HashSet<>(children)
			.stream()
			.filter(child -> child.getInSlot() == slot && !child.equals(dontRemove))
			.forEach(PresenterComponent::unBindChild);
	}


	public <T extends PresenterComponent<?> & Presenter> void removeFromSlot(RemovableSlot<T> slot, T child) {
		if (child == null || child.getInSlot() != slot) {
			return;
		}

		if (!child.isPopup()) {
			getView().removeFromSlot(slot, child);
		}

		child.unBindChild();
	}


	public <T extends PresenterComponent<?> & Presenter> void setInSlot(IsSlot<T> slot, T child) {
		setInSlot(slot, child, true);
	}


	public <T extends PresenterComponent<?> & Presenter> void setInSlot(IsSlot<T> slot, T child, boolean performReset) {
		if (child != null) {
			bindChild(slot, child);

			internalClearSlot(slot, child);

			if (!child.isPopup()) {
				getView().setInSlot(slot, child);
			}

			if (isVisible()) {
				child.internalReveal();
				if (performReset) {
					final RootMVP rootPresenter = getMVP().getRootMVP();

					if (rootPresenter != null) {
						getMVP().getRootMVP().getPresenter().reset();
					}
				}
			}
		} else {
			clearSlot((RemovableSlot<?>) slot);
		}
	}



	public <T extends PresenterComponent<?> & Presenter> T getChild(IsSingleSlot<T> slot) {
		Iterator<T> it = getSlotChildren(slot).iterator();
		return it.hasNext() ? it.next() : null;
	}

	public <T extends PresenterComponent<?> & Presenter> Set<T> getChildren(Slot<T> slot) {
		return getSlotChildren(slot);
	}

	@Override
	public <T extends PresenterComponent<?> & Presenter & Comparable<T>> List<T> getOrderedChildren(OrderedSlot<T> slot) {
		List<T> result = new ArrayList<>(getSlotChildren(slot));
		Collections.sort(result);
		return result;
	}

	private <T extends PresenterComponent<?> & Presenter> Set<T> getSlotChildren(IsSlot<T> slot) {
		return children
			.stream()
			.filter(child -> child.getInSlot() == slot)
			.map(child -> (T) child)
			.collect(Collectors.toSet());
	}


	public Set<PresenterComponent<?>> getChildren() {
		return children;
	}

	@Override
	public void addToPopupSlot(PresenterComponent<? extends PopupView> child) {
		addToSlot(POPUP_SLOT, child);
	}

	@Override
	public void removeFromPopupSlot(PresenterComponent<? extends PopupView> child) {
		removeFromSlot(POPUP_SLOT,child);
	}

	@Override
	public Component asComponent() {
		return getView().asComponent();
	}

	public MVP<? extends PresenterComponent<?>> getMVP() {
		return mvp;
	}

	@Inject
	public void injectMVP(MVP<? extends PresenterComponent<?>> mvp) {
		this.mvp = mvp;
		init();
	}

	protected void init() {

	}
}
