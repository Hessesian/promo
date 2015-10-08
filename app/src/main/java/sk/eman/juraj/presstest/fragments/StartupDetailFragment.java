package sk.eman.juraj.presstest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import sk.eman.juraj.presstest.AppController;
import sk.eman.juraj.presstest.Globals;
import sk.eman.juraj.presstest.R;
import sk.eman.juraj.presstest.activities.StartupDetailActivity;
import sk.eman.juraj.presstest.activities.StartupListActivity;
import sk.eman.juraj.presstest.models.Movies;
import sk.eman.juraj.presstest.objects.StartupContent;

/**
 * A fragment representing a single Startup detail screen.
 * This fragment is either contained in a {@link StartupListActivity}
 * in two-pane mode (on tablets) or a {@link StartupDetailActivity}
 * on handsets.
 */
public class StartupDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Movies.Results mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StartupDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = StartupContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_startup_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.fragment_startup_detail_description)).setText(mItem.getOverview());
            ((TextView) rootView.findViewById(R.id.fragment_startup_detail_rating)).setText("Rating: " + mItem.getVote_average());

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            String image_width= "500";
            ((NetworkImageView)rootView.findViewById(R.id.fragment_startup_detail_icon))
                    .setImageUrl(Globals.IMG_URL + image_width + mItem.getBackdrop_path(), imageLoader);
        }
        return rootView;
    }
}
