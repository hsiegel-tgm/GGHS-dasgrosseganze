package com.example.myproject2;

import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class Header {
	private TextField m_usersearch;
	private String m_username, m_userid;
	private final FatNavigator navigator;
	
	public Header(VerticalLayout layout, String text, String username, String userid, FatNavigator nav){
		this.m_username = username;
		this.m_userid = userid;
		this.navigator = nav;

		Button button_search = new Button("search");
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
		
		GridLayout gl3 = new GridLayout(3,1);
		gl3.setWidth("100%");
		gl3.setMargin(false);
		gl3.setSpacing(true);
		gl3.setColumnExpandRatio(0, 0.5f);
		gl3.setColumnExpandRatio(1, 0.3f);
		gl3.setColumnExpandRatio(3, 0.2f);
	
		Label welcome = new Label (text);
		welcome.addStyleName(Reindeer.LABEL_H1);
		
		gl3.addComponent(welcome);
		
		GridLayout gl4 = new GridLayout(2,1);

		gl4.addComponent(m_usersearch);
		gl4.addComponent(button_search);

		gl3.addComponent(gl4);
		
		// LogOut Button
		Button button_LogOut = new Button("Log Out");
		button_LogOut.addClickListener(new PinkShoes(navigator, Variables.LOGIN));
		button_LogOut.addStyleName(Reindeer.BUTTON_LINK);

		// adding buttons
		gl3.addComponent(button_LogOut);

		layout.addComponent(gl3);
	}
}
