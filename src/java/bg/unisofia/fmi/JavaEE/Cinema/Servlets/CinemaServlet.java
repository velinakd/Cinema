package bg.unisofia.fmi.JavaEE.Cinema.Servlets;

import bg.unisofia.fmi.JavaEE.Cinema.Beans.CinemaBean;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Cinema;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Theather;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CinemaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	CinemaBean cinemaBean;

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
                
		response.getWriter().println("<h1>Cinema Servlet</h1>");
		try {
			appendCinemaTable(response);
			appendCinemaAddForm(response);
                        appendCinemaRemoveForm(response);
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
			doCinemaAdd(request);
                        doCinemaRemove(request);
			doGet(request, response);
		} catch (Exception e) {
			response.getWriter().println(
					"Persistence operation failed with reason: "
							+ e.getMessage());
		}
	}

	private void appendCinemaTable(HttpServletResponse response)
			throws SQLException, IOException {
		// Append table that lists all cinemas
		List<Cinema> resultList = cinemaBean.getAllCinemas();
		response.getWriter().println(
				"<p><table border=\"1\"><tr><th colspan=\"7\">"
						+ (resultList.isEmpty() ? "0 " : resultList.size()
								+ " ")
								+ "Cinemas in the Database</th></tr>");
		if (resultList.isEmpty()) {
			response.getWriter().println(
					"<tr><td colspan=\"7\">Database is empty</td></tr>");
		} else {
			response.getWriter()
			.println(
					"<tr><th>Cinema ID</th><th>Cinema Name</th><th>Cinema Address</th><th>Cinema Phone</th><th>Cinema E-Mail</th><th>Theather count</th><th>Total seats</th></tr>");
		}
		for (Cinema c : resultList) {
                        List<Theather> theatherList = c.getTheatherList();
                        int tmp=0;
                        for (Theather t : theatherList)
                            tmp+=t.getSeatList().size();
			response.getWriter().println(
					"<tr><td>"
					+ c.getCinemaID()
					+ "</td><td>"
					+ c.getCinemaName()
					+ "</td><td>"
					+ c.getCinemaAddress()
					+ "</td><td>"
					+ c.getCinemaPhone()
					+ "</td><td>"
					+ c.getCinemaEMail()
                                        + "</td><td>"
                                        + c.getTheatherList().size()
                                        + "</td><td>"
                                        + tmp
					+ "</td></tr>");
		}
		response.getWriter().println("</table></p>");
	}

	private void appendCinemaAddForm(HttpServletResponse response) throws IOException {
		// Append form through which new cinemas can be added
		response.getWriter()
				.println(
                                            "<p><form action=\"\" method=\"post\">"
                                                            + "<br>Cinema name:&nbsp;<input type=\"text\" name=\"CinemaName\">"
                                                            + "<br>Cinema address:&nbsp;<input type=\"text\" name=\"CinemaAddress\">"
                                                            + "<br>Cinema phone:&nbsp;<input type=\"text\" name=\"CinemaPhone\">"
                                                            + "<br>Cinema E-Mail:&nbsp;<input type=\"text\" name=\"CinemaEMail\">"
                                                            + "<br>&nbsp;<input type=\"submit\" value=\"Add Cinema\">"
                                                            + "</form></p>");
	}

	private void doCinemaAdd(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		// Extract name of cinema to be added from request
		String cinemaName = request.getParameter("CinemaName");
		String cinemaAddress = request.getParameter("CinemaAddress");
		String cinemaPhone = request.getParameter("CinemaPhone");
		String cinemaEMail = request.getParameter("CinemaEMail");

		if (cinemaName != null && cinemaAddress != null
				&& cinemaPhone != null && cinemaEMail != null
				&& !cinemaName.trim().isEmpty() && !cinemaAddress.trim().isEmpty()
				&& !cinemaPhone.trim().isEmpty() && !cinemaEMail.trim().isEmpty()) {
			Cinema cinema = new Cinema();
			cinema.setCinemaName(cinemaName);
			cinema.setCinemaAddress(cinemaAddress);
			cinema.setCinemaPhone(cinemaPhone);
			cinema.setCinemaEMail(cinemaEMail);

			cinemaBean.addCinema(cinema);
		}
	}
        
        private void appendCinemaRemoveForm(HttpServletResponse response) throws IOException {
        // Append form through which new cinemas can be added
        response.getWriter()
                        .println(
                                    "<p><form action=\"\" method=\"post\">"
                                                    + "<br>Cinema ID:&nbsp;<input type=\"text\" name=\"RemoveCinemaID\">"
                                                    + "<br>&nbsp;<input type=\"submit\" value=\"Remove Cinema\">"
                                                    + "</form></p>");
	}
	private void doCinemaRemove(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		// Extract name of cinema to be added from request
		String cinemaRemovalID = request.getParameter("RemoveCinemaID");

		if (cinemaRemovalID != null
				&& !cinemaRemovalID.trim().isEmpty()) {
                      List<Cinema> allCinemas = cinemaBean.getAllCinemas();
                      for (Cinema c : allCinemas)
                      {
                          if (c.getCinemaID() == Long.parseLong(cinemaRemovalID))
                            cinemaBean.removeCinemaByID(c.getCinemaID());
                      }
		}
	}

}