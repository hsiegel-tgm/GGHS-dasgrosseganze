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
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
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
 * 
 * */
public class EditEvent extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	private Vector<User> usr = new Vector<User>();
	private List<User> m_allusers;
	private List<Abgestimmt> m_usersabgestimmt;

	private Button m_save, m_plus, m_minus;

	private VerticalLayout m_dateLayout;

	
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

	
	protected EditEvent(FatNavigator nav) {
		this.navigator = nav;
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
		textfield_eventname.setValue(m_event.getName());

		// Eventort
		textfield_eventort = new TextField("eventort:");
		textfield_eventort.setRequired(true);
		textfield_eventort.setRequiredError("Please set the Event Location");
		textfield_eventort.setValue(m_event.getOrt());

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
	
	public void zeiten(GridLayout gl){
		GridLayout gl2 = new GridLayout(1,1);
		m_dateLayout = new VerticalLayout();
		Label l = new Label("Possible Time Dates:");		

		Button button_plus = new Button("+"); //TODO
		Button button_minus = new Button("-"); //TODO
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(button_plus);
		hl.addComponent(button_minus);
		m_dateLayout.addComponent(l);
		m_dateLayout.addComponent(hl);
		for (Zeit z : m_times) {
			m_dateLayout.addComponent(newZeit(z.getAnfang()));
		}
		
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
		new Header(this,"Edit Event " + m_event.getName(), m_username, m_userid, navigator);

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
		
		userinvitedbasic(gl);
		
		gl.addComponent(new Label("&nbsp",ContentMode.HTML));

		Boolean b = QueryHelper.usershavevoted(m_event);

		if (b != null && b.booleanValue()) {
			datefix(gl);
		}

		if (b != null && !b.booleanValue()) {
			zeiten(gl);
		}

		gl.addComponent(new Label("&nbsp",ContentMode.HTML));
		
		buttons(gl);

		this.addComponent(gl);
		
	}
	




	public void userinvitedbasic(GridLayout gl) {
		Table m_tableUsers = new Table("Invited Users");
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

		gl.addComponent(m_tableUsers);
	}

	public void datefix(GridLayout gl) {
        m_fix = new ComboBox("FIX DATUM");
        m_fix.setInvalidAllowed(false);
        m_fix.setNullSelectionAllowed(false);
        for( Zeit z : m_times){
        	m_fix.addItem(z.getAnfang()+"");
        }
    
		gl.addComponent(m_fix);
	}

	public void save(){
		// fetching values
		String name = textfield_eventname.getValue();
		String ort = textfield_eventort.getValue();

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
//			for(Zeit z : m_times){
//				QueryHelper.delete(z);
//			}
//			
//			for (int i = 0; i < popupDateField_zeiten.size(); i++) {
//				Date d = popupDateField_zeiten.elementAt(i).getValue();
//				Zeit eventTime = new Zeit(d, d, m_event); //TODO endzeit
//			
//				// saving the time
//				QueryHelper.update(eventTime);
//			}
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
		Notification.show(("Event was saved..."));

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