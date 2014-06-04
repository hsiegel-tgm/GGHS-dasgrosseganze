package model;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.example.myproject2.SendEmail;
import com.example.myproject2.Variables;




@NamedQueries({
	@NamedQuery(name=Variables.GETNOTIFICATION_BYUSERID,query="FROM DoodleNotification n WHERE n.user.ID = :id"), 
})
@Entity
@Table(name="notification")

/**
 * @version 1
 * @Author Hannah Siegel, Daniel Herczeg (Doku)
 *
 * Stellt eine Notification innerhalb des Kalender-Systemes dar
 *
**/
public class DoodleNotification {

	@Id
	@GeneratedValue
	private Long ID;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private User user;
	
	private String text;
	
	private boolean geliefert;

	private Date geschriebenAm;

	/**
	 * @author Hannah Siegel
	 * @version 1
	 *
	 * Default-Konstruktor.
	**/
	public DoodleNotification(){
	}
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param user    Benutzer, dem die Notification zugeordnet wird
	 * @param text    Die Nachricht
	 *
	 * Fuegt einen neuen Kommentar hinzu
	**/
	public DoodleNotification(User user, String text) {
		super();
		this.user = user;
		this.text = text;
		this.geliefert = false;
		this.geschriebenAm=new Date();
//		try {
//			// SendEmail.send(user.geteMail(), "New Notification", text); //TODO
//		} catch (AddressException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Der User, fuer den die Nachricht bestimmt ist
	 *
	 * Gibt den Benutzer zurueck
	**/
	public User getUser() {
		return user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param user    Setzt den Benutzer
	 *
	 * Setzt den Benutzer, fuer den die Benachrichtigung bestimmt ist
	**/
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Den Text, der dem benutzer mitgeteilt werden soll
	 *
	 * Gibt den Text der Benachrichtigung zurueck
	**/
	public String getText() {
		return text;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param text    Der neue Text der Notification
	 *
	 * Setzt einen neuen Text fuer die Notification
	**/
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Ein Wert, der angibt, ob die Nachricht zugestellt wurde, oder nicht.
	 *
	 * Gibt einen Wert zurueck, der angibt, ob die Nachricht zugestellt wurde, oder nicht.
	**/
	public boolean isGeliefert() {
		return geliefert;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param geliefert    Der neue Wert fuer geliefert
	 *
	 * Aendert den Wert, der angibt, ob eine Nachricht zugestellt wurde, oder nicht
	**/
	public void setGeliefert(boolean geliefert) {
		this.geliefert = geliefert;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Das Datum der Notification
	 *
	 * Gibt das Datum der Nachricht zurueck
	**/
	public Date getGeschriebenAm() {
		return geschriebenAm;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param geschriebenAm    Das neue Datum fuer die Benachrichtigung
	 *
	 * Setzt das Datum der Notification neu
	**/
	public void setGeschriebenAm(Date geschriebenAm) {
		this.geschriebenAm = geschriebenAm;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Die ID der Nachricht
	 *
	 * Gibt die ID der Notification zurueck
	**/
	public Long getID() {
		return ID;
	}
}