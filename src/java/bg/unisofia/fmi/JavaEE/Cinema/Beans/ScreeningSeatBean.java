package bg.unisofia.fmi.JavaEE.Cinema.Beans;

import bg.unisofia.fmi.JavaEE.Cinema.Classes.ScreeningSeat;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Session Bean implementation class SeatBean
 */
@Stateless
@LocalBean
public class ScreeningSeatBean {

	@PersistenceContext
	private EntityManager em;

	public List<ScreeningSeat> getAllScreeningSeats() {
		return em.createNamedQuery("AllScreeningSeats", ScreeningSeat.class).getResultList();
	}
        
        public ScreeningSeat getSeatByID(long id) {
            return em.find(ScreeningSeat.class, id);
	}

	public void addSeat(ScreeningSeat seat) {
		em.persist(seat);
		em.flush();
                seat.getScreening().addScreeningSeat(seat);
                em.merge(seat.getScreening());
	}        
        
        public void reserveScreeningSeat(ScreeningSeat seat) {
            if (seat.getScreeningSeatStatus().equals("Invalid")) { }
            else
            {
                List<ScreeningSeat> screeningSeatList = seat.getScreening().getScreeningSeatList();
                seat.setScreeningSeatStatus("Reserved");
                screeningSeatList.set(seat.getScreeningSeatRelativeNumber()-1, seat);
                seat.getScreening().setScreeningSeatList(screeningSeatList);
                em.merge(seat.getScreening());               
            }
        }
        
        public void reserveScreeningSeatByID(Long id)
        {
            reserveScreeningSeat(getSeatByID(id));
        }
        
        public void removeReservation(ScreeningSeat seat) {
            if (seat.getScreeningSeatStatus().equals("Invalid")) { }
            else
            {
                List<ScreeningSeat> screeningSeatList = seat.getScreening().getScreeningSeatList();
                seat.setScreeningSeatStatus("Available");
                screeningSeatList.set(seat.getScreeningSeatRelativeNumber()-1, seat);
                seat.getScreening().setScreeningSeatList(screeningSeatList);
                em.merge(seat.getScreening());                
            }
        }
        public void removeReservationByID(Long id)
        {
            removeReservation(getSeatByID(id));
        }
}