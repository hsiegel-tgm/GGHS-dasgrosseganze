
package com.example.myproject2;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.DoodleEvent;
import model.User;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;


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
 * 
 * 
 * 
 * */



public class StartPage extends VerticalLayout implements View {

	private  FatNavigator navigator;
	private Master master;
	
	protected  StartPage(FatNavigator nav, Master m) {
		this.master=m;
		this.navigator = nav;
		final VerticalLayout layout = this;
		layout.setMargin(true);
		

		 Table sample = new Table("All Events");
	   //  sample.setSizeFull();
	     sample.setSelectable(true);
	     sample.setMultiSelect(false);
	     sample.setImmediate(true);

	     sample.setColumnReorderingAllowed(true);
	     sample.setColumnCollapsingAllowed(true);

	   //  sample.setColumnHeaders(new String[] { "Event ID", "Event Name" , "Location" });

	     sample.addContainerProperty("Event ID", Integer.class,  null);
	     sample.addContainerProperty("Event Name",  String.class,  null);
	     sample.addContainerProperty("Location",       String.class, null);
	     
	     Session session = InitSession.getSession().openSession();
			Transaction t = session.beginTransaction();
			t.begin();
	     
			// create query
			Query q = (Query) session.getNamedQuery("getEvents");
			
			// run query and fetch result
			List<?> res = q.list();

			for(int i = 0 ; i<res.size();++i){
				DoodleEvent e = (DoodleEvent) res.get(i);
				
				sample.addItem(new Object[] {
			    		    (int)(e.getID().longValue()),e.getName(),e.getOrt()}, new Integer(i));
			}
			

			
			
//			
//			
//	     sample.addItem(new Object[] {
//	    		    1,"testE1","testO1"}, new Integer(1));
//	     sample.addItem(new Object[] {
//	    		    2,"testE2","testO2"} ,new Integer(2));
//	     sample.addItem(new Object[] {
//	    		    3,"testE3","testO3"}, new Integer(3	));
//	     
	 	t.commit();
		session.close();
	     
		layout.addComponent(sample);	

	 
//
//	        sample.addValueChangeListener(new ValueChangeListener() {
//	            @Override
//	            public void valueChange(final ValueChangeEvent event) {
//	                final String valueString = String.valueOf(event.getProperty()
//	                        .getValue());
//	                Notification.show("Value changed:", valueString,
//	                        Type.TRAY_NOTIFICATION);
//	            }
//	        });
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

        //testing..
		String s;
		try {
		    VaadinSession.getCurrent().getLockInstance().lock();
		    s = (String) VaadinSession.getCurrent().getAttribute(Variables.USERNAME);
			layout.addComponent(new Label("username: " + s));
		} finally {
		    VaadinSession.getCurrent().getLockInstance().unlock();
		}
	}


	@Override
	public void enter(ViewChangeEvent event) {
	}
}



