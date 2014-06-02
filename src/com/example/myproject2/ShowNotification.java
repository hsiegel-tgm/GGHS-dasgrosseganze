

package com.example.myproject2;

import java.util.List;

import model.DoodleNotification;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;


public class ShowNotification extends VerticalLayout implements View {

	private FatNavigator navigator;
	private String m_username, m_userid;
	private Table m_tableUsers;
	private List<DoodleNotification> m_notifications;
	
	public ShowNotification(FatNavigator nav) {
		this.navigator = nav;
	}

	public void initializingTables() {
		m_tableUsers = new Table("All Notifications");
		m_tableUsers.setSelectable(false);
		m_tableUsers.setMultiSelect(false);
		m_tableUsers.setImmediate(true);
		m_tableUsers.setColumnReorderingAllowed(true);
		m_tableUsers.setColumnCollapsingAllowed(true);
		m_tableUsers.addContainerProperty("Notification ID", Long.class, null);
		m_tableUsers.addContainerProperty("Text", String.class, null);
		m_tableUsers.addContainerProperty("Am", String.class, null);
	}

	public void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);
		initializingTables();

		if(m_notifications!=null){
			int i = 0;
			for (DoodleNotification dn : m_notifications) {
				m_tableUsers.addItem(new Object[] {dn.getID().longValue(), dn.getText(), dn.getGeschriebenAm()+""}, new Integer(i));
				i++;
			}
		}
		layout.addComponent(m_tableUsers);

		addingButtons();
	}

	public void addingButtons() {

		// LogOut Button
		Button button_LogOut = new Button("Log Out");
		button_LogOut.addClickListener(new PinkShoes(navigator, Variables.LOGIN));
		
		// LogOut Button
		Button button_back = new Button("Back");
		button_back.addClickListener(new PinkShoes(navigator, Variables.STARTPAGE,m_username,m_userid));

		// adding buttons
		this.addComponent(button_back);
		this.addComponent(button_LogOut);
	}

	public void executeQuerys() {
		m_notifications =  QueryHelper.executeId(Variables.GETNOTIFICATION_BYUSERID,m_userid);	
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
