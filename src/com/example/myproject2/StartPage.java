package com.example.myproject2;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.annotation.WebServlet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.DoodleEvent;
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

	StartPage(FatNavigator nav, Master m) {
		this.navigator = nav;
	}

	void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);
		layout.addComponent(new Label("Welcome: " + m_username));

		//myevents
		final Table myevents = new Table("Your Events");
		myevents.setSelectable(true);
		myevents.setMultiSelect(false);
		myevents.setImmediate(true);
		myevents.setColumnReorderingAllowed(true);
		myevents.setColumnCollapsingAllowed(true);
		myevents.addContainerProperty("Event ID", Integer.class, null);
		myevents.addContainerProperty("Event Name", String.class, null);
		myevents.addContainerProperty("Location", String.class, null);

		//invited events
		final Table invitedEvents2 = new Table("All Events");
		invitedEvents2.setSelectable(true);
		invitedEvents2.setMultiSelect(false);
		invitedEvents2.setImmediate(true);
		invitedEvents2.setColumnReorderingAllowed(true);
		invitedEvents2.setColumnCollapsingAllowed(true);
		invitedEvents2.addContainerProperty("Event ID", Integer.class, null);
		invitedEvents2.addContainerProperty("Event Name", String.class, null);
		invitedEvents2.addContainerProperty("Location", String.class, null);
		invitedEvents2.addContainerProperty("Admin", String.class, null);
		
		
		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();

		// create query
		Query q = (Query) session.getNamedQuery("getEvents");

		// run query and fetch result
		List<?> res = q.list();

		int isadminzv = 0;
		int isinvited = 0;

		final List<Long> list1 = new ArrayList<Long>();
		final List<Long> list2 = new ArrayList<Long>();

		
		for (int i = 0; i < res.size(); ++i) {
			DoodleEvent e = (DoodleEvent) res.get(i);
			
			String admin = e.getAdmin().getUsername();
			
			if(m_username.equals(admin)){
				myevents.addItem(
						new Object[] { (int) (e.getID().longValue()), e.getName(),
								e.getOrt() },
						new Integer(isadminzv));
				isadminzv++;
				list1.add(e.getID());
			}
			else{
				//TODO check if user is invited..
				invitedEvents2.addItem(
						new Object[] { (int) (e.getID().longValue()), e.getName(),
								e.getOrt(), e.getAdmin().getUsername() },
						new Integer(isinvited));
				isinvited++;
				list2.add(e.getID());

			}
		}
		
		t.commit();
		session.close();
		
		layout.addComponent(myevents);
		layout.addComponent(invitedEvents2);
		
		myevents.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				Long eventid = list1.get((Integer) myevents.getValue());		
				navigator.navigateTo(Variables.VOTE+"/"+m_username+"/"+m_userid+"/"+eventid+"/admin");
			}
		});
		
		invitedEvents2.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				Long eventid = list2.get((Integer) invitedEvents2.getValue());		
				navigator.navigateTo(Variables.VOTE+"/"+m_username+"/"+m_userid+"/"+eventid+"/invited");
			}
		});

		
		// LogOut Button
		Button button_NewEvent = new Button("New Event");
		button_NewEvent.addClickListener(new PinkShoes(navigator,Variables.NEWEVENT,m_username,m_userid));
			
				// adding buttons
				layout.addComponent(button_NewEvent);
		
		
		// LogOut Button
		Button button_LogOut = new Button("Log Out");
		button_LogOut.addClickListener(new PinkShoes(navigator,
				Variables.LOGIN));
	
		// adding buttons
		layout.addComponent(button_LogOut);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		m_username = event.getParameters().split("/")[0];
		m_userid = event.getParameters().split("/")[1];
		init();
	}
}
