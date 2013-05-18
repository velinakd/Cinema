package bg.unisofia.fmi.JavaEE.Cinema.Classes;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_SCREENING")
@NamedQuery(name = "AllScreenings", query = "select s from Screening s")
public class Screening implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screeningID;
    @ManyToOne
    private Theather screeningTheather;
    @Basic
    private String screningType;
    @ManyToOne
    private Movie screeningMovie;
    @Basic
    private Double screeningPrice;
    @OneToMany(mappedBy="screening")
    private List<ScreeningSeat> screeningSeatList;

    public List<ScreeningSeat> getScreeningSeatList() {
        return screeningSeatList;
    }

    public void setScreeningSeatList(List<ScreeningSeat> screeningSeatList) {
        this.screeningSeatList = screeningSeatList;
    }
    
    public Long getScreeningID() {
        return screeningID;
    }

    public Theather getScreeningTheather() {
        return screeningTheather;
    }

    public void setScreeningTheather(Theather screeningTheather) {
        this.screeningTheather = screeningTheather;
    }

    public String getScreningType() {
        return screningType;
    }

    public void setScreningType(String screningType) {
        this.screningType = screningType;
    }

    public Movie getScreeningMovie() {
        return screeningMovie;
    }

    public void setScreeningMovie(Movie screeningMovie) {
        this.screeningMovie = screeningMovie;
    }

    public Double getScreeningPrice() {
        return screeningPrice;
    }

    public void setScreeningPrice(Double screeningPrice) {
        this.screeningPrice = screeningPrice;
    }
    public void addScreeningSeat(ScreeningSeat screeningSeat) {
        this.screeningSeatList.add(screeningSeat);
    }
    
}