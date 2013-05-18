package bg.unisofia.fmi.JavaEE.Cinema.Classes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "T_SSEAT")
@NamedQuery(name = "AllScreeningSeats", query = "select s from ScreeningSeat s")

public class ScreeningSeat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screeningSeatID;
    @Basic
    private int screeningSeatRow;
    @Basic
    private int screeningSeatNumber;
    @Basic
    private int screeningSeatRelativeNumber;
    @Basic
    private String screeningSeatStatus;  
    @ManyToOne
    private Screening screening;

    public Long getScreeningSeatID() {
        return screeningSeatID;
    }

    public int getScreeningSeatRow() {
        return screeningSeatRow;
    }

    public void setScreeningSeatRow(int screeningSeatRow) {
        this.screeningSeatRow = screeningSeatRow;
    }

    public int getScreeningSeatNumber() {
        return screeningSeatNumber;
    }

    public void setScreeningSeatNumber(int screeningSeatNumber) {
        this.screeningSeatNumber = screeningSeatNumber;
    }

    public int getScreeningSeatRelativeNumber() {
        return screeningSeatRelativeNumber;
    }

    public void setScreeningSeatRelativeNumber(int screeningSeatRelativeNumber) {
        this.screeningSeatRelativeNumber = screeningSeatRelativeNumber;
    }

    public String getScreeningSeatStatus() {
        return screeningSeatStatus;
    }

    public void setScreeningSeatStatus(String screeningSeatStatus) {
        this.screeningSeatStatus = screeningSeatStatus;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }
}
