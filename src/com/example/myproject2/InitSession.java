package com.example.myproject2;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/*
 * 
 * 2014-05-07 Hannah erstellt
 * 
 * 
 * TODO JUnit
 * TODO Komment
 * TODO Design pefekto
 * TODO GUI Test
 * TODO Variablen
 * TODO Coding style
 * 
 * 
 * 
 * 
 * */
public class InitSession {

	private static SessionFactory sessionFactory = buildsf(); 
	 
	
	 public static SessionFactory buildsf() { 
	    try {
	    	 SessionFactory sessionFactory = new AnnotationConfiguration()
	         .configure("/hibernate.cfg.xml") // give path to hibernate.cfg.xml (recommended)
	         .buildSessionFactory();
	    	 
	    	 return sessionFactory;
	    	
	    } catch (Throwable ex) { 
	      System.err.println("Initial SessionFactory creation failed." + ex);
	      throw new ExceptionInInitializerError(ex);
	    }
	 }
	 
	 public static SessionFactory getSession(){
		 return sessionFactory;
	 }
	 
} 