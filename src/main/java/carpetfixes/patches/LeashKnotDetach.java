package carpetfixes.patches;

import net.minecraft.entity.mob.MobEntity;

public interface LeashKnotDetach {
    void onDetachLeash(MobEntity caller);
}