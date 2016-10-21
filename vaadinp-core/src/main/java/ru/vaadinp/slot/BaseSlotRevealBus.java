package ru.vaadinp.slot;

import ru.vaadinp.annotations.dagger.IntoSlotMap;
import ru.vaadinp.vp.NestedPresenter;
import ru.vaadinp.vp.NestedVPComponent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Singleton
public class BaseSlotRevealBus implements SlotRevealBus {
    private Map<NestedSlot, NestedVPComponent<?, ?>> providerMap = new HashMap<>();

    @Inject
    public BaseSlotRevealBus(@IntoSlotMap  Set<Map.Entry<NestedSlot, NestedVPComponent<?, ?>>> entries) {
        for (Map.Entry<NestedSlot, NestedVPComponent<?, ?>> entry : entries) {
            providerMap.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void registerSlot(NestedSlot nestedSlot, NestedVPComponent<?, ?> vpComponent) {
        providerMap.put(nestedSlot, vpComponent);
    }




    @Override
    public void fireReveal(NestedSlot slot, NestedPresenter<?> child) {
        final NestedVPComponent<?, ?> presenterProvider = providerMap.get(slot);

        Objects.requireNonNull(presenterProvider, "Presenter provider not found by slot");

        final NestedPresenter<?> presenter = (NestedPresenter<?>) presenterProvider.getPresenter();
        presenter.forceReveal();

        presenter.setInSlot(slot, child);
    }
}