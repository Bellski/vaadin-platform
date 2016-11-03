package ru.vaadinp.slot;

import ru.vaadinp.vp.PresenterComponent;
import ru.vaadinp.vp.api.Presenter;

import java.util.List;
import java.util.Set;

public interface HasSlots {
    <T extends PresenterComponent<?> & Presenter> void addToSlot(MultiSlot<T> slot, T child);
    <T extends PresenterComponent<?> & Presenter> void removeFromSlot(RemovableSlot<T> slot, T child);
    <T extends PresenterComponent<?> & Presenter> void setInSlot(IsSlot<T> slot, T child);
    <T extends PresenterComponent<?> & Presenter> void setInSlot(IsSlot<T> slot, T child, boolean performReset);
    <T extends PresenterComponent<?> & Presenter> T getChild(IsSingleSlot<T> slot);
    <T extends PresenterComponent<?> & Presenter> Set<T> getChildren(Slot<T> slot);
    <T extends PresenterComponent<?> & Presenter & Comparable<T>> List<T> getOrderedChildren(OrderedSlot<T> slot);
    void clearSlot(RemovableSlot<?> slot);
}