package net.sourceforge.javydreamercsw.validation.manager.web.traceability;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TreeTable;
import com.validation.manager.core.VMUI;
import com.validation.manager.core.db.Baseline;
import com.validation.manager.core.db.ExecutionStep;
import com.validation.manager.core.db.ExecutionStepHasIssue;
import com.validation.manager.core.db.Issue;
import com.validation.manager.core.db.Project;
import com.validation.manager.core.db.Requirement;
import com.validation.manager.core.db.TestCase;
import com.validation.manager.core.tool.Tool;
import net.sourceforge.javydreamercsw.validation.manager.web.ValidationManagerUI;

/**
 * Trace Matrix component. Traces relationship from requirements to test case
 * steps including results and issues.
 *
 * @author Javier Ortiz Bultron<javier.ortiz.78@gmail.com>
 */
public class TraceMatrix extends TreeTable {

    private final Project p;

    public TraceMatrix(Project p) {
        this.p = p;
        init();
    }

    public TraceMatrix(Project p, String caption) {
        super(caption);
        this.p = p;
        init();
    }

    private void init() {
        addContainerProperty(ValidationManagerUI.getInstance()
                .translate("general.requirement"),
                String.class, "");
        addContainerProperty(ValidationManagerUI.getInstance()
                .translate("general.test.case"),
                Label.class, "");
        addContainerProperty(ValidationManagerUI.getInstance()
                .translate("general.result"),
                Label.class, "");
        addContainerProperty(ValidationManagerUI.getInstance()
                .translate("general.issue"),
                Label.class, "");
        Tool.extractRequirements(p).forEach((r) -> {
            if (r.getParentRequirementId() == null) {
                addRequirement(r);
            }
        });
        setAnimationsEnabled(true);
        for (Object item : getItemIds().toArray()) {
            expandItem(item);
        }
        setChildrenAllowedAsNeeded();
        setSizeFull();
    }

    private void expandItem(Object item) {
        setCollapsed(item, false);
        if (hasChildren(item)) {
            getChildren(item).forEach(child -> {
                expandItem(child);
            });
        }
    }

    private void addRequirement(Requirement r) {
        addItem(new Object[]{r.getUniqueId(),
            new Label(), new Label(), new Label()}, r.getUniqueId());
        addTestCases(r);
        //Add children
        r.getRequirementList().forEach(child -> {
            addRequirement(child);
            setParent(child.getUniqueId(), r.getUniqueId());
        });
        setItemIcon(r.getUniqueId(), VMUI.REQUIREMENT_ICON);
    }

    /**
     * Builder of unique id's for items.
     *
     * @param item Item to get the id from
     * @return key for the object
     */
    private Object buildId(Object item) {
        return buildId(item, null);
    }

    /**
     * Builder of unique id's for items.
     *
     * @param item Item to get the id from
     * @param postfix postfix to add to the key
     * @return key for the object
     */
    private Object buildId(Object item, Object postfix) {
        Object pf;
        Object key = null;
        if (postfix == null) {
            pf = "";
        } else {
            pf = "-" + postfix;
        }
        if (item instanceof TestCase) {
            TestCase tc = (TestCase) item;
            key = "tc-" + tc.getId() + pf;
        } else if (item instanceof Requirement) {
            Requirement r = (Requirement) item;
            key = r.getUniqueId() + pf;
        } else if (item instanceof ExecutionStep) {
            ExecutionStep es = (ExecutionStep) item;
            key = "es-" + es.getExecutionStepPK().getStepId() + "-"
                    + es.getExecutionStepPK().getStepTestCaseId() + "-"
                    + es.getExecutionStepPK().getTestCaseExecutionId() + pf;
        } else if (item instanceof Issue) {
            Issue issue = (Issue) item;
            key = "issue-" + issue.getIssuePK().getId() + pf;
        }
        return key;
    }

    private void addTestCases(Requirement r) {
        r.getHistoryList().forEach(h -> {
            h.getExecutionStepList().forEach(es -> {
                TestCase tc = es.getStep().getTestCase();
                Label label = new Label(tc.getName());
                label.setIcon(VMUI.TEST_ICON);
                Object rId = buildId(r);
                Object tcID = buildId(tc, rId);
                Object esId = buildId(es);
                if (!containsId(tcID)) {
                    addItem(new Object[]{"",
                        label, new Label(), new Label()}, tcID);
                    setParent(tcID, rId);
                }
                if (es.getResultId() != null) {
                    String result = es.getResultId().getResultName();
                    Label resultLabel
                            = new Label(ValidationManagerUI.getInstance()
                                    .translate(result));
                    addItem(new Object[]{"",
                        new Label("Step #" + es.getStep().getStepSequence()),
                        resultLabel, new Label()}, esId);
                    setParent(esId, tcID);
                    //Completed. Now check result
                    Resource icon;
                    switch (result) {
                        case "result.pass":
                            icon = VaadinIcons.CHECK;
                            break;
                        case "result.fail":
                            icon = VaadinIcons.CLOSE;
                            break;
                        case "result.blocked":
                            icon = VaadinIcons.PAUSE;
                            break;
                        case "result.progress":
                            icon = VaadinIcons.AUTOMATION;
                            break;
                        default:
                            icon = VaadinIcons.CLOCK;
                            break;
                    }
                    resultLabel.setIcon(icon);
                }
                addIssues(es);
            });
        });
    }

    private void setChildrenAllowedAsNeeded() {
        getItemIds().forEach(item -> {
            setChildrenAllowed(item,
                    hasChildren(item));
        });
    }

    private void addIssues(ExecutionStep es) {
        for (ExecutionStepHasIssue eshi : es.getExecutionStepHasIssueList()) {
            int issueNumber = eshi.getIssue().getIssuePK().getId();
            Label label = new Label("Issue #" + issueNumber);
            label.setIcon(VaadinIcons.BUG);
            Object esId = buildId(eshi.getExecutionStep());
            Object issueId = buildId(eshi.getIssue(), esId);
            addItem(new Object[]{"",
                new Label(), new Label(), label}, issueId);
            setParent(issueId, esId);
            setChildrenAllowed(issueId, false);
        }
    }

    public Component getMenu() {
        HorizontalLayout hl = new HorizontalLayout();
        ComboBox baseline = new ComboBox("Filter by Baseline");
        baseline.setReadOnly(true);
        Tool.extractRequirements(p).forEach(r -> {
            r.getHistoryList().forEach(h -> {
                h.getBaselineList().forEach(b -> {
                    if (!baseline.containsId(b)) {
                        baseline.addItem(b);
                        baseline.setItemCaption(b, b.getBaselineName());
                    }
                });
            });
        });
        baseline.addValueChangeListener(event -> {
            removeAllItems();
            Baseline b = (Baseline) baseline.getValue();
            b.getHistoryList().forEach(h -> {
                addRequirement(h.getRequirementId());
            });
        });
        hl.addComponent(baseline);
        return hl;
    }
}
