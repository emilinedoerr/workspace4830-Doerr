

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * I plan to develop a Web application SleepLog that stores data about sleeping habits.
 * SleepLog will store the attributes:
 * Date
 * Time in Bed
 * Time Asleep
 * Time Awake
 * Time Out of Bed
 * 
 * Table name: SleepLog
 * DB Name: MyDBTechExercise
 */

@WebServlet("/SearchSleepLog")
public class SearchSleepLog extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SearchSleepLog() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      String keyword = request.getParameter("keyword");
	      search(keyword, response);
	   }

	   void search(String keyword, HttpServletResponse response) throws IOException {
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      String title = "Sleep Log Results";
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	            "transitional//en\">\n"; //
	      out.println(docType + //
	            "<html>\n" + //
	            "<head><title>" + title + "</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n" + //
	            "<h1 align=\"center\">" + title + "</h1>\n");

	      Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      try {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;

	         if (keyword.isEmpty()) {
	            String selectSQL = "SELECT * FROM SleepLog";
	            preparedStatement = connection.prepareStatement(selectSQL);
	         } else {
	            String selectSQL = "SELECT * FROM SleepLog WHERE DATE LIKE ?";
	            String theDate = keyword + "%";
	            preparedStatement = connection.prepareStatement(selectSQL);
	            preparedStatement.setString(1, theDate);
	         }
	         ResultSet rs = preparedStatement.executeQuery();

	         while (rs.next()) {
	            String date = rs.getString("DATE").trim();
	            String inBed = rs.getString("IN_BED").trim();
	            String asleep = rs.getString("ASLEEP").trim();
	            String awake = rs.getString("AWAKE").trim();
	            String outOfBed = rs.getString("OUT_OF_BED").trim();

	            if (keyword.isEmpty() || date.contains(keyword)) {
	               out.println("Date: " + date + "<br>");
	               out.println("Time in Bed: " + inBed + "<br>");
	               out.println("Time Asleep: " + asleep + "<br>");
	               out.println("Time Awake: " + awake + "<br>");
	               out.println("Time Out of Bed: " + outOfBed + "<br>");
	            }
	         }
	         out.println("<a href=/Tech_Exercise/insertSleepLog.html>Insert Sleep Log Data</a> <br>");
	         out.println("</body></html>");
	         rs.close();
	         preparedStatement.close();
	         connection.close();
	      } catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (preparedStatement != null)
	               preparedStatement.close();
	         } catch (SQLException se2) {
	         }
	         try {
	            if (connection != null)
	               connection.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
	   }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
