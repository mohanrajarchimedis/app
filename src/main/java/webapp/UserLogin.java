package webapp;

import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/login")
public class UserLogin extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("WEB-INF/Views/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        int temp = 0;
        String name = request.getParameter("username");
        String pwd = request.getParameter("password");

        final String DB_URL = "jdbc:sqlserver://localhost\\MSSQLSERVER;Database=Ronald;portNumber=1433";
        final String USER = "root";
        final String PASS = "root";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            Statement stmt = conn.createStatement();
            String sql;
            sql = "select pass from users where username='" + name + "'";

            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {
                String password = rs.getString("pass");
                temp++;
                if (pwd.equals(password)) {
                    Date currentDate = new Date();
                    System.out.println("Successful Login by " + name + ", on " + currentDate);
                    request.setAttribute("name", name);
                    request.getRequestDispatcher("WEB-INF/Views/MainPage.jsp").forward(request, response);
                } else {
                    Date currentDate = new Date();
                    request.setAttribute("error", "Username available, Password Wrong");
                    request.getRequestDispatcher("WEB-INF/Views/login.jsp").forward(request, response);
                    System.out.println("Unsuccessful attempt by " + name + " on " + currentDate);
                }
            }

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
        }
        if (temp == 0) {
            request.setAttribute("error", "Username not available, Create a new user account");
            request.getRequestDispatcher("WEB-INF/Views/login.jsp").forward(request, response);
        }
    }

}
