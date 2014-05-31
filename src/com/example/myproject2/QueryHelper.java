package com.example.myproject2;

import java.util.List;

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
}
