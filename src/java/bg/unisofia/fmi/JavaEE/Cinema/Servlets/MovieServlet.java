package bg.unisofia.fmi.JavaEE.Cinema.Servlets;

import bg.unisofia.fmi.JavaEE.Cinema.Beans.MovieBean;
import bg.unisofia.fmi.JavaEE.Cinema.Classes.Movie;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	MovieBean movieBean;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
                
		response.getWriter().println("<h1>Cinema Servlet</h1>");
		try {
			appendMovieTable(response);
			appendMovieAddForm(response);
                        appendMovieRemoveForm(response);
		} catch (Exception e) {
			response.getWriter().println(
					"Persistence operation failed in GET with reason: "
							+ e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			doMovieAdd(request);
                        doMovieRemove(request);
			doGet(request, response);
		} catch (Exception e) {
			response.getWriter().println(
					"Persistence operation failed with reason: "
							+ e.getMessage());
		}
	}

	private void appendMovieTable(HttpServletResponse response)
			throws SQLException, IOException {
		List<Movie> resultList = movieBean.getAllMovies();
		response.getWriter().println(
				"<p><table border=\"1\"><tr><th colspan=\"8\">"
						+ (resultList.isEmpty() ? "0 " : resultList.size()
								+ " ")
								+ "Movies in the Database</th></tr>");
		if (resultList.isEmpty()) {
			response.getWriter().println(
					"<tr><td colspan=\"8\">Database is empty</td></tr>");
		} else {
			response.getWriter()
			.println(
					"<tr><th>Movie ID</th><th>Movie Name</th><th>Year</th><th>Movie length</th><th>Age group</th><th>Poster</th><th>Plot</th><th>Screenings</th></tr>");
		}
		for (Movie m : resultList) {
			response.getWriter().println(
					"<tr><td>"
					+ m.getMovieID()
					+ "</td><td>"
					+ m.getMovieName()
					+ "</td><td>"
					+ m.getMovieYear()
					+ "</td><td>"
					+ m.getMovieLength()
					+ "</td><td>"
					+ m.getAgeGroup()
                                        + "</td><td>"
                                        + m.getMoviePoster()
                                        + "</td><td>"
                                        + m.getMoviePlot()
                                        + "</td><td>"
                                        + m.getMovieScreenings().size()
					+ "</td></tr>");
		}
		response.getWriter().println("</table></p>");
	}

	private void appendMovieAddForm(HttpServletResponse response) throws IOException {
		response.getWriter()
				.println(
                                            "<p><form action=\"\" method=\"post\">"
                                                            + "<br>Movie name:&nbsp;<input type=\"text\" name=\"MovieName\">"
                                                            + "<br>Year:&nbsp;<input type=\"text\" name=\"MovieYear\">"
                                                            + "<br>Length:&nbsp;<input type=\"text\" name=\"MovieLength\">"
                                                            + "<br>Age group:&nbsp;<input type=\"text\" name=\"AgeGroup\">"
                                                            + "<br>Poster:&nbsp;<input type=\"text\" name=\"MoviePoster\">"
                                                            + "<br>Plot:&nbsp;<input type=\"text\" name=\"MoviePlot\">"
                                                            + "<br>&nbsp;<input type=\"submit\" value=\"Add Movie\">"
                                                            + "</form></p>");
	}

	private void doMovieAdd(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
                String movieName = request.getParameter("MovieName");
                String movieYear = request.getParameter("MovieYear");
                String movieLength = request.getParameter("MovieLength");
                String ageGroup = request.getParameter("AgeGroup");
                String moviePoster = request.getParameter("MoviePoster");
                String moviePlot = request.getParameter("MoviePlot");

		if (movieName != null && movieYear != null
				&& movieLength != null && ageGroup != null
                                && moviePoster != null && moviePlot != null
				&& !movieName.trim().isEmpty() && !movieYear.trim().isEmpty()
				&& !movieLength.trim().isEmpty() && !ageGroup.trim().isEmpty()
                                && !moviePoster.trim().isEmpty() && !moviePlot.trim().isEmpty()) {
			Movie movie = new Movie();
                        movie.setMovieName(movieName);
                        movie.setMovieYear(Integer.parseInt(movieYear));
                        movie.setMovieLength(Integer.parseInt(movieLength));
                        movie.setAgeGroup(Integer.parseInt(ageGroup));
                        movie.setMoviePoster(moviePoster);
                        movie.setMoviePlot(moviePlot);

			movieBean.addMovie(movie);
		}
	}
        
        private void appendMovieRemoveForm(HttpServletResponse response) throws IOException {
        // Append form through which new cinemas can be added
        response.getWriter()
                        .println(
                                    "<p><form action=\"\" method=\"post\">"
                                                    + "<br>Movie ID:&nbsp;<input type=\"text\" name=\"RemoveMovieID\">"
                                                    + "<br>&nbsp;<input type=\"submit\" value=\"Remove Movie\">"
                                                    + "</form></p>");
	}
	private void doMovieRemove(HttpServletRequest request) throws ServletException,
			IOException, SQLException {
		String movieRemovalID = request.getParameter("RemoveMovieID");

		if (movieRemovalID != null
				&& !movieRemovalID.trim().isEmpty()) {
                      List<Movie> allMovies = movieBean.getAllMovies();
                      for (Movie m : allMovies)
                      {
                          if (m.getMovieID() == Long.parseLong(movieRemovalID))
                            movieBean.removeMovieByID(Long.parseLong(movieRemovalID));
                      }
		}
	}

}