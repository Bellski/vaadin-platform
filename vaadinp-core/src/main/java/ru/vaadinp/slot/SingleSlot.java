package ru.vaadinp.slot;

import ru.vaadinp.vp.PresenterComponent;

/**
 * Created by Aleksandr on 07.08.2016.
 */
public class SingleSlot<T extends PresenterComponent<?>> implements IsSingleSlot<T>, RemovableSlot<T> {

    @Override
    public boolean isPopup() {
        return false;
    }

    @Override
    public boolean isRemovable() {
        return true;
    }
}
