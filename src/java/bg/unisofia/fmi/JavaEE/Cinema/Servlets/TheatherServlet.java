/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.unisofia.fmi.JavaEE.Cinema.Servlets;

import bg.unisofia.fmi.JavaEE.Cinema.Beans.CinemaBean;
import bg.unisofia.fmi.JavaEE.Cinema.Beans.SeatBean;
import bg.unisofia.fmi.JavaEE.Cinema.Beans.TheatherBean;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Cinema;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Seat;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Theather;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ss
 */
public class TheatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

        @EJB
	TheatherBean theatherBean;
        @EJB
        CinemaBean cinemaBean;

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
                
		response.getWriter().println("<h1>Theather Servlet</h1>");
		try {
			appendTheatherTable(response);
			appendTheatherAddForm(response);
                        appendTheatherRemoveForm(response);
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
			doTheatherAdd(request);
                        doTheatherRemove(request);
			doGet(request, response);
		} catch (Exception e) {
			response.getWriter().println(
					"Persistence operation failed with reason: "
							+ e.getMessage());
		}
	}	

	private void appendTheatherTable(HttpServletResponse response)
			throws SQLException, IOException {
		// Append table that lists all theathers
		List<Theather> resultList = theatherBean.getAllTheathers();
		response.getWriter().println(
				"<p><table border=\"1\"><tr><th colspan=\"6\">"
						+ (resultList.isEmpty() ? "0 " : resultList.size()
								+ " ")
								+ "Theathers in the Database</th></tr>");
		if (resultList.isEmpty()) {
			response.getWriter().println(
					"<tr><td colspan=\"6\">Database is empty</td></tr>");
		} else {
			response.getWriter()
			.println(
					"<tr><th>Theather ID</th><th>Theather Number</th><th>Rows</th><th>Seats on a row</th><th>Cinema name</th><th>Total Seats</th></tr>");
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
                                        + "</td><td>"
                                        + t.getSeatList().size()
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
                                                                + "<br>Rows:&nbsp;<input type=\"text\" name=\"RowCount\">"
                                                                + "<br>Seats on a row:&nbsp;<input type=\"text\" name=\"SeatCount\">"
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
                        List<Cinema> allCinemas = cinemaBean.getAllCinemas();
                        for (Cinema c : allCinemas)
                            if (c.getCinemaID() == Long.parseLong(tCinemaID))
                            {
                                theather.setCinema(c);
                                theatherBean.addTheather(theather);
                                // Извикай SeatServlet тук за тази зала, за да генерираш седалките.
                                // Може залата да се подаде като параметър в URL-а.
                            }
                }
        }
        
        private void appendTheatherRemoveForm(HttpServletResponse response) throws IOException {
        // Append form through which new cinemas can be added
        response.getWriter()
                        .println(
                                    "<p><form action=\"\" method=\"post\">"
                                                    + "<br>Theather ID:&nbsp;<input type=\"text\" name=\"RemoveTheatherID\">"
                                                    + "<br>&nbsp;<input type=\"submit\" value=\"Remove Theather\">"
                                                    + "</form></p>");
	}
	private void doTheatherRemove(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		// Extract name of cinema to be added from request
		String theatherRemovalID = request.getParameter("RemoveTheatherID");

		if (theatherRemovalID != null
				&& !theatherRemovalID.trim().isEmpty()) {
                      List<Theather> allTheathers = theatherBean.getAllTheathers();
                      for (Theather t : allTheathers)
                      {
                          if (t.getTheatherID() == Long.parseLong(theatherRemovalID))
                            theatherBean.removeTheatherByID(Long.parseLong(theatherRemovalID));
                      }
		}
	}
}	