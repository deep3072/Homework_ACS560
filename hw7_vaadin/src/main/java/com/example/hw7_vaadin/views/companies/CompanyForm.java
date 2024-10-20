package com.example.hw7_vaadin.views.companies;

import com.example.hw7_vaadin.entities.Company;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import lombok.Getter;

public class CompanyForm extends FormLayout {

    private static final long serialVersionUID = 476310807171214015L;

    private final TextField name = new TextField("Name");

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button cancel = new Button("Cancel");

    private final Binder<Company> binder = new BeanValidationBinder<>(Company.class);
    private Company company;
    private boolean isAdd;

    public CompanyForm() {
        addClassName("company-form");

        binder.bindInstanceFields(this);

        add(name, createButtonsLayout());
        setWidth("25em");
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> handleSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(event -> fireEvent(new CancelEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, cancel);
    }

    private void handleSave() {
        try {
            binder.writeBean(company);

            if (isAdd) {
                fireEvent(new AddEvent(this, company));
            } else {
                fireEvent(new UpdateEvent(this, company));
            }

        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void update(Company company, boolean isAdd) {
        this.isAdd = isAdd;

        delete.setVisible(!isAdd);

        if (company != null) {
            this.company = company;
        } else {
            name.setValue("");
            this.company = new Company();
        }

        binder.setBean(company);
    }

    public static abstract class CompanyFormEvent extends ComponentEvent<CompanyForm> {

        private static final long serialVersionUID = 8892029064323709532L;

        @Getter
        private Company company;

        protected CompanyFormEvent(CompanyForm source, Company company) {
            super(source, false);
            this.company = company;
        }
    }

    public static class AddEvent extends CompanyFormEvent {

        private static final long serialVersionUID = -8168200990060394704L;

        protected AddEvent(CompanyForm source, Company company) {
            super(source, company);
        }
    }

    public static class CancelEvent extends CompanyFormEvent {

        private static final long serialVersionUID = -6473184605060760145L;

        protected CancelEvent(CompanyForm source) {
            super(source, null);
        }
    }

    public static class DeleteEvent extends CompanyFormEvent {

        private static final long serialVersionUID = -5085028297106734234L;

        protected DeleteEvent(CompanyForm source, Company company) {
            super(source, company);
        }
    }

    public static class UpdateEvent extends CompanyFormEvent {

        private static final long serialVersionUID = -5085028297106734234L;

        protected UpdateEvent(CompanyForm source, Company company) {
            super(source, company);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
            ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}