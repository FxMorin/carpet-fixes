package carpetfixes.settings.conditions;

import carpet.settings.Condition;
import carpetfixes.helpers.Utils;
import carpetfixes.settings.VersionPredicates;

public class LT_22w05a_VersionCondition implements Condition {

    @Override
    public boolean isTrue() {
        return Utils.isMCVersionCompat(VersionPredicates.LT_22w05a);
    }
}
