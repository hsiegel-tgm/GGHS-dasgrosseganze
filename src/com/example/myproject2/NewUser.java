package com.example.myproject2;

import javax.servlet.annotation.WebServlet;

import model.User;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Query;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * 
 * @version 2014-05-07 Hannah erstellt
 * @author Hannah Siegel
 * 
 *         TODO JUnit TODO Komment TODO Design pefekto TODO GUI Test TODO
 *         Variablen TODO Coding style
 * 
 *         TODO moeglich password email mit password
 * 
 * 
 * */
public class NewUser extends VerticalLayout implements View {
	// Textfields
	private TextField textfield_username, textfield_email;

	// Navigator and master object
	private FatNavigator m_navigator;

	// Layout
	private CustomLayout layout;

	/**
	 * Constructor
	 * 
	 * @param nav
	 *            navigator object
	 */
	protected NewUser(FatNavigator nav) {

		this.m_navigator = nav;

		// HTML Design
		InputStream layoutFile = getClass().getResourceAsStream("newuser.html");
		layout = null;
		try {
			layout = new CustomLayout(layoutFile);
		} catch (Exception e) {
			System.out.println("there was a problem"); // TODO ex handling
		}
		layout.addStyleName("NewUser");

		// username Textfield
		textfield_username = new TextField();

		// email Textfield
		textfield_email = new TextField();

		// Send and Back Button
		Button button_send = new Button("Anmelden");
		Button button_back = new Button("Zurück");
		button_send.setClickShortcut(KeyCode.ENTER, null);
		button_back.setClickShortcut(KeyCode.ARROW_LEFT, null);

		// adding layout to webpage
		layout.addComponent(new Label("username:"));
		layout.addComponent(textfield_username, "textfield_username");
		layout.addComponent(new Label("email:"));
		layout.addComponent(textfield_email, "textfield_email");
		layout.addComponent(button_send, "button_send");
		layout.addComponent(button_back, "button_back");
		this.addComponent(layout);

		// Back Button Listener
		button_back
				.addClickListener(new PinkShoes(m_navigator, Variables.LOGIN));

		// Send Button Listener
		button_send.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				// fetching values
				String username = textfield_username.getValue();
				String email = textfield_email.getValue();

				boolean valid = true;

				if (username == null || username.equals("")) {
					Notification.show("please set a username",
							Notification.TYPE_WARNING_MESSAGE); //TODO dep
					valid = false;
				}
				try {
					InternetAddress emailAddr = new InternetAddress(email);
					emailAddr.validate();
				} catch (AddressException ex) {
					Notification.show("please set a valid email address",
							Notification.TYPE_WARNING_MESSAGE); //TODO dep
					valid = false;

				}

				if (valid) {
					// new User object
					//String pw = RandomStringUtils.randomAlphanumeric(25);
					String pw = "123"; //TODO aendern irgendwann :P
					try {
						SendEmail.send("hannah.k.siegel@gmail.com","Your password", "Thank you for registering at TheBigWhole. Your password is "+pw);
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					User u = new User(username, email,pw);
					
					if(QueryHelper.saveObject(u)){
						// Notification
						Notification.show("Successful - You have been registered.");
					}
				}
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
