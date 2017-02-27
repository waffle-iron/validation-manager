/*
 * This class builds the demo data.
 */
package com.validation.manager.core;

import com.validation.manager.core.db.Project;
import com.validation.manager.core.db.controller.ProjectJpaController;
import com.validation.manager.core.db.controller.exceptions.NonexistentEntityException;
import com.validation.manager.core.server.core.ProjectServer;
import com.validation.manager.core.server.core.RequirementServer;
import com.validation.manager.core.server.core.RequirementSpecNodeServer;
import com.validation.manager.core.server.core.RequirementSpecServer;
import com.validation.manager.core.server.core.TestPlanServer;
import com.validation.manager.core.server.core.TestProjectServer;
import com.validation.manager.core.server.core.TestServer;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author Javier Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
public class DemoBuilder {

    private static final Logger LOG
            = Logger.getLogger(DemoBuilder.class.getSimpleName());

    public static void buildDemoProject() throws Exception {
        LOG.info("Creating demo projects...");
        ProjectJpaController controller
                = new ProjectJpaController(DataBaseManager
                        .getEntityManagerFactory());
        Project rootProject = new Project("Demo");
        controller.create(rootProject);
        for (int i = 0; i < 5; i++) {
            Project temp = new Project("Sub " + (i + 1));
            controller.create(temp);
            addDemoProjectRequirements(temp);
            addDemoProjectTestProject(temp);
            rootProject.getProjectList().add(temp);
        }
        addDemoProjectRequirements(rootProject);
        controller.edit(rootProject);
        LOG.info("Done!");
    }

    private static void addDemoProjectRequirements(Project p) throws Exception {
        for (int i = 0; i < 5; i++) {
            //Create a spec
            RequirementSpecServer temp = new RequirementSpecServer("Spec " + i,
                    "Description " + i, p.getId(), 1);
            temp.write2DB();
            RequirementSpecNodeServer node = temp.addSpecNode("Node " + i,
                    "Description " + i, "Scope " + i);
            for (int y = 0; y < 5; y++) {
                RequirementServer req
                        = new RequirementServer("Requirement " + y,
                                "Description " + y,
                                node.getRequirementSpecNodePK(), "Notes",
                                1, 1);
                req.write2DB();
                node.getRequirementList().add(req.getEntity());
            }
            node.write2DB();
            p.getRequirementSpecList().add(temp.getEntity());
        }
        new ProjectJpaController(DataBaseManager
                .getEntityManagerFactory()).edit(p);
    }

    private static void addDemoProjectTestProject(Project p) throws
            NonexistentEntityException, Exception {
        TestProjectServer tp = new TestProjectServer("Test Project", true);
        tp.setName("Test Project");
        tp.setNotes("Notes");
        tp.setActive(true);
        tp.write2DB();
        //Add the test structur
        TestPlanServer tps = new TestPlanServer(tp.getEntity(), true, true);
        tps.setName("Test Plan #1");
        tps.setNotes("Notes");
        tps.write2DB();
        for (int i = 0; i < 5; i++) {
            TestServer ts = new TestServer("Test #" + (i + 1),
                    "Purpose " + (i + 1),
                    "Scope " + (i + 1));
            ts.setNotes("Notes " + (i + 1));
            ts.write2DB();
            tps.addTest(ts.getEntity());
        }
        ProjectServer ps = new ProjectServer(p);
        ps.setTestProjectList(new ArrayList<>());
        ps.getTestProjectList().add(tp.getEntity());
        //Save it
        ps.write2DB();
    }
}