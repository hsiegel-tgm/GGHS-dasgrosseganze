package com.example.myproject2;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.annotation.WebServlet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.DoodleEvent;
import model.Eingeladen;
import model.User;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.data.Property.ValueChangeEvent;

/**
 * 
 * @author Hannah Siegel
 * 
 *         2014-05-07 Hannah erstellt
 * 
 * 
 *         TODO JUnit TODO Komment TODO Design pefekto TODO GUI Test TODO Coding
 *         style
 * 
 * 
 * 
 * 
 * */
public class StartPage extends VerticalLayout implements View {

	private FatNavigator navigator;
	private String m_username, m_userid;
	private Table m_myevents, m_invitedEvents2;
	private List<DoodleEvent> m_events; // ueberfluessig? haha admin?
	private List<DoodleEvent> m_invitedTo;

	StartPage(FatNavigator nav, Master m) {
		this.navigator = nav;
	}

	public void initializingTables() {
		// myevents
		m_myevents = new Table("Your Events");
		m_myevents.setSelectable(true);
		m_myevents.setMultiSelect(false);
		m_myevents.setImmediate(true);
		m_myevents.setColumnReorderingAllowed(true);
		m_myevents.setColumnCollapsingAllowed(true);
		m_myevents.addContainerProperty("Event ID", Integer.class, null);
		m_myevents.addContainerProperty("Event Name", String.class, null);
		m_myevents.addContainerProperty("Location", String.class, null);

		// invited events
		m_invitedEvents2 = new Table("All Events");
		m_invitedEvents2.setSelectable(true);
		m_invitedEvents2.setMultiSelect(false);
		m_invitedEvents2.setImmediate(true);
		m_invitedEvents2.setColumnReorderingAllowed(true);
		m_invitedEvents2.setColumnCollapsingAllowed(true);
		m_invitedEvents2.addContainerProperty("Event ID", Integer.class, null);
		m_invitedEvents2.addContainerProperty("Event Name", String.class, null);
		m_invitedEvents2.addContainerProperty("Location", String.class, null);
		m_invitedEvents2.addContainerProperty("Admin", String.class, null);

	}

	public void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);
		layout.addComponent(new Label("Welcome: " + m_username));

		initializingTables();

		int isadminzv = 0;
		int isinvited = 0;

		final List<Long> list1 = new ArrayList<Long>();
		final List<Long> list2 = new ArrayList<Long>();

		for (DoodleEvent ev : m_events) {
			m_myevents.addItem(new Object[] { (int) (ev.getID().longValue()),
					ev.getName(), ev.getOrt() }, new Integer(isadminzv));
			isadminzv++;
			list1.add(ev.getID());
		}

		for (DoodleEvent ev : m_invitedTo) {
			m_invitedEvents2.addItem(
					new Object[] { (int) (ev.getID().longValue()),
							ev.getName(), ev.getOrt(),
							ev.getAdmin().getUsername() }, new Integer(
							isinvited));
			isinvited++;
			list2.add(ev.getID());
		}

		layout.addComponent(m_myevents);
		layout.addComponent(m_invitedEvents2);

		m_myevents.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				Long eventid = list1.get((Integer) m_myevents.getValue());
				navigator.navigateTo(Variables.VOTE + "/" + m_username + "/"
						+ m_userid + "/" + eventid + "/admin");
			}
		});

		m_invitedEvents2
				.addValueChangeListener(new Property.ValueChangeListener() {
					public void valueChange(ValueChangeEvent event) {
						Long eventid = list2.get((Integer) m_invitedEvents2
								.getValue());
						navigator.navigateTo(Variables.VOTE + "/" + m_username
								+ "/" + m_userid + "/" + eventid + "/invited");
					}
				});

		addingButtons();
	}

	public void addingButtons() {
		// LogOut Button
		Button button_NewEvent = new Button("New Event");
		button_NewEvent.addClickListener(new PinkShoes(navigator,
				Variables.NEWEVENT, m_username, m_userid));

		// adding buttons
		this.addComponent(button_NewEvent);

		// LogOut Button
		Button button_LogOut = new Button("Log Out");
		button_LogOut
				.addClickListener(new PinkShoes(navigator, Variables.LOGIN));

		// adding buttons
		this.addComponent(button_LogOut);
	}

	public void executeQuerys() {
		m_events = QueryHelper.executeId(Variables.GETEVENT_BYADMIN, m_userid);

		m_invitedTo = QueryHelper.executeId("getEingeladenforSpecificUser",
				m_userid);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		m_username = event.getParameters().split("/")[0];
		m_userid = event.getParameters().split("/")[1];
		executeQuerys();
		init();
	}
}
