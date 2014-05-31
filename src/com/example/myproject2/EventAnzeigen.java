package com.example.myproject2;

import java.util.List;
import java.util.Vector;

import javax.servlet.annotation.WebServlet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.DoodleEvent;
import model.Eingeladen;
import model.User;
import model.Zeit;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
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
 * @author Hannah Siegel
 * 
 *         2014-05-07 Hannah erstellt
 * 
 * */
public class EventAnzeigen extends VerticalLayout implements View {

	private FatNavigator m_navigator;
	private String m_username, m_userid, m_eventque;
	private boolean m_isadmin;
	private Vector<CheckBox> checkboxes;
	private DoodleEvent m_event;

	private List<Zeit> m_times;
	private List<Eingeladen> m_eingeladene;

	protected EventAnzeigen(FatNavigator nav, Master m) {
		this.m_navigator = nav;
	}

	void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);

		layout.addComponent(new Label("Event: " + m_event.getName()));
		
		// TABELLE
		final Table table = new Table("Vote for Event number " + m_eventque);
		table.setSelectable(false);
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(true);

		table.addContainerProperty("User", String.class, null);
		
		for (Zeit z : m_times) {
		   table.addContainerProperty(z.getAnfang() + "", CheckBox.class, null); 
		}

		checkboxes = new Vector<CheckBox>();		
		Object[] o2 = new Object[m_times.size()+1];

		int inc = 1;
		for (Eingeladen e : m_eingeladene) {
			if(e.getUser().getUsername().equals(m_username)){

				//Object[] o = new Object[m_times.size()+1];
				o2[0]=m_username;
				int looper=1;
				for (Zeit z : m_times) {
					CheckBox cbx = new CheckBox("", false);
					checkboxes.add(cbx);
					o2[looper]=cbx;
					looper++;
				}
//				table.addItem(o,inc);
				//loop durch zeiten
			}
			else{
				User u = e.getUser();
				Object[]o=new Object [m_times.size()+1];
				int i = 1;
				o[0]=e.getUser().getUsername();
				for (Zeit z : m_times) {
					Boolean wertung = QueryHelper.getWertung(u.getID().longValue()+"", z.getID().longValue()+"");
					if(wertung!=null){
						CheckBox cbx2 = new CheckBox("", wertung );
						cbx2.setEnabled(false);
						o[i]=cbx2;
					}
					++i;
				}
				if(o[1]!=null)
					table.addItem(o,inc);
				//table.addItem(new Object[] {e.getUser().getUsername(),cbx2,cbx3,cbx4},inc);

			}
			++inc;
		}
		table.addItem(o2,inc);

		layout.addComponent(table);

		// Printing Eventname

		if (m_isadmin) {
			// D Button
			Button button_delete = new Button("Delete This Event");
			layout.addComponent(button_delete);

			button_delete.addClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {

					Session session = InitSession.getSession().openSession();
					Transaction t = session.beginTransaction();
					t.begin();

					for (Zeit z : m_times) {
						session.delete(z);
					}

					//session.delete(e);

					t.commit();
					session.close();

					m_navigator.navigateTo(Variables.STARTPAGE + "/" + m_username
							+ "/" + m_userid);
				}
			});

		}

	
		addingButtons(layout);
	}
	
	public void executeQuerys(){
		List<?> l = QueryHelper.executeId(Variables.GETEVENT_BYID, m_eventque);
		
		m_event = (DoodleEvent) l.get(0);
		
		m_times = QueryHelper.executeId("getTimePossibilitesforSpecificEvent",  m_event.getID()+"");
		
		m_eingeladene = QueryHelper.executeId("getEingeladenforSpecificEvent",  m_eventque);		
	}
	
	public void addingButtons(VerticalLayout layout) {

		// LogOut Button
		Button button_LogOut = new Button("Log Out");
		button_LogOut.addClickListener(new PinkShoes(m_navigator, Variables.LOGIN));

		// back Button
		Button button_back = new Button("Back");
		button_back.addClickListener(new PinkShoes(m_navigator,
				Variables.STARTPAGE, m_username, m_userid));

	
		
		// LogOut Button
		Button button_save = new Button("Save");
		button_save.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				save();
			}
		});
		
		// adding buttons
		layout.addComponent(button_save);
		layout.addComponent(button_back);
		layout.addComponent(button_LogOut);
	}
	
	public void save(){
		if(checkboxes.elementAt(0).getValue()){
			this.addComponent(new Label("1: true"));

		}else{
			this.addComponent(new Label("1: false"));

		}
		if(checkboxes.elementAt(1).getValue()){
			this.addComponent(new Label("2: true"));

		}else{
			this.addComponent(new Label("2: false"));

		}
	}
	
	//IoC Prinzip
	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		m_username = event.getParameters().split("/")[0];
		m_userid = event.getParameters().split("/")[1];
		m_eventque = event.getParameters().split("/")[2];
		String s = event.getParameters().split("/")[3];
		if (s.equals("admin"))
			m_isadmin = true;
		executeQuerys();
		init();
	}
}
