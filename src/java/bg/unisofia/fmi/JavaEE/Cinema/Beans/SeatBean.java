package bg.unisofia.fmi.JavaEE.Cinema.Beans;

import bg.unisofia.fmi.JavaEE.Cinema.Classes.Seat;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Theather;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;

/**
 * Session Bean implementation class SeatBean
 */
@Stateless
@LocalBean
public class SeatBean {

	@PersistenceContext
	private EntityManager em;

	public List<Seat> getAllSeats() {
		return em.createNamedQuery("AllSeats", Seat.class).getResultList();
	}
        
        public Seat getSeatByID(long id) {
            return em.find(Seat.class, id);
	}

	public void addSeat(Seat seat) {
		em.persist(seat);
		em.flush();
                seat.getTheather().addSeat(seat);
                em.merge(seat.getTheather());
	}
        public void invalidateSeat(Seat seat)
        {
                seat.setSeatStatus("Invalid");
                List<Seat> seatList = seat.getTheather().getSeatList();
                seatList.set(seat.getSeatRelativeNumber()-1, seat);
                seat.getTheather().setSeatList(seatList);
                em.merge(seat);
        }
}