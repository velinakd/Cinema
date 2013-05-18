package bg.unisofia.fmi.JavaEE.Cinema.Servlets;

import bg.unisofia.fmi.JavaEE.Cinema.Beans.MovieBean;
import bg.unisofia.fmi.JavaEE.Cinema.Beans.ScreeningBean;
import bg.unisofia.fmi.JavaEE.Cinema.Beans.SeatBean;
import bg.unisofia.fmi.JavaEE.Cinema.Beans.TheatherBean;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Movie;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Screening;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Theather;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ScreeningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ScreeningBean screeningBean;
        @EJB
        MovieBean movieBean;
        @EJB
        TheatherBean theatherBean;
        @EJB
        SeatBean seatBean;

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
                
		response.getWriter().println("<h1>Screening Servlet</h1>");
		try {
			appendScreeningTable(response);
                        appendScreeningAddForm(response);
                        appendScreeningRemoveForm(response);
		} catch (Exception e) {
			response.getWriter().println(
					"Persistence operation failed in GET with reason: "
							+ e.getMessage());
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			doScreeningAdd(request);
                        doScreeningRemove(request);
			doGet(request, response);
		} catch (Exception e) {
			response.getWriter().println(
					"Persistence operation failed with reason: "
							+ e.getMessage());
		}
	}

	private void appendScreeningTable(HttpServletResponse response)
			throws SQLException, IOException {
		// Append table that lists all cinemas
		List<Screening> resultList = screeningBean.getAllScreenings();
		response.getWriter().println(
				"<p><table border=\"1\"><tr><th colspan=\"5\">"
						+ (resultList.isEmpty() ? "0 " : resultList.size()
								+ " ")
								+ "Screenings in the Database</th></tr>");
		if (resultList.isEmpty()) {
			response.getWriter().println(
					"<tr><td colspan=\"5\">Database is empty</td></tr>");
		} else {
			response.getWriter()
			.println(
					"<tr><th>Screening ID</th><th>Type</th><th>Price</th><th>Movie Name</th><th>Theather name</th></tr>");
		}
		for (Screening s : resultList) {
			response.getWriter().println(
					"<tr><td>"
					+ s.getScreeningID()
					+ "</td><td>"
					+ s.getScreningType()
					+ "</td><td>"
					+ s.getScreeningPrice()
					+ "</td><td>"
					+ s.getScreeningMovie().getMovieName()
					+ "</td><td>"
                                        + s.getScreeningTheather().getTheatherNumber()
					+ "</td></tr>");
		}
		response.getWriter().println("</table></p>");
	}

	private void appendScreeningAddForm(HttpServletResponse response) throws IOException {
		// Append form through which new cinemas can be added
		response.getWriter()
				.println(
                                            "<p><form action=\"\" method=\"post\">"
                                                            + "<br>Theather ID:&nbsp;<input type=\"text\" name=\"TheatherID\">"
                                                            + "<br>Movie ID:&nbsp;<input type=\"text\" name=\"MovieID\">"
                                                            + "<br>Screening price:&nbsp;<input type=\"text\" name=\"ScreeningPrice\">"
                                                            + "<br>Screening type:&nbsp;<input type=\"text\" name=\"ScreeningType\">"
                                                            + "<br>&nbsp;<input type=\"submit\" value=\"Add Screening\">"
                                                            + "</form></p>");
	}

	private void doScreeningAdd(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		// Extract name of cinema to be added from request
                String theatherIDString = request.getParameter("TheatherID");
                String movieIDString = request.getParameter("MovieID");
                String screeningPrice = request.getParameter("ScreeningPrice");
                String screeningType = request.getParameter("ScreeningType");
                if (theatherIDString != null && movieIDString != null
                                && screeningPrice != null && screeningType != null
				&& !theatherIDString.trim().isEmpty()
                                && !movieIDString.trim().isEmpty()
                                && !screeningPrice.trim().isEmpty()
                                && !screeningType.trim().isEmpty()) {       
                    Theather theather = theatherBean.getTheatherByID(Long.parseLong(theatherIDString));
                    Movie movie = movieBean.getMovieByID(Long.parseLong(movieIDString));
                    Screening screening = new Screening();
                    screening.setScreeningMovie(movie);
                    screening.setScreeningPrice(Double.parseDouble(screeningPrice));
                    screening.setScreningType(screeningType);
                    screening.setScreeningTheather(theather);
                    screeningBean.addScreening(screening);
                }
	}
        
        private void appendScreeningRemoveForm(HttpServletResponse response) throws IOException {
        response.getWriter()
                        .println(
                                    "<p><form action=\"\" method=\"post\">"
                                                    + "<br>Screening ID:&nbsp;<input type=\"text\" name=\"RemoveScreeningID\">"
                                                    + "<br>&nbsp;<input type=\"submit\" value=\"Remove Screening\">"
                                                    + "</form></p>");
	}
	private void doScreeningRemove(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		String screeningRemovalID = request.getParameter("RemoveScreeningID");
		if (screeningRemovalID != null
				&& !screeningRemovalID.trim().isEmpty()) {
                    List<Screening> screeningList = screeningBean.getAllScreenings();
                    for (Screening s : screeningList)
                    {
                        if (s.getScreeningID() == Long.parseLong(screeningRemovalID))
                           screeningBean.removeScreeningByID(Long.parseLong(screeningRemovalID));
                    }
		}
	} 
}