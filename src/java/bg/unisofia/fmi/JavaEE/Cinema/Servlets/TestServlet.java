package bg.unisofia.fmi.JavaEE.Cinema.Servlets;

import bg.unisofia.fmi.JavaEE.Cinema.Beans.CinemaBean;
import bg.unisofia.fmi.JavaEE.Cinema.Beans.TheatherBean;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Cinema;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Theather;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PersistenceEJBServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	CinemaBean cinemaBean;
        @EJB
	TheatherBean theatherBean;

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
                
		response.getWriter().println("<p>Persistence with JPA!</p>");
		try {
			appendCinemaTable(response);
			appendCinemaAddForm(response);
			appendTheatherTable(response);
			appendTheatherAddForm(response);
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
			doTheatherAdd(request);
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
				"<p><table border=\"1\"><tr><th colspan=\"5\">"
						+ (resultList.isEmpty() ? "" : resultList.size()
								+ " ")
								+ "Entries in the Database</th></tr>");
		if (resultList.isEmpty()) {
			response.getWriter().println(
					"<tr><td colspan=\"5\">Database is empty</td></tr>");
		} else {
			response.getWriter()
			.println(
					"<tr><th>Cinema ID</th><th>Cinema Name</th><th>Cinema Address</th><th>Cinema Phone</th><th>Cinema E-Mail</th></tr>");
		}
		for (Cinema c : resultList) {
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

	private void appendTheatherTable(HttpServletResponse response)
			throws SQLException, IOException {
		// Append table that lists all theathers
		List<Theather> resultList = theatherBean.getAllTheathers();
		response.getWriter().println(
				"<p><table border=\"1\"><tr><th colspan=\"5\">"
						+ (resultList.isEmpty() ? "" : resultList.size()
								+ " ")
								+ "Entries in the Database</th></tr>");
		if (resultList.isEmpty()) {
			response.getWriter().println(
					"<tr><td colspan=\"5\">Database is empty</td></tr>");
		} else {
			response.getWriter()
			.println(
					"<tr><th>Theather ID</th><th>Theather Number</th><th>Row count</th><th>Seat count</th><th>Cinema name</th></tr>");
		}
		for (Theather t : resultList) {
			response.getWriter().println(
					"<tr><td>"
					+ t.getTheatherID()
                                        + "</td><td>"
                                        + t.getTheatherNumber()
                                        + "</td><td>"
                                        + t.getRowCount()
                                        + "</td><td>"
                                        + t.getSeatCount()
                                        + "</td><td>"
                                        + t.getCinema().getCinemaName()
                                        + "</td></tr>");
		}
		response.getWriter().println("</table></p>");
	}

        private void appendTheatherAddForm(HttpServletResponse response) throws IOException {
                // Append form through which new theathers can be added
                response.getWriter()
                                .println(
                                                "<p><form action=\"\" method=\"post\">"
                                                                + "<br>Theather Number:&nbsp;<input type=\"text\" name=\"TheatherNumber\">"
                                                                + "<br>Row count:&nbsp;<input type=\"text\" name=\"RowCount\">"
                                                                + "<br>Seat count:&nbsp;<input type=\"text\" name=\"SeatCount\">"
                                                                + "<br>Cinema ID:&nbsp;<input type=\"text\" name=\"TCinemaID\">"
                                                                + "<br><input type=\"submit\" value=\"Add Theather\">"
                                                                + "</form></p>");
        }

        private void doTheatherAdd(HttpServletRequest request) throws ServletException,
                        IOException, SQLException {
                // Extract name of theather to be added from request
                String theatherNumber = request.getParameter("TheatherNumber");
                String rowCount = request.getParameter("RowCount");
                String seatCount = request.getParameter("SeatCount");
                String tCinemaID = request.getParameter("TCinemaID");

                if (theatherNumber != null && rowCount != null
                                && seatCount != null && tCinemaID != null
                                && !theatherNumber.trim().isEmpty()
                                && !rowCount.trim().isEmpty()
                                && !seatCount.trim().isEmpty()
                                && !tCinemaID.trim().isEmpty())
                {
                        Theather theather = new Theather();
                        theather.setTheatherNumber(Integer.parseInt(theatherNumber));
                        theather.setRowCount(Integer.parseInt(rowCount));
                        theather.setSeatCount(Integer.parseInt(seatCount));
                        Cinema cinema = cinemaBean.getCinemaByID(Integer.parseInt(tCinemaID));
                        theather.setCinema(cinema);

                        theatherBean.addTheather(theather);
                }
            }
        }	