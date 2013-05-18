/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class ScreeningSeatServlet extends HttpServlet {
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
                        appendScreeningSeatAddForm(response);
                        appendReservationForm(response);
                        appendReservationRemoveForm(response);
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
                        doScreeningSeatAdd(request);
			doReservationAdd(request);
                        doReservationRemove(request);
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
								+ "Reservations in the Database</th></tr>");
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
        
        private void appendScreeningSeatAddForm(HttpServletResponse response) throws IOException {
		response.getWriter()
				.println(
                                            "<p><form action=\"\" method=\"post\">"
                                                            + "<br>Screening ID:&nbsp;<input type=\"text\" name=\"ScreeningIDAdd\">"
                                                            + "<br>&nbsp;<input type=\"submit\" value=\"Add screening seats\">"
                                                            + "</form></p>");
	}
        
        private void doScreeningSeatAdd(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		String screeningIDAdd = request.getParameter("ScreeningIDAdd");
		if (screeningIDAdd != null
				&& !screeningIDAdd.trim().isEmpty()) {
                Screening screening = screeningBean.getScreeningByID(Long.parseLong(screeningIDAdd));
                if (!screening.getScreeningSeatList().isEmpty()) { }
                else
                {
                    for (int i=1; i <= screening.getScreeningTheather().getRowCount(); i++)
                        for (int j=1; j <= screening.getScreeningTheather().getSeatCount(); j++)
                        {
                            ScreeningSeat screeningSeat = new ScreeningSeat();
                            screeningSeat.setScreening(screening);
                            screeningSeat.setScreeningSeatRow(i);
                            screeningSeat.setScreeningSeatNumber(j);
                            screeningSeat.setScreeningSeatRelativeNumber(
                                    (i-1)*(int)screening.getScreeningTheather().getSeatCount()+j);
                            screeningSeat.setScreeningSeatStatus(screening.getScreeningTheather()
                                    .getSeatList().get(screeningSeat.getScreeningSeatRelativeNumber()-1)
                                    .getSeatStatus());
                            screeningSeatBean.addSeat(screeningSeat);
                        }                    
                }
            } 
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
}