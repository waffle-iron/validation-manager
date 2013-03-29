package com.validation.manager.core.server.core;

import com.validation.manager.core.DataBaseManager;
import com.validation.manager.core.EntityServer;
import com.validation.manager.core.db.Requirement;
import com.validation.manager.core.db.RequirementSpec;
import com.validation.manager.core.db.RequirementSpecNode;
import com.validation.manager.core.db.controller.ProjectJpaController;
import com.validation.manager.core.db.controller.RequirementSpecJpaController;
import com.validation.manager.core.db.controller.RequirementSpecNodeJpaController;
import com.validation.manager.core.db.controller.SpecLevelJpaController;
import com.validation.manager.core.db.controller.exceptions.IllegalOrphanException;
import com.validation.manager.core.db.controller.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
public class RequirementSpecServer extends RequirementSpec implements EntityServer {

    public RequirementSpecServer(String name, String description, int projectId, int specLevelId) {
        super(projectId, specLevelId);
        setProject(new ProjectJpaController(DataBaseManager.getEntityManagerFactory()).findProject(projectId));
        setSpecLevel(new SpecLevelJpaController(DataBaseManager.getEntityManagerFactory()).findSpecLevel(specLevelId));
        setDescription(description);
        setName(name);
        setVersion(1);
        setModificationDate(new Date());
    }

    /**
     * Add a node to this spec.
     *
     * This is equivalent to a section in a document.
     *
     * Although database might allow a requirement being on different nodes,
     * this constraint is imposed via software.
     *
     * @param name name of new node
     * @param description description of node
     * @param scope scope of node
     * @param requirements List of requirements for this node
     * @throws Exception if errors creating the node occur 
     */
    public void addSpecNode(String name, String description, String scope, 
            List<Requirement> requirements) throws Exception {
        RequirementSpecNodeServer sns = new RequirementSpecNodeServer(
                new RequirementSpecJpaController(
                DataBaseManager.getEntityManagerFactory())
                .findRequirementSpec(getRequirementSpecPK()), 
                name, description, scope);
        sns.write2DB();
        for (Iterator<Requirement> it = requirements.iterator(); 
                it.hasNext();) {
            Requirement req = it.next();
            req.setRequirementSpecNode(sns);
        }
        getRequirementSpecNodeList().add(
                new RequirementSpecNodeJpaController(
                DataBaseManager.getEntityManagerFactory())
                .findRequirementSpecNode(sns.getRequirementSpecNodePK()));
        write2DB();
    }

    @Override
    public int write2DB() throws Exception {
        if (getRequirementSpecNodeList() == null) {
            setRequirementSpecNodeList(new ArrayList<RequirementSpecNode>());
        }
        if (getRequirementSpecPK() != null 
                && getRequirementSpecPK().getId() > 0) {
            RequirementSpec rs = new RequirementSpecJpaController(
                    DataBaseManager.getEntityManagerFactory())
                    .findRequirementSpec(getRequirementSpecPK());
            rs.setDescription(getDescription());
            rs.setName(getName());
            rs.setModificationDate(getModificationDate());
            rs.setProject(getProject());
            rs.setRequirementSpecNodeList(getRequirementSpecNodeList());
            rs.setSpecLevel(getSpecLevel());
            rs.setVersion(getVersion());
            new RequirementSpecJpaController(
                    DataBaseManager.getEntityManagerFactory()).edit(rs);
        } else {
            RequirementSpec rs = new RequirementSpec();
            rs.setDescription(getDescription());
            rs.setName(getName());
            rs.setModificationDate(getModificationDate());
            rs.setProject(getProject());
            rs.setRequirementSpecNodeList(getRequirementSpecNodeList());
            rs.setSpecLevel(getSpecLevel());
            rs.setVersion(getVersion());
            new RequirementSpecJpaController(
                    DataBaseManager.getEntityManagerFactory()).create(rs);
            setRequirementSpecPK(rs.getRequirementSpecPK());
        }
        return getRequirementSpecPK().getId();
    }

    public static void deleteRequirementSpec(RequirementSpec rs) 
            throws IllegalOrphanException, NonexistentEntityException {
        new RequirementSpecJpaController(
                DataBaseManager.getEntityManagerFactory())
                .destroy(rs.getRequirementSpecPK());
    }
}
