package ru.vaadinp.slot;

import ru.vaadinp.vp.NestedPresenter;

/**
 * Created by Aleksandr on 07.08.2016.
 */
public class NestedSlot implements IsNested<NestedPresenter<?>> {
    @Override
    public boolean isPopup() {
        return false;
    }

    @Override
    public boolean isRemovable() {
        return true;
    }
}
