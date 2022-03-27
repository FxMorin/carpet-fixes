package carpetfixes.helpers;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventManager {

    /**
     Using a list is better than using a set since we only loop through the eventListeners, and since
     searching only happens during startup when initializing the event listeners, we don't care about
     the negligible search cost of using an arraylist. This in terms saves us a bunch of memory over
     a HashSet, while still only allowing one instance of Runnable per event.
     */

    private static final Map<CF_Event, List<Runnable>> eventListeners = Maps.newHashMap();

    public enum CF_Event {
        DATAPACK_RELOAD
    }

    public static void onEvent(CF_Event event) {
        eventListeners.get(event).forEach((Runnable::run));
    }

    public static void addEventListener(CF_Event event, Runnable runnable) {
        List<Runnable> runnables = eventListeners.computeIfAbsent(event,(e) -> new ArrayList<>());
        if (!runnables.contains(runnable)) runnables.add(runnable);
    }
}
