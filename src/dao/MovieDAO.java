package dao;

import model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private String url = "jdbc:mysql://localhost:3307/entregable";
    private String user = "root";
    private String pass = "1234";

    public void insert(Movie m) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "INSERT INTO movies (title, genre, duration, budget) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, m.getTitle());
            pstmt.setString(2, m.getGenre());
            pstmt.setInt(3, m.getDuration());
            pstmt.setDouble(4, m.getBudget());
            pstmt.executeUpdate();
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void update(Movie m, int id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "UPDATE movies SET title=?, genre=?, duration=?, budget=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, m.getTitle());
            pstmt.setString(2, m.getGenre());
            pstmt.setInt(3, m.getDuration());
            pstmt.setDouble(4, m.getBudget());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void delete(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "DELETE FROM movies WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void getWithActorCount() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT m.title, COUNT(c.actor_id) as total FROM movies m LEFT JOIN cast c ON m.id = c.movie_id GROUP BY m.id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                System.out.println("Movie: " + rs.getString("title") + " | Actors: " + rs.getInt("total"));
            }
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }

    public List<Movie> getTop3ByBudget() {
        List<Movie> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT * FROM movies ORDER BY budget DESC LIMIT 3";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) { list.add(map(rs)); }
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
        return list;
    }

    private Movie map(ResultSet rs) throws SQLException {
        Movie m = new Movie();
        m.setId(rs.getInt("id"));
        m.setTitle(rs.getString("title"));
        m.setGenre(rs.getString("genre"));
        m.setDuration(rs.getInt("duration"));
        m.setBudget(rs.getDouble("budget"));
        return m;
    }
}