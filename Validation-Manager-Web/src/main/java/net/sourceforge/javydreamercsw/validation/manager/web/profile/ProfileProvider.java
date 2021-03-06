/* 
 * Copyright 2017 Javier A. Ortiz Bultron javier.ortiz.78@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.javydreamercsw.validation.manager.web.profile;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.validation.manager.core.IMainContentProvider;
import com.validation.manager.core.api.internationalization.InternationalizationProvider;
import com.validation.manager.core.server.core.VMUserServer;
import com.validation.manager.core.tool.MD5;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.javydreamercsw.validation.manager.web.ValidationManagerUI;
import net.sourceforge.javydreamercsw.validation.manager.web.provider.AbstractProvider;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 * The provider for the profile tab to manage your account.
 *
 * @author Javier A. Ortiz Bultron javier.ortiz.78@gmail.com
 */
@ServiceProvider(service = IMainContentProvider.class, position = 5)
public class ProfileProvider extends AbstractProvider {

    private static final Logger LOG
            = Logger.getLogger(ProfileProvider.class.getSimpleName());

    @Override
    public boolean shouldDisplay() {
        //Show whenever an user is logged in.
        return ValidationManagerUI.getInstance().getUser() != null;
    }

    @Override
    public String getComponentCaption() {
        return "message.admin.userProfile";
    }

    @Override
    public Component getContent() {
        VMUserServer user = ValidationManagerUI.getInstance().getUser();
        Panel form = new Panel();
        FormLayout layout = new FormLayout();
        form.setContent(layout);
        form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        BeanFieldGroup binder = new BeanFieldGroup(user.getClass());
        binder.setItemDataSource(user);
        Field<?> fn = binder.buildAndBind(Lookup.getDefault().lookup(InternationalizationProvider.class).
                translate("general.first.name"),
                "firstName", TextField.class);
        layout.addComponent(fn);
        Field<?> ln = binder.buildAndBind(Lookup.getDefault().lookup(InternationalizationProvider.class).
                translate("general.last.name"),
                "lastName", TextField.class);
        layout.addComponent(ln);
        Field<?> username = binder.buildAndBind(Lookup.getDefault().lookup(InternationalizationProvider.class).
                translate("general.username"),
                "username", TextField.class);
        layout.addComponent(username);
        PasswordField pw = (PasswordField) binder.buildAndBind(
                Lookup.getDefault().lookup(InternationalizationProvider.class).
                        translate("general.password"),
                "password", PasswordField.class);
        PasswordChangeListener listener = new PasswordChangeListener();
        pw.addTextChangeListener(listener);
        pw.setConverter(new UserPasswordConverter());
        layout.addComponent(pw);
        Field<?> email = binder.buildAndBind(Lookup.getDefault().lookup(InternationalizationProvider.class).
                translate("general.email"),
                "email", TextField.class);
        layout.addComponent(email);
        ComboBox locale = new ComboBox(Lookup.getDefault().lookup(InternationalizationProvider.class).
                translate("general.locale"));
        ValidationManagerUI.getAvailableLocales().forEach(l -> {
            locale.addItem(l.toString());
        });
        if (user.getLocale() != null) {
            locale.setValue(user.getLocale());
        }
        layout.addComponent(locale);
        Button update = new Button(Lookup.getDefault().lookup(InternationalizationProvider.class).
                translate("general.update"));
        update.addClickListener((Button.ClickEvent event) -> {
            try {
                VMUserServer us = new VMUserServer(user);
                us.setFirstName((String) fn.getValue());
                us.setLastName((String) ln.getValue());
                us.setEmail((String) email.getValue());
                us.setUsername((String) username.getValue());
                us.setLocale((String) locale.getValue());
                String password = (String) pw.getValue();
                if (listener.isChanged() && !password.equals(user.getPassword())) {
                    //Different password. Prompt for confirmation
                    MessageBox mb = MessageBox.create();
                    VerticalLayout vl = new VerticalLayout();
                    Label l = new Label(Lookup.getDefault().lookup(InternationalizationProvider.class).
                            translate("password.confirm.pw.message"));
                    vl.addComponent(l);
                    PasswordField np = new PasswordField(Lookup.getDefault()
                            .lookup(InternationalizationProvider.class)
                            .translate("general.password"));
                    vl.addComponent(np);
                    mb.asModal(true)
                            .withCaption(Lookup.getDefault()
                                    .lookup(InternationalizationProvider.class).
                                    translate("password.confirm.pw"))
                            .withMessage(vl)
                            .withButtonAlignment(Alignment.MIDDLE_CENTER)
                            .withOkButton(() -> {
                                try {
                                    if (password.equals(MD5.encrypt(np.getValue()))) {
                                        us.setHashPassword(true);
                                        us.setPassword(np.getValue());
                                        us.write2DB();
                                        Notification.show(Lookup.getDefault().lookup(InternationalizationProvider.class).
                                                translate("audit.user.account.password.change"),
                                                Notification.Type.ASSISTIVE_NOTIFICATION);
                                    } else {
                                        Notification.show(Lookup.getDefault().lookup(InternationalizationProvider.class).
                                                translate("password.does.not.match"),
                                                Notification.Type.WARNING_MESSAGE);
                                    }
                                    mb.close();
                                } catch (Exception ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                            }, ButtonOption.focus(),
                                    ButtonOption.closeOnClick(false),
                                    ButtonOption.icon(VaadinIcons.CHECK))
                            .withCancelButton(
                                    ButtonOption.icon(VaadinIcons.CLOSE)
                            ).getWindow().setIcon(ValidationManagerUI.SMALL_APP_ICON);
                    mb.open();
                } else {
                    us.write2DB();
                }
                ValidationManagerUI.getInstance().setUser(us);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
                Notification.show(Lookup.getDefault().lookup(InternationalizationProvider.class).
                        translate("general.error.record.update"),
                        ex.getLocalizedMessage(),
                        Notification.Type.ERROR_MESSAGE);
            }
        });
        Button cancel = new Button(Lookup.getDefault()
                .lookup(InternationalizationProvider.class).
                translate("general.cancel"));
        cancel.addClickListener((Button.ClickEvent event) -> {
            binder.discard();
            ValidationManagerUI.getInstance().updateScreen();
        });
        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(update);
        hl.addComponent(cancel);
        layout.addComponent(hl);
        return form;
    }

    private class PasswordChangeListener implements TextChangeListener {

        private boolean changed = false;

        @Override
        public void textChange(FieldEvents.TextChangeEvent event) {
            changed = true;
        }

        /**
         * @return the changed
         */
        public boolean isChanged() {
            return changed;
        }
    }
}
