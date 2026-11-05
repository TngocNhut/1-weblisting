package murach.email;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import murach.business.User;
import murach.data.UserDB;

public class EmailListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (!"add".equals(request.getParameter("action"))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String email = clean(request.getParameter("email"));
        String firstName = clean(request.getParameter("firstName"));
        String lastName = clean(request.getParameter("lastName"));
        if (email.isEmpty() || email.length() > 255 || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
                || firstName.isEmpty() || firstName.length() > 100 || lastName.isEmpty() || lastName.length() > 100) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please enter a valid email and name.");
            return;
        }
        User user = new User(firstName, lastName, email);
        try {
            UserDB.insert(user);
        } catch (SQLException e) {
            getServletContext().log("Could not save email registration", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not save registration. Check database configuration.");
            return;
        }
        // Escape values for HTML; the original user has already been saved to PostgreSQL.
        User displayUser = new User(escape(firstName), escape(lastName), escape(email));
        request.setAttribute("user", displayUser);
        request.getRequestDispatcher("/thanks.jsp").forward(request, response);
    }
    private static String clean(String value) { return value == null ? "" : value.trim(); }
    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
