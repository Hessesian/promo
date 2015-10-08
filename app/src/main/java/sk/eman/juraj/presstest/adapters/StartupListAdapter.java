package sk.eman.juraj.presstest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import sk.eman.juraj.presstest.AppController;
import sk.eman.juraj.presstest.Globals;
import sk.eman.juraj.presstest.R;
import sk.eman.juraj.presstest.models.Movies;

/**
 * Created by Juraj on 8.10.2015.
 */
public class StartupListAdapter  extends ArrayAdapter<Movies.Results> {

    private final Context context;
    private final List<Movies.Results> movie;

    public StartupListAdapter(Context context, List<Movies.Results> movie) {
        super(context, -1, movie);
        this.context = context;
        this.movie = movie;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_startup_list, parent, false);
        }

        TextView movie_name = (TextView) convertView.findViewById(R.id.fragment_startup_detail_description);
        TextView movie_date = (TextView) convertView.findViewById(R.id.fragment_startup_detail_rating);
        NetworkImageView movie_icon = (NetworkImageView) convertView.findViewById(R.id.fragment_startup_detail_icon);

        int correctPosition;
        if (convertView.getParent() != null) {
            correctPosition = ((ListView) parent).getPositionForView(convertView);
        } else {
            correctPosition = position;
        }


        if (!movie.isEmpty()) {
            movie_name.setText(movie.get(correctPosition).getTitle());
            movie_date.setText(movie.get(position).getRelease_date());
            movie_icon.setImageResource(R.drawable.error);
            if (movie.get(position).getPoster_path() != null) {
                //TODO:load from cache
                ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                String image_width= "150";
                movie_icon.setImageUrl(Globals.IMG_URL + image_width + movie.get(correctPosition).getPoster_path(), imageLoader);
            } else {
                movie_icon.setImageResource(R.drawable.error);
            }
        }
        return convertView;
    }
}


