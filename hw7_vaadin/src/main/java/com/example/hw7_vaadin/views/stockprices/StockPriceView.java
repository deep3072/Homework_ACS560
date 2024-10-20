package com.example.hw7_vaadin.views.stockprices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.example.hw7_vaadin.entities.StockPrice;
import com.example.hw7_vaadin.services.CompanyService;
import com.example.hw7_vaadin.services.StockPriceService;
import com.example.hw7_vaadin.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.UI;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "stockprices", layout = MainLayout.class)
@PageTitle("Stock Prices | Data Manager")
public class StockPriceView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    @Autowired
    private StockPriceService stockPriceService;

    @Autowired
    private CompanyService companyService;

    private final Grid<StockPrice> grid;
    private final TextField filterText;
    private final StockPriceForm stockPriceForm;

    private boolean filtering = false;

    public StockPriceView(StockPriceService stockPriceService, CompanyService companyService) {
        this.stockPriceService = stockPriceService;
        this.companyService = companyService;

        addClassName("list-view");
        setSizeFull();

        grid = createGrid();
        stockPriceForm = createForm();
        filterText = createFilter();

        add(createToolbar(filterText), getContent());
        updateGrid();
        closeForm();
    }

    private StockPriceForm createForm() {
        StockPriceForm stockPriceForm = new StockPriceForm(companyService.getCompanies());

        stockPriceForm.addListener(StockPriceForm.AddEvent.class, this::addStockPrice);
        stockPriceForm.addListener(StockPriceForm.DeleteEvent.class, this::deleteStockPrice);
        stockPriceForm.addListener(StockPriceForm.CancelEvent.class, e -> closeForm());
        stockPriceForm.addListener(StockPriceForm.UpdateEvent.class, this::updateStockPrice);

        return stockPriceForm;
    }

    private Grid<StockPrice> createGrid() {
        Grid<StockPrice> grid = new Grid<>(StockPrice.class);

        grid.addClassNames("stockprice-grid");
        grid.setSizeFull();
        grid.setColumns("id", "date", "open", "high", "low", "close", "adjClose", "volume");
        grid.addColumn(stockPrice -> stockPrice.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> handleSelected(event.getValue()));

        return grid;
    }

    private TextField createFilter() {
        TextField filterText = new TextField();
        filterText.setValueChangeTimeout(1000);
        filterText.setPlaceholder("Filter by date...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> handleFilter());

        return filterText;
    }

    private Component createToolbar(TextField filterText) {
        Button addStockPriceButton = new Button("Add Stock Price");
        addStockPriceButton.addClickListener(click -> handleAdd());

        var toolbar = new HorizontalLayout(filterText, addStockPriceButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, stockPriceForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, stockPriceForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void handleFilter() {
        if (!filtering) {
            return;
        }

        String filter = filterText.getValue();
        List<StockPrice> stockPrices;

        if (filter.length() > 2) {
            stockPrices = stockPriceService.getStockPricesByDate(filter);
        } else {
            stockPrices = stockPriceService.getAllStockPrices();
        }

        grid.setItems(stockPrices);
    }

    private void updateGrid() {
        List<StockPrice> stockPrices = stockPriceService.getAllStockPrices();
        // System.out.println("Stock prices: " + stockPrices);
        grid.setItems(stockPrices);
        filtering = false;
        filterText.clear();
        filtering = true;
    }

    private void handleSelected(StockPrice stockPrice) {
        stockPriceForm.update(stockPrice, companyService.getCompanies(), false);
        stockPriceForm.setVisible(true);
        addClassName("editing");
    }

    private void handleAdd() {
        grid.asSingleSelect().clear();
        stockPriceForm.update(null, companyService.getCompanies(), true);
        stockPriceForm.setVisible(true);
        addClassName("editing");
    }

    private void closeForm() {
        stockPriceForm.setVisible(false);
        removeClassName("editing");
    }

    private void addStockPrice(StockPriceForm.AddEvent event) {
        stockPriceService.addStockPrice(event.getStockPrice());
        updateGrid();
        closeForm();
    }

    private void updateStockPrice(StockPriceForm.UpdateEvent event) {
        stockPriceService.updateStockPrice(event.getStockPrice().getId(), event.getStockPrice());
        // updateGrid();
        UI.getCurrent().getPage().reload();
        closeForm();
    }

    private void deleteStockPrice(StockPriceForm.DeleteEvent event) {
        stockPriceService.deleteStockPrice(event.getStockPrice().getId());
        // updateGrid();
        UI.getCurrent().getPage().reload();
        closeForm();
    }
}