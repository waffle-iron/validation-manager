package net.sourceforge.javydreamercsw.client.ui.components.reuirement.mapping.importer;

import com.validation.manager.core.db.Project;
import com.validation.manager.core.db.Requirement;
import com.validation.manager.core.server.core.ProjectServer;
import com.validation.manager.core.server.core.RequirementServer;
import com.validation.manager.core.tool.table.extractor.TableExtractor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileFilter;
import net.sourceforge.javydreamercsw.client.ui.components.AbstractImportTopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//net.sourceforge.javydreamercsw.client.ui.components.reuirement.mapping.importer//RequirementMappingImporter//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "RequirementMappingImporterTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window",
        id = "net.sourceforge.javydreamercsw.client.ui.components.reuirement.mapping.importer.RequirementMappingImporterTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_RequirementMappingImporterAction",
        preferredID = "RequirementMappingImporterTopComponent"
)
@Messages({
    "CTL_RequirementMappingImporterAction=RequirementMappingImporter",
    "CTL_RequirementMappingImporterTopComponent=RequirementMappingImporter Window",
    "HINT_RequirementMappingImporterTopComponent=This is a RequirementMappingImporter window"
})
public final class RequirementMappingImporterTopComponent extends AbstractImportTopComponent {

    private static final Logger LOG
            = Logger.getLogger(RequirementMappingImporterTopComponent.class.getSimpleName());
    private Project project = null;

    public RequirementMappingImporterTopComponent() {
        super();
        setName(Bundle.CTL_RequirementMappingImporterTopComponent());
        setToolTipText(Bundle.HINT_RequirementMappingImporterTopComponent());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        delimiter = new JComboBox(model);
        importButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        importedTable = new javax.swing.JTable();
        spinner = new javax.swing.JSpinner();
        saveButton = new javax.swing.JButton();
        header = new javax.swing.JCheckBox();
        addDelimiterButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        delimiterField = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(RequirementMappingImporterTopComponent.class, "RequirementMappingImporterTopComponent.jLabel1.text")); // NOI18N

        delimiter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ",", ";", " " }));

        org.openide.awt.Mnemonics.setLocalizedText(importButton, org.openide.util.NbBundle.getMessage(RequirementMappingImporterTopComponent.class, "RequirementMappingImporterTopComponent.importButton.text")); // NOI18N
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        importedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(importedTable);

        spinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        spinner.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(saveButton, org.openide.util.NbBundle.getMessage(RequirementMappingImporterTopComponent.class, "RequirementMappingImporterTopComponent.saveButton.text")); // NOI18N
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(header, org.openide.util.NbBundle.getMessage(RequirementMappingImporterTopComponent.class, "RequirementMappingImporterTopComponent.header.text")); // NOI18N
        header.setEnabled(false);
        header.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headerActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(addDelimiterButton, org.openide.util.NbBundle.getMessage(RequirementMappingImporterTopComponent.class, "RequirementMappingImporterTopComponent.addDelimiterButton.text")); // NOI18N
        addDelimiterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDelimiterButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(RequirementMappingImporterTopComponent.class, "RequirementMappingImporterTopComponent.jLabel2.text")); // NOI18N

        delimiterField.setText(org.openide.util.NbBundle.getMessage(RequirementMappingImporterTopComponent.class, "RequirementMappingImporterTopComponent.delimiterField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(2, 2, 2)
                        .addComponent(spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(header))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(importButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(addDelimiterButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(delimiterField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(saveButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(3, 3, 3)
                                    .addComponent(delimiter, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delimiterField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addDelimiterButton))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delimiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(importButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(header))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFileChooser fc = new JFileChooser();
                boolean valid = false;
                fc.setFileFilter(new FileFilter() {

                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory()
                                || (f.isFile()
                                && (f.getName().endsWith(".xls")
                                || f.getName().endsWith(".xlsx")
                                || f.getName().endsWith(".xlsm")));
                    }

                    @Override
                    public String getDescription() {
                        return "Validation Manager Requirement Mapping Import Files";
                    }
                });
                int returnVal = fc.showOpenDialog(new JFrame());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    TableExtractor te = new TableExtractor(file);
                    try {
                        tables.clear();
                        tables.addAll(te.extractTables());
                        if (tables.size() > 0) {
                            double max = Double.valueOf("" + tables.size());
                            spinner.setModel(new SpinnerNumberModel(1.0, 1.0,
                                    max, 1.0));
                            spinner.setValue(1.0);
                            LOG.log(Level.INFO, "Loaded {0} tables!",
                                    tables.size());
                            valid = true;
                            displayTable(1);
                        } else {
                            LOG.log(Level.INFO, "Found no tables!");
                        }
                    } catch (FileNotFoundException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (ClassNotFoundException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                enableUI(valid);
            }
        });
    }//GEN-LAST:event_importButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        LOG.info("Saving imported table...");
        setImportSuccess(true);
        project = Utilities.actionsGlobalContext().lookup(Project.class);
        //Process the mapping
        if (isImportSuccess()) {
            for (int row = 0; row < importedTable.getModel().getRowCount(); row++) {
                String val1 = (String) importedTable.getModel().getValueAt(row, 0);
                Requirement req1 = findRequirement(val1);
                String val2 = (String) importedTable.getModel().getValueAt(row, 1);
                Requirement req2 = findRequirement(val2);
                if (req1 != null && req2 != null) {
                    //Both are valid
                    req1.getRequirementList().add(req2);
                    try {
                        new RequirementServer(req1).write2DB();
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                        setImportSuccess(false);
                    }
                } else if (req1 == null) {
                    LOG.warning(MessageFormat.format(
                            "Unable to find requirement {0} in this project.", val1));
                } else {
                    LOG.warning(MessageFormat.format(
                            "Unable to find requirement {0} in this project.", val2));
                }
            }
        }
        if (isImportSuccess()) {
            this.close();
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void headerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headerActionPerformed
        handleHeaderActionPerformed();
    }//GEN-LAST:event_headerActionPerformed

    private void addDelimiterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDelimiterButtonActionPerformed
        handleAddDelimiterButtonActionPerformed();
    }//GEN-LAST:event_addDelimiterButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDelimiterButton;
    private javax.swing.JComboBox delimiter;
    private javax.swing.JTextField delimiterField;
    private javax.swing.JCheckBox header;
    private javax.swing.JButton importButton;
    private javax.swing.JTable importedTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton saveButton;
    private javax.swing.JSpinner spinner;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        enableUI(false);
        tables.clear();
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public DefaultCellEditor getEditor() {
        return null;
    }

    @Override
    public JTextField getDelimetterField() {
        return delimiterField;
    }

    @Override
    public JCheckBox getHeaderCheckbox() {
        return header;
    }

    @Override
    public JScrollPane getScrollPane() {
        return jScrollPane1;
    }

    @Override
    public void setImportTable(JTable table) {
        importedTable = table;
    }

    @Override
    public JTable getImportTable() {
        return importedTable;
    }

    @Override
    public JSpinner getSpinner() {
        return spinner;
    }

    @Override
    public JComboBox getDelimiter() {
        return delimiter;
    }

    @Override
    public void setModel(DefaultComboBoxModel model) {
        this.model = model;
    }

    @Override
    public DefaultComboBoxModel getModel() {
        return model;
    }

    @Override
    public JButton getSaveButton() {
        return saveButton;
    }

    private Requirement findRequirement(String id) {
        Requirement req = null;
        for (Requirement r : ProjectServer.getRequirements(project)) {
            if (r.getUniqueId().equals(id)) {
                req = r;
                break;
            }
        }
        return req;
    }

    @Override
    public void init() {
        initComponents();
    }
}
