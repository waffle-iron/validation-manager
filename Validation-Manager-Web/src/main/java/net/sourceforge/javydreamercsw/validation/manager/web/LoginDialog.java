package net.sourceforge.javydreamercsw.validation.manager.web;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.validation.manager.core.db.VmUser;
import com.validation.manager.core.server.core.VMUserServer;

/**
 *
 * @author Javier A. Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
@SuppressWarnings("serial")
public final class LoginDialog extends VMWindow {

    private final ShortcutAction enterKey = new ShortcutAction(java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("general.login"),
            ShortcutAction.KeyCode.ENTER, null);

    private final TextField name = new TextField(java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("general.username"));
    private final PasswordField password = new PasswordField(java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("general.password"));

    private final Button loginButton = new Button(java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("general.login"),
            (ClickEvent event) -> {
                tryToLogIn();
            });

    private final Button cancelButton = new Button(java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("general.cancel"),
            (ClickEvent event) -> {
                LoginDialog.this.close();
            });

    public LoginDialog(ValidationManagerUI menu) {
        super(menu, java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("general.login"));
        init();
    }

    public void init() {
        //Layout
        FormLayout layout = new FormLayout();
        setContent(layout);
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.addComponent(loginButton);
        hlayout.addComponent(cancelButton);
        layout.addComponent(name);
        layout.addComponent(password);
        name.focus();
        StringLengthValidator nameVal = new StringLengthValidator(
                java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("password.length.message"));
        nameVal.setMinLength(5);
        name.addValidator(nameVal);
        name.setImmediate(true);
        StringLengthValidator passVal = new StringLengthValidator(
                java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("password.empty.message"));
        passVal.setMinLength(3);
        password.addValidator(passVal);
        password.setImmediate(true);
        layout.addComponent(hlayout);
        layout.setComponentAlignment(name, Alignment.TOP_LEFT);
        layout.setComponentAlignment(password, Alignment.MIDDLE_LEFT);
        layout.setComponentAlignment(hlayout, Alignment.BOTTOM_LEFT);
        layout.setSpacing(true);
        layout.setMargin(true);

        // Keyboard navigation - enter key is a shortcut to login
        addActionHandler(new Handler() {
            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{enterKey};
            }

            @Override
            public void handleAction(Action action, Object sender,
                    Object target) {
                tryToLogIn();
            }
        });
    }

    private void tryToLogIn() {
        if (VMUserServer.validCredentials(name.getValue(),
                password.getValue(), true)) {
            VmUser user
                    = VMUserServer.getUser(name.getValue(),
                            password.getValue(), true);
            if (menu != null) {
                menu.setUser(new VMUserServer(user));
            }
            close();
        } else {
            if (menu != null) {
                menu.setUser(null);
            }
            new Notification(java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("general.login.invalid.title"),
                    java.util.ResourceBundle.getBundle("com/validation/manager/resources/VMMessages").getString("general.login.invalid.message"),
                    Notification.Type.WARNING_MESSAGE, true)
                    .show(Page.getCurrent());
            password.setValue("");
        }
    }

    public void clear() {
        name.clear();
        password.clear();
    }
}
