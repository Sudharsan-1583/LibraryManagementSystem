import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();

        // Database connection details
        String jdbcUrl = "jdbc:mysql://localhost:3306/librarymanagementsystem";
        String dbUser = "root";
        String dbPassword = "root";

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            // Prepare the SQL query
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                // Authentication successful

                RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
                rd.forward(request, response);
            } else {
                // Authentication failed
                // response.sendRedirect("index.html");
                 out.print("<h1>No user found</h1>");
            }

            // Close database resources
            statement.close();
            connection.close();

        } catch (SQLException e) {
            out.print(e.getLocalizedMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            out.print(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
