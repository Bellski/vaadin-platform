package ru.vaadinp.vp;

import dagger.Lazy;
import ru.vaadinp.place.Place;
import ru.vaadinp.slot.NestedSlot;
import ru.vaadinp.slot.root.RootMVP;
import ru.vaadinp.vp.api.NestedMVP;
import ru.vaadinp.vp.api.PlaceMVP;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by oem on 10/12/16.
 */
public class NestedMVPImpl<P extends BaseNestedPresenter<?>> extends MVPImpl<P> implements NestedMVP<P>, PlaceMVP<P> {
	private final NestedMVP<? extends BaseNestedPresenter<?>> parent;
	private Set<NestedMVP<? extends BaseNestedPresenter<?>>> children;

	private final Place place;

	public NestedMVPImpl(Lazy<? extends ViewImpl<?>> lazyView,
						 Lazy<P> lazyPresenter,
						 RootMVP rootMVP,
						 NestedMVP<? extends BaseNestedPresenter<?>> parent) {

		this(null, lazyView, lazyPresenter, null, rootMVP, parent, null);
	}

	public NestedMVPImpl(Lazy<? extends ViewImpl<?>> lazyView,
						 Lazy<P> lazyPresenter,
						 MVPInfo info,
						 RootMVP rootMVP,
						 NestedMVP<? extends BaseNestedPresenter<?>> parent) {

		this(null, lazyView, lazyPresenter, info, rootMVP, parent, null);
	}

	public NestedMVPImpl(Lazy<Object> lazyModel,
						 Lazy<? extends ViewImpl<?>> lazyView,
						 Lazy<P> lazyPresenter,
						 RootMVP rootMVP,
						 NestedMVP<? extends BaseNestedPresenter<?>> parent,
						 Place place) {

		this(lazyModel, lazyView, lazyPresenter, null, rootMVP, parent, place);
	}

	public NestedMVPImpl(Lazy<Object> lazyModel,
						 Lazy<? extends ViewImpl<?>> lazyView,
						 Lazy<P> lazyPresenter,
						 MVPInfo info,
						 RootMVP rootMVP,
						 NestedMVP<? extends BaseNestedPresenter<?>> parent,
						 Place place) {

		super(lazyModel, lazyView, lazyPresenter, info, rootMVP);

		this.parent = parent;
		this.place = place;

		if (parent != null) {
			parent.addChild(this);
		}
	}

	@Override
	public void revealInSlot(NestedSlot nestedSlot, BaseNestedPresenter<?> child) {
		parent.getPresenter().forceReveal();
		parent.getPresenter().setInSlot(nestedSlot, child);
	}

	@Override
	public Place getPlace() {
		return place;
	}

	@Override
	public Set<NestedMVP<? extends BaseNestedPresenter<?>>> getChildren() {
		return children;
	}

	@Override
	public void addChild(NestedMVP<?> child) {
		if (children == null) {
			children = new HashSet<>();
		}

		children.add(child);
	}

}
