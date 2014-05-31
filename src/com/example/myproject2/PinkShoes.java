package com.example.myproject2;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author Hannah Siegel and Dominik Scholz
 * 
 * This class has no need for additional comments, it is simply perfect
 *
 */
public class PinkShoes implements Button.ClickListener,Property.ValueChangeListener {

	private FatNavigator m_navigator;
	private String m_direction , m_parameter;
	
	public PinkShoes(FatNavigator na,String di,String... args){
		m_navigator = na;
		m_direction = di;
		m_parameter = "";
		
		for(String arg : args) 
			m_parameter += "/" + arg;
	} 
	
	@Override
	public void buttonClick(ClickEvent event) {
		m_navigator.navigateTo(m_direction+m_parameter);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		//TODO evtl

		
	}
}
