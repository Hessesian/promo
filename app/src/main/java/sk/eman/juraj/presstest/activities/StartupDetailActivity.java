package sk.eman.juraj.presstest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import sk.eman.juraj.presstest.R;
import sk.eman.juraj.presstest.fragments.StartupDetailFragment;
import sk.eman.juraj.presstest.models.Movies;
import sk.eman.juraj.presstest.objects.StartupContent;


/**
 * An activity representing a single Startup detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StartupListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link StartupDetailFragment}.
 */
public class StartupDetailActivity extends AppCompatActivity {

    Movies.Results mItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(StartupDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(StartupDetailFragment.ARG_ITEM_ID));
            StartupDetailFragment fragment = new StartupDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.startup_detail_container, fragment)
                    .commit();

            mItem = StartupContent.ITEM_MAP.get(getIntent().getStringExtra(StartupDetailFragment.ARG_ITEM_ID));
            getSupportActionBar().setTitle(mItem.getTitle());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, StartupListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
