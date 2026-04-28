import dao.ActorDAO;
import dao.MovieDAO;
import model.Actor;
import model.Movie;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MovieDAO movieDAO = new MovieDAO();
        ActorDAO actorDAO = new ActorDAO();

        Movie movie = new Movie("Interstellar", "Sci-Fi", 169, 165000000.0);
        Actor actor = new Actor("Matthew McConaughey", "USA", 54);

        try {
            movieDAO.insert(movie);
            actorDAO.insert(actor);

            int targetMovieId = 1;
            int targetActorId = 1;

            movie.setBudget(170000000.0);
            movieDAO.update(movie, targetMovieId);

            actor.setAge(55);
            actorDAO.update(actor, targetActorId);

            actorDAO.assignToMovie(targetActorId, targetMovieId, "Cooper");
            actorDAO.removeFromMovie(targetActorId, targetMovieId);

            List<Movie> topMovies = movieDAO.getTop3ByBudget();
            List<Movie> crowdedMovies = movieDAO.getMoviesWithMoreThan3Actors();
            Movie longestSciFi = movieDAO.getLongestByGenre("Sci-Fi");
            
            double avgAge = actorDAO.getAverageAge();
            List<Actor> lonelyActors = actorDAO.getActorsWithNoMovies();

            movieDAO.delete(targetMovieId);
            actorDAO.delete(targetActorId);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}