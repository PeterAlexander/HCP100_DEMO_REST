package org.persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "T_Person")
@NamedQueries({
	@NamedQuery(name = "AllPersons", query = "select p from Person p"),
	@NamedQuery(name = "Filtered", query = "select p from Person p where p.lastName = :pname")
	})

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	public Person() {	
	}
	@Id
	@GeneratedValue
	private String id;
	@Basic
	private String firstName;
	@Basic
	private String lastName;
	@Basic
	private String eMail;
	@Basic
	private String homeTown;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String param) {
		this.firstName = param;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String param) {
		this.lastName = param;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String param) {
		this.eMail = param;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String param) {
		this.homeTown = param;
	}

}