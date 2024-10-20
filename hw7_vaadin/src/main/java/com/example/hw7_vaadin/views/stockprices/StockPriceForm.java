package com.example.hw7_vaadin.views.stockprices;

import com.example.hw7_vaadin.entities.Company;
import com.example.hw7_vaadin.entities.StockPrice;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.util.List;

public class StockPriceForm extends FormLayout {

    private static final long serialVersionUID = 1L;

    private final ComboBox<Company> company = new ComboBox<>("Company");
    private final TextField date = new TextField("Date");
    private final NumberField open = new NumberField("Open");
    private final NumberField high = new NumberField("High");
    private final NumberField low = new NumberField("Low");
    private final NumberField close = new NumberField("Close");
    private final NumberField adjClose = new NumberField("Adj Close");
    // private final NumberField volume = new NumberField("Volume");
    private final TextField volume = new TextField("Volume");


    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button cancel = new Button("Cancel");

    private final Binder<StockPrice> binder = new BeanValidationBinder<>(StockPrice.class);
    private StockPrice stockPrice;
    private boolean isAdd;

    public StockPriceForm(List<Company> companies) {
        addClassName("stockprice-form");

        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);

        binder.bindInstanceFields(this);

        add(company, date, open, high, low, close, adjClose, volume, createButtonsLayout());
        this.setWidth("25em");
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
            binder.writeBean(stockPrice);

            if (isAdd) {
                fireEvent(new AddEvent(this, stockPrice));
            } else {
                fireEvent(new UpdateEvent(this, stockPrice));
            }
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void update(StockPrice stockPrice, List<Company> companies, boolean isAdd) {
        this.isAdd = isAdd;

        delete.setVisible(!isAdd);

        company.setItems(companies);

        if (stockPrice != null) {
            this.stockPrice = stockPrice;
        } else {
            company.setValue(null);
            date.setValue("");
            open.setValue(null);
            high.setValue(null);
            low.setValue(null);
            close.setValue(null);
            adjClose.setValue(null);
            volume.setValue("");
            this.stockPrice = new StockPrice();
        }

        binder.setBean(stockPrice);
    }

    public static abstract class StockPriceFormEvent extends ComponentEvent<StockPriceForm> {

        private static final long serialVersionUID = 1L;

        @Getter
        private StockPrice stockPrice;

        protected StockPriceFormEvent(StockPriceForm source, StockPrice stockPrice) {
            super(source, false);
            this.stockPrice = stockPrice;
        }
    }

    public static class AddEvent extends StockPriceFormEvent {
        private static final long serialVersionUID = 1L;

        protected AddEvent(StockPriceForm form, StockPrice stockPrice) {
            super(form, stockPrice);
        }
    }

    public static class CancelEvent extends StockPriceFormEvent {
        private static final long serialVersionUID = 1L;

        protected CancelEvent(StockPriceForm form) {
            super(form, null);
        }
    }

    public static class DeleteEvent extends StockPriceFormEvent {
        private static final long serialUID = 1L;

        protected DeleteEvent(StockPriceForm form, StockPrice stockPrice) {
            super(form, stockPrice);
        }
    }

    public static class UpdateEvent extends StockPriceFormEvent {
        private static final long serialUID = 1L;

        protected UpdateEvent(StockPriceForm form, StockPrice stockPrice) {
            super(form, stockPrice);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
            ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}