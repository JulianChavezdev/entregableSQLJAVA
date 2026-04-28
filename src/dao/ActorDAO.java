package dao;

import model.Actor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void update(Actor a, int id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "UPDATE actors SET name=?, nationality=?, age=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, a.getName());
            pstmt.setString(2, a.getNationality());
            pstmt.setInt(3, a.getAge());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { System.err.println("Error updating actor: " + e.getMessage()); }
    }

    public void delete(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "DELETE FROM actors WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Actor deleted (id=" + id + ")");
        } catch (SQLException e) { System.err.println("Error deleting actor: " + e.getMessage()); }
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

    public void removeFromMovie(int actorId, int movieId) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "DELETE FROM cast WHERE actor_id = ? AND movie_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, actorId);
            pstmt.setInt(2, movieId);
            pstmt.executeUpdate();
            System.out.println("Actor (id=" + actorId + ") removed from movie (id=" + movieId + ")");
        } catch (SQLException e) { System.err.println("Error removing actor from movie: " + e.getMessage()); }
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

    public List<Actor> getActorsWithNoMovies() {
        List<Actor> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT * FROM actors WHERE id NOT IN (SELECT DISTINCT actor_id FROM cast)";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            System.out.println("--- Actors with no movies ---");
            while (rs.next()) {
                Actor a = map(rs);
                list.add(a);
                System.out.println(a);
            }
        } catch (SQLException e) { System.err.println("Error getting actors with no movies: " + e.getMessage()); }
        return list;
    }
}