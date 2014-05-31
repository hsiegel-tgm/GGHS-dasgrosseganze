package com.example.myproject2;

import javax.faces.bean.SessionScoped;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

/**
 * @version 2014-05-07 Hannah erstellt
 * @author Hannah Siegel
 * 
 * TODO JUnit
 * TODO Komment

 * TODO Coding style
 * */


@SuppressWarnings("serial")
@Theme("myproject2")
@SessionScoped
public class Master extends UI {
	private FatNavigator navigator;
	private static String username;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Master.class)
	public static class Servlet extends VaadinServlet {
	}	
		
	/* (non-Javadoc)
	 * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
	 */
	protected void init (VaadinRequest request){
		//new navigator
		navigator = new FatNavigator(this,this);
		
		//addin the Views
		navigator.addView(Variables.LOGIN,new Login(navigator));
		navigator.addView(Variables.REGISTER,new NewUser(navigator,this));
		navigator.addView(Variables.STARTPAGE,new StartPage(navigator,this));
		navigator.addView(Variables.NEWEVENT,new NewEvent(navigator,this));
		navigator.addView(Variables.VOTE,new EventAnzeigen(navigator,this));
	}
	
	/**
	 * setter for the username
	 * 
	 * @param username
	 */
	public void setUsername(String username){
		this.username=username;
	}
	
	/**
	 * getter for the username
	 * 
	 * @return username
	 */
	public String getUsername(){
		return this.username;
	}
}
