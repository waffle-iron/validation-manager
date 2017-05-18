package net.sourceforge.javydreamercsw.validation.manager.web.wizard.plan;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.ValoTheme;
import com.validation.manager.core.DataBaseManager;
import com.validation.manager.core.db.TestCaseExecution;
import com.validation.manager.core.db.controller.TestCaseExecutionJpaController;
import com.validation.manager.core.db.controller.exceptions.NonexistentEntityException;
import com.validation.manager.core.server.core.TestCaseExecutionServer;
import com.validation.manager.core.server.core.TestCaseServer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.javydreamercsw.validation.manager.web.ByteToStringConverter;
import net.sourceforge.javydreamercsw.validation.manager.web.ValidationManagerUI;
import org.vaadin.teemu.wizards.WizardStep;

/**
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
public class DetailStep implements WizardStep {

    private List<Integer> testCases;
    private final TestCaseExecution tce;
    private static final Logger LOG
            = Logger.getLogger(DetailStep.class.getSimpleName());

    public DetailStep() {
        this.tce = new TestCaseExecution();
    }

    @Override
    public String getCaption() {
        return ValidationManagerUI.RB.getString("add.details");
    }

    @Override
    public Component getContent() {
        Panel form = new Panel(ValidationManagerUI.RB.getString("execution.detail"));
        FormLayout layout = new FormLayout();
        form.setContent(layout);
        form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        BeanFieldGroup binder = new BeanFieldGroup(TestCaseExecution.class);
        binder.setItemDataSource(tce);
        TextArea name = new TextArea(ValidationManagerUI.RB.
                getString("general.name"));
        name.setConverter(new ByteToStringConverter());
        binder.bind(name, "name");
        layout.addComponent(name);
        TextArea scope = new TextArea(ValidationManagerUI.RB.
                getString("general.scope"));
        scope.setConverter(new ByteToStringConverter());
        binder.bind(scope, "scope");
        layout.addComponent(scope);
        if (tce.getId() != null) {
            TextArea conclusion = new TextArea(ValidationManagerUI.RB.
                    getString("general.comclusion"));
            conclusion.setConverter(new ByteToStringConverter());
            binder.bind(conclusion, "conclusion");
            layout.addComponent(conclusion);
            conclusion.setSizeFull();
            layout.addComponent(conclusion);
        }
        binder.setBuffered(false);
        binder.bindMemberFields(form);
        form.setSizeFull();
        return form;
    }

    @Override
    public boolean onAdvance() {
        try {
            //Create the record
            new TestCaseExecutionJpaController(DataBaseManager
                    .getEntityManagerFactory()).create(tce);
            TestCaseExecutionServer tces = new TestCaseExecutionServer(tce);
            //Now create the execution records
            TestCaseServer tc;
            for (Integer id : testCases) {
                //Retrieve the TestCase to get the steps
                tc = new TestCaseServer(id);
                tces.addTestCase(tc.getEntity());
            }
            try {
                tces.write2DB();
                ValidationManagerUI ui = ValidationManagerUI.getInstance();
                ui.buildProjectTree(ui.getSelectdValue());
                ui.updateProjectList();
                ui.updateScreen();
                ui.displayObject(tces.getEntity(), false);
                return true;
            } catch (NonexistentEntityException ex) {
                LOG.log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean onBack() {
        return true;
    }

    /**
     * @return the testCases
     */
    public List<Integer> getTestCases() {
        return testCases;
    }

    /**
     * @param testCases the testCases to set
     */
    public void setTestCases(List<Integer> testCases) {
        this.testCases = testCases;
    }
}
