package com.example.myproject2;

import javax.servlet.annotation.WebServlet;

import model.DoodleEvent;
import model.Kommentar;
import model.User;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Query;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * 
 * @version 2014-05-07 Hannah erstellt
 * @author Hannah Siegel
 * 
 *         TODO JUnit TODO Komment TODO Design pefekto TODO GUI Test TODO
 *         Variablen TODO Coding style
 * 
 *         TODO moeglich password email mit password
 * 
 * 
 * TODO hearst nein nein also der admin kann nicht posten? wtf
 * 
 * */
public class NewComment extends VerticalLayout implements View {
	// Textfields
	private TextField textfield_commentar;

	// Navigator and master object
	private FatNavigator m_navigator;

	private DoodleEvent m_event;
	private User m_user;
	
	private String m_username, m_userid, m_eventid;
	private String m_isadmin;
	
	// Layout
	private CustomLayout layout;

	public void init(){
		final VerticalLayout layout = this;
		layout.setMargin(true);

		// username Textfield
		textfield_commentar = new TextField();

		// Send and Back Button
		Button button_send = new Button("Save");
		Button button_back = new Button("Zurueck");
		button_send.setClickShortcut(KeyCode.ENTER, null);
		button_back.setClickShortcut(KeyCode.ARROW_LEFT, null);

		// adding layout to webpage
		layout.addComponent(new Label("comment:"));
		layout.addComponent(textfield_commentar);
		

		// Back Button Listener
		button_back.addClickListener(new PinkShoes(m_navigator, Variables.VOTE,m_username,m_userid,m_eventid,m_isadmin)); //TODO which ones?

		// Send Button Listener
		button_send.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				// fetching values
				String text = textfield_commentar.getValue();


				if (text == null || text.equals("")) {
					Notification.show("please set a text",
							Notification.TYPE_WARNING_MESSAGE); //TODO dep
				}else{
					// new User object
					Kommentar k = new Kommentar(m_event, text, new Date(), m_user); 
					System.out.println("try saving...");

					if(QueryHelper.saveObject(k)){
						// Notification
						Notification.show("Saved...");

						new PinkShoes(m_navigator, Variables.VOTE,m_username,m_userid,m_eventid,m_isadmin).navigation();

					}
				}
			}
		});
		layout.addComponent(button_send);
		layout.addComponent(button_back);

	}
	
	/**
	 * Constructor
	 * 
	 * @param nav
	 *            navigator object
	 */
	protected NewComment(FatNavigator nav) {
		this.m_navigator = nav;
	}

	public void executeQuerys(){
		List<?> l = QueryHelper.executeId(Variables.GETEVENT_BYID, m_eventid);
		
		m_event = (DoodleEvent) l.get(0);
		
		m_user = (User) QueryHelper.executeId(Variables.GETUSER_BYID,  m_userid).get(0);	
		
		System.out.println("event:"+m_event.getName()+" user: "+m_user.getUsername());
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		m_username = event.getParameters().split("/")[0];
		m_userid = event.getParameters().split("/")[1];
		m_eventid = event.getParameters().split("/")[2];
		m_isadmin = event.getParameters().split("/")[3];
		
		executeQuerys();
		
		init();
	}
}
