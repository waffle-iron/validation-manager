package net.sourceforge.javydreamercsw.validation.manager.web;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.validation.manager.core.db.History;
import com.validation.manager.core.db.Requirement;
import org.vaadin.teemu.wizards.WizardStep;

/**
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
public class SelectRequirementVersionStep implements WizardStep {

    private History h;
    private final Requirement r;
    private final ComboBox history = new ComboBox(java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("general.history"));

    public SelectRequirementVersionStep(Requirement r) {
        this.r = r;
    }

    @Override
    public String getCaption() {
        return r.getUniqueId();
    }

    @Override
    public Component getContent() {
        BeanItemContainer<History> historyContainer
                = new BeanItemContainer<>(History.class, getRequirement().getHistoryList());
        history.setContainerDataSource(historyContainer);
        history.getItemIds().forEach(id -> {
            History temp = ((History) id);
            String version = temp.getMajorVersion() + "."
                    + temp.getMidVersion() + "." + temp.getMinorVersion();
            history.setItemCaption(id, version);
        });
        if (r.getHistoryList().size() == 1) {
            //Only one, pre-select it.
            history.setValue(r.getHistoryList().get(0));
        }
        return history;
    }

    @Override
    public boolean onAdvance() {
        if (history.getValue() != null) {
            h = (History) history.getValue();
            return true;
        }
        return false;
    }

    @Override
    public boolean onBack() {
        return true;
    }

    /**
     * @return the h
     */
    public History getHistory() {
        return h;
    }

    /**
     * @return the r
     */
    public Requirement getRequirement() {
        return r;
    }
}
