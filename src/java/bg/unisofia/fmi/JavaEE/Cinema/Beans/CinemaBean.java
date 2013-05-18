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
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Seat;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Theather;

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
	
	public Cinema getCinemaByID(long id) {
		return em.find(Cinema.class, id);
	}

	public void addCinema(Cinema cinema) {
		em.persist(cinema);
		em.flush();
	}
        
        public void removeCinema(Cinema cinema) {
                for (Theather t : cinema.getTheatherList())
                {
                    for (Seat s : t.getSeatList())
                        em.remove(s);
                    em.remove(t);
                }
                em.remove(cinema);
                em.flush();
        }

        public void removeCinemaByID(long removalID) {
                Cinema cinema = getCinemaByID(removalID);
                for (Theather t : cinema.getTheatherList())
                {
                    for (Seat s : t.getSeatList())
                        em.remove(s);
                    em.remove(t);
                }
                em.remove(cinema);
                em.flush();
        }
}