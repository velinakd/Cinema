package bg.unisofia.fmi.JavaEE.Cinema.Beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.unisofia.fmi.JavaEE.Cinema.Classes.Screening;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.ScreeningSeat;
import java.util.Iterator;

/**
 * Session Bean implementation class CinemaBean
 */
@Stateless
@LocalBean
public class ScreeningBean {

	@PersistenceContext
	private EntityManager em;

	public List<Screening> getAllScreenings() {
		return em.createNamedQuery("AllScreenings", Screening.class).getResultList();
	}
	
	public Screening getScreeningByID(long id) {
		return em.find(Screening.class, id);
	}

	public void addScreening(Screening screening) {
		em.persist(screening);
		em.flush();
	}
        
        public void removeScreening(Screening screening) {
                for (ScreeningSeat s : screening.getScreeningSeatList())
                em.remove(s);
                List<Screening> screeningList = screening.getScreeningMovie().getMovieScreenings();
                screeningList.remove(screening);
                screening.getScreeningMovie().setMovieScreenings(screeningList);
                em.flush();
                em.remove(screening);
                em.flush();
        }

        public void removeScreeningByID(long removalID) {
                removeScreening(getScreeningByID(removalID));
        }
}