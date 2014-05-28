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
public class Abstimmen extends VerticalLayout implements View {

	private  FatNavigator navigator;
	private Master master;
	
	private int user_id=5;
	private Long event_id=17L;
	
	protected  Abstimmen(FatNavigator nav, Master m) {
		this.master=m;
		this.navigator = nav;
		final VerticalLayout layout = this;
		layout.setMargin(true);
		
		layout.addComponent(new Label("Abstimmung..."));
		
		
		Vector<CheckBox> checkboxes  = new Vector<CheckBox>();
		
		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		
		// create query
		Query q = (Query) session.getNamedQuery("getTimePossibilitesforSpecificEvent");
				
		
		q.setParameter("id", event_id.longValue());
		
		
		// run query and fetch result
		List<Zeit> res = q.list();
		
		
		for(Zeit z : res){
			CheckBox s = new CheckBox("",false);
			 checkboxes.addElement(s);
			 layout.addComponent(s);
		}
//		
//		for(int i = 0 ; i<res.size();++i){
//			 Zeit z = (Zeit) res.get(i);
//			 
//		}
		
		
		
	    
		t.commit();
		session.close();
		
		
//		
//		
//	
//
//		 final Table sample = new Table("Vote for Event number"+event_id);
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
		button_LogOut.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Variables.LOGIN);
			}
		});
		
		//newEvent Button
		Button button_newEvent= new Button("New Event");
		button_newEvent.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Variables.NEWEVENT);
			}
		});
		
		//adding buttons
		layout.addComponent(button_newEvent);	
		layout.addComponent(button_LogOut);	

//        //testing..
//		String s;
//		try {
//		    VaadinSession.getCurrent().getLockInstance().lock();
//		    s = (String) VaadinSession.getCurrent().getAttribute(Variables.USERNAME);
//			layout.addComponent(new Label("username: " + s));
//		} finally {
//		    VaadinSession.getCurrent().getLockInstance().unlock();
//		}
	}


	@Override
	public void enter(ViewChangeEvent event) {
	}
}



