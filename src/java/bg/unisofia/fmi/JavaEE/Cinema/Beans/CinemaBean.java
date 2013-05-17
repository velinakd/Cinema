/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.unisofia.fmi.JavaEE.Cinema.Beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.unisofia.fmi.JavaEE.Cinema.Classes.Cinema;
import javax.persistence.Query;

/**
 * Session Bean implementation class CinemaBean
 */
@Stateless
@LocalBean
public class CinemaBean {

	@PersistenceContext
	private EntityManager em;

	public List<Cinema> getAllCinemas() {
		return em.createNamedQuery("AllCinemas", Cinema.class).getResultList();
	}
	
	public Cinema getCinemaByID(int id) {
		Query query = em.createQuery("SELECT c FROM Cinema c WHERE c.cinemaID = :id");
		query.setParameter("id", id);
		return (Cinema)query.getSingleResult();
	}

	public void addCinema(Cinema cinema) {
		em.persist(cinema);
		em.flush();
	}
}