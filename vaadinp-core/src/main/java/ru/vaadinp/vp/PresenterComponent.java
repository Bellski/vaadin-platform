package ru.vaadinp.vp;

import com.vaadin.ui.Component;
import ru.vaadinp.slot.*;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by oem on 10/7/16.
 */
public class PresenterComponent<VIEW extends View> implements HasSlots, HasPopupsSlot, Presenter {
	private PresenterComponent<?> parent;
	IsSlot<?> inSlot;

	private final VIEW view;

	private boolean visible;

	private final Set<PresenterComponent<?>> children = new HashSet<>(3);

	private final PopupSlot<PresenterComponent<? extends PopupView>> POPUP_SLOT = new PopupSlot<>();

	private VPComponent<? extends PresenterComponent<VIEW>, ?> vpComponent;

	public PresenterComponent(VIEW view) {
		this.view = view;
		view.bindPresenter(this);
	}

	public PresenterComponent<?> getParent() {
		return parent;
	}

	public boolean isVisible() {
		return visible;
	}

	protected void setVisible(boolean visible) {
		this.visible = visible;
	}

	public IsSlot<?> getInSlot() {
		return inSlot;
	}

	public VIEW getView() {
		return view;
	}

	@Override
	public <T extends PresenterComponent<?>> void addToSlot(MultiSlot<T> slot, T child) {
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

	boolean isPopup() {
		return inSlot != null && inSlot.isPopup();
	}

	private <T extends PresenterComponent<?>> void bindChild(IsSlot<T> slot, PresenterComponent<?> child) {
		if (child.parent != this) {
			if (child.parent != null) {
				if (!child.getInSlot().isRemovable()) {
					throw new IllegalArgumentException("Cannot move a child of a permanent slot to another slot");
				}
				child.parent.children.remove(child);
			}
			child.parent = this;
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

	private <T extends PresenterComponent<?>> void bindToSlot(IsSlot<T> slot) {
		this.inSlot = slot;
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

	@Override
	public <T extends PresenterComponent<?>> void removeFromSlot(RemovableSlot<T> slot, T child) {
		if (child == null || child.inSlot != slot) {
			return;
		}

		if (!child.isPopup()) {
			getView().removeFromSlot(slot, child);
		}

		child.unBindChild();
	}

	@Override
	public <T extends PresenterComponent<?>> void setInSlot(IsSlot<T> slot, T child) {
		setInSlot(slot, child, true);
	}

	@Override
	public <T extends PresenterComponent<?>> void setInSlot(IsSlot<T> slot, T child, boolean performReset) {
		if (child != null) {
			bindChild(slot, child);

			internalClearSlot(slot, child);

			if (!child.isPopup()) {
				getView().setInSlot(slot, child);
			}

			if (isVisible()) {
				child.internalReveal();
			}
		} else {
			clearSlot((RemovableSlot<?>) slot);
		}
	}

	@Override
	public <T extends PresenterComponent<?>> T getChild(IsSingleSlot<T> slot) {
		Iterator<T> it = getSlotChildren(slot).iterator();
		return it.hasNext() ? it.next() : null;
	}

	@Override
	public <T extends PresenterComponent<?>> Set<T> getChildren(Slot<T> slot) {
		return getSlotChildren(slot);
	}

	@Override
	public <T extends PresenterComponent<?> & Comparable<T>> List<T> getOrderedChildren(OrderedSlot<T> slot) {
		List<T> result = new ArrayList<>(getSlotChildren(slot));
		Collections.sort(result);
		return result;
	}

	private <T extends PresenterComponent<?>> Set<T> getSlotChildren(IsSlot<T> slot) {
		return children
			.stream()
			.filter(child -> child.getInSlot() == slot)
			.map(child -> (T) child)
			.collect(Collectors.toSet());
	}

	@Override
	public void clean() {

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

	public VPComponent<? extends PresenterComponent<VIEW>, ?> getVpComponent() {
		return vpComponent;
	}

	@Inject
	public void setVpComponent(VPComponent<? extends PresenterComponent<VIEW>, ?> vpComponent) {
		this.vpComponent = vpComponent;
	}
}
