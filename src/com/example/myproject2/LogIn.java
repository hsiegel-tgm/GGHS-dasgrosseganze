package com.example.myproject2;

import javax.servlet.annotation.WebServlet;

import model.User;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
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
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;









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

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;


/**
 * @author Hannah Siegel
 *  
 * meli
 * TODO JUnit
 * 

 * G&H
 * TODO Design pefekto
 * TODO GUI Test
 * 
 * Hannah
 * TODO Coding style
 * 
 * */

@SuppressWarnings("serial")
@Theme("myproject2")
public class LogIn extends VerticalLayout implements View {
	private TextField textfield_user;
	private  FatNavigator navigator;
	private Master master;

	/**
	 * @param nav
	 * @param m
	 */
	public  LogIn( FatNavigator nav, Master m) {
		this.navigator=nav;
		this.master=m;
		
		final VerticalLayout layout =this;
		layout.setMargin(true);

		layout.addComponent(new Label("LOG IN"));
		
		//Layout Components
		Button button_login = new Button("Go!");
        textfield_user = new TextField();
		Button button_NewUser = new Button("Register new User!");

        //Adding Components
		layout.addComponent(textfield_user);
		layout.addComponent(button_login);
		layout.addComponent(button_NewUser); 

		
		//Register new User Listener
		button_NewUser.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Variables.REGISTER);
			}
		});
		
		//Login Listener
		button_login.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				//Fetching value
				String inputUsername = textfield_user.getValue();

				//Fetching usernames
				Session session =  InitSession.getSession().openSession();
				List<?> res = session.getNamedQuery("getUsers").list();
				session.close();

				//checking if username is known
				boolean b = true;
				for (int i = 0; i < res.size(); ++i) {
					User user = (User) res.get(i);
					if((inputUsername.equals(user.getUsername()))&&b){
						//log in successful
						
						//Saving into Session
						try {
						    VaadinSession.getCurrent().getLockInstance().lock();
						    VaadinSession.getCurrent().setAttribute(Variables.USERNAME, inputUsername);
						} finally {
						    VaadinSession.getCurrent().getLockInstance().unlock();
						}
						
						//Navigate to startpage
						navigator.navigateTo(Variables.STARTPAGE);
						b = false;
					}
				}
				
				//Message
				if(b)
					layout.addComponent(new Label("Could not find username - please register! :)"));
				
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("WELCOME . . .");		
	}
}