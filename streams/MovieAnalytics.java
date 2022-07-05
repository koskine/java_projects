import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.*;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.FileReader;


/* 
    Author: Iiro Koskinen H299947
    A class that analyses Movie objects
*/

public class MovieAnalytics {
    private ArrayList<Movie> movies;


    public MovieAnalytics() {
        movies = new ArrayList<Movie>();
    }

    // prints out a movies info
    public static Consumer<Movie> showInfo() {
        Consumer<Movie> result = new Consumer<Movie>() {
            public void accept(Movie m) {
                System.out.format("%s (By %s, %d)%n", m.getTitle(), m.getDirector(), m.getReleaseYear());
            }
        };
        return result;
    }

    // adds movies from the file to the database
    public void populateWithData(String fileName) throws IOException {
        try(var file = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = file.readLine()) != null){
                String[] parts = line.split(";");
                int year = Integer.parseInt(parts[1]);
                int duration = Integer.parseInt(parts[2]);
                double score = Double.parseDouble(parts[4]);
                Movie newMovie = new Movie(parts[0], year, duration, parts[3], score, parts[5]);
                movies.add(newMovie);
            }
        }
    }



    // returns a stream of movies released after year
    public Stream<Movie> moviesAfter(int year) {
        Movie[] moviearray = new Movie[movies.size()];
        movies.toArray(moviearray);
        Stream<Movie> result = Arrays.stream(moviearray).filter(new Predicate<Movie>() {
            @Override
            public boolean test(Movie m) {
                return m.getReleaseYear() >= year;
            }
        }).sorted(new Comparator<Movie>() {
            public int compare(Movie a, Movie b) {
                int cmp = Integer.compare(a.getReleaseYear(), b.getReleaseYear());
                if (cmp == 0) {
                    cmp = a.getTitle().compareTo(b.getTitle());
                }
                return cmp;
            }
        });

        return result;
    }

    // returns a stream of movies released before year
    public Stream<Movie> moviesBefore(int year) {
        Movie[] moviearray = new Movie[movies.size()];
        movies.toArray(moviearray);
        Stream<Movie> result = Arrays.stream(moviearray).filter(new Predicate<Movie>() {
            @Override
            public boolean test(Movie m) {
                return m.getReleaseYear() <= year;
            }
        }).sorted(new Comparator<Movie>() {
            public int compare(Movie a, Movie b) {
                int cmp = Integer.compare(a.getReleaseYear(), b.getReleaseYear());
                if (cmp == 0) {
                    cmp = a.getTitle().compareTo(b.getTitle());
                }
                return cmp;
                }
            });

        return result;
    }

    // returns a stream of movies released between yearA and yearB
    public Stream<Movie> moviesBetween(int yearA, int yearB) {
        Movie[] moviearray = new Movie[movies.size()];
        movies.toArray(moviearray);
        Stream<Movie> result = Arrays.stream(moviearray).filter(new Predicate<Movie>() {
            @Override
            public boolean test(Movie m) {
                return m.getReleaseYear() >= yearA && m.getReleaseYear() <= yearB;
            }
        }).sorted(new Comparator<Movie>() {
            public int compare(Movie a, Movie b) {
                int cmp = Integer.compare(a.getReleaseYear(), b.getReleaseYear());
                if (cmp == 0) {
                    cmp = a.getTitle().compareTo(b.getTitle());
                }
                return cmp;
                }
            });

        return result;
    }

    // returns a stream of movies directed by director
    public Stream<Movie> moviesByDirector(String director) {
        Movie[] moviearray = new Movie[movies.size()];
        movies.toArray(moviearray);
        Stream<Movie> result = Arrays.stream(moviearray).filter(new Predicate<Movie>() {
            @Override
            public boolean test(Movie m) {
                return m.getDirector().equalsIgnoreCase(director);
            }
        }).sorted(new Comparator<Movie>() {
            public int compare(Movie a, Movie b) {
                int cmp = Integer.compare(a.getReleaseYear(), b.getReleaseYear());
                if (cmp == 0) {
                    cmp = a.getTitle().compareTo(b.getTitle());
                }
                return cmp;
                }
            });

        return result;
    }
}
