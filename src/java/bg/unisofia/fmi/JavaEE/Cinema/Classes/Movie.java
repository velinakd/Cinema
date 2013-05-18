package bg.unisofia.fmi.JavaEE.Cinema.Classes;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class Movie
 */
@Entity
@Table(name = "T_MOVIE")
@NamedQuery(name = "AllMovies", query = "select m from Movie m")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieID;
    @Basic
    private String movieName;
    @Basic
    private int movieYear;
    @Basic
    private int movieLength;
    @Basic
    private int ageGroup;
    @Basic
    private String moviePoster;
    @Basic
    private String moviePlot;
    @OneToMany(mappedBy = "screeningMovie", fetch=FetchType.EAGER)
    private List<Screening> movieScreenings;

    public List<Screening> getMovieScreenings() {
        return movieScreenings;
    }

    public void setMovieScreenings(List<Screening> movieScreenings) {
        this.movieScreenings = movieScreenings;
    }

    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(int movieYear) {
        this.movieYear = movieYear;
    }

    public int getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(int movieLength) {
        this.movieLength = movieLength;
    }

    public int getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMoviePlot() {
        return moviePlot;
    }

    public void setMoviePlot(String moviePlot) {
        this.moviePlot = moviePlot;
    }
    public void addScreening(Screening screening)
    {
        this.movieScreenings.add(screening);
    }
}