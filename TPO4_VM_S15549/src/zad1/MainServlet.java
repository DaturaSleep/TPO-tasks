package zad1;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String driver = "org.apache.derby.jdbc.ClientDriver";
	public String protocol = "jdbc:derby:";
	public Connection con;

	public void init() throws ServletException {
		try {
			DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
			String url = "jdbc:derby://localhost:1527/ksidb";
			con = DriverManager.getConnection(url);

		} catch (Exception exc) {
			throw new ServletException(exc.getStackTrace().toString(), exc);
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.setContentType("text/html;charset=ISO-8859-2");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Servlet Example</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h3>List Of Books</h3>");
		out.println("Choose your author:<br>");

		out.println("<P>");
		out.print("<form action=\"");
		out.print("MainServlet\" ");
		out.println("method=POST>");
		out.println("Author(s):");
		out.println("<select name='cmb1'>");
		out.println("<option selected disabled>Choose here</option>");
		ArrayList<String> authors = getAllAutors();
		//System.out.println(authors.size());

		for (String s : authors) {

			out.println("<option>" + s + "</option>");
		}

		out.println("</select>");
		out.println("<input type=submit>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
		String authorName = request.getParameter("cmb1");
		if (authorName != null) {
			ArrayList<String> books = new ArrayList<>();
			books = getDataAutor(authorName);
			out.println("<ul>");
			for (int i = 0; i < books.size()-1; i = i + 2) {
				out.println("<li>\"" + books.get(i) + "\" cost in PLN:" + books.get(i+1) + "</li>");
			}
				out.println("</ul>");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse res) throws IOException, ServletException {
		doGet(request, res);
	}

	public ArrayList<String> getAllAutors() throws ServletException {

		ArrayList<String> data = new ArrayList<>();
		try {

			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT(AUTOR.NAME) FROM autor");
			rs.afterLast();
			while (rs.previous()) {
				data.add(rs.getString(1));
			}

		} catch (Exception exc) {
			System.out.println(exc);

		}
		return data;
	}

	public ArrayList<String> getDataAutor(String autor) {
		ArrayList<String> data = new ArrayList<>();
		try {

			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(
					"SELECT AUTOR.NAME, POZYCJE.TYTUL, POZYCJE.CENA  FROM autor, pozycje where POZYCJE.AUTID = AUTOR.AUTID and AUTOR.NAME LIKE \'%"
							+ autor + "%\'");
			rs.afterLast();
			while (rs.previous()) {
				data.add(rs.getString(2));
				data.add(rs.getString(3));
			}

		} catch (Exception exc) {
			System.out.println(exc);

		}
		return data;

	}

}