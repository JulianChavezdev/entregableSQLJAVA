package dao;

import model.Actor;
import java.sql.*;

public class ActorDAO {
    private String url = "jdbc:mysql://localhost:3307/entregable";
    private String user = "root";
    private String pass = "1234";

    public void insert(Actor a) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "INSERT INTO actors (name, nationality, age) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, a.getName());
            pstmt.setString(2, a.getNationality());
            pstmt.setInt(3, a.getAge());
            pstmt.executeUpdate();
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void assignToMovie(int actorId, int movieId, String role) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "INSERT INTO cast (actor_id, movie_id, role) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, actorId);
            pstmt.setInt(2, movieId);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getNationalityCount() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT nationality, COUNT(*) as total FROM actors GROUP BY nationality";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                System.out.println("Nationality: " + rs.getString("nationality") + " | Total: " + rs.getInt("total"));
            }
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public double getAverageAge() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT AVG(age) FROM actors";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
        return 0;
    }

    private Actor map(ResultSet rs) throws SQLException {
        Actor a = new Actor();
        a.setId(rs.getInt("id"));
        a.setName(rs.getString("name"));
        a.setNationality(rs.getString("nationality"));
        a.setAge(rs.getInt("age"));
        return a;
    }
}