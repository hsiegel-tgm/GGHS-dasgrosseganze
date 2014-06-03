

package com.example.myproject2;

import java.util.List;

import model.DoodleNotification;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;


public class ShowNotification extends VerticalLayout implements View {

	private FatNavigator navigator;
	private String m_username, m_userid;
	private Table m_tableNotifications;
	private List<DoodleNotification> m_notifications;
	
	public ShowNotification(FatNavigator nav) {
		this.navigator = nav;
	}

	public void initializingTables() {
		m_tableNotifications = new Table();
		m_tableNotifications.setSelectable(false);
		m_tableNotifications.setMultiSelect(false);
		m_tableNotifications.setImmediate(true);
		m_tableNotifications.setColumnReorderingAllowed(true);
		m_tableNotifications.setColumnCollapsingAllowed(true);
		m_tableNotifications.addContainerProperty("Notification ID", Long.class, null);
		m_tableNotifications.addContainerProperty("Text", String.class, null);
		m_tableNotifications.addContainerProperty("Datum", String.class, null);
	}

	public void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);
		layout.addStyleName(Reindeer.LAYOUT_WHITE);

		new Header(this,"Notifications", m_username, m_userid, navigator);

		initializingTables();

		if(m_notifications!=null){
			int i = 0;
			for (DoodleNotification dn : m_notifications) {
				m_tableNotifications.addItem(new Object[] {dn.getID().longValue(), dn.getText(), dn.getGeschriebenAm()+""}, new Integer(i));
				i++;
			}
		}
		GridLayout gl = new GridLayout(3,3);
		gl.setWidth("100%");
		gl.setMargin(true);
		gl.setSpacing(true);
		gl.setColumnExpandRatio(0, 0.1f);
		gl.setColumnExpandRatio(1, 0.2f);
		gl.setColumnExpandRatio(2, 0.7f);
		
		gl.setRowExpandRatio(0, 0.1f);
		gl.setRowExpandRatio(1, 0.8f);
		gl.setRowExpandRatio(2 , 0.1f);

		gl.addComponent(new Label(""));
		Label caption1 = new Label("Notifications");
		caption1.addStyleName(Reindeer.LABEL_H2);
		gl.addComponent(caption1);
		gl.addComponent(new Label(""));
		
		gl.addComponent(new Label(""));
		gl.addComponent(m_tableNotifications);
		gl.addComponent(new Label(""));
		
		gl.addComponent(new Label("<span style=\"color: white;\">.</span>",ContentMode.HTML));
		gl.addComponent(new Label(""));
		gl.addComponent(new Label(""));
		
		layout.addComponent(gl);
		

		Button button_back = new Button("Back");
		button_back.addClickListener(new PinkShoes(navigator, Variables.STARTPAGE,m_username,m_userid));
		this.addComponent(button_back);
	}

	

	public void executeQuerys() {
		m_notifications = QueryHelper.executeId(Variables.GETNOTIFICATION_BYUSERID,m_userid);	
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
