package bg.unisofia.fmi.JavaEE.Cinema.Beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.unisofia.fmi.JavaEE.Cinema.Classes.Movie;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Screening;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.ScreeningSeat;

/**
 * Session Bean implementation class CinemaBean
 */
@Stateless
@LocalBean
public class MovieBean {

	@PersistenceContext
	private EntityManager em;

	public List<Movie> getAllMovies() {
		return em.createNamedQuery("AllMovies", Movie.class).getResultList();
	}
	
	public Movie getMovieByID(long id) {
		return em.find(Movie.class, id);
	}

	public void addMovie(Movie movie) {
		em.persist(movie);
		em.flush();
	}
        
        public void removeMovie(Movie movie) {
                for (Screening s : movie.getMovieScreenings())
                {
                    for (ScreeningSeat x : s.getScreeningSeatList())
                        em.remove(x);
                    s.setScreeningTheather(null);
                    em.remove(s);
                }
                em.flush();
                em.remove(movie);
                em.flush();
        }

        public void removeMovieByID(long removalID) {
                removeMovie(getMovieByID(removalID));
        }
}