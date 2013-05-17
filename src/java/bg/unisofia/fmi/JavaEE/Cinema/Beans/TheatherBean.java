package bg.unisofia.fmi.JavaEE.Cinema.Beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.unisofia.fmi.JavaEE.Cinema.Classes.Theather;
import javax.persistence.Query;

/**
 * Session Bean implementation class TheatherBean
 */
@Stateless
@LocalBean
public class TheatherBean {

	@PersistenceContext
	private EntityManager em;

	public List<Theather> getAllTheathers() {
		return em.createNamedQuery("AllTheathers", Theather.class).getResultList();
	}
        
        public Theather getTheatherByID(int id) {
            Query query = em.createQuery("SELECT t FROM Theather t WHERE t.theatherID = :id");
            query.setParameter("id", id);
            return (Theather)query.getSingleResult();
	}

	public void addTheather(Theather theather) {
		em.persist(theather);
		em.flush();
	}
}