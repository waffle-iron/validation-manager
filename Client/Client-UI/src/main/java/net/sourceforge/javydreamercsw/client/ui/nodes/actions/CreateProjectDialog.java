package net.sourceforge.javydreamercsw.client.ui.nodes.actions;

import com.validation.manager.core.db.Project;
import com.validation.manager.core.db.controller.exceptions.IllegalOrphanException;
import com.validation.manager.core.db.controller.exceptions.NonexistentEntityException;
import com.validation.manager.core.server.core.ProjectServer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.sourceforge.javydreamercsw.client.ui.components.project.explorer.ProjectExplorerComponent;
import org.openide.util.Exceptions;

/**
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
public class CreateProjectDialog extends AbstractCreationDialog {

    /**
     * Creates new form CreateProjectDialog
     */
    public CreateProjectDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        populateProjectList(this.parent, true);
        setIconImage(new ImageIcon("com/validation/manager/resources/icons/VMSmall.png").getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        notes = new javax.swing.JTextArea();
        name = new javax.swing.JTextField();
        parent = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CreateProjectDialog.class, "CreateProjectDialog.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CreateProjectDialog.class, "CreateProjectDialog.jLabel2.text")); // NOI18N

        notes.setColumns(20);
        notes.setRows(5);
        notes.setText(org.openide.util.NbBundle.getMessage(CreateProjectDialog.class, "CreateProjectDialog.notes.text")); // NOI18N
        jScrollPane1.setViewportView(notes);

        name.setText(org.openide.util.NbBundle.getMessage(CreateProjectDialog.class, "CreateProjectDialog.name.text")); // NOI18N

        parent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(CreateProjectDialog.class, "CreateProjectDialog.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(save, org.openide.util.NbBundle.getMessage(CreateProjectDialog.class, "CreateProjectDialog.save.text")); // NOI18N
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(cancel, org.openide.util.NbBundle.getMessage(CreateProjectDialog.class, "CreateProjectDialog.cancel.text")); // NOI18N
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                            .addComponent(name)
                            .addComponent(parent, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(save)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(parent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save)
                    .addComponent(cancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        //Save changes
        if (name.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid project name",
                    "Invalid Value",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            //First we update the project
            ProjectServer ps = new ProjectServer(name.getText().trim(), notes.getText().trim());
            Project parentProject = getSelectedProject(parent);
            if (parentProject != null) {
                ps.setParentProjectId(parentProject);
            }
            try {
                //Now we save it
                ps.write2DB();
            } catch (IllegalOrphanException ex) {
                Exceptions.printStackTrace(ex);
            } catch (NonexistentEntityException ex) {
                Exceptions.printStackTrace(ex);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
            ProjectExplorerComponent.refresh();
            dispose();
        }
    }//GEN-LAST:event_saveActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        //Cancel
        dispose();
    }//GEN-LAST:event_cancelActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name;
    private javax.swing.JTextArea notes;
    private javax.swing.JComboBox parent;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables
}
