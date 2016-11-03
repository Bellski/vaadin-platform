package ru.vaadinp.slot;

import ru.vaadinp.vp.BaseNestedPresenter;
import ru.vaadinp.vp.api.NestedPresenter;

/**
 * Created by Aleksandr on 14.07.2016.
 */
public interface IsNested<P extends BaseNestedPresenter<?> & NestedPresenter> extends IsSingleSlot<P> {
}
