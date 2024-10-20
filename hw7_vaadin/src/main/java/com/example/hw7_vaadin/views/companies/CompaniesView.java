package com.example.hw7_vaadin.views.companies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.example.hw7_vaadin.entities.Company;
import com.example.hw7_vaadin.services.CompanyService;
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

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "companies", layout = MainLayout.class)
@PageTitle("Companies | Stock Prices")
public class CompaniesView extends VerticalLayout {

    private static final long serialVersionUID = 6436483924131073477L;

    @Autowired
    private CompanyService companyService;

    private final Grid<Company> grid;
    private final TextField filterText;
    private final CompanyForm companyForm;

    private boolean filtering = false;

    public CompaniesView(CompanyService companyService) {
        this.companyService = companyService;

        addClassName("list-view");
        setSizeFull();

        grid = createGrid();
        companyForm = createForm();
        filterText = createFilter();

        add(createToolbar(filterText), getContent());
        updateGrid();
        closeForm();
    }

    private CompanyForm createForm() {
        CompanyForm companyForm = new CompanyForm();

        companyForm.addListener(CompanyForm.AddEvent.class, this::addCompany);
        companyForm.addListener(CompanyForm.DeleteEvent.class, this::deleteCompany);
        companyForm.addListener(CompanyForm.CancelEvent.class, e -> closeForm());
        companyForm.addListener(CompanyForm.UpdateEvent.class, this::updateCompany);

        return companyForm;
    }

    private Grid<Company> createGrid() {
        Grid<Company> grid = new Grid<>(Company.class);

        grid.addClassNames("company-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> handleSelected(event.getValue()));

        return grid;
    }

    private TextField createFilter() {
        TextField filterText = new TextField();
        filterText.setValueChangeTimeout(1000);
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> handleFilter());

        return filterText;
    }

    private Component createToolbar(TextField filterText) {
        Button addCompanyButton = new Button("Add company");
        addCompanyButton.addClickListener(click -> handleAdd());

        var toolbar = new HorizontalLayout(filterText, addCompanyButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, companyForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, companyForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void handleFilter() {
        if (!filtering) {
            return;
        }

        String filter = filterText.getValue();
        List<Company> companies;

        if (filter.length() > 2) {
            companies = companyService.getCompanies(filter);
        } else {
            companies = companyService.getCompanies();
        }

        grid.setItems(companies);
    }

    private void updateGrid() {
        List<Company> companies = companyService.getCompanies();
        grid.setItems(companies);
        filtering = false;
        filterText.clear();
        filtering = true;
    }

    private void handleSelected(Company company) {
        companyForm.update(company, false);
        companyForm.setVisible(true);
        addClassName("editing");
    }

    private void handleAdd() {
        grid.asSingleSelect().clear();
        companyForm.update(null, true);
        companyForm.setVisible(true);
        addClassName("editing");
    }

    private void closeForm() {
        companyForm.setVisible(false);
        removeClassName("editing");
    }

    private void addCompany(CompanyForm.AddEvent event) {
        companyService.addCompany(event.getCompany());
        updateGrid();
        closeForm();
    }

    private void updateCompany(CompanyForm.UpdateEvent event) {
        companyService.updateCompany(event.getCompany());
        updateGrid();
        closeForm();
    }

    private void deleteCompany(CompanyForm.DeleteEvent event) {
        companyService.deleteCompany(event.getCompany().getId());
        updateGrid();
        closeForm();
    }
}