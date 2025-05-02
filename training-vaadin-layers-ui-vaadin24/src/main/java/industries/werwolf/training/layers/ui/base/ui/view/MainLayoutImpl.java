package industries.werwolf.training.layers.ui.base.ui.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import industries.werwolf.training.layers.presenter.base.MainLayout;
import industries.werwolf.training.layers.presenter.base.MainLayoutPresenter;
import industries.werwolf.training.layers.service.util.SecurityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.Optional;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@Layout
@AnonymousAllowed
public final class MainLayoutImpl extends AppLayout implements MainLayout {

    private final MainLayoutPresenter presenter;

    MainLayoutImpl(MainLayoutPresenter presenter) {
        this.presenter = presenter;
        setPrimarySection(Section.DRAWER);
        addToDrawer(createHeader(), new Scroller(createSideNav()));
        Optional.ofNullable(SecurityUtils.getUser()).ifPresent(u -> addToDrawer(createUserMenu(u)));
    }

    private Div createHeader() {
        // TODO Replace with real application logo and name
        Icon appLogo = VaadinIcon.CUBES.create();
        appLogo.addClassNames(TextColor.PRIMARY, IconSize.LARGE);

        Span appName = new Span("Training Vaadin Layers");
        appName.addClassNames(FontWeight.SEMIBOLD, FontSize.LARGE);

        Div header = new Div(appLogo, appName);
        header.addClassNames(Display.FLEX, Padding.MEDIUM, Gap.MEDIUM, AlignItems.CENTER);
        return header;
    }

    private SideNav createSideNav() {
        SideNav nav = new SideNav();
        nav.addClassNames(Margin.Horizontal.MEDIUM);
        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
        return nav;
    }

    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
        if (menuEntry.icon() != null) {
            return new SideNavItem(menuEntry.title(), menuEntry.path(), new Icon(menuEntry.icon()));
        } else {
            return new SideNavItem(menuEntry.title(), menuEntry.path());
        }
    }

    private Component createUserMenu(User user) {
        // TODO Replace with real user information and actions
        String username = user.getUsername();

        Avatar avatar = new Avatar(username);
        avatar.addThemeVariants(AvatarVariant.LUMO_XSMALL);
        avatar.addClassNames(Margin.Right.SMALL);
        avatar.setColorIndex(5);

        MenuBar userMenu = new MenuBar();
        userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        userMenu.addClassNames(Margin.MEDIUM);

        MenuItem userMenuItem = userMenu.addItem(avatar);
        userMenuItem.add(username);
        userMenuItem.getSubMenu().addItem("View Profile");
        userMenuItem.getSubMenu().addItem("Manage Settings");
        userMenuItem.getSubMenu().addItem("Logout", click -> presenter.logoutClicked());

        return userMenu;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if(attachEvent.isInitialAttach()) {
            presenter.init(this);
        }
    }

    @Override
    public void logout() {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }

    @Override
    public void navigateHome() {
        UI.getCurrent().getPage().setLocation("/");
    }
}
