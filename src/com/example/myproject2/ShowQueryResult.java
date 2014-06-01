
package com.example.myproject2;

import java.util.List;

import model.User;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;


public class ShowQueryResult extends VerticalLayout implements View {

	private FatNavigator navigator;
	private String m_username, m_userid, m_startwith;
	private Table m_myevents;
	private List<User> m_users;
	private TextField m_usersearch;
	
	ShowQueryResult(FatNavigator nav) {
		this.navigator = nav;
	}

	public void initializingTables() {
		// myevents
		m_myevents = new Table("All Users");
		m_myevents.setSelectable(false);
		m_myevents.setMultiSelect(false);
		m_myevents.setImmediate(true);
		m_myevents.setColumnReorderingAllowed(true);
		m_myevents.setColumnCollapsingAllowed(true);
		m_myevents.addContainerProperty("User ID", Long.class, null);
		m_myevents.addContainerProperty("Username", String.class, null);
		m_myevents.addContainerProperty("Email", String.class, null);
	}

	public void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);
		search();
		initializingTables();
		
		int i = 0;
		for (User u : m_users) {
			m_myevents.addItem(new Object[] { u.getID() , u.getUsername() , u.geteMail()}, new Integer(i));
			i++;
		}

		layout.addComponent(m_myevents);

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
		//TODO nicht schoen..
		if(m_startwith.equals("_")){
			m_users = QueryHelper.executeBasic(Variables.GETUSER);
		}else{
			m_users = QueryHelper.executeLike(Variables.GETUSER_BYNAME,m_startwith);
		}
	}
	
	public void search(){
		Button button_search = new Button("Search");
		m_usersearch = new TextField();

		button_search.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				String text = m_usersearch.getValue();
				if(!text.equals("")){
					new PinkShoes(navigator,Variables.SHOWQUERYRESULT,m_username,m_userid,text).navigation();
				}
				else{
					new PinkShoes(navigator,Variables.SHOWQUERYRESULT,m_username,m_userid,"_").navigation();
				}
			}
		});
		
		this.addComponent(m_usersearch);
		this.addComponent(button_search);
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
