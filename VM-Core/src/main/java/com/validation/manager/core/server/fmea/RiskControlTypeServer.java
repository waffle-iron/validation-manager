package com.validation.manager.core.server.fmea;

import static com.validation.manager.core.DataBaseManager.getEntityManagerFactory;
import com.validation.manager.core.EntityServer;
import com.validation.manager.core.db.RiskControlType;
import com.validation.manager.core.db.controller.RiskControlTypeJpaController;
import com.validation.manager.core.db.controller.exceptions.IllegalOrphanException;
import com.validation.manager.core.db.controller.exceptions.NonexistentEntityException;

/**
 *
 * @author Javier A. Ortiz Bultron javier.ortiz.78@gmail.com
 */
public class RiskControlTypeServer extends RiskControlType
        implements EntityServer<RiskControlType> {

    public RiskControlTypeServer(String name, String description) {
        super(name, description);
        setId(0);
    }

    @Override
    public int write2DB() throws IllegalOrphanException,
            NonexistentEntityException, Exception {
        if (getId() > 0) {
            RiskControlType rct = getEntity();
            update(rct, this);
            new RiskControlTypeJpaController(
                    getEntityManagerFactory()).edit(rct);
        } else {
            RiskControlType rct = new RiskControlType();
            update(rct, this);
            new RiskControlTypeJpaController(
                    getEntityManagerFactory()).create(rct);
            setId(rct.getId());
        }
        return getId();
    }

    public static boolean deleteRiskControlType(RiskControlType rct)
            throws IllegalOrphanException, NonexistentEntityException {
        new RiskControlTypeJpaController(
                getEntityManagerFactory()).destroy(rct.getId());
        return true;
    }

    @Override
    public RiskControlType getEntity() {
        return new RiskControlTypeJpaController(
                getEntityManagerFactory())
                .findRiskControlType(getId());
    }

    @Override
    public void update(RiskControlType target, RiskControlType source) {
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        if (source.getRiskControlList() != null) {
            target.setRiskControlList(source.getRiskControlList());
        }
    }
    
    @Override
    public void update() {
        update(this, getEntity());
    }
}
