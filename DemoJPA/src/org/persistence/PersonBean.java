package org.persistence;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * Session Bean implementation class PersonBean
 */
@Stateless
@LocalBean
public class PersonBean  {

    /**
     * Default constructor. 
     */
    public PersonBean() {
        // TODO Auto-generated constructor stub
    }

    
    @PersistenceContext
    private EntityManager em;
	private Query query;

    /**
     * Get all persons from the table.
     */
    public List<Person> getAllPersons() {
        return em.createNamedQuery("AllPersons", Person.class).getResultList();
    }
    
    
    //Get one person from the table

    public List<Person> getFilterdPersons(String lastName) { 
    	query = null;
    	 query = em.createQuery("select p from Person p" + " where p.lastName = :name");
    	 query.setParameter("name",lastName);
		List<Person> resultList = query.getResultList();
		return resultList;	
    }
    
    
    /**
     * Add a person to the table.
     */
    public void addPerson(Person person) {
        em.persist(person);
        em.flush();
    }
    
    
}
