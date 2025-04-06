// employee.html
<!DOCTYPE html>
<html>
<head>
    <title>Search Employee</title>
</head>
<body>
    <form action="EmployeeServlet" method="get">
        Enter Employee ID: <input type="text" name="empId">
        <input type="submit" value="Search">
    </form>
</body>
</html>



// EmployeeServlet.java
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String empId = request.getParameter("empId");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/company", "root", "password");

            String query = empId != null && !empId.isEmpty()
                ? "SELECT * FROM employees WHERE id = ?" 
                : "SELECT * FROM employees";

            PreparedStatement ps = conn.prepareStatement(query);

            if(empId != null && !empId.isEmpty()) {
                ps.setInt(1, Integer.parseInt(empId));
            }

            ResultSet rs = ps.executeQuery();

            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Position</th></tr>");
            while(rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("position") + "</td></tr>");
            }
            out.println("</table>");
            conn.close();
        } catch(Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
