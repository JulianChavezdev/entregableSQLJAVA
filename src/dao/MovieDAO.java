package dao;

import model.Actor;
import model.Movie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private String url = "jdbc:mysql://localhost:3307/entregable";
    private String user = "root";
    private String pass = "1234";

    public void insert(Movie m) {
        String sql = "INSERT INTO movies (title, genre, duration, budget) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, m.getTitle());
            pstmt.setString(2, m.getGenre());
            pstmt.setInt(3, m.getDuration());
            pstmt.setDouble(4, m.getBudget());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Movie m, int id) {
        String sql = "UPDATE movies SET title=?, genre=?, duration=?, budget=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, m.getTitle());
            pstmt.setString(2, m.getGenre());
            pstmt.setInt(3, m.getDuration());
            pstmt.setDouble(4, m.getBudget());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Movie> getTop3ByBudget() {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT * FROM movies ORDER BY budget DESC LIMIT 3";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapMovie(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Actor> getActorsByMovie(int movieId) {
        List<Actor> list = new ArrayList<>();
        String sql = "SELECT a.* FROM actors a JOIN cast c ON a.id = c.actor_id WHERE c.movie_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapActor(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Movie> getMoviesWithMoreThan3Actors() {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT m.* FROM movies m JOIN cast c ON m.id = c.movie_id GROUP BY m.id HAVING COUNT(c.actor_id) > 3";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapMovie(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Movie getLongestByGenre(String genre) {
        String sql = "SELECT * FROM movies WHERE genre = ? ORDER BY duration DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, genre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapMovie(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Movie mapMovie(ResultSet rs) throws SQLException {
        Movie m = new Movie();
        m.setId(rs.getInt("id"));
        m.setTitle(rs.getString("title"));
        m.setGenre(rs.getString("genre"));
        m.setDuration(rs.getInt("duration"));
        m.setBudget(rs.getDouble("budget"));
        return m;
    }

    private Actor mapActor(ResultSet rs) throws SQLException {
        Actor a = new Actor();
        a.setId(rs.getInt("id"));
        a.setName(rs.getString("name"));
        a.setNationality(rs.getString("nationality"));
        a.setAge(rs.getInt("age"));
        return a;
    }
}