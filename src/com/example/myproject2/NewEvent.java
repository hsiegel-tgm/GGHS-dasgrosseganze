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
import model.DoodleNotification;
import model.Eingeladen;
import model.User;
import model.Zeit;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
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
 *         TODO JUnit TODO Design pefekto TODO GUI Test //TODO Minus Button TODO
 *         nav, master obj??
 * 
 * 
 * */
public class NewEvent extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private VerticalLayout m_dateLayout;
	
	private Vector<User> usr = new Vector<User>();
	
	private String m_username, m_userid;

	// Textfields
	private TextField textfield_eventname, textfield_eventort;

	// Datefields
	private Vector<PopupDateField> popupDateField_zeiten = new Vector<PopupDateField>();

	// Friends
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
	}

	public PopupDateField newZeit(Date d) {
		PopupDateField p = new PopupDateField();
		p.setValue(d);
		p.setImmediate(true);
		p.setTimeZone(TimeZone.getTimeZone("UTC"));
		p.setLocale(Locale.US);
		p.setResolution(Resolution.HOUR);
		popupDateField_zeiten.add(p);
		return p;
	}

	public void textFields(GridLayout gl){
		GridLayout gl2 = new GridLayout(1,3);
		gl2.setWidth("60%");
		gl2.setMargin(true);
		gl2.setSpacing(true);
		gl2.setRowExpandRatio(0, 0.3f);
		gl2.setRowExpandRatio(1, 0.1f);
		gl2.setRowExpandRatio(2, 0.6f);
		gl2.addStyleName(Reindeer.LAYOUT_WHITE);
		
		// Eventname
		textfield_eventname = new TextField("eventname:");
		textfield_eventname.setRequired(true);
		textfield_eventname.setRequiredError("Please set the Eventname");

		// Eventort
		textfield_eventort = new TextField("eventort:");
		textfield_eventname.setRequired(true);
		textfield_eventort.setRequiredError("Please set the Event Location");
		
		gl2.addComponent(textfield_eventname);	
		gl2.addComponent(new Label("&nbsp",ContentMode.HTML));
		gl2.addComponent(textfield_eventort);
		gl.addComponent(gl2);

	}
	
	public void buttons(GridLayout gl){
		Button button_save = new Button(Variables.SAVE);
		Button button_back = new Button(Variables.BACK);
	
		GridLayout gl2 = new GridLayout(3,1);
		gl2.setWidth("30%");
		gl2.setMargin(true);
		gl2.setSpacing(true);
		gl2.setColumnExpandRatio(0, 0.4f);
		gl2.setColumnExpandRatio(1, 0.2f);
		gl2.setColumnExpandRatio(2, 0.4f);
		gl2.addStyleName(Reindeer.LAYOUT_WHITE);

		gl2.addComponent(button_back);	
		gl2.addComponent(new Label("&nbsp",ContentMode.HTML));
		gl2.addComponent(button_save);	
		gl.addComponent(gl2);
		
		// Back Button Listener
		button_back.addClickListener(new PinkShoes(navigator,Variables.STARTPAGE, m_username, m_userid));
	
		// Send Button Listener
		button_save.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				save();
			}
		});
	}
	
	public void users(GridLayout gl){
		// Users availaible for Invitation
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

		// Saving Users into Collection
		boolean founduser = false;
		for (int i = 0; i < result_allUsers.size(); i++) {
			User u = (User) result_allUsers.get(i);
			// Filtering the admin user
			if (!(u.getUsername().equals(m_username))) {
				twinColSet_friends.addItem(!founduser ? i : i-1);
				usr.add(u);
				twinColSet_friends.setItemCaption(!founduser ? i : i-1, "" + u.getUsername());
			} else {
				founduser = true;
			}
		}
		twinColSet_friends.setRows(result_allUsers.size());

		// closing Database
		t.commit();
		session.close();

		GridLayout gl2 = new GridLayout(1,1);
		gl2.setWidth("60%");
		gl2.setMargin(true);
		gl2.setSpacing(true);
		gl2.setRowExpandRatio(0, 0.5f);
		gl2.addStyleName(Reindeer.LAYOUT_WHITE);
		gl2.addComponent(twinColSet_friends);
		gl.addComponent(gl2);
	}
	
	public void zeiten(GridLayout gl){
		GridLayout gl2 = new GridLayout(1,1);
		m_dateLayout = new VerticalLayout();
		Label l = new Label("Possible Time Dates:");		

		Button button_plus = new Button("+"); //TODO
		Button button_minus = new Button("-"); //TODO
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(button_plus);
		hl.addComponent(button_minus);
		m_dateLayout.addComponent(hl);
		
		m_dateLayout.addComponent(l);
		m_dateLayout.addComponent(newZeit(new Date()));
		
		// Minus Button Listener
		button_minus.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (popupDateField_zeiten.size() > 1) {
					//remove date possibility
					PopupDateField p = popupDateField_zeiten.elementAt(popupDateField_zeiten.size() - 1);
					m_dateLayout.removeComponent(p);
					popupDateField_zeiten.remove(p);
				}
			}
		});
		
		// Plus Button Listener
		button_plus.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				m_dateLayout.addComponent(newZeit(new Date()));
			}
		});
		
		gl2.setWidth("60%");
		gl2.setMargin(true);
		gl2.setSpacing(true);
		gl2.setRowExpandRatio(0, 0.5f);
		gl2.addStyleName(Reindeer.LAYOUT_WHITE);
		gl2.addComponent(m_dateLayout);
		gl.addComponent(gl2);
	}
	
	public void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);
		layout.addStyleName(Reindeer.LAYOUT_WHITE);
		new Header(this,"New Event", m_username, m_userid, navigator);

		GridLayout gl = new GridLayout(1,7);
		gl.setWidth("100%");
		gl.setMargin(true);
		gl.setSpacing(true);
		gl.setRowExpandRatio(0, 0.2f); //text
		gl.setRowExpandRatio(1, 0.1f); //space
		gl.setRowExpandRatio(2, 0.2f); //users
		gl.setRowExpandRatio(3, 0.1f); //space
		gl.setRowExpandRatio(4, 0.2f); //times
		gl.setRowExpandRatio(5, 0.1f); //space
		gl.setRowExpandRatio(6, 0.1f); //buttons
		
		textFields(gl);
		
		gl.addComponent(new Label("&nbsp",ContentMode.HTML));
		
		users(gl);
		
		gl.addComponent(new Label("&nbsp",ContentMode.HTML));

		zeiten(gl);

		gl.addComponent(new Label("&nbsp",ContentMode.HTML));
		
		buttons(gl);

		this.addComponent(gl);
		
	
		//TODO
		
		
	}

	public void save(){
		// fetching values
		String name = textfield_eventname.getValue();
		String ort = textfield_eventort.getValue();

		Collection<?> invitedUsers = (Collection<?>) twinColSet_friends.getValue();
		Vector vector_invitedUsers = new Vector();
		vector_invitedUsers.addAll(invitedUsers);
		
		// Validation
		boolean valid = true;

		try {
			// validating text Fields
			textfield_eventort.validate();
			textfield_eventname.validate();
			
			// validating all the Dates
			for (int i = 0; i < popupDateField_zeiten.size(); i++) {
				// checking if date lies in the past
				Date d = popupDateField_zeiten.elementAt(i).getValue();
				if (d.before(new Date())) {
					popupDateField_zeiten.elementAt(i).setValidationVisible(true);
					Notification.show("Please set a Future Date - Date Input Field number:"+ (i + 1),Notification.Type.WARNING_MESSAGE);
					valid = false;
				}
			}

			// Checking if there is at least one User invited
			if (vector_invitedUsers.size() == 0) {
				valid = false;
				Notification.show("Please Invite at least one User",Notification.Type.WARNING_MESSAGE);
			}

		} catch (InvalidValueException e) {
			valid = false;
			Notification.show(e.getMessage(),Notification.Type.WARNING_MESSAGE);
		}

		if (valid) {
			//TODO
			// Database Connection
			Session session = InitSession.getSession().openSession();
			Transaction t = session.beginTransaction();
			t.begin();

			// create query
			Query q = (Query) session.getNamedQuery("getSpecificUser");

			// setting parameters
			q.setString("id", m_userid);

			// run query and fetch reslut
			List<?> res = q.list();

			// User
			User admin = (User) res.get(0);

			// new User object
			DoodleEvent e = new DoodleEvent(name, ort, admin);

			// Saving Event
			session.save(e);

			System.out.println("popupDateField_zeiten.size: "+ popupDateField_zeiten.size());
			
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

			t.commit();
			session.close();

			//Saving invited users into DB
			for (int j = 0; j < vector_invitedUsers.size(); ++j) {
				//fetching number of added User
				int nummer = ((Integer) (vector_invitedUsers.elementAt(j)));
				User u = usr.elementAt(nummer);
				//saving into DB
				Eingeladen eingeladen = new Eingeladen(u, e);						
				QueryHelper.saveObject(eingeladen);
			}
			
			QueryHelper.notificate(e, "Dear User, you just got invited to "+e.getName());

			// Notification
			Notification.show("Event was saved...",Notification.Type.HUMANIZED_MESSAGE);

		}
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		m_username = event.getParameters().split("/")[0];
		m_userid = event.getParameters().split("/")[1];
		init();
	}
}