package bg.unisofia.fmi.JavaEE.Cinema.Classes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "T_SEAT")
@NamedQuery(name = "AllSeats", query = "select s from Seat s")

public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatID;
    @Basic
    private int seatRow;
    @Basic
    private int seatNumber;
    @Basic
    private int seatRelativeNumber;
    @Basic
    private String seatStatus;    
    @ManyToOne
    @JoinColumn(name="THEATHER", nullable=false)
    private Theather theather;

    public Long getSeatID() {
        return seatID;
    }

    public void setSeatID(Long seatID) {
        this.seatID = seatID;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getSeatRelativeNumber() {
        return seatRelativeNumber;
    }

    public void setSeatRelativeNumber(int seatRelativeNumber) {
        this.seatRelativeNumber = seatRelativeNumber;
    }
    
    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }

    public Theather getTheather() {
        return theather;
    }

    public void setTheather(Theather theather) {
        this.theather = theather;
    }

    
}
