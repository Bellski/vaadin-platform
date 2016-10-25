package ru.vaadinp;

import com.vaadin.server.Page;
import com.vaadin.ui.UI;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by Aleksandr on 23.10.2016.
 */
@Module
public class VaadinDeclarations {
    private UI ui;

    public VaadinDeclarations(UI ui) {
        this.ui = ui;
    }

    @Provides
    @Singleton
    UI ui() {
        return ui;
    }

    @Provides
    @Singleton
    static Page page(UI ui) {
        return ui.getPage();
    }
}
