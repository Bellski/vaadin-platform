package ru.vaadinp.vp;

import ru.vaadinp.slot.TabSlot;

/**
 * Created by oem on 10/13/16.
 */
public interface TabContainer {
	void addTab(Tab tab);
	void setInTab(TabSlot tabSlot, IsTab isTab);
}
