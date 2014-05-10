package com.example.myproject2;

import java.util.List;

import model.User;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class TestUI extends UI{
	private static final long serialVersionUID = 1L;
	private TextField searchField;

	public TestUI(){
		init(null);
	}
	
	@Override
	protected void init(VaadinRequest request) {
		
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		
		setContent(layout);

		layout.addComponent(new Label("LOG IN"));
		Button button = new Button("OK!");
        searchField = new TextField();

		layout.addComponent(searchField);
		layout.addComponent(button);
		
		Session s =  InitSession.getSession().openSession();
		Transaction t= s.getTransaction();
		t.begin();
		s.save(new User("laptop"));
		t.commit();
		s.close();
		
		
		button.addClickListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				String text = searchField.getValue();

				Session session =  InitSession.getSession().openSession();

				List<?> res = session.getNamedQuery("getUsers").list();
				
				boolean b = true;
				
				for (int i = 0; i < res.size(); ++i) {
					User user = (User) res.get(i);
					layout.addComponent(new Label(""+res.size()));
					layout.addComponent(new Label(user.getUsername()));
					if((text.equals(user.getUsername()))&&b){
						//log in successful
						//call startpage
						layout.addComponent(new Label("Log in successful"));
						
						b = false;
					}
				}
				
				if(b)
					layout.addComponent(new Label("Could not find username"));
				
				session.close();
			}
		});		
	}

}
