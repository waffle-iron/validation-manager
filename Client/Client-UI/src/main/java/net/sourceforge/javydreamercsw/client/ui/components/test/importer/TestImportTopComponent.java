package net.sourceforge.javydreamercsw.client.ui.components.test.importer;

import com.validation.manager.core.tool.msword.importer.TableExtractor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//net.sourceforge.javydreamercsw.client.ui.components//TestImport//EN",
        autostore = false
        )
@TopComponent.Description(
        preferredID = "TestImportTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
        )
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "net.sourceforge.javydreamercsw.client.ui.components.TestImportTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TestImportAction",
        preferredID = "TestImportTopComponent"
        )
@Messages({
    "CTL_TestImportAction=Test Import",
    "CTL_TestImportTopComponent=Test Import Window",
    "HINT_TestImportTopComponent=This is a Test Import window"
})
public final class TestImportTopComponent extends TopComponent {

    private final List<XWPFTable> tables = new ArrayList<XWPFTable>();
    private static final Logger LOG
            = Logger.getLogger(TestImportTopComponent.class.getSimpleName());

    public TestImportTopComponent() {
        initComponents();
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                LOG.log(Level.INFO, "Value changed to: {0}", spinner.getValue());
                displayTable((int) Math.round(Double.valueOf(
                        spinner.getValue().toString())));
            }
        });
        setName(Bundle.CTL_TestImportTopComponent());
        setToolTipText(Bundle.HINT_TestImportTopComponent());
        enableUI(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        spinner = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        importedTable = new javax.swing.JTable();
        importButton = new javax.swing.JButton();
        header = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TestImportTopComponent.class, "TestImportTopComponent.jLabel1.text")); // NOI18N

        spinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        spinner.setEnabled(false);

        importedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(importedTable);

        org.openide.awt.Mnemonics.setLocalizedText(importButton, org.openide.util.NbBundle.getMessage(TestImportTopComponent.class, "TestImportTopComponent.importButton.text")); // NOI18N
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(header, org.openide.util.NbBundle.getMessage(TestImportTopComponent.class, "TestImportTopComponent.header.text")); // NOI18N
        header.setEnabled(false);
        header.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(importButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(2, 2, 2)
                        .addComponent(spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(header)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(importButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(header))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFileChooser fc = new JFileChooser();
                boolean valid = false;
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
                            displayTable(1);
                            LOG.log(Level.INFO, "Loaded {0} tables!", tables.size());
                            valid = true;
                        } else {
                            LOG.log(Level.INFO, "Found no tables!");
                        }
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                enableUI(valid);
            }
        });
    }//GEN-LAST:event_importButtonActionPerformed

    private void headerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headerActionPerformed
        displayTable((int) Math.round(Double.valueOf(spinner.getValue().toString())));
    }//GEN-LAST:event_headerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox header;
    private javax.swing.JButton importButton;
    private javax.swing.JTable importedTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spinner;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
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

    private void displayTable(Integer index) {
        LOG.log(Level.FINE, "Changed value to: {0}", index);
        //Build the table
        assert index > 0 && index < tables.size() :
                "Invalid index: " + index + ". Must be between 1 and "
                + tables.size();
        XWPFTable table = tables.get(index - 1);
        int rows = table.getNumberOfRows();
        int columns = table.getRow(0).getTableCells().size();
        Object[][] data = new Object[columns][rows];
        String[] title = new String[columns];
        for (int i = 0; i < columns; i++) {
            title[i] = "Column " + (i + 1);
        }
        int rowNum = 0;
        int columnNum;
        for (XWPFTableRow row : table.getRows()) {
            columnNum = 0;
            for (XWPFTableCell cell : row.getTableCells()) {
                if (header.isSelected() && rowNum == 0) {
                    title[columnNum] = cell.getText();
                } else {
                    data[columnNum][rowNum] = cell.getText();
                }
            }
            rowNum++;
        }
        //Rebuild the table model to fit this table
        importedTable = new JTable();
        importedTable.setModel(new javax.swing.table.DefaultTableModel(
                data,
                title
                ) {
                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;
                    }
                });
        jScrollPane1.setViewportView(importedTable);
    }

    private void enableUI(boolean valid) {
        spinner.setEnabled(valid);
        header.setEnabled(valid);
        importedTable.setEnabled(valid);
    }
}
