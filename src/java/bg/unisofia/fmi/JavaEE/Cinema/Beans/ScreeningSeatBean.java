package bg.unisofia.fmi.JavaEE.Cinema.Beans;

import bg.unisofia.fmi.JavaEE.Cinema.Classes.Screening;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.ScreeningSeat;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Seat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
        
        public void addSeatsForScreening(Screening screening) {
		long rowCount = screening.getScreeningTheather().getRowCount();
                long rowSeats = screening.getScreeningTheather().getSeatCount();
                List<ScreeningSeat> sSeatList = new ArrayList<ScreeningSeat>();
                for (int i=1; i<= rowCount; i++)
                    for (int j=1; j <= rowSeats; j++)
                    {
                        Seat seat = screening.getScreeningTheather().getSeatList()
                                .get((i-1)*(int)rowSeats+j-1);
                        ScreeningSeat sSeat = new ScreeningSeat();
                        sSeat.setScreening(screening);
                        sSeat.setScreeningSeatNumber(seat.getSeatNumber());
                        sSeat.setScreeningSeatRow(seat.getSeatRow());
                        sSeat.setScreeningSeatRelativeNumber(seat.getSeatRelativeNumber());
                        sSeat.setScreeningSeatStatus(seat.getSeatStatus());
                        em.persist(sSeat);
                        em.flush();
                        sSeatList.add(sSeat);
                    }
                screening.setScreeningSeatList(sSeatList);
                em.merge(screening);
	}    
        
        public void reserveScreeningSeat(ScreeningSeat seat) {
            if (seat.getScreeningSeatStatus().equals("Invalid")) { }
            else if (!seat.getScreeningSeatStatus().equals("Available")) { }
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
            else if (!seat.getScreeningSeatStatus().equals("Reserved")) { }
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
        
        public void sellSeat(ScreeningSeat seat)
        {
            List<ScreeningSeat> seatList = seat.getScreening().getScreeningSeatList();
            if (seat.getScreeningSeatStatus().equals("Reserved"))
                seat.setScreeningSeatStatus("Sold");
            else if (seat.getScreeningSeatStatus().equals("Invalid")) { }
            else
                seat.setScreeningSeatStatus("Sold [No Reservation]");
            seatList.set(seat.getScreeningSeatRelativeNumber()-1, seat);
            seat.getScreening().setScreeningSeatList(seatList);
            em.merge(seat);
        }
        public void sellSeatByID(long id)
        {
            sellSeat(getSeatByID(id));
        }
}