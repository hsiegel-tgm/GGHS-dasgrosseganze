package com.example.myproject2;

import java.util.List;

import model.Abgestimmt;
import model.DoodleEvent;
import model.DoodleNotification;
import model.Eingeladen;
import model.User;
import model.Zeit;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.vaadin.ui.Notification;

public class QueryHelper {

	public static List executeBasic(String queryname) {
		// Fetching usernames
		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		List<?> res = session.getNamedQuery(queryname).list();
		t.commit();
		session.close();
		return res;
	}

	public static List executeId(String queryname, String id) {
		// Fetching usernames
		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		Query q2 = (Query) session.getNamedQuery(queryname);
		q2.setParameter("id", Long.parseLong(id));
		List<?> res = q2.list();
		t.commit();
		session.close();
		return res;
	}

	public static List executeLike(String queryname, String id) {
		// Fetching usernames
		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		Query q2 = (Query) session.getNamedQuery(queryname);
		q2.setParameter("id", "%" + id + "%");
		List<?> res = q2.list();
		t.commit();
		session.close();
		return res;
	}

	public static Boolean getWertung(String userID, String zeitID) {
		boolean b = false;
		try {
			Session session = InitSession.getSession().openSession();
			Transaction t = session.beginTransaction();
			t.begin();
			Query q2 = (Query) session.getNamedQuery(Variables.GETWERTUNG);
			q2.setParameter("userid", Long.parseLong(userID));
			q2.setParameter("zeitid", Long.parseLong(zeitID));

			List<Boolean> res = q2.list();
			b = res.get(0);
			t.commit();
			session.close();
		} catch (Exception e) {
			System.out.println("no value");
			return null;
		}
		return b;
	}

	public static List<Abgestimmt> executeAbstimmung(String userID,
			String zeitID) {
		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		Query q2 = (Query) session
				.getNamedQuery(Variables.GETABGESTIMMT_BYIDEVENT);
		q2.setParameter("userid", Long.parseLong(userID));
		q2.setParameter("zeitid", Long.parseLong(zeitID));
		List<Abgestimmt> res = q2.list();
		t.commit();
		session.close();

		return res;

	}

	public static void delete(Object o) {
		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		session.delete(o);
		t.commit();
		session.close();
	}

	public static void update(Object o) {
		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		session.update(o);
		t.commit();
		session.close();
	}

	public static void saveAbgestimmt(Zeit z, User u, boolean value) {

		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		Query q2 = (Query) session
				.getNamedQuery(Variables.GETABGESTIMMT_BYIDEVENT);
		q2.setParameter("userid", Long.parseLong(u.getID() + ""));
		q2.setParameter("zeitid", Long.parseLong(z.getID() + ""));
		List<Abgestimmt> res = q2.list();

		if (res.size() != 0) {
			Abgestimmt a = res.get(0);
			a.setWertung(value);
			session.update(a);
		} else {
			Abgestimmt a = new Abgestimmt(u, z, value);
			session.save(a);
		}

		t.commit();
		session.close();

	}

	public static boolean saveObject(Object o) {
		boolean b = true;
		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		session.save(o);
		t.commit();
		session.close();

		return b;
	}

	public static void notificate(DoodleEvent e, String text) {

		Session session = InitSession.getSession().openSession();
		Transaction t = session.beginTransaction();
		t.begin();
		Query q2 = (Query) session
				.getNamedQuery(Variables.GETEINGELADEN_BYEVENTID);
		q2.setParameter("id", Long.parseLong(e.getID() + ""));
		List<Eingeladen> res = q2.list();
		t.commit();
		session.close();

		for (Eingeladen ein : res) {
			User u = ein.getUser();
			DoodleNotification dn = new DoodleNotification(u, text);
			QueryHelper.saveObject(dn);
		}

	}

	public static Boolean usershavevoted(DoodleEvent e) {
		List<Zeit> times = QueryHelper.executeId(Variables.GETZEIT_BYEVENTID,
				e.getID() + "");
		List<Eingeladen> usersinvited = QueryHelper.executeId(
				Variables.GETEINGELADEN_BYEVENTID, e.getID() + "");

		Zeit z = times.get(0);

		List<Abgestimmt> a = QueryHelper.executeId(
				Variables.GETABGESTIMMT_BYEVENTID, z.getID().longValue() + "");

		if (a == null)
			return null;
		else if (a.size() == usersinvited.size())
			return true;
		else
			return false;
	}

	public static void deleteAbgestimmt(String eventid) {
		Session session = null;
		Transaction t = null;

		try {
			List<Zeit> zeiten = QueryHelper.executeId(Variables.GETZEIT_BYEVENTID, eventid);
			for (Zeit z : zeiten) {
				session = InitSession.getSession().openSession();
				t = session.beginTransaction();
				t.begin();
				Query q2 = (Query) session
						.getNamedQuery(Variables.GETABGESTIMMT_BYEVENTID);
				q2.setParameter("id", Long.parseLong(z.getID() + ""));
				List<Abgestimmt> res = q2.list();
				if (res.size() != 0) {
					session.delete(res.get(0));
				}
			}
		} catch (Exception e) {
			// TODO
		} finally {
			t.commit();
			session.close();
		}
	}
	
	//TODO thread macht...
	public static void updateNotification(String userid){
		List <DoodleNotification> m_notifications =  QueryHelper.executeId(Variables.GETNOTIFICATION_BYUSERID,userid);
		
		for (DoodleNotification dn : m_notifications ){
			if(!dn.isGeliefert()){
				Notification.show(dn.getText(),Notification.Type.TRAY_NOTIFICATION);
				dn.setGeliefert(true);
				QueryHelper.update(dn);
			}
		}
	}
}
