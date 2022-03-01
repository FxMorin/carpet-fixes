package carpetfixes.settings;

import carpet.settings.Condition;
import carpetfixes.helpers.Utils;

public class VersionConditions {
    public static class LT_1_18_2_pre1 implements Condition {
        @Override public boolean isTrue() {return Utils.isMCVersionCompat(VersionPredicates.LT_1_18_2_pre1);}
    }
    public static class GT_22w05a implements Condition {
        @Override public boolean isTrue() {return Utils.isMCVersionCompat(VersionPredicates.GT_22w05a);}
    }
    public static class LT_22w05a implements Condition {
        @Override public boolean isTrue() {return Utils.isMCVersionCompat(VersionPredicates.LT_22w05a);}
    }
    public static class GT_22w04a implements Condition {
        @Override public boolean isTrue() {return Utils.isMCVersionCompat(VersionPredicates.GT_22w04a);}
    }
    public static class LT_22w03a implements Condition {
        @Override public boolean isTrue() {return Utils.isMCVersionCompat(VersionPredicates.LT_22w03a);}
    }
    public static class GT_1_18_1 implements Condition {
        @Override public boolean isTrue() {return Utils.isMCVersionCompat(VersionPredicates.GT_1_18_1);}
    }
}
