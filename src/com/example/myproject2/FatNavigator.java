package com.example.myproject2;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

public class FatNavigator extends Navigator {
	
	public FatNavigator(UI ui, Master master) {
		super(ui, master);
	}

	private static final long serialVersionUID = 1L;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
