package bg.unisofia.fmi.JavaEE.Cinema.Beans;

import bg.unisofia.fmi.JavaEE.Cinema.Classes.Screening;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.ScreeningSeat;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Seat;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.unisofia.fmi.JavaEE.Cinema.Classes.Theather;

/**
 * Session Bean implementation class TheatherBean
 */

@Stateless
@LocalBean
public class TheatherBean {

	@PersistenceContext
	private EntityManager em;

	public List<Theather> getAllTheathers() {
		return em.createNamedQuery("AllTheathers", Theather.class).getResultList();
	}
        
	public Theather getTheatherByID(long id) {
		return em.find(Theather.class, id);
	}

	public void addTheather(Theather theather) {
		em.persist(theather);
		em.flush();
                theather.getCinema().addTheather(theather);
                em.merge(theather.getCinema());
	}
        
        public void removeTheather(Theather theather) {
                for (Seat s : theather.getSeatList())
                    em.remove(s);
                for (Screening s : theather.getScreeningList())
                {
                    for (ScreeningSeat ss : s.getScreeningSeatList())
                        em.remove(ss);
                    List<Screening> screeningList = s.getScreeningMovie().getMovieScreenings();
                    screeningList.remove(s);
                    s.getScreeningMovie().setMovieScreenings(screeningList);
                    em.remove(s);
                }
                List<Theather> theatherList = theather.getCinema().getTheatherList();
                theatherList.remove(theather);
                theather.getCinema().setTheatherList(theatherList);
                em.flush();
                em.remove(theather);
                em.flush();
        }
        public void removeForCinema(Theather theather) {
                 for (Seat s : theather.getSeatList())
                    em.remove(s);
                for (Screening s : theather.getScreeningList())
                {
                    for (ScreeningSeat ss : s.getScreeningSeatList())
                        em.remove(ss);
                    List<Screening> screeningList = s.getScreeningMovie().getMovieScreenings();
                    screeningList.remove(s);
                    s.getScreeningMovie().setMovieScreenings(screeningList);
                    em.remove(s);
                }
                em.remove(theather);
                em.flush();           
        }

        public void removeTheatherByID(long removalID) {
                removeTheather(getTheatherByID(removalID));
        }
}