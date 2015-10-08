package sk.eman.juraj.presstest.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.eman.juraj.presstest.models.Movies;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class StartupContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Movies.Results> ITEMS = new ArrayList<Movies.Results>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Movies.Results> ITEM_MAP = new HashMap<String, Movies.Results>();

    public static void addItem(Movies.Results item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getRemoteId(), item);
    }


}
