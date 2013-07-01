package com.validation.manager.core.server.core;

import com.validation.manager.core.DataBaseManager;
import com.validation.manager.core.EntityServer;
import com.validation.manager.core.db.controller.RiskControlJpaController;
import com.validation.manager.core.db.controller.RiskControlTypeJpaController;
import com.validation.manager.core.db.controller.exceptions.NonexistentEntityException;
import com.validation.manager.core.db.fmea.RiskControl;
import com.validation.manager.core.db.fmea.RiskControlPK;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
public class RiskControlServer extends RiskControl implements EntityServer<RiskControl> {

    public RiskControlServer(RiskControlPK riskControlPK) {
        super(riskControlPK);
        setRiskControlType(new RiskControlTypeJpaController(
                DataBaseManager.getEntityManagerFactory()).findRiskControlType(
                riskControlPK.getRiskControlTypeId()));
    }

    public RiskControlServer(int riskControlTypeId) {
        super(new RiskControlPK(riskControlTypeId));
        setRiskControlType(new RiskControlTypeJpaController(DataBaseManager.getEntityManagerFactory()).findRiskControlType(riskControlTypeId));
    }

    @Override
    public int write2DB() throws NonexistentEntityException, Exception {
        RiskControlJpaController controller = new RiskControlJpaController(DataBaseManager.getEntityManagerFactory());
        if (getRiskControlPK().getId() > 0) {
            RiskControl target = controller.findRiskControl(getRiskControlPK());
            update(target, this);
            controller.edit(target);
        } else {
            RiskControl target = new RiskControl(getRiskControlPK());
            update(target, this);
            controller.create(target);
            setRiskControlPK(target.getRiskControlPK());
        }
        return getRiskControlPK().getId();
    }

    public static boolean deleteRiskControl(RiskControl rc) {
        try {
            new RiskControlJpaController(
                    DataBaseManager.getEntityManagerFactory())
                    .destroy(rc.getRiskControlPK());
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RiskControlServer.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return false;
    }

    public RiskControl getEntity() {
        return new RiskControlJpaController(
                DataBaseManager.getEntityManagerFactory())
                .findRiskControl(getRiskControlPK());
    }

    public void update(RiskControl target, RiskControl source) {
        target.setRiskItemList(source.getRiskItemList());
        target.setRiskItemList1(source.getRiskItemList1());
        target.setTestCaseList(source.getTestCaseList());
        target.setRequirementList(source.getRequirementList());
        target.setRiskControlType(source.getRiskControlType());
    }
}
