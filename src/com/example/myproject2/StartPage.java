
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



