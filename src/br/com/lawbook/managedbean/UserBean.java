package br.com.lawbook.managedbean;

import java.util.ResourceBundle;

import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;

import org.hibernate.HibernateException;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import br.com.lawbook.business.service.UserService;
import br.com.lawbook.model.Authority;
import br.com.lawbook.model.Profile;
import br.com.lawbook.model.User;
import br.com.lawbook.util.FacesUtil;

import com.sun.faces.taglib.jsf_core.SetPropertyActionListenerImpl;

/**
 * @author Edilson Luiz Ales Junior
 * @version 26NOV2011-08
 *
 */
@ManagedBean
@RequestScoped
public class UserBean {

	private final MenuModel menu;
	private static final UserService USER_SERVICE = UserService.getInstance();

	public UserBean() {
		this.menu = new DefaultMenuModel();

		final FacesContext context = FacesContext.getCurrentInstance();
		final ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
		final ResourceBundle rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", context.getViewRoot().getLocale());

		MenuItem item = new MenuItem();
		item.setId("menuItemHome");
		item.setValue(rs.getString("template_menu_home"));
		item.setUrl("/pages/home.jsf");
		this.menu.addMenuItem(item);

		// TODO Profile menu option doesn't fire in administration pages, try the same approach used in customerInfo.xhtml
		item = new MenuItem();
		item.setId("menuItemProfile");
		item.setValue(rs.getString("template_menu_profile"));
		final ValueExpression target = expressionFactory.createValueExpression(context.getELContext(), "#{profileBean.profileOwner}", Profile.class);
		final ValueExpression propertyValue = expressionFactory.createValueExpression(context.getELContext(),"#{profileBean.authProfile}", Profile.class);
		final MethodExpression action = expressionFactory.createMethodExpression(context.getELContext(), "#{userBean.profileOutcome}", String.class, new Class[]{});
		final ActionListener handler = new SetPropertyActionListenerImpl(target, propertyValue);
		item.setActionExpression(action);
        item.addActionListener(handler);
        item.setAjax(false);
		this.menu.addMenuItem(item);

		final Submenu sub = new Submenu();
		sub.setId("subMenuAccount");
		sub.setLabel(rs.getString("template_menu_account"));

		try {
			final User user = USER_SERVICE.getAuthorizedUser();
			for (final Authority auth: user.getAuthority()) {
				if (auth.getName().equals("ADMIN")) {
					item = new MenuItem();
					item.setId("menuItemAdmin");
					item.setValue(rs.getString("template_menu_account_admin"));
					item.setUrl("/pages/admin/administration.jsf");
					sub.getChildren().add(item);
				}
			}
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		} catch (final Exception e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}

		item = new MenuItem();
		item.setId("menuItemSettings");
		item.setValue(rs.getString("template_menu_account_settings"));
		item.setUrl("/pages/settings.jsf");
		sub.getChildren().add(item);

		item = new MenuItem();
		item.setId("menuItemLogout");
		item.setValue(rs.getString("template_menu_account_logout"));
		item.setUrl("/j_spring_security_logout");
		sub.getChildren().add(item);

		this.menu.addSubmenu(sub);
	}

	public MenuModel getMenu() {
		return this.menu;
	}

	public String profileOutcome() {
		return "profile?faces-redirect=true";
	}

}
