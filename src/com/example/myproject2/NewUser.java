
package com.example.myproject2;

import javax.servlet.annotation.WebServlet;

import model.User;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
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
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

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
 * TODO JUnit
 * TODO Komment
 * TODO Design pefekto
 * TODO GUI Test
 * TODO Variablen
 * TODO Coding style
 * 
 * TODO moeglich
 * password
 * email mit password
 * 
 * 
 * */
public class NewUser extends VerticalLayout implements View {
	//Textfields
	private TextField textfield_username,textfield_email;
	//Navigator and master object
	private  FatNavigator navigator;
	private Master master;
	
	/**
	 * Constructor
	 * 
	 * @param nav navigator object
	 * @param m master object
	 */
	protected  NewUser(FatNavigator nav,Master m) {
		this.navigator = nav;
		this.master=m;
		
		final VerticalLayout layout = this;
		layout.setMargin(true);

		layout.addComponent(new Label("NEW USER"));
		
		//username Textfield
		layout.addComponent(new Label("username:"));
        textfield_username = new TextField();
		layout.addComponent(textfield_username);

	

		//email Textfield
		layout.addComponent(new Label("email:"));
        textfield_email = new TextField();
		layout.addComponent(textfield_email);

		//Send and Back Button
		Button button_send = new Button("Send");
		Button button_back = new Button("Back");
		layout.addComponent(button_send);		
		layout.addComponent(button_back);
		
		//Back Button Listener
		button_back.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Variables.LOGIN);
			}
		});
		
		//Send Button Listener
		button_send.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				//fetching values
				String username = textfield_username.getValue();	
				String email = textfield_email.getValue();		
				
				//new User object
				User u  = new User(username,email);

				//Database connection
				Session session =  InitSession.getSession().openSession();
				Transaction t = session.beginTransaction();
				t.begin();
				//saving user
				session.save(u);
				t.commit();
				session.close();
				
				//Notification
				layout.addComponent(new Label("User was saved"));

			}
		});
	}


	@Override
	public void enter(ViewChangeEvent event) {
	}
	

	
}



