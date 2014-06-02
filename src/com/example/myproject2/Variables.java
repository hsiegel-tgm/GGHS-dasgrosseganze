package com.example.myproject2;

/**
 * @author Hannah Siegel
 *
 */
public class Variables {
	//Pages
	public static final String USERNAME = "username";
	public static final String LOGIN = "";
	public static final String REGISTER = "newuser";
	public static final String STARTPAGE = "main";
	public static final String NEWEVENT = "newevent";
	public static final String EDITEVENT = "editevent";
	public static final String COMMENT = "comment";
	public static final String VOTE = "vote";
	public static final String SHOWQUERYRESULT = "showresult";
	public static final String SHOWNOTIFICATIONS = "shownotification";

	
	
	//Buttons
	public static final String SAVE = "Save";
	public static final String BACK = "Back";
	public static final String LOGOUT = "Logout";
	
	//Querys
	public static final String GETALLEVENTS = "getEvents";
	public static final String GETEVENT_BYID = "getSpecificEvent";
	public static final String GETEVENT_BYADMIN = "getAdminsEvent";
	public static final String GETABGESTIMMT_BYUSER = "getAbgestimmtfromspecificUser";
	public static final String GETABGESTIMMT = "getAbgestimmt";
	public static final String GETWERTUNG = "getWertung";
	public static final String GETKOMMENTS = "getKommentsforEvent";
	public static final String GETUSER = "getUsers";
	public static final String GETUSER_BYID = "getSpecificUser";
	public static final String GETUSER_BYNAME = "getSpecificUserbyName";
	public static final String GETABGESTIMMT_BYIDEVENT = "getAbgestimmtbyuserevent";
	public static final String GETZEIT_BYEVENTID = "getTimePossibilitesforSpecificEvent";
	public static final String GETEINGELADEN_BYEVENTID = "getEingeladenforSpecificEvent";
	public static final String GETABGESTIMMT_BYEVENTID = "getAbgestimmtbyevent";
	public static final String GETNOTIFICATION_BYUSERID = "getNotificationbyuser";

	
	public static String getInvitedText(String username2, String name) {
		return "Dear "+username2 +" You have just been invited to the event called "+name;
	}

	

//	public static final String GETALLEVENTS = "getEvents";
//	public static final String GETALLEVENTS = "getEvents";
//	public static final String GETALLEVENTS = "getEvents";

	
}
