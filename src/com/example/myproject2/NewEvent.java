package com.example.myproject2;

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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import model.DoodleEvent;
import model.Eingeladen;
import model.User;
import model.Zeit;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @version 2014-05-26 Hannah erstellt
 * @author Hannah Siegel
 * 
 *         TODO JUnit 
TODO Design pefekto 
TODO GUI Test 
//TODO Minus Button
 * TODO nav, master obj??
 * 
 * 
 * */
public class NewEvent extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	
	// Textfields
	private TextField textfield_eventname, textfield_eventort;
	
	// Datefields
	private Vector<PopupDateField> popupDateField_zeiten = new Vector<PopupDateField>();
	
	//Friends
	private TwinColSelect twinColSet_friends;

	// Navigator and master object
	private FatNavigator navigator;
	private Master master;

	/**
	 * Constructor
	 * 
	 * @param nav
	 *            navigator object
	 * @param m
	 *            master object
	 */
	protected NewEvent(FatNavigator nav, Master m) {
		this.navigator = nav;
		this.master = m;

		final VerticalLayout layout = this;
		layout.setMargin(true);

		// Eventname
		textfield_eventname = new TextField();
		textfield_eventname.setRequired(true);
		textfield_eventname.setRequiredError("Please set the Eventname");

		// Eventort
		textfield_eventort = new TextField();
		//textfield_eventort.setValidationVisible(true);
		textfield_eventort.setRequired(true);
		textfield_eventort.setRequiredError("Please set the Event Location");		
		
		// Buttons
		Button button_save = new Button(Variables.SAVE);
		Button button_back = new Button(Variables.BACK);
		Button button_logout = new Button(Variables.LOGOUT);
		Button button_plus = new Button("+");

		// Initialize first DateField
		popupDateField_zeiten.add(new PopupDateField());
		popupDateField_zeiten.elementAt(0).setValue(new Date());
		popupDateField_zeiten.elementAt(0).setImmediate(true);
		popupDateField_zeiten.elementAt(0).setTimeZone(
				TimeZone.getTimeZone("UTC"));
		popupDateField_zeiten.elementAt(0).setLocale(Locale.US);
		popupDateField_zeiten.elementAt(0).setResolution(Resolution.MINUTE);
		
		
		// TODO SESSION das gehoert auf jetztigen gaeendert
		String username_TESTING = "user2";
		
		//Users availaible for Invitation
		twinColSet_friends = new TwinColSelect();
		twinColSet_friends.setNullSelectionAllowed(false);
		twinColSet_friends.setMultiSelect(true);
		twinColSet_friends.setImmediate(true);
		twinColSet_friends.setLeftColumnCaption("Availaible Users");
		twinColSet_friends.setRightColumnCaption("Invited Users");
		
		// Database Connection
		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();

		// create query
		Query query_allUsers = (Query) session.getNamedQuery("getUsers");

		// run query and fetch reslut
		List<?> result_allUsers = query_allUsers.list();

		//Saving Users into Collection
		for (int i = 0; i < result_allUsers.size(); i++) {
			User u = (User) result_allUsers.get(i);
			//Filtering the admin user
			if (!(u.getUsername().equals(username_TESTING))) {
				twinColSet_friends.addItem(i);
				twinColSet_friends.setItemCaption(i, "" + u.getUsername());
			}
		}
		twinColSet_friends.setRows(result_allUsers.size());

		//closing Database
		t.commit();
		session.close();

		// Adding the Components
		Label l = new Label("NEW EVENT");
		l.setSizeFull();
		layout.addComponent(l);
		layout.addComponent(new Label("Eventname:"));
		layout.addComponent(textfield_eventname);
		layout.addComponent(new Label("Eventlocation:"));
		layout.addComponent(textfield_eventort);
		layout.addComponent(popupDateField_zeiten.elementAt(0));
		layout.addComponent(twinColSet_friends);
		layout.addComponent(button_plus);
		layout.addComponent(button_save);
		layout.addComponent(button_back);
		layout.addComponent(button_logout);

		// Back Button Listener
		button_back.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Variables.STARTPAGE);
			}
		});

		// Logout Button Listener
		button_logout.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Variables.LOGIN);
			}
		});
		
		// Plus Button Listener
		button_plus.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				// New Date Possibilities
				popupDateField_zeiten.add(new PopupDateField());
				popupDateField_zeiten.lastElement().setValue(new Date());
				popupDateField_zeiten.lastElement().setImmediate(true);
				popupDateField_zeiten.lastElement().setTimeZone(
						TimeZone.getTimeZone("UTC"));
				popupDateField_zeiten.lastElement().setLocale(Locale.US);
				popupDateField_zeiten.lastElement().setResolution(
						Resolution.MINUTE);
				layout.addComponent(popupDateField_zeiten.lastElement());

			}
		});

		
		// Send Button Listener
		button_save.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// fetching values
				String name = textfield_eventname.getValue();
				String ort = textfield_eventort.getValue();

				//fetching invited Users
				Collection<?> invitedUsers = (Collection<?>) twinColSet_friends.getValue(); 
				Vector vector_invitedUsers = new Vector();
				vector_invitedUsers.addAll(invitedUsers);

				//Validation
				boolean valid = true;

				try {
					//validating text Fields
					textfield_eventort.validate();
					textfield_eventname.validate();
					
					// validating all the Dates
					for (int i = 0; i < popupDateField_zeiten.size(); i++) {
						// checking if date lies in the past
						Date d = popupDateField_zeiten.elementAt(i).getValue();
						if (d.before(new Date())) {
							popupDateField_zeiten.elementAt(i).setValidationVisible(true);
							layout.addComponent(new Label("Please set a Future Date - Date Input Field number:"+(i+1)));
							valid=false;
						}
					}
					
					//Checking if there is at least one User invited
					if(vector_invitedUsers.size()==0){
						valid =  false;
						layout.addComponent(new Label("Please Invite at least one User"));
					}
					
				} catch (InvalidValueException e) {
					valid = false;
					layout.addComponent(new Label(e.getMessage()));
				}

				if (valid) {
					// Database Connection
					Session session = InitSession.getSession().openSession();
					Transaction t = session.beginTransaction();
					t.begin();

					// create query
					Query q = (Query) session.getNamedQuery("getSpecificUser");

					// setting parameters
					q.setString("id", "1"); // TODO SESSION - User session

					// run query and fetch reslut
					List<?> res = q.list();

					// User
					User admin = (User) res.get(0);

					// new User object
					DoodleEvent e = new DoodleEvent(name, ort, admin);

					// Saving Event
					session.save(e);

					// saving all the Dates
					for (int i = 0; i < popupDateField_zeiten.size(); i++) {
						// checking if date lies in the Future
						Date d = popupDateField_zeiten.elementAt(i).getValue();
						if (d.after(new Date())) {
							Zeit eventTime = new Zeit(d, d, e); // TODO endzeit
							// saving the time
							session.save(eventTime);
						}
					}

					//Saving Invited Users
					for (int j = 0; j < vector_invitedUsers.size(); ++j) {
						int user_nummer = ((Integer) (vector_invitedUsers.elementAt(j))) + 1;

						// create query
						Query query_InvitedUser = (Query) session.getNamedQuery("getSpecificUser");

						// setting parameters
						query_InvitedUser.setString("id", "" + user_nummer);

						// run query and fetch result
						List<?> result_3 = query_InvitedUser.list();

						// User
						User invitedUser = (User) result_3.get(0);

						//Saving into database
						Eingeladen eingeladen = new Eingeladen(invitedUser, e);
						session.save(eingeladen);
					}

					t.commit();
					session.close();

					// Notification
					layout.addComponent(new Label("Event was saved..."));
				}
			}
		});

	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}