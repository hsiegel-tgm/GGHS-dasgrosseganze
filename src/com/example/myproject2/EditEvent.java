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
import com.vaadin.ui.Table;
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
public class EditEvent extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private Vector<User> usr = new Vector<User>();
	private List<User> m_allusers;
	private Button m_save, m_plus, m_minus;

	private String m_username, m_userid, m_eventid;

	// Textfields
	private TextField textfield_eventname, textfield_eventort;

	// Datefields
	private Vector<PopupDateField> popupDateField_zeiten = new Vector<PopupDateField>();

	// Friends
	private TwinColSelect twinColSet_friends;

	private DoodleEvent m_event;

	private List<Zeit> m_times;
	private List<Eingeladen> m_usersinvited;

	// Navigator and master object
	private FatNavigator navigator;

	/**
	 * Constructor
	 * 
	 * @param nav
	 *            navigator object
	 * @param m
	 *            master object
	 */
	protected EditEvent(FatNavigator nav) {
		this.navigator = nav;
	}

	public void textfields() {
		// Eventname
		textfield_eventname = new TextField();
		textfield_eventname.setRequiredError("Please set the Eventname");
		textfield_eventname.setValue(m_event.getName());

		// Eventort
		textfield_eventort = new TextField();
		textfield_eventort.setValue(m_event.getOrt());
		textfield_eventort.setRequiredError("Please set the Event Location");

		this.addComponent(new Label("Eventname:"));
		this.addComponent(textfield_eventname);
		this.addComponent(new Label("Eventlocation:"));
		this.addComponent(textfield_eventort);
	}

	public void buttons() {
		// Buttons
		m_save = new Button(Variables.SAVE);
		Button button_back = new Button(Variables.BACK);
		Button button_logout = new Button(Variables.LOGOUT);
		m_plus = new Button("+");
		m_minus = new Button("-");

		this.addComponent(m_plus);
		this.addComponent(m_minus);
		this.addComponent(m_save);
		this.addComponent(button_back);
		this.addComponent(button_logout);

		// Back Button Listener
		button_back.addClickListener(new PinkShoes(navigator,
				Variables.STARTPAGE, m_username, m_userid));

		// Logout Button Listener
		button_logout.addClickListener(new PinkShoes(navigator,
				Variables.LOGIN, m_username, m_userid));
	}

	public void zeiten() {
		for (Zeit z : m_times) {
			PopupDateField p = new PopupDateField();
			p.setValue(z.getAnfang());
			p.setImmediate(true);
			p.setTimeZone(TimeZone.getTimeZone("UTC"));
			p.setLocale(Locale.US);
			p.setResolution(Resolution.HOUR);
			this.addComponent(p);
			popupDateField_zeiten.add(p);
		}
	}

	public void userinvitedwuuuhu() {
		// Users availaible for Invitation
		twinColSet_friends = new TwinColSelect();
		twinColSet_friends.setNullSelectionAllowed(false);
		twinColSet_friends.setMultiSelect(true);
		twinColSet_friends.setImmediate(true);
		twinColSet_friends.setLeftColumnCaption("Availaible Users");
		twinColSet_friends.setRightColumnCaption("Invited Users");

		// Saving Users into Collection
		boolean founduser = false;
		for (int i = 0; i < m_allusers.size(); i++) {
			User u = (User) m_allusers.get(i);
			// Filtering the admin user
			if (!(u.getUsername().equals(m_username))) {
				twinColSet_friends.addItem(!founduser ? i : i - 1);
				usr.add(u);
				twinColSet_friends.setItemCaption(!founduser ? i : i - 1, ""
						+ u.getUsername());
			} else {
				founduser = true;
			}
		}
		twinColSet_friends.setRows(m_allusers.size()); //TODO set table rows??

		this.addComponent(twinColSet_friends);

	}

	public void userinvitedbasic() {
		Table m_tableUsers = new Table("All Users");
		m_tableUsers.setSelectable(false);
		m_tableUsers.setMultiSelect(false);
		m_tableUsers.setImmediate(true);
		m_tableUsers.setColumnReorderingAllowed(true);
		m_tableUsers.setColumnCollapsingAllowed(true);
		m_tableUsers.addContainerProperty("User ID", Long.class, null);
		m_tableUsers.addContainerProperty("Username", String.class, null);
		m_tableUsers.addContainerProperty("Email", String.class, null);
		//m_tableUsers.addContainerProperty("Ausladen", Button.class, null); //TODO

		
		int i = 0;
		for (Eingeladen e : m_usersinvited) {
			User u = e.getUser();
			Button b = new Button("ausladen");
			m_tableUsers.addItem(new Object[] { u.getID() , u.getUsername() , u.geteMail(),b}, new Integer(i));
			i++;
			b.addClickListener(new Button.ClickListener(){
				@Override
				public void buttonClick(ClickEvent event) {
					Notification.show("helllo");					
				}
			});
		}
		
	
		this.addComponent(m_tableUsers);

	}

	public void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);

		layout.addComponent(new Label("EDITING EVENT: " + m_event.getName()));

		textfields();

		buttons();

		zeiten();
		
		userinvitedbasic();

		// Plus Button Listener
		m_plus.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				PopupDateField p = new PopupDateField();
				// New Date Possibilities
				p.setValue(new Date());
				p.setImmediate(true);
				p.setTimeZone(TimeZone.getTimeZone("MEZ"));
				p.setLocale(Locale.US);
				p.setResolution(Resolution.MINUTE);
				layout.addComponent(p);
				popupDateField_zeiten.add(p);
			}
		});

		// Minus Button Listener
		m_minus.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (popupDateField_zeiten.size() > 1) {
					// remove date possibility
					PopupDateField p = popupDateField_zeiten
							.elementAt(popupDateField_zeiten.size() - 1);
					EditEvent.this.removeComponent(p);
					popupDateField_zeiten.remove(p);
				}
			}
		});

		// Send Button Listener
		m_save.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// fetching values
				String name = textfield_eventname.getValue();
				String ort = textfield_eventort.getValue();

				Collection<?> invitedUsers = (Collection<?>) twinColSet_friends
						.getValue();
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
							popupDateField_zeiten.elementAt(i)
									.setValidationVisible(true);
							layout.addComponent(new Label(
									"Please set a Future Date - Date Input Field number:"
											+ (i + 1)));
							valid = false;
						}
					}

					// Checking if there is at least one User invited
					if (vector_invitedUsers.size() == 0) {
						valid = false;
						layout.addComponent(new Label(
								"Please Invite at least one User"));
					}

				} catch (InvalidValueException e) {
					valid = false;
					layout.addComponent(new Label(e.getMessage()));
				}

				DoodleEvent eve = m_event;

				eve.setName(name);
				eve.setName(ort);

				QueryHelper.update(eve);
				// t2.commit();
				// session2.close();

				// QueryHelper.saveObject(eve);

				//
				// // saving all the Dates
				// for (int i = 0; i < popupDateField_zeiten.size(); i++) {
				// // checking if date lies in the Future
				// Date d = popupDateField_zeiten.elementAt(i).getValue();
				// if (d.after(new Date())) {
				// Zeit eventTime = new Zeit(d, d, e); // TODO endzeit
				// // saving the time
				// session.save(eventTime);
				// }
				// }

				// //Database connection
				// Session session2 = InitSession.getSession().openSession();
				// Transaction t2 = session2.beginTransaction();
				// t2.begin();
				//
				// //Saving invited users into DB
				// for (int j = 0; j < vector_invitedUsers.size(); ++j) {
				// //fetching number of added User
				// int nummer = ((Integer) (vector_invitedUsers.elementAt(j)));
				//
				// //saving into DB
				// Eingeladen eingeladen = new Eingeladen(usr.elementAt(nummer),
				// e);
				// session2.save(eingeladen);
				// }
				//
				// t2.commit();
				// session2.close();

				// Notification
				layout.addComponent(new Label("Event was saved..."));
			}
			// }
		});
	}

	public void executeQuerys() {

		List<?> l = QueryHelper.executeId(Variables.GETEVENT_BYID, m_eventid);

		m_event = (DoodleEvent) l.get(0);

		m_times = QueryHelper.executeId(Variables.GETZEIT_BYEVENTID,m_event.getID() + "");

		m_allusers = QueryHelper.executeBasic(Variables.GETUSER);
		
		m_usersinvited = QueryHelper.executeId(Variables.GETEINGELADEN_BYEVENTID,m_event.getID()+"");

		// m_comments = QueryHelper.executeId(Variables.GETKOMMENTS,
		// m_event.getID()+"");
		//
		// m_eingeladene =
		// QueryHelper.executeId("getEingeladenforSpecificEvent", m_eventque);
		//

	}

	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		m_username = event.getParameters().split("/")[0];
		m_userid = event.getParameters().split("/")[1];
		m_eventid = event.getParameters().split("/")[2];
		executeQuerys();
		init();
	}
}