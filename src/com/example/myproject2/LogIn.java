package com.example.myproject2;

import javax.servlet.annotation.WebServlet;

import model.DoodleEvent;
import model.Eingeladen;
import model.Kommentar;
import model.User;
import model.Zeit;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
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
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;



//import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;


/**
 * @author Hannah Siegel & Laurenz Gebauer
 * @version 2014-05-31
 * 
 * The class Login implements the Login Function of our program
 * */

@SuppressWarnings("serial")
@Theme("myproject2")
public class Login extends VerticalLayout implements View {
	//user Textfield
	private TextField textfield_user;
	private PasswordField textfield_password;
	
	//Navigator Object
	private FatNavigator navigator;
	
	//Layout
	private CustomLayout layout;
	
	/**
	 * Constructor of the class
	 * 
	 * @param nav
	 * @param m
	 */
	public Login(FatNavigator nav) {
		this.navigator=nav;
		
		//HTML Design
		InputStream layoutFile = getClass().getResourceAsStream("startpage.html");
		layout=null;
		try {
			layout = new CustomLayout(layoutFile);
		} catch (Exception e) {
			System.out.println("there was a problem");
		}
		layout.addStyleName("sada");

		//Layout Components
		Button okbutton = new Button("Login");
		okbutton.setClickShortcut(KeyCode.ENTER,null);
		textfield_user = new TextField();
		textfield_password = new PasswordField();
        Button neuerBenutzer = new Button("Neu?");
        textfield_user.setRequired(true);
		textfield_user.setRequiredError("Please set the Username");
		
        //Adding Components
		layout.addComponent(new Label("LOG IN"));
		layout.addComponent(textfield_user, "username");
		layout.addComponent(textfield_password, "password");
		layout.addComponent(okbutton,"okbutton");
		layout.addComponent(neuerBenutzer,"neuerBenutzer");
		
		//adding layout to webpage
		this.addComponent(layout);
		
		//Register Button Listener
		neuerBenutzer.addClickListener(new PinkShoes(navigator,
				Variables.REGISTER));
		
		//Login Listener
		okbutton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				//Fetching value
				String inputUsername = textfield_user.getValue();
				String pw = textfield_password.getValue();

				//fetching all the users out of the DB
				List<User> res = QueryHelper.executeBasic("getUsers");
								
				//checking if username is known
				boolean found_user = false;
				for(User user : res){
					if((inputUsername.equals(user.getUsername()))){
						found_user = true;

						if(user.getPassword().equals(pw)){

							//log in successful
							String user_id = user.getID().longValue()+"";
						
							//Navigate to startpage
							new PinkShoes(navigator,Variables.STARTPAGE,inputUsername,user_id).navigation();	
						}
						else{
							Notification.show("False password",Notification.TYPE_WARNING_MESSAGE);
						}
					}
				}
				
				//Message
				if(!found_user){
					Notification.show("Could not find username - please register! :)");
				}
			}
		});
		
		
	}

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
	}
}