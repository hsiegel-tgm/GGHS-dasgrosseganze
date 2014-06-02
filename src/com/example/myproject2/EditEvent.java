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

import model.Abgestimmt;
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
import com.vaadin.ui.ComboBox;
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
	private List<Abgestimmt> m_usersabgestimmt;

	private Button m_save, m_plus, m_minus;

	private String m_username, m_userid, m_eventid;

	// Textfields
	private TextField textfield_eventname, textfield_eventort;

	//private PopupDateField m_fix;
	private ComboBox m_fix;

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
		textfield_eventname.setRequired(true);
		// Eventort
		textfield_eventort = new TextField();
		textfield_eventort.setValue(m_event.getOrt());
		textfield_eventort.setRequiredError("Please set the Event Location");
		textfield_eventort.setRequired(true);

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
			newZeit(z.getAnfang());
		}
		m_plus = new Button("+");
		m_minus = new Button("-");

		this.addComponent(m_plus);
		this.addComponent(m_minus);

		// Plus Button Listener
		m_plus.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				newZeit(new Date());
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
		twinColSet_friends.setRows(m_allusers.size()); // TODO set table rows??

		this.addComponent(twinColSet_friends);

	}

	public void userinvitedbasic() {
		// TODO event anzeigen? :)
		Table m_tableUsers = new Table("All Users");
		m_tableUsers.setSelectable(false);
		m_tableUsers.setMultiSelect(false);
		m_tableUsers.setImmediate(true);
		m_tableUsers.setColumnReorderingAllowed(true);
		m_tableUsers.setColumnCollapsingAllowed(true);
		m_tableUsers.addContainerProperty("User ID", Long.class, null);
		m_tableUsers.addContainerProperty("Username", String.class, null);
		m_tableUsers.addContainerProperty("Email", String.class, null);

		int i = 0;
		for (Eingeladen e : m_usersinvited) {
			User u = e.getUser();
			m_tableUsers.addItem(
					new Object[] { u.getID(), u.getUsername(), u.geteMail() },
					new Integer(i));
			i++;
		}

		this.addComponent(m_tableUsers);
	}

	public void newZeit(Date d) {
		PopupDateField p = new PopupDateField();
		p.setValue(d);
		p.setImmediate(true);
		p.setTimeZone(TimeZone.getTimeZone("UTC"));
		p.setLocale(Locale.US);
		p.setResolution(Resolution.HOUR);
		this.addComponent(p);
		popupDateField_zeiten.add(p);
	}

//	public Boolean usershavevoted() {
//		Zeit z = m_times.get(0);
//		List<Abgestimmt> a = QueryHelper.executeId(Variables.GETABGESTIMMT_BYEVENTID, z.getID().longValue() + "");
//		
//		if (a == null)
//			return null;
//		else if (a.size() == m_usersinvited.size())
//			return true;
//		else
//			return false;
//	}

	public void datefix() {
		this.addComponent(new Label(""));
		

        m_fix = new ComboBox("FIX DATUM");
        m_fix.setInvalidAllowed(false);
        m_fix.setNullSelectionAllowed(false);
        for( Zeit z : m_times){
        	m_fix.addItem(z.getAnfang()+"");
        }
    

    
		
//		
//		m_fix = new PopupDateField();
//		m_fix.setValue(new Date());
//		m_fix.setImmediate(true);
//		m_fix.setTimeZone(TimeZone.getTimeZone("UTC"));
//		m_fix.setLocale(Locale.US);
//		m_fix.setResolution(Resolution.HOUR);
		this.addComponent(m_fix);
	}

	public void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);

		layout.addComponent(new Label("EDITING EVENT: " + m_event.getName()));

		textfields();

		buttons();

		Boolean b = QueryHelper.usershavevoted(m_event);
		System.out.println("B::" + b.booleanValue());

		if (b != null && b.booleanValue()) {
			datefix();
		}

		if (b != null && !b.booleanValue()) {
			zeiten();
		}

		userinvitedbasic();

		// Send Button Listener
		m_save.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// fetching values
				String name = textfield_eventname.getValue();
				String ort = textfield_eventort.getValue();

				// Collection<?> invitedUsers = (Collection<?>)
				// twinColSet_friends.getValue();
				// Vector vector_invitedUsers = new Vector();
				// vector_invitedUsers.addAll(invitedUsers);

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
							Notification.show("Please set a Future Date - Date Input Field number:"+ (i + 1),Notification.TYPE_WARNING_MESSAGE);
							valid = false;
						}
					}
					
					//TODO fix datum check wenn leer?
					
					// // Checking if there is at least one User invited
					// if (vector_invitedUsers.size() == 0) {
					// valid = false;
					// layout.addComponent(new Label(
					// "Please Invite at least one User"));
					// }

				} catch (InvalidValueException e) {
					valid = false;
					Notification.show(e.getMessage(),Notification.TYPE_WARNING_MESSAGE);
				}

				DoodleEvent eve = m_event;

				if (m_fix != null) {
			        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    Date d = null;
					try {
						d = simpleDateFormat.parse(m_fix.getValue()+"");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					eve.setFixDatum(d);
					QueryHelper.notificate(m_event, "Dear User, the event " +m_event.getName()+" just got a final date: "+d);
				}

				eve.setName(name);
				eve.setOrt(ort);
				QueryHelper.update(eve);
				
				//TODO nur wenn wirklich aenderung..
				QueryHelper.notificate(eve, "Dear User, the event " +eve.getName()+" just got edited");

				//TODO schirch arg nein alles schleeeecht
				//if(usershavevoted() != null && !usershavevoted()){
//					for(Zeit z : m_times){
//						QueryHelper.delete(z);
//					}
//					
//					for (int i = 0; i < popupDateField_zeiten.size(); i++) {
//						Date d = popupDateField_zeiten.elementAt(i).getValue();
//						Zeit eventTime = new Zeit(d, d, m_event); //TODO endzeit
//					
//						// saving the time
//						QueryHelper.update(eventTime);
//					}
				//}

				
				
				
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

		m_times = QueryHelper.executeId(Variables.GETZEIT_BYEVENTID,
				m_event.getID() + "");

		m_allusers = QueryHelper.executeBasic(Variables.GETUSER);

		m_usersinvited = QueryHelper.executeId(
				Variables.GETEINGELADEN_BYEVENTID, m_event.getID() + "");

		m_usersabgestimmt = QueryHelper.executeId(
				Variables.GETABGESTIMMT_BYEVENTID, m_event.getID() + "");

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