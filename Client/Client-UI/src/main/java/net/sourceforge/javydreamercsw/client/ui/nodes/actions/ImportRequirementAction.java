package net.sourceforge.javydreamercsw.client.ui.nodes.actions;

import com.validation.manager.core.DataBaseManager;
import com.validation.manager.core.db.RequirementSpecNode;
import com.validation.manager.core.db.controller.RequirementSpecNodeJpaController;
import com.validation.manager.core.tool.requirement.importer.RequirementImportException;
import com.validation.manager.core.tool.requirement.importer.RequirementImporter;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
public class ImportRequirementAction extends AbstractAction {

    public ImportRequirementAction() {
        super("Import Requirements",
                new ImageIcon("com/validation/manager/resources/icons/Signage/Add Square.png"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.getName().endsWith(".xls")
                                || file.getName().endsWith(".xlsx");
                    }

                    @Override
                    public String getDescription() {
                        return "Excel Files";
                    }
                });
                fc.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.getName().endsWith(".csv");
                    }

                    @Override
                    public String getDescription() {
                        return "Comma delimited Files";
                    }
                });
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fc.getSelectedFile();
                        RequirementSpecNode rsns = Lookup.getDefault().lookup(RequirementSpecNode.class);
                        RequirementImporter instance = new RequirementImporter(file,
                                new RequirementSpecNodeJpaController(
                                DataBaseManager.getEntityManagerFactory())
                                .findRequirementSpecNode(rsns.getRequirementSpecNodePK()));
                        instance.importFile();
                        instance.processRequirements();
                    } catch (UnsupportedOperationException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (RequirementImportException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        });
    }
}
