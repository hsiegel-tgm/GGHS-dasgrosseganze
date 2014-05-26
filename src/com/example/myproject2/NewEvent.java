

package com.example.myproject2;

import javax.servlet.annotation.WebServlet;

import model.DoodleEvent;
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
 * @version 2014-05-26 Hannah erstellt
 * @author Hannah Siegel
 * 
 * TODO JUnit
 * TODO Komment
 * TODO Design pefekto
 * TODO GUI Test
 * TODO Coding style
 * 
 * */
public class NewEvent extends VerticalLayout implements View {
	//Textfields
	private TextField textfield_eventname,textfield_eventort;
	//Navigator and master object
	private  FatNavigator navigator;
	private Master master;
	
	/**
	 * Constructor
	 * 
	 * @param nav navigator object
	 * @param m master object
	 */
	protected  NewEvent(FatNavigator nav,Master m) {
		this.navigator = nav;
		this.master = m;
		
		final VerticalLayout layout = this;
		layout.setMargin(true);

		layout.addComponent(new Label("NEW EVENT"));
		
		//username Textfield
		layout.addComponent(new Label("eventname:"));
		textfield_eventname = new TextField();
		layout.addComponent(textfield_eventname);

		layout.addComponent(new Label("eventort:"));
		textfield_eventort = new TextField();
		layout.addComponent(textfield_eventort);


		//Send and Back Button
		Button button_save = new Button("Save");
		Button button_back = new Button("Back");
		layout.addComponent(button_save);		
		layout.addComponent(button_back);
		
		//Back Button Listener
		button_back.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Variables.STARTPAGE);
			}
		});
		
		//Send Button Listener
		button_save.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				//fetching values
				String name = textfield_eventname.getValue();	
				String ort = textfield_eventname.getValue();	

				User admin = new User(); //TODO id lalal not lik thot
				
				//new User object
				DoodleEvent e = new DoodleEvent(name,ort,admin);

				//Database connection
				Session session =  InitSession.getSession().openSession();
				Transaction t = session.beginTransaction();
				t.begin();
				//saving user
				session.save(e);
				t.commit();
				session.close();
				
				//Notification
				layout.addComponent(new Label("Event was saved"));

			}
		});
	}


	@Override
	public void enter(ViewChangeEvent event) {
	}
	

	
}



