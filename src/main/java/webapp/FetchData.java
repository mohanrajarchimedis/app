package webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/fetch")
public class FetchData extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        // JDBC driver name and database URL
        final String DB_URL = "jdbc:sqlserver://localhost\\MSSQLSERVER;Database=Ronald;portNumber=1433";
        final String USER = "root";
        final String PASS = "root";
        try {
            // Register JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            Statement stmt = conn.createStatement();
            String sql;
            sql = "select * from employee";

            ResultSet rs = stmt.executeQuery(sql);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Fetch Data Results</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table border=1 width=100% height=100%>");
            out.println("<tr><th>First Name</th><th>Last Name</th><th>Designation</th><th>Company</th><th>Project</th><th>Home Town</th><tr>");
            // Extract data from result set
            while (rs.next()) {
                String name1 = rs.getString("FirstName");
                String locality = rs.getString("Address");
                String name2 = rs.getString("LastName");
                String project = rs.getString("project");
                String company = rs.getString("company");
                String designation = rs.getString("designation");
                out.println("<tr><td>" + name1 + "</td><td>" + name2 + "</td><td>"
                        + designation + "</td><td>" + company + "</td><td>"
                        + project + "</td><td>" + locality + "</td></tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            System.out.println("InnoDB Successful");
        }
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");

        // JDBC driver name and database URL
        final String DB_URL = "jdbc:sqlserver://localhost\\MSSQLSERVER;Database=Ronald;portNumber=1433";
        final String USER = "root";
        final String PASS = "root";

        try {
            // Register JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            Statement stmt = conn.createStatement();
            String sql;
            sql = "select * from employee where FirstName like '%" + username + "%'";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Fetch Data Result</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table border=1 width=100% height=100%>");
            out.println("<tr><th>First Name</th><th>Last Name</th><th>Designation</th><th>Company</th><th>Project</th><th>Home Town</th><tr>");
            // Extract data from result set
            while (rs.next()) {
                String name1 = rs.getString("FirstName");
                String locality = rs.getString("Address");
                String name2 = rs.getString("LastName");
                String project = rs.getString("project");
                String company = rs.getString("company");
                String designation = rs.getString("designation");
                out.println("<tr><td>" + name1 + "</td><td>" + name2 + "</td><td>"
                        + designation + "</td><td>" + company + "</td><td>"
                        + project + "</td><td>" + locality + "</td></tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            System.out.println("InnoDB Successful");
        }

    }
}
