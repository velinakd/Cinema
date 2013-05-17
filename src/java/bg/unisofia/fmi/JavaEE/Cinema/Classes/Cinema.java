package bg.unisofia.fmi.JavaEE.Cinema.Classes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class Cinema.
 */
@Entity
@Table(name = "T_CINEMA")
@NamedQuery(name = "AllCinemas", query = "select c from Cinema c")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinemaID;
    @Basic
    private String cinemaName;
    @Basic
    private String cinemaAddress;
    @Basic
    private String cinemaPhone;
    @Basic
    private String cinemaEMail;
    @OneToMany
    private List<Theather> theatherList;
    
    public Cinema()
    {
    	theatherList = new ArrayList<Theather>();
    }

    public long getCinemaID() {
        return cinemaID;
    }

	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

	public String getCinemaAddress() {
		return cinemaAddress;
	}

	public void setCinemaAddress(String cinemaAddress) {
		this.cinemaAddress = cinemaAddress;
	}

	public String getCinemaPhone() {
		return cinemaPhone;
	}

	public void setCinemaPhone(String cinemaPhone) {
		this.cinemaPhone = cinemaPhone;
	}

	public String getCinemaEMail() {
		return cinemaEMail;
	}

	public void setCinemaEMail(String cinemaEMail) {
		this.cinemaEMail = cinemaEMail;
	}

	public List<Theather> getTheatherList() {
		return theatherList;
	}

	public void setTheatherList(List<Theather> theatherList) {
		this.theatherList = theatherList;
	}
    
}