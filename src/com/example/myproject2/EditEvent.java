package com.example.myproject2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import model.DoodleEvent;
import model.Eingeladen;
import model.User;
import model.Zeit;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * The class EditEvent is the View Component of the Edit Event Page
 * 
 * @author Hannah Siegel & Laurenz Gebauer
 * 
 * @version 2014-05-26 Hannah erstellt
 *
 * */
public class EditEvent extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	//DateLayout
	private VerticalLayout m_dateLayout;
	
	//parameters
	private String m_username, m_userid, m_eventid;

	// Textfields
	private TextField textfield_eventname, textfield_eventort;

	//fix DateField
	private ComboBox m_fix;

	// Datefields
	private Vector<PopupDateField> popupDateField_zeiten = new Vector<PopupDateField>();

	//Event
	private DoodleEvent m_event;

	//times
	private List<Zeit> m_times;
	
	//invited users
	private List<Eingeladen> m_usersinvited;

	// Navigator object
	private FatNavigator m_navigator;

	/**
	 * @param nav navigator object
	 */
	protected EditEvent(FatNavigator nav) {
		this.m_navigator = nav;
	}

	/**
	 *  The function init calls the other methods and puts everything into a neat GridLayout 
	 */
	public void init() {
		this.setMargin(true);
		this.addStyleName(Reindeer.LAYOUT_WHITE);
		
		//inluding Header
		new Header(this,"Edit Event '" + m_event.getName()+"'", m_username, m_userid, m_navigator);

		//GridLayout init
		GridLayout gl = new GridLayout(1,7);
		gl.setWidth("100%");
		gl.setMargin(true);
		gl.setSpacing(true);
		gl.setRowExpandRatio(0, 0.2f); //text
		gl.setRowExpandRatio(1, 0.1f); //space
		gl.setRowExpandRatio(2, 0.2f); //users
		gl.setRowExpandRatio(3, 0.1f); //space
		gl.setRowExpandRatio(4, 0.2f); //times or fix
		gl.setRowExpandRatio(5, 0.1f); //space
		gl.setRowExpandRatio(6, 0.1f); //buttons
		
		textFields(gl); //text
		gl.addComponent(new Label("&nbsp",ContentMode.HTML)); //space
		userinvitedbasic(gl); //users
		gl.addComponent(new Label("&nbsp",ContentMode.HTML)); //space
		
		//times or fix
		Boolean b = QueryHelper.usershavevoted(m_event);
		if (b != null && b.booleanValue()) {
			datefix(gl);
		}

		if (b != null && !b.booleanValue()) {
			timepossibilities(gl);
		}

		gl.addComponent(new Label("&nbsp",ContentMode.HTML)); //space
		
		buttons(gl); //buttons

		//adding to Layout
		this.addComponent(gl);
	}
	
	/**
	 * The method newZeit returns a new PopupDateField Object with initialized Parameters
	 * 
	 * @param d
	 * @return
	 */
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
	
	/**
	 * The method textFields adds the text Fields
	 * @param gl
	 */
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

		//adding to Layout
		gl2.addComponent(textfield_eventname);	
		gl2.addComponent(new Label("&nbsp",ContentMode.HTML));
		gl2.addComponent(textfield_eventort);
		gl.addComponent(gl2);
	}
	
	/**
	 * The method buttons adds the buttons onto the framework
	 * 
	 * @param gl
	 */
	public void buttons(GridLayout gl){
		Button button_save = new Button(Variables.SAVE);
		Button button_back = new Button(Variables.BACK);
	
		//initializing Layout
		GridLayout gl2 = new GridLayout(3,1);
		gl2.setWidth("30%");
		gl2.setMargin(true);
		gl2.setSpacing(true);
		gl2.setColumnExpandRatio(0, 0.4f);
		gl2.setColumnExpandRatio(1, 0.2f);
		gl2.setColumnExpandRatio(2, 0.4f);
		gl2.addStyleName(Reindeer.LAYOUT_WHITE);

		//adding Components
		gl2.addComponent(button_back);	
		gl2.addComponent(new Label("&nbsp",ContentMode.HTML));
		gl2.addComponent(button_save);	
		gl.addComponent(gl2);
		
		// Back Button Listener
		button_back.addClickListener(new PinkShoes(m_navigator,Variables.STARTPAGE, m_username, m_userid));
	
		// Send Button Listener
		button_save.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				save();
			}
		});
	}
	
	/**
	 * The method timepossibilities adds the time possibilities
	 * 
	 * @param gl
	 */
	public void timepossibilities(GridLayout gl){
		GridLayout gl2 = new GridLayout(1,1);
		m_dateLayout = new VerticalLayout();
		Label l = new Label("Possible Time Dates:");		

		Button button_plus = new Button("+");
		Button button_minus = new Button("-");
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
		
		//initializing Layout
		gl2.setWidth("60%");
		gl2.setMargin(true);
		gl2.setSpacing(true);
		gl2.setRowExpandRatio(0, 0.5f);
		gl2.addStyleName(Reindeer.LAYOUT_WHITE);
		gl2.addComponent(m_dateLayout);
		gl.addComponent(gl2);
	}
	
	/**
	 * Show invited Users
	 * 
	 * @param gl
	 */
	public void userinvitedbasic(GridLayout gl) {
		//initializing Table
		Table tableUsers = new Table("Invited Users");
		tableUsers.setSelectable(false);
		tableUsers.setMultiSelect(false);
		tableUsers.setImmediate(true);
		tableUsers.setColumnReorderingAllowed(true);
		tableUsers.setColumnCollapsingAllowed(true);
		tableUsers.addContainerProperty("User ID", Long.class, null);
		tableUsers.addContainerProperty("Username", String.class, null);
		tableUsers.addContainerProperty("Email", String.class, null);

		//filling with data
		int i = 0;
		for (Eingeladen e : m_usersinvited) {
			User u = e.getUser();
			tableUsers.addItem(
					new Object[] { u.getID(), u.getUsername(), u.geteMail() },
					new Integer(i));
			i++;
		}

		//adding to Layout
		gl.addComponent(tableUsers);
	}

	/**
	 * The method datefix adds the ComboBox onto the layout
	 * @param gl
	 */
	public void datefix(GridLayout gl) {
        m_fix = new ComboBox("Fix date:");
        m_fix.setInvalidAllowed(false);
        m_fix.setNullSelectionAllowed(false);
        for( Zeit z : m_times){
        	m_fix.addItem(z.getAnfang()+"");
        }
    
		gl.addComponent(m_fix);
	}

	/**
	 *  the method save saves into the database
	 */
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
					Notification.show("Please set a Future Date - Date Input Field number:"+ (i + 1),Notification.Type.WARNING_MESSAGE);
					valid = false;
				}
			}
		} catch (InvalidValueException e) {
			valid = false;
			Notification.show(e.getMessage(),Notification.Type.WARNING_MESSAGE);
		}

		//saving fix date value
		if (m_fix != null) {
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date d = null;
			try {
				d = simpleDateFormat.parse(m_fix.getValue()+"");
			} catch (ParseException e) {
				valid = false;
				Notification.show("Not valid SimpleDateFormat",Notification.Type.WARNING_MESSAGE);
			} 
			m_event.setFixDatum(d);
			QueryHelper.notificate(m_event, "Dear User, the event " +m_event.getName()+" just got a final date: "+d);
		}

		if(valid){
			//updating event
			m_event.setName(name);
			m_event.setOrt(ort);
			QueryHelper.update(m_event);
		
			QueryHelper.notificate(m_event, "Dear User, the event " +m_event.getName()+" just got edited");
		}
//		TODO
//		for(Zeit z : m_times){
//			QueryHelper.delete(z);
//		}
			
//		for (int i = 0; i < popupDateField_zeiten.size(); i++) {
//			Date d = popupDateField_zeiten.elementAt(i).getValue();
//			Zeit eventTime = new Zeit(d, d, m_event);
//			
//			// saving the time
//			QueryHelper.update(eventTime);
//		}
	
		Notification.show(("Event was updated..."));
	}
	
	/**
	 * The method execute Query executes all Querys
	 */
	public void executeQuerys() {
		List<?> l = QueryHelper.executeId(Variables.GETEVENT_BYID, m_eventid);
		if(l!=null){
			m_event = (DoodleEvent) l.get(0);
		}
		
		//fetching times
		m_times = QueryHelper.executeId(Variables.GETZEIT_BYEVENTID,m_event.getID() + "");

		//fetching invited users
		m_usersinvited = QueryHelper.executeId(Variables.GETEINGELADEN_BYEVENTID, m_event.getID() + "");
	}

	//IoC
	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();

		//gettin Parameters
		try{
			m_username = event.getParameters().split("/")[0];
			m_userid = event.getParameters().split("/")[1];
			m_eventid = event.getParameters().split("/")[2];
		}catch (Exception e){
			new PinkShoes(m_navigator, Variables.LOGIN).navigation();
		}
		//executing Querys
		executeQuerys();
		
		//initializing GUI Components
		init();
	}
}