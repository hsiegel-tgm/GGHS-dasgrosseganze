
package com.example.myproject2;

import java.util.List;

import model.User;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;


public class ShowQueryResult extends VerticalLayout implements View {

	private FatNavigator navigator;
	private String m_username, m_userid, m_startwith;
	private Table m_tableUsers;
	private List<User> m_users;
	private TextField m_usersearch;
	
	ShowQueryResult(FatNavigator nav) {
		this.navigator = nav;
	}

	public void initializingTables() {
		m_tableUsers = new Table();
		m_tableUsers.setSelectable(false);
		m_tableUsers.setMultiSelect(false);
		m_tableUsers.setImmediate(true);
		m_tableUsers.setColumnReorderingAllowed(true);
		m_tableUsers.setColumnCollapsingAllowed(true);
		m_tableUsers.addContainerProperty("User ID", Long.class, null);
		m_tableUsers.addContainerProperty("Username", String.class, null);
		m_tableUsers.addContainerProperty("Email", String.class, null);
	}

	public void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);
		layout.addStyleName(Reindeer.LAYOUT_WHITE);
		
		new Header(this,"Query Result", m_username, m_userid, navigator);
		
		initializingTables();
		
		int i = 0;
		for (User u : m_users) {
			m_tableUsers.addItem(new Object[] { u.getID() , u.getUsername() , u.geteMail()}, new Integer(i));
			i++;
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
		Label caption1 = new Label("Query Result");
		caption1.addStyleName(Reindeer.LABEL_H2);
		gl.addComponent(caption1);
		gl.addComponent(new Label(""));
		
		gl.addComponent(new Label(""));
		gl.addComponent(m_tableUsers);
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
		//TODO nicht schoen..
		if(m_startwith.equals("_")){
			m_users = QueryHelper.executeBasic(Variables.GETUSER);
		}else{
			m_users = QueryHelper.executeLike(Variables.GETUSER_BYNAME,m_startwith);
		}
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		m_username = event.getParameters().split("/")[0];
		m_userid = event.getParameters().split("/")[1];
		m_startwith = event.getParameters().split("/")[2];
		executeQuerys();
		init();
	}
}
