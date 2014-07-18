package net.sourceforge.javydreamercsw.graph;

import com.validation.manager.core.db.Project;
import com.validation.manager.core.db.Requirement;
import com.validation.manager.core.db.RequirementSpec;
import com.validation.manager.core.db.RequirementSpecNode;
import com.validation.manager.core.server.core.ProjectServer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import net.sourceforge.javydreamercsw.javafx.lib.ChartProvider;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
@ServiceProvider(service = ChartProvider.class)
public class ProjectRequirementStatsChartProvider implements ChartProvider<Project> {

    private Properties p = new Properties();
    private final Map<Properties, Class> props = new HashMap<>();
    private Map<String, Integer> stats = new HashMap<>();
    public static final String INCLUDE_SUBPROJECTS
            = "included-subprojects";
    private static final Logger LOG
            = Logger.getLogger(ProjectRequirementStatsChartProvider.class.getSimpleName());
    private static final ResourceBundle rb
            = ResourceBundle.getBundle("com.validation.manager.resources.VMMessages");

    public ProjectRequirementStatsChartProvider() {
        Properties temp = new Properties();
        temp.put(INCLUDE_SUBPROJECTS,
                "Included Subprojects.");
        props.put(temp, Boolean.class);
    }

    @Override
    public boolean supports(Class c) {
        return c.isInstance(Project.class) || c.equals(Project.class);
    }

    @Override
    public Chart getChart(Project entity) {
        //Reset Stats
        stats.clear();
        addProjectRequirements(entity);
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList();
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");
        for (Entry<String, Integer> entry : stats.entrySet()) {
            PieChart.Data data = new PieChart.Data(entry.getKey()
                    + " (" + entry.getValue() + ")",
                    entry.getValue());
            pieChartData.add(data);
        }
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Project Requirement Stats");
        return chart;
    }

    private void addProjectRequirements(Project p) {
        LOG.log(Level.FINE, "Analyzing Project: {0}", p.getName());
        ProjectServer ps = new ProjectServer(p);
        LOG.log(Level.FINE, "Specs: {0}", ps.getRequirementSpecList().size());
        for (RequirementSpec rs : ps.getRequirementSpecList()) {
            LOG.log(Level.FINE, "Nodes: {0}", rs.getRequirementSpecNodeList().size());
            for (RequirementSpecNode rsn : rs.getRequirementSpecNodeList()) {
                LOG.log(Level.FINE, "Requirements: {0}", rsn.getRequirementList().size());
                for (Requirement r : rsn.getRequirementList()) {
                    String status = r.getRequirementStatusId().getStatus();
                    if (rb.containsKey(status)) {
                        status = rb.getString(status);
                    }
                    if (stats.containsKey(status)) {
                        stats.put(status,
                                stats.get(status) + 1);
                    } else {
                        stats.put(status, 1);
                    }
                }
            }
        }
        if (this.p.getOrDefault(INCLUDE_SUBPROJECTS, Boolean.TRUE) == Boolean.TRUE) {
            ps.getChildren().stream().forEach((child) -> {
                addProjectRequirements(child);
            });
        }
    }

    @Override
    public String getName() {
        return "Project Requirement Stats";
    }

    @Override
    public String getDescription() {
        return "Shows stats of requirements for this project.";
    }

    @Override
    public Properties getProperties() {
        return p;
    }

    @Override
    public void setProperties(Properties props) {
        this.p = props;
    }

    @Override
    public Map<Properties, Class> getPropertyMap() {
        return props;
    }
}