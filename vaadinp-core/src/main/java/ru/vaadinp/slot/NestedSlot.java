package ru.vaadinp.slot;

import ru.vaadinp.vp.BaseNestedPresenter;

/**
 * Created by Aleksandr on 07.08.2016.
 */
public class NestedSlot implements IsNested<BaseNestedPresenter<?>> {
    @Override
    public boolean isPopup() {
        return false;
    }

    @Override
    public boolean isRemovable() {
        return true;
    }
}
