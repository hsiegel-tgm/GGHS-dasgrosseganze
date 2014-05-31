package com.example.myproject2;

import java.util.List;

import model.Abgestimmt;
import model.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class QueryHelper {
	
	public static List executeBasic(String queryname){
		//Fetching usernames
		Session session =  InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		List<?> res = session.getNamedQuery(queryname).list();
		t.commit();
		session.close();
		return res;
	}
	
	public static List executeId(String queryname,String id){
		//Fetching usernames
		Session session =  InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		Query q2 = (Query) session.getNamedQuery(queryname);
		q2.setParameter("id", Long.parseLong(id));
		List<?> res = q2.list();
		t.commit();
		session.close();
		return res;
	}
	
	public static Boolean getWertung(String userID, String zeitID){
		boolean b = false;
		try{
		Session session =  InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		Query q2 = (Query) session.getNamedQuery(Variables.GETWERTUNG);
		q2.setParameter("userid", Long.parseLong(userID));
		q2.setParameter("zeitid", Long.parseLong(zeitID));

		List<Boolean> res = q2.list();
		b = res.get(0);
		t.commit();
		session.close();
		}
		catch(Exception e){
			System.out.println("no value");
			return null;
		}
		return b;
	}
	
	public static void saveObject(Object o){
		Abgestimmt a = (Abgestimmt) o;
		Boolean wertung = QueryHelper.getWertung(a.getUser().getID().longValue()+"", a.getZeit().getID().longValue()+"");
		if(wertung == null){
			Session session =  InitSession.getSession().openSession();
			Transaction t = session.beginTransaction();
			t.begin();
			session.save(o);
			t.commit();
			session.close();
		}
		else{
			System.out.println("gibts schon in der Db");
		}
	
	}
}
