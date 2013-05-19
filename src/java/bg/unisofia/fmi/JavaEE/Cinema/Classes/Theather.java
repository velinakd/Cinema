package bg.unisofia.fmi.JavaEE.Cinema.Classes;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "T_THEATHER")
@NamedQuery(name = "AllTheathers", query = "select t from Theather t")

public class Theather implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long theatherID;
	@Basic
	private long theatherNumber; //local
        @Basic
        private long rowCount;
        @Basic
        private long seatCount;
        @ManyToOne
        @JoinColumn(name="CINEMA", nullable=false)
	private Cinema cinema;
        
        @OneToMany(mappedBy = "theather", fetch=FetchType.EAGER)
        private List<Seat> seatList;
        
        @OneToMany(mappedBy = "screeningTheather", fetch=FetchType.EAGER)
        private List<Screening> screeningList;
	
	public long getTheatherID() {
		return theatherID;
	}
	public void setTheatherID(long theatherID) {
		this.theatherID = theatherID;
	}
	public long getTheatherNumber() {
		return theatherNumber;
	}
	public void setTheatherNumber(long theatherNumber) {
		this.theatherNumber = theatherNumber;
	}
        public long getRowCount() {
            return rowCount;
        }

        public void setRowCount(long rowCount) {
            this.rowCount = rowCount;
        }

        public long getSeatCount() {
            return seatCount;
        }

        public void setSeatCount(long seatCount) {
            this.seatCount = seatCount;
        }
	public Cinema getCinema() {
		return cinema;
	}
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
        public List<Seat> getSeatList() {
                return seatList;
        }

        public void setSeatList(List<Seat> seatList) {
                this.seatList = seatList;
        }
        public void addSeat(Seat seat)
        {
            this.seatList.add(seat);
        }
        public List<Screening> getScreeningList() {
            return screeningList;
        }

        public void setScreeningList(List<Screening> screeningList) {
            this.screeningList = screeningList;
        }
        public void addScreening(Screening screening)
        {
            this.screeningList.add(screening);
        }
}