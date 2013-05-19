package bg.unisofia.fmi.JavaEE.Cinema.Beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.unisofia.fmi.JavaEE.Cinema.Classes.Cinema;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Theather;
import javax.ejb.EJB;

/**
 * Session Bean implementation class CinemaBean
 */
@Stateless
@LocalBean
public class CinemaBean {

	@PersistenceContext
	private EntityManager em;
        
        @EJB
        TheatherBean theatherBean;

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
                    theatherBean.removeForCinema(t);
                }
                em.remove(cinema);
                em.flush();
        }

        public void removeCinemaByID(long removalID) {
                removeCinema(getCinemaByID(removalID));
        }
}