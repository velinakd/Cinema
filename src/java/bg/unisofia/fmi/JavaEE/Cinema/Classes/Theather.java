package bg.unisofia.fmi.JavaEE.Cinema.Classes;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "T_THEATHER")
@NamedQuery(name = "AllTheathers", query = "select t from Theather t")

public class Theather {
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
	private Cinema cinema;
	
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
}