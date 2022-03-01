package carpetfixes.settings;

import carpet.settings.Condition;
import carpetfixes.helpers.Utils;

public class VersionConditions {
    public static class LT_1_18_2_pre1 extends CustomCondition {
        @Override public String getPredicate() {return VersionPredicates.LT_1_18_2_pre1;}
    }
    public static class GT_22w05a extends CustomCondition {
        @Override public String getPredicate() {return VersionPredicates.GT_22w05a;}
    }
    public static class LT_22w05a extends CustomCondition {
        @Override public String getPredicate() {return VersionPredicates.LT_22w05a;}
    }
    public static class GT_22w04a extends CustomCondition {
        @Override public String getPredicate() {return VersionPredicates.GT_22w04a;}
    }
    public static class LT_22w03a extends CustomCondition {
        @Override public String getPredicate() {return VersionPredicates.LT_22w03a;}
    }

    public static class CustomCondition implements Condition, hasVersionPredicate {
        @Override
        public String getPredicate() {
            return "*";
        }

        @Override
        public boolean isTrue() {
            return Utils.isMCVersionCompat(getPredicate());
        }
    }

    public interface hasVersionPredicate {
        String getPredicate();
    }
}
