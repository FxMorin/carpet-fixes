package carpetfixes.settings;

import java.lang.reflect.Field;
import java.util.List;

/**
 * A parsed Carpet-Fixes bug, with its field, value, and other useful stuff.
 *
 * It is generated from the fields with the {@link Bug} annotation
 * when being parsed by {@link BugManager#parseSettingsClass(CustomSettingsManager,Class,boolean)}.
 */
public final class ParsedBug {
    public final Field field;
    public final String name;
    public final List<String> reports;
    public final List<String> related;
    public final List<String> categories;
    public final String resolution;
    public boolean reIntroduce;
    public final BugManager bugManager;

    ParsedBug(Field field, Bug bug, BugManager bugManager) {
        this.field = field;
        this.name = field.getName();
        this.reIntroduce = bug.reIntroduce();
        this.reports = List.of(bug.reports());
        this.related = List.of(bug.related());
        this.categories = List.of(bug.categories());
        this.resolution = bug.resolution();
        this.bugManager = bugManager;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == ParsedBug.class && ((ParsedBug)obj).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
