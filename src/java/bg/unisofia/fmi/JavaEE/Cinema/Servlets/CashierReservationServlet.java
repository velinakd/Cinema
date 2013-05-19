package bg.unisofia.fmi.JavaEE.Cinema.Servlets;

import bg.unisofia.fmi.JavaEE.Cinema.Beans.ScreeningSeatBean;
import bg.unisofia.fmi.JavaEE.Cinema.Beans.ScreeningBean;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Screening;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.ScreeningSeat;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CashierReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ScreeningBean screeningBean;
        @EJB
        ScreeningSeatBean screeningSeatBean;

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
                
		response.getWriter().println("<h1>Screening Seat Servlet</h1>");
		try {
			appendScreeningSeatTable(response);
                        appendReservationForm(response);
                        appendReservationRemoveForm(response);
                        appendSellingForm(response);
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
			doReservationAdd(request);
                        doReservationRemove(request);
                        doSale(request);
			doGet(request, response);
		} catch (Exception e) {
			response.getWriter().println(
					"Persistence operation failed with reason: "
							+ e.getMessage());
		}
	}

	private void appendScreeningSeatTable(HttpServletResponse response)
			throws SQLException, IOException {
		List<ScreeningSeat> resultList = screeningSeatBean.getAllScreeningSeats();
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
					"<tr><th>Screening Seat ID</th><th>Status</th><th>Seat Relative number</th><th>Seat row</th><th>Seat number</th><th>Screening ID</th><th>Theather ID</th></tr>");
		}
		for (ScreeningSeat s : resultList) {
			response.getWriter().println(
					"<tr><td>"
					+ s.getScreeningSeatID()
					+ "</td><td>"
					+ s.getScreeningSeatStatus()
                                        + "</td><td>"
                                        + s.getScreeningSeatRelativeNumber()
                                        + "</td><td>"
                                        + s.getScreeningSeatRow()
                                        + "</td><td>"
                                        + s.getScreeningSeatNumber()
					+ "</td><td>"
					+ s.getScreening().getScreeningID()
					+ "</td><td>"
					+ s.getScreening().getScreeningTheather().getTheatherID()
					+ "</td></tr>");
		}
		response.getWriter().println("</table></p>");
	}
        
	private void appendReservationForm(HttpServletResponse response) throws IOException {
		response.getWriter()
				.println(
                                            "<p><form action=\"\" method=\"post\">"
                                                            + "<br>Seat ID:&nbsp;<input type=\"text\" name=\"SeatID\">"
                                                            + "<br>&nbsp;<input type=\"submit\" value=\"Reserve Seat\">"
                                                            + "</form></p>");
	}

	private void doReservationAdd(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
                String screeningID = request.getParameter("SeatID");
                if (screeningID != null && !screeningID.trim().isEmpty()) {
                   screeningSeatBean.reserveScreeningSeatByID(Long.parseLong(screeningID));
                }
	}
        
        private void appendReservationRemoveForm(HttpServletResponse response) throws IOException {
        response.getWriter()
                        .println(
                                    "<p><form action=\"\" method=\"post\">"
                                                    + "<br>Seat ID:&nbsp;<input type=\"text\" name=\"RemoveReservationID\">"
                                                    + "<br>&nbsp;<input type=\"submit\" value=\"Remove Reservation\">"
                                                    + "</form></p>");
	}
	private void doReservationRemove(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		String reservationRemovalID = request.getParameter("RemoveReservationID");
		if (reservationRemovalID != null
				&& !reservationRemovalID.trim().isEmpty()) {
                    screeningSeatBean.removeReservationByID(Long.parseLong(reservationRemovalID));
		}
	} 
        
        private void appendSellingForm(HttpServletResponse response) throws IOException {
        response.getWriter()
                        .println(
                                    "<p><form action=\"\" method=\"post\">"
                                                    + "<br>Seat ID:&nbsp;<input type=\"text\" name=\"SaleSeatID\">"
                                                    + "<br>&nbsp;<input type=\"submit\" value=\"Sell seat\">"
                                                    + "</form></p>");
	}
	private void doSale(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		String saleSeatID = request.getParameter("SaleSeatID");
		if (saleSeatID != null
				&& !saleSeatID.trim().isEmpty()) {
                    screeningSeatBean.sellSeatByID(Long.parseLong(saleSeatID));
		}
	} 
}