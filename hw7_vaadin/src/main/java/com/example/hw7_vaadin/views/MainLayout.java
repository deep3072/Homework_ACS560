package com.example.hw7_vaadin.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.hw7_vaadin.security.SecurityService;
import com.example.hw7_vaadin.views.companies.CompaniesView;
import com.example.hw7_vaadin.views.stockprices.StockPriceView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;



public class MainLayout extends AppLayout {

    private static final long serialVersionUID = -5291741451913578403L;

    @Autowired
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Stock Prices");
        logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);

        String username = securityService.getAuthenticatedUser().getUsername();
        Button logout = new Button("Log out " + username, e -> securityService.logout());

        var header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink companiesLink = new RouterLink("Companies", CompaniesView.class);
        companiesLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink stockPricesLink = new RouterLink("Stock Prices", StockPriceView.class);
        stockPricesLink.setHighlightCondition(HighlightConditions.sameLocation());


        addToDrawer(new VerticalLayout(companiesLink, stockPricesLink));
    }
}
