import dao.MovieDAO;
import dao.ActorDAO;
import model.Actor;
import model.Movie;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MovieDAO movieDao = new MovieDAO();
        ActorDAO actorDao = new ActorDAO();

        System.out.println("=== MOVIE CRUD TESTS ===");
        Movie movie = new Movie("Inception", "Action", 148, 160000000.0);
        movieDao.insert(movie);

        movie.setTitle("Inception - Special Edition");
        movieDao.update(movie, 12);

        movieDao.delete(13);

        System.out.println("=== MOVIE QUERIES ===");
        movieDao.getWithActorCount();

        List<Movie> topMovies = movieDao.getTop3ByBudget();
        for (Movie m : topMovies) {
            System.out.println(m);
        }

        System.out.println("=== ACTOR TESTS ===");
        Actor actor = new Actor("Leonardo DiCaprio", "American", 49);
        actorDao.insert(actor);

        actorDao.assignToMovie(1, 1, "Cobb");

        actorDao.getNationalityCount();

        System.out.printf("Average age: %.2f\n", actorDao.getAverageAge());
    }
}