// import java.io.IOException;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import javax.servlet.RequestDispatcher;
// import java.io.PrintWriter;

// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// @WebServlet("/LoginServlet")
// public class LoginServlet extends HttpServlet {
//     private static final long serialVersionUID = 1L;

//     protected void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         String username = request.getParameter("username");
//         String password = request.getParameter("password");
//         PrintWriter out = response.getWriter();

//         // Database connection details
//         String jdbcUrl = "jdbc:mysql://localhost:3306/librarymanagementsystem";
//         String dbUser = "root";
//         String dbPassword = "root";

//         try {
//             // Load the MySQL JDBC driver
//             Class.forName("com.mysql.cj.jdbc.Driver");

//             // Connect to the database
//             Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

//             // Prepare the SQL query
//             String sql = "SELECT * FROM users WHERE username=? AND password=?";
//             PreparedStatement statement = connection.prepareStatement(sql);
//             statement.setString(1, username);
//             statement.setString(2, password);

//             // Execute the query
//             ResultSet result = statement.executeQuery();

//             if (result.next()) {
//                 // Authentication successful

//                 RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
//                 rd.forward(request, response);
//             } else {
//                 // Authentication failed
//                RequestDispatcher rd = request.getRequestDispatcher("index.html");
// rd.forward(request, response);

//                // out.print("<h1>No user found</h1>");
//             }

//             // Close database resources
//             statement.close();
//             connection.close();

//         } catch (SQLException e) {
//             out.print(e.getLocalizedMessage());
//             e.printStackTrace();
//         } catch (ClassNotFoundException e) {
//             out.print(e.getLocalizedMessage());
//             e.printStackTrace();
//         }
//     }
// }

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

            // Prepare the SQL query for users
            String userSql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement userStatement = connection.prepareStatement(userSql);
            userStatement.setString(1, username);
            userStatement.setString(2, password);

            // Prepare the SQL query for books
            String bookSql = "SELECT * FROM book";
            PreparedStatement bookStatement = connection.prepareStatement(bookSql);

            // Execute the user query
            ResultSet userResult = userStatement.executeQuery();

            if (userResult.next()) {
                // Authentication successful
                out.println("<html>");
                out.println("<head><title>Welcome Page</title></head>");
                out.println("<body>");
                out.println("<h1>Welcome to the Library Management System</h1>");

                out.println("</table>");

                // Execute the book query
                ResultSet bookResult = bookStatement.executeQuery();

                out.println("<h2>Books</h2>");
                out.println("<table border='1'>");
                out.println("<tr>");
                out.println("<th>Title</th>");
                out.println("<th>Author</th>");
                out.println("<th>Publisher</th>");
                out.println("<th>Edition</th>");
                out.println("<th>Price</th>");

                out.println("</tr>");

                while (bookResult.next()) {
                    String title = bookResult.getString("title");
                    String author = bookResult.getString("author");
                    String publisher = bookResult.getString("publisher");
                    int edition = bookResult.getInt("edition");
                    double price = bookResult.getDouble("price");

                    out.println("<tr>");
                    out.println("<td>" + title + "</td>");
                    out.println("<td>" + author + "</td>");
                    out.println("<td>" + publisher + "</td>");
                    out.println("<td>" + edition + "</td>");
                    out.println("<td>" + price + "</td>");
                    out.println("</tr>");
                }
                // out.println("<td colspan=\"5\"><input type=\"submit\" style=\"align-items:
                // center;></td>");

                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            } else {
                // Authentication failed
                // response.sendRedirect("index.html");
                out.println("<html>");
                out.println("<body>");
                out.println("<h2>Wrong Username or Password</h2>");
                out.println("</body>");
                out.println("</html>");

            }

            // Close database resources
            userStatement.close();
            bookStatement.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            out.print(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
