package sk.eman.juraj.presstest.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import sk.eman.juraj.presstest.AppController;
import sk.eman.juraj.presstest.Globals;
import sk.eman.juraj.presstest.R;
import sk.eman.juraj.presstest.adapters.StartupListAdapter;
import sk.eman.juraj.presstest.fragments.StartupDetailFragment;
import sk.eman.juraj.presstest.fragments.StartupListFragment;
import sk.eman.juraj.presstest.models.Movies;
import sk.eman.juraj.presstest.objects.StartupContent;
import sk.eman.juraj.presstest.utils.GsonRequest;


/**
 * An activity representing a list of Startups. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StartupDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link StartupListFragment} and the item details
 * (if present) is a {@link StartupDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link StartupListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class StartupListActivity extends FragmentActivity
        implements StartupListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private int currentPage;
    private int page=1;
    private StartupListAdapter listAdapter;
    private SwipeRefreshLayout listRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_list);

        if (findViewById(R.id.startup_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            Bundle arguments = new Bundle();
            StartupListFragment fragment = new StartupListFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.startup_list, fragment)
                    .commit();
        }
        if(savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            StartupListFragment fragment = new StartupListFragment();
            transaction.replace(R.id.startup_list, fragment);
            transaction.commit();
        }
    }

    private Response.Listener<Movies> createMyReqSuccessListener() {
        return new Response.Listener<Movies>() {
            @Override
            public void onResponse(final Movies response) {
                System.out.println(response.toString() + response.getResults().toString());

                response.save();
                       for(Movies.Results item : response.getResults()){
                            StartupContent.addItem(item);
                       }

                new Thread(new Runnable() {
                    @Override
                    public void run(){
                        for(Movies.Results item : response.getResults()){
                            item.save();
                        }

                    }}).start();
                Toast.makeText(getBaseContext(), "Page "+(currentPage)+" loaded", Toast.LENGTH_SHORT).show();
                if(listAdapter!=null) {
                    listAdapter.notifyDataSetChanged();
                    listRefreshLayout.setRefreshing(false);
                }

            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(listAdapter!=null) {
                    List<Movies> queryResults = new Select().from(Movies.class)
                            .execute();

                    if(queryResults.size()-1<=(currentPage)) {
                        Toast.makeText(getBaseContext(), "Page "+(currentPage)+" loaded form cache", Toast.LENGTH_SHORT).show();
                        Movies mov = queryResults.get(currentPage>=queryResults.size() ? queryResults.size()-1 : currentPage);

                        try {
                            for (Movies.Results res : mov.items()) {
                                StartupContent.addItem(res);
                            }
                        }catch(SQLiteException s){
                            Toast.makeText(getBaseContext(), "Error loading Page "+(currentPage), Toast.LENGTH_SHORT).show();
                        }
                        listAdapter.notifyDataSetChanged();
                    }
                    listRefreshLayout.setRefreshing(false);
                }
            }
        };
    }


    public void requestData(){
        currentPage = new Integer(page);
        String tag_gson_obj = "gson_obj_req";
        GsonRequest<Movies> myReq = new GsonRequest<Movies>(
                Globals.API_URL+""+currentPage,
                Movies.class,
                null,
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        AppController.getInstance().addToRequestQueue(myReq, tag_gson_obj);
    }

    /**
     * Callback method from {@link StartupListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(StartupDetailFragment.ARG_ITEM_ID, id);
            StartupDetailFragment fragment = new StartupDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.startup_detail_container, fragment)
                    .commit();
            Toast.makeText(getBaseContext(), "lel " + (id), Toast.LENGTH_SHORT).show();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, StartupDetailActivity.class);
            detailIntent.putExtra(StartupDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
            Toast.makeText(getBaseContext(), "lel "+(id), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh(Globals.Direction direction, StartupListAdapter mAdapter, SwipeRefreshLayout mSwipeRefreshLayout) {
        requestData();
        listAdapter=mAdapter;
        this.listRefreshLayout=mSwipeRefreshLayout;
        page++;
    }


}
