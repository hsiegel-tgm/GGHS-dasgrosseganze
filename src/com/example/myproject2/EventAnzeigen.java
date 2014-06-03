package com.example.myproject2;

import java.util.List;
import java.util.Vector;

import javax.servlet.annotation.WebServlet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Abgestimmt;
import model.DoodleEvent;
import model.DoodleNotification;
import model.Eingeladen;
import model.Kommentar;
import model.User;
import model.Zeit;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.data.Property.ValueChangeEvent;

/**
 * @author Hannah Siegel
 * 
 *         2014-05-07 Hannah erstellt
 * 
 * */
public class EventAnzeigen extends VerticalLayout implements View {

	private FatNavigator m_navigator;
	private String m_username, m_userid, m_eventque;
	private User user;
	private boolean m_isadmin;
	private Vector<CheckBox> checkboxes;
	private DoodleEvent m_event;
	private List<Zeit> m_times;
	private List<Eingeladen> m_eingeladene;
	private List<Kommentar> m_comments;
	private Kommentar kommentar;
	
	protected EventAnzeigen(FatNavigator nav, Master m) {
		this.m_navigator = nav;
	}

	public void addingtable(){
		final Table table = new Table("");
		table.setSelectable(false);
		table.setColumnReorderingAllowed(true);

		table.addContainerProperty("User", String.class, null);
		
		for (Zeit z : m_times) {
		   table.addContainerProperty(z.getAnfang() + "", CheckBox.class, null); 
		}

		checkboxes = new Vector<CheckBox>();		
		Object[] o2 = new Object[m_times.size()+1];

		int inc = 1;
		for (Eingeladen e : m_eingeladene) {
			if(e.getUser().getUsername().equals(m_username)){
				user = e.getUser();
				o2[0]=m_username;
				int looper=1;
				for (Zeit z : m_times) {
					Boolean wertung = QueryHelper.getWertung(user.getID().longValue()+"", z.getID().longValue()+"");
					if(wertung==null)
						wertung = false;
					CheckBox cbx = new CheckBox("", wertung);
					if(m_event.getFixDatum()!=null){
						cbx.setEnabled(false);
					}
					checkboxes.add(cbx);
					o2[looper]=cbx;
					looper++;
				}
			}
			else{
				User u = e.getUser();
				Object[]o=new Object [m_times.size()+1];
				int i = 1;
				o[0]=e.getUser().getUsername();
				for (Zeit z : m_times) {
					Boolean wertung = QueryHelper.getWertung(u.getID().longValue()+"", z.getID().longValue()+"");
					if(wertung!=null){
						CheckBox cbx2 = new CheckBox("", wertung );
						cbx2.setEnabled(false);
						o[i]=cbx2;
					}
					++i;
				}
				if(o[1]!=null)
					table.addItem(o,inc);
			}
			++inc;
		}
		table.addItem(o2,inc);

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
		Label caption1 = new Label("Invited Users");
		caption1.addStyleName(Reindeer.LABEL_H2);
		gl.addComponent(caption1);
		gl.addComponent(new Label(""));
		
		gl.addComponent(new Label(""));
		gl.addComponent(table);
		gl.addComponent(new Label(""));
		
		gl.addComponent(new Label("<span style=\"color: white;\">.</span>",ContentMode.HTML));
		gl.addComponent(new Label(""));
		gl.addComponent(new Label(""));
		
		this.addComponent(gl);
	}
	
	public void addingcomments(){
		//TODO
//		layout.addComponent(new Label("KOMMENTARE:"));
//		for(Kommentar k : m_comments){
//			layout.addComponent(new Label("von: "+k.getUser().getUsername() + "   gepostet: "+k.getGepostet()));
//			layout.addComponent(new Label("           "+k.getText()));
//			Button button_deleteComment = new Button ("-");
//			layout.addComponent(button_deleteComment);
//			kommentar = k;
//			button_deleteComment.addClickListener(new Button.ClickListener() {
//				@Override
//				public void buttonClick(ClickEvent event) {
//					QueryHelper.delete(kommentar);	//TODO				
//				}});
//		}
		
	}
	public void init() {
		final VerticalLayout layout = this;
		layout.setMargin(true);
		layout.addStyleName(Reindeer.LAYOUT_WHITE);

		new Header(this,"This is "+m_event.getName(), m_username, m_userid, m_navigator);

		layout.addComponent(new Label("Ort: " + m_event.getOrt()));

		if(m_event.getFixDatum()!=null){
			layout.addComponent(new Label("Am: " + m_event.getFixDatum()));
		}
		
		addingtable();
		
		addingcomments(); //TODO
	
		addingButtons();

	}
	
	public void executeQuerys(){
		List<?> l = QueryHelper.executeId(Variables.GETEVENT_BYID, m_eventque);
		
		m_event = (DoodleEvent) l.get(0);
		
		m_times = QueryHelper.executeId("getTimePossibilitesforSpecificEvent",  m_event.getID()+"");
		m_comments = QueryHelper.executeId(Variables.GETKOMMENTS,  m_event.getID()+"");

		m_eingeladene = QueryHelper.executeId("getEingeladenforSpecificEvent",  m_eventque);		
	}
	
	public void addingButtons() {
		//TODO ordnen
		Button button_newcomment = new Button("New Comment");
	
		//newcomment
		if(m_isadmin){
			button_newcomment.addClickListener(new PinkShoes(m_navigator, Variables.COMMENT,m_username,m_userid,m_event.getID().longValue()+"","admin"));
		}
		else{
			button_newcomment.addClickListener(new PinkShoes(m_navigator, Variables.COMMENT,m_username,m_userid,m_event.getID().longValue()+"","invited"));
		}
		
		// back Button
		Button button_back = new Button("Back");
		button_back.addClickListener(new PinkShoes(m_navigator,
					Variables.STARTPAGE, m_username, m_userid));		
		
			// D Button
		Button button_delete = new Button("Delete This Event");
		Button button_edit = new Button("Edit This Event");

			
		button_delete.addClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					delete();
				}
			});
		
		Button button_save = new Button("Save");
		button_save.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				save();
			}
		});
		
		button_edit.addClickListener(new PinkShoes(m_navigator, Variables.EDITEVENT, m_username, m_userid, m_event.getID().longValue()+""));
			
		GridLayout gl;
		if (m_isadmin) {
			gl = new GridLayout(8,1);
			gl.setWidth("100%");
			gl.setMargin(true);
			gl.setSpacing(true);
			gl.setColumnExpandRatio(0, 0.05f);//b
			gl.setColumnExpandRatio(1, 0.05f);
			gl.setColumnExpandRatio(2, 0.05f);//b
			gl.setColumnExpandRatio(3, 0.05f);	
			gl.setColumnExpandRatio(4, 0.05f);//b	
			gl.setColumnExpandRatio(5, 0.05f);	
			gl.setColumnExpandRatio(6, 0.05f);//b	
			gl.setColumnExpandRatio(7, 0.65f);	
			gl.addComponent(button_edit);
			gl.addComponent(new Label(""));
			gl.addComponent(button_delete);
		}
		else{
			gl = new GridLayout(6,1);
			gl.setWidth("100%");
			gl.setMargin(true);
			gl.setSpacing(true);
			gl.setColumnExpandRatio(0, 0.05f);//b
			gl.setColumnExpandRatio(1, 0.05f);
			gl.setColumnExpandRatio(2, 0.05f);//b
			gl.setColumnExpandRatio(3, 0.05f);	
			gl.setColumnExpandRatio(4, 0.05f);//b	
			gl.setColumnExpandRatio(5, 0.75f);	
			gl.addComponent(button_save);
		}
		gl.addComponent(new Label(""));
		gl.addComponent(button_newcomment);
		gl.addComponent(new Label(""));
		gl.addComponent(button_back);
		gl.addComponent(new Label(""));
		
		this.addComponent(gl);
	}
	
	public void save(){
		int inc=0;
		
		for(Zeit z : m_times){
			QueryHelper.saveAbgestimmt(z,user,checkboxes.elementAt(inc).getValue());
			++inc;
			Boolean b = QueryHelper.usershavevoted(m_event);
			if(b!=null&&b.booleanValue()){
				DoodleNotification dn = new DoodleNotification(m_event.getAdmin(),"all users have voted - "+m_event.getName());
				QueryHelper.saveObject(dn);
			}
		}
		Notification.show("saved yout choices... ");
	}
	public void delete(){
		QueryHelper.notificate(m_event, "Dear User, the event " +m_event.getName()+" just got cancelled");

		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();

		for (Eingeladen ein : m_eingeladene) {
			session.delete(ein);
		}
		
		for (Zeit z : m_times) {
			session.delete(z);
			
		}
		
		for (Kommentar k : m_comments) {
			session.delete(k);
		}
		
		QueryHelper.deleteAbgestimmt(m_event.getID()+"");
		//todo: abgestimmt loeschen??
		//session.delete(m_event);// TODO!!

		t.commit();
		session.close();
		
		m_navigator.navigateTo(Variables.STARTPAGE + "/" + m_username
				+ "/" + m_userid);
	}
	
	//IoC Prinzip
	@Override
	public void enter(ViewChangeEvent event) {
		this.removeAllComponents();
		m_username = event.getParameters().split("/")[0];
		m_userid = event.getParameters().split("/")[1];
		m_eventque = event.getParameters().split("/")[2];
		String s = event.getParameters().split("/")[3];
		if (s.equals("admin")){
			m_isadmin = true;
		}else{
			m_isadmin = false;
		}
		executeQuerys();
		init();
	}
}
