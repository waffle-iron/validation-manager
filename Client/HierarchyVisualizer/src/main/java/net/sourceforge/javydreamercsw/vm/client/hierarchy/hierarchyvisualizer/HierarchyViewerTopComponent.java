package net.sourceforge.javydreamercsw.vm.client.hierarchy.hierarchyvisualizer;

import com.validation.manager.core.db.Requirement;
import java.awt.BorderLayout;
import java.util.Collection;
import java.util.logging.Logger;
import javax.swing.JComponent;
import net.sourceforge.javydreamercsw.vm.client.hierarchy.hierarchyvisualizer.scene.HierarchyScene;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//net.sourceforge.javydreamercsw.vm.client.hierarchy.hierarchyvisualizer//HierarchyViewer//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "HierarchyViewerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "net.sourceforge.javydreamercsw.vm.client.hierarchy.hierarchyvisualizer.HierarchyViewerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_HierarchyViewerAction",
        preferredID = "HierarchyViewerTopComponent")
@Messages({
    "CTL_HierarchyViewerAction=Hierarchy Viewer",
    "CTL_HierarchyViewerTopComponent=Relationship Window",
    "HINT_HierarchyViewerTopComponent=This is a Hierarchy Viewer window"
})
public final class HierarchyViewerTopComponent extends TopComponent implements LookupListener {

    private Lookup.Result<Requirement> result =
            Utilities.actionsGlobalContext().lookupResult(Requirement.class);
    private static final Logger LOG =
            Logger.getLogger(HierarchyViewerTopComponent.class.getSimpleName());
    private HierarchyScene scene;
    private final JComponent myView;

    public HierarchyViewerTopComponent() {
        initComponents();
        setName(Bundle.CTL_HierarchyViewerTopComponent());
        setToolTipText(Bundle.HINT_HierarchyViewerTopComponent());
        result.addLookupListener((HierarchyViewerTopComponent) this);

        scene = new HierarchyScene();
        myView = scene.createView();

        hierarchyPane.setViewportView(myView);
        
        add(scene.createSatelliteView(), BorderLayout.WEST);
        
        associateLookup(Lookups.fixed(
                // exposed TopComponent, 
                //   and SatelliteViewProvider, BirdViewProvider interfaces, too
                (HierarchyViewerTopComponent) this, 
                getActionMap() // do not forget expose ActionMap
                , scene // if some object needs it and if it is final
                ));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        hierarchyPane = new BeanTreeView();

        setLayout(new java.awt.BorderLayout());
        add(hierarchyPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane hierarchyPane;
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

    @Override
    public void resultChanged(LookupEvent le) {
        Collection<? extends Requirement> results = result.allInstances();
        if (results.isEmpty()) {
            LOG.info("No requirements selected.");
        } else {
            LOG.info("Selected the following requirements:");
            for (Requirement req : results) {
                LOG.info(req.getUniqueId());
                scene.addRequirement(req);
            }
        }
    }
}
