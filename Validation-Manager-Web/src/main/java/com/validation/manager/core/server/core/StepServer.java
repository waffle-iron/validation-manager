package com.validation.manager.core.server.core;

import com.validation.manager.core.DataBaseManager;
import com.validation.manager.core.EntityServer;
import com.validation.manager.core.db.Step;
import com.validation.manager.core.db.StepPK;
import com.validation.manager.core.db.TestCasePK;
import com.validation.manager.core.db.VmException;
import com.validation.manager.core.db.controller.StepJpaController;
import com.validation.manager.core.db.controller.TestCaseJpaController;
import com.validation.manager.core.db.controller.VmExceptionJpaController;
import com.validation.manager.core.db.controller.exceptions.NonexistentEntityException;

/**
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
public class StepServer extends Step implements EntityServer{

    public StepServer(int testCaseId, int testCaseTestId, int stepSequence, String text) {
        super(new StepPK(testCaseId, testCaseTestId), stepSequence, text);
        setTestCase(new TestCaseJpaController(DataBaseManager.getEntityManagerFactory()).findTestCase(new TestCasePK(testCaseId)));
    }

    @Override
    public int write2DB() throws NonexistentEntityException, Exception {
        StepJpaController controller = new StepJpaController(DataBaseManager.getEntityManagerFactory());
        if (getStepPK().getId() > 0) {
            Step temp = controller.findStep(getStepPK());
            temp.setNotes(getNotes());
            temp.setRequirementList(getRequirementList());
            temp.setStepSequence(getStepSequence());
            temp.setTestCase(getTestCase());
            temp.setText(getText());
            temp.setVmExceptionList(getVmExceptionList());
            controller.edit(temp);
        } else {
            Step temp = new Step(getStepPK(), getStepSequence(), getText());
            temp.setNotes(getNotes());
            temp.setRequirementList(getRequirementList());
            temp.setTestCase(getTestCase());
            temp.setVmExceptionList(getVmExceptionList());
            controller.create(temp);
            setStepPK(temp.getStepPK());
        }
        return getStepPK().getId();
    }

    public static void deleteStep(Step s) throws NonexistentEntityException, Exception {
        for(VmException e:s.getVmExceptionList()){
            new VmExceptionJpaController(DataBaseManager.getEntityManagerFactory()).destroy(e.getVmExceptionPK());
        }
        s.getVmExceptionList().clear();
        new StepJpaController(DataBaseManager.getEntityManagerFactory()).edit(s);
        new StepJpaController(DataBaseManager.getEntityManagerFactory()).destroy(s.getStepPK());
    }
}
