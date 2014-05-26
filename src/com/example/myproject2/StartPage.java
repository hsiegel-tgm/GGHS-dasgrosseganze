
package com.example.myproject2;

import javax.servlet.annotation.WebServlet;

import model.User;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;


/*
 * 
 * 2014-05-07 Hannah erstellt
 * 
 * 
 * TODO JUnit
 * TODO Komment
 * TODO Design pefekto
 * TODO GUI Test
 * TODO Variablen
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
		
		Button buttonLogOut = new Button("Log Out");
		buttonLogOut.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Variables.LOGIN);
			}
		});
		Button button_newEvent= new Button("New Event");
		button_newEvent.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Variables.NEWEVENT);
			}
		});
		
		layout.addComponent(new Label("Doodle - not yet implemented!"));
		layout.addComponent(button_newEvent);	
		layout.addComponent(buttonLogOut);	

        
		String s;
		
		try {
		    VaadinSession.getCurrent().getLockInstance().lock();
		    s = (String) VaadinSession.getCurrent().getAttribute(Variables.USERNAME);
		} finally {
		    VaadinSession.getCurrent().getLockInstance().unlock();
		}
		layout.addComponent(new Label("username: " + s));
	}


	@Override
	public void enter(ViewChangeEvent event) {
	}
}



