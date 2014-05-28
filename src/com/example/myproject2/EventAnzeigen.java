package com.example.myproject2;

import java.util.List;
import java.util.Vector;

import javax.servlet.annotation.WebServlet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.DoodleEvent;
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
 * 2014-05-07 Hannah erstellt
 * 
 * 
 * TODO JUnit
 * TODO Komment
 * TODO Design pefekto
 * TODO GUI Test
 * TODO Coding style
 * 
 * */
public class EventAnzeigen extends VerticalLayout implements View {

	private  FatNavigator navigator;
	private String m_username, m_userid, m_eventque;
	private boolean m_isadmin;
	private DoodleEvent e;
	protected  EventAnzeigen(FatNavigator nav, Master m) {
		this.navigator = nav;
	}

	
	void init(){
		final VerticalLayout layout = this;
		layout.setMargin(true);

		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		
		//fetching event 
		Query q2 = (Query) session.getNamedQuery("getSpecificEvent");

		q2.setParameter("id",Long.parseLong(m_eventque));
		
		List<?> l = q2.list();
		
		e = (DoodleEvent) l.get(0);
		
		//Eventname
		layout.addComponent(new Label("Event: "+e.getName()));
		
		// create query
		Query q = (Query) session.getNamedQuery("getTimePossibilitesforSpecificEvent");
				
		
		q.setParameter("id", e.getID().longValue());
			
		// run query and fetch result
		List<Zeit> res = q.list();
		
		Vector<CheckBox> checkboxes  = new Vector<CheckBox>();

		for(Zeit z : res){
			CheckBox s = new CheckBox(z.getAnfang()+"",false);
			 checkboxes.addElement(s);
			 layout.addComponent(s);
		}
		
		t.commit();
		session.close();	
		
		
		if(m_isadmin){
			//newEvent Button
			Button button_delete= new Button("Delete This Event");
			layout.addComponent(button_delete);
			
			button_delete.addClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					
					Session session = InitSession.getSession().openSession();
					Transaction t = session.beginTransaction();
					t.begin();
					
					// create query
					Query q = (Query) session.getNamedQuery("getTimePossibilitesforSpecificEvent");
							
					
					q.setParameter("id", e.getID().longValue());
						
					// run query and fetch result
					List<Zeit> res = q.list();
					
					for(Zeit z : res){
						session.delete(z);
					}
					
					session.delete(e);					
					
					t.commit();
					session.close();
					
					navigator.navigateTo(Variables.STARTPAGE + "/" + m_username+ "/" + m_userid);
				}
			});
			
		}
		
	
// 				TABELLE 		
		
//		final Table sample = new Table("Vote for Event number"+event_id);
//	     sample.setSelectable(true);
//	     sample.setMultiSelect(false);
//	     sample.setImmediate(true);
//
//	     sample.setColumnReorderingAllowed(true);
//	     sample.setColumnCollapsingAllowed(true);
//
//	     sample.addContainerProperty("Event ID", Integer.class,  null);
//	     sample.addContainerProperty("Event Name", String.class,  null);
//	     sample.addContainerProperty("Location", String.class, null);
//	     sample.addContainerProperty("Admin", String.class, null);
//
//	 
//	     
//			// create query
//			Query q = (Query) session.getNamedQuery("getEvents");
//			
//			// run query and fetch result
//			List<?> res = q.list();
//
//			for(int i = 0 ; i<res.size();++i){
//				DoodleEvent e = (DoodleEvent) res.get(i);
//				
//				sample.addItem(new Object[] {
//			    		    (int)(e.getID().longValue()),e.getName(),e.getOrt(),e.getAdmin().getUsername()}, new Integer(i));
//				
//			}
//			
//	     
//		layout.addComponent(sample);	
//	
//		
//		sample.addValueChangeListener(new Property.ValueChangeListener() {
//		    public void valueChange(ValueChangeEvent event) {
//		        Notification.show("Selected: " + sample.getValue());
//		    }
//		});
//		
		
		//LogOut Button
		Button button_LogOut = new Button("Log Out");
		button_LogOut.addClickListener(new PinkShoes(navigator,
				Variables.LOGIN));
		
		//newEvent Button
		Button button_back= new Button("Back");
		button_back.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Variables.STARTPAGE + "/" + m_username+ "/" + m_userid);
			}
		});
		
		//adding buttons
		layout.addComponent(button_back);	
		layout.addComponent(button_LogOut);	

		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		m_username = event.getParameters().split("/")[0];
		m_userid = event.getParameters().split("/")[1];
		m_eventque = event.getParameters().split("/")[2];
		String s = event.getParameters().split("/")[3];
		if(s.equals("admin"))
			m_isadmin=true;

		init();
	}
}



