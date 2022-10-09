package carpetfixes.settings;

import carpet.api.settings.Rule.Condition;
import carpetfixes.helpers.Utils;

public class VersionConditions {
    /*public static class GT_22w11a extends CustomCondition {
        @Override public String getPredicate() {return VersionPredicates.GT_22w11a;}
    }*/

    public static class CustomCondition implements Condition, hasVersionPredicate {
        @Override
        public String getPredicate() {
            return "*";
        }

        @Override
        public boolean shouldRegister() {
            return Utils.isMCVersionCompat(getPredicate());
        }
    }

    public interface hasVersionPredicate {
        String getPredicate();
    }
}
