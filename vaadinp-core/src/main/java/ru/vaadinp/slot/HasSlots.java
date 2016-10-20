package ru.vaadinp.slot;

import ru.vaadinp.vp.PresenterComponent;

import java.util.List;
import java.util.Set;

public interface HasSlots {
    <T extends PresenterComponent<?>> void addToSlot(MultiSlot<T> slot, T child);
    void clearSlot(RemovableSlot<?> slot);
    <T extends PresenterComponent<?>> void removeFromSlot(RemovableSlot<T> slot, T child);
    <T extends PresenterComponent<?>> void setInSlot(IsSlot<T> slot, T child);
    <T extends PresenterComponent<?>> void setInSlot(IsSlot<T> slot, T child, boolean performReset);
    <T extends PresenterComponent<?>> T getChild(IsSingleSlot<T> slot);
    <T extends PresenterComponent<?>> Set<T> getChildren(Slot<T> slot);
    <T extends PresenterComponent<?> & Comparable<T>> List<T> getOrderedChildren(OrderedSlot<T> slot);
}