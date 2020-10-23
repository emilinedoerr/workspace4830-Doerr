
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

@WebServlet("/InsertSleepLog")
public class InsertSleepLog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InsertSleepLog() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String date = request.getParameter("date");
		String inBed = request.getParameter("inBed");
		String asleep = request.getParameter("asleep");
		String awake = request.getParameter("awake");
		String outOfBed = request.getParameter("outOfBed");


		Connection connection = null;
		String insertSql = " INSERT INTO SleepLog (id, DATE, IN_BED, ASLEEP, AWAKE, OUT_OF_BED) values (default, ?, ?, ?, ?, ?)";

		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, date);
			preparedStmt.setString(2, inBed);
			preparedStmt.setString(3, asleep);
			preparedStmt.setString(4, awake);
			preparedStmt.setString(5, outOfBed);
			preparedStmt.execute();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Insert Data into Sleep Log";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		out.println(docType + //
				"<html>\n" + //
				"<head><title>" + title + "</title></head>\n" + //
				"<body bgcolor=\"#f0f0f0\">\n" + //
				"<h2 align=\"center\">" + title + "</h2>\n" + //
				"<ul>\n" + //

				"  <li><b>Date</b>: " + date + "\n" + //
				"  <li><b>Time in Bed</b>: " + inBed + "\n" + //
				"  <li><b>Time Asleep</b>: " + asleep + "\n" + //
				"  <li><b>Time Awake</b>: " + awake + "\n" + //
				"  <li><b>Time Out of Bed</b>: " + outOfBed + "\n" + //

				"</ul>\n");

		out.println("<a href=/Tech_Exercise/searchSleepLog.html>Search Sleep Logs</a> <br>");
		out.println("</body></html>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
