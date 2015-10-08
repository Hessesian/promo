package sk.eman.juraj.presstest.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juraj on 7.10.2015.
 */

@Table(name = "Pages")
public class Movies extends Model {

    public Movies(){
        super();
    }

    @Column(name = "page", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    int page;

    ArrayList<Results> results;

    public ArrayList<Results> getResults(){
        return this.results;
    }

    public int getPage(){
        return page;
    }
    public List<Results> items() {
        return getMany(Results.class, "Results");
    }

    @Table(name = "Movies")
    public static class Results extends Model {

        public Results(){
            super();
        }

        @Column(name = "movies", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
        public Movies movies;

        @Column(name = "title")
        private String title;

        @Column(name = "original_title")
        private String original_title;


        private float popularity;

        @Column(name = "backdrop_path")
        private String backdrop_path;

        @Column(name = "poster_path")
        private String poster_path;

        @Column(name = "release_date")
        private String release_date;

        private boolean adult;


        private long budget;

        private String homepage;

        @Column(name = "overview")
        private String overview;

        // todo still there??

        @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
        private String id;


        private long revenue;

        private int runtime;


        private String tagline;


        private float user_rating;

        @Column(name = "user_rating")
        private float vote_average;

        private int voteCount;


        private String status;


        public String getBackdrop_path() {
            return backdrop_path;
        }


        public String getOriginal_title() {
            return original_title;
        }


        public float getPopularity() {
            return popularity;
        }


        public String getPoster_path() {
            return poster_path;
        }


        public String getRelease_date() {
            return release_date;
        }


        public String getTitle() {
            return title;
        }


        public boolean isAdult() {
            return adult;
        }


        public long getBudget() {
            return budget;
        }


        public String getHomepage() {
            return homepage;
        }


        public String getRemoteId() {
            return id;
        }


        public String getOverview() {
            return overview;
        }


        public long getRevenue() {
            return revenue;
        }


        public int getRuntime() {
            return runtime;
        }


        public String getTagline() {
            return tagline;
        }


        public float getVote_average() {
            return vote_average;
        }


        public int getVoteCount() {
            return voteCount;
        }


        public String getStatus() {
            return status;
        }


        public float getUser_rating() {
            return user_rating;
        }

        @Override
        public String toString()  {
            return title + " - " + release_date;
        }
    }

    @Override
    public String toString()  {
        return ""+page;
    }

}
