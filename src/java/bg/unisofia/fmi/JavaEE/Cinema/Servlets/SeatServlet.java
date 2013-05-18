package bg.unisofia.fmi.JavaEE.Cinema.Servlets;

import bg.unisofia.fmi.JavaEE.Cinema.Beans.SeatBean;
import bg.unisofia.fmi.JavaEE.Cinema.Beans.TheatherBean;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Seat;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Theather;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	TheatherBean theatherBean;
        @EJB
        SeatBean seatBean;

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
                
		response.getWriter().println("<h1>Seat Servlet</h1>");
		try {
			appendSeatTable(response);
			appendSeatGenerationForm(response);
                        appendSeatRemoveForm(response);
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
			doSeatAdd(request);
                        doSeatInvalidation(request);
			doGet(request, response);
		} catch (Exception e) {
			response.getWriter().println(
					"Persistence operation failed with reason: "
							+ e.getMessage());
		}
	}

	private void appendSeatTable(HttpServletResponse response)
			throws SQLException, IOException {
		// Append table that lists all cinemas
		List<Seat> resultList = seatBean.getAllSeats();
		response.getWriter().println(
				"<p><table border=\"1\"><tr><th colspan=\"7\">"
						+ (resultList.isEmpty() ? "0 " : resultList.size()
								+ " ")
								+ "Seats in the Database</th></tr>");
		if (resultList.isEmpty()) {
			response.getWriter().println(
					"<tr><td colspan=\"7\">Database is empty</td></tr>");
		} else {
			response.getWriter()
			.println(
					"<tr><th>Seat ID</th><th>Seat row</th><th>Seat Number</th><th>Seat Status</th><th>Seat Relative number</th><th>Theather name</th><th>Cinema Name</th></tr>");
		}
		for (Seat s : resultList) {
			response.getWriter().println(
					"<tr><td>"
					+ s.getSeatID()
					+ "</td><td>"
					+ s.getSeatRow()
					+ "</td><td>"
					+ s.getSeatNumber()
					+ "</td><td>"
					+ s.getSeatStatus()
					+ "</td><td>"
                                        + s.getSeatRelativeNumber()
                                        + "</td><td>"
					+ s.getTheather().getTheatherNumber()
                                        + "</td><td>"
                                        + s.getTheather().getCinema().getCinemaName()
					+ "</td></tr>");
		}
		response.getWriter().println("</table></p>");
	}

	private void appendSeatGenerationForm(HttpServletResponse response) throws IOException {
		// Append form through which new cinemas can be added
		response.getWriter()
				.println(
                                            "<p><form action=\"\" method=\"post\">"
                                                            + "<br>Theather ID:&nbsp;<input type=\"text\" name=\"TheatherID\">"
                                                            + "<br>&nbsp;<input type=\"submit\" value=\"Add Seats\">"
                                                            + "</form></p>");
	}

	private void doSeatAdd(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		// Extract name of cinema to be added from request
                String theatherIDString = request.getParameter("TheatherID");
                if (theatherIDString != null
				&& !theatherIDString.trim().isEmpty()) {       
                Theather theather = theatherBean.getTheatherByID(Long.parseLong(theatherIDString));
                if (!theather.getSeatList().isEmpty()) { }
                else {
                    for (int i=1; i<= theather.getRowCount(); i++)
                        for (int j=1; j<= theather.getSeatCount(); j++)
                        {
                            Seat seat = new Seat();
                            seat.setTheather(theather);
                            seat.setSeatStatus("Available");
                            seat.setSeatRow(i);
                            seat.setSeatNumber(j);
                            seat.setSeatRelativeNumber((i-1)*(int)theather.getSeatCount()+j);
                            seatBean.addSeat(seat);
                        }
                }
            }
	}
        
        private void appendSeatRemoveForm(HttpServletResponse response) throws IOException {
        response.getWriter()
                        .println(
                                    "<p><form action=\"\" method=\"post\">"
                                                    + "<br>Seat ID:&nbsp;<input type=\"text\" name=\"RmSID\">"
                                                    + "<br>&nbsp;<input type=\"submit\" value=\"Invalidate Seat\">"
                                                    + "</form></p>");
	}
	private void doSeatInvalidation(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		String seatRemovalID = request.getParameter("RmSID");
		if (seatRemovalID != null
				&& !seatRemovalID.trim().isEmpty()) {
                      seatBean.invalidateSeat(seatBean.getSeatByID(Long.parseLong(seatRemovalID)));
		}
	}

}