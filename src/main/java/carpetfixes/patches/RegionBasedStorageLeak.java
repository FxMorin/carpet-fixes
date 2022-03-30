package carpetfixes.patches;

import java.util.Optional;

public interface RegionBasedStorageLeak<R> {
    Optional<R> remove(long pos);
    int getTotalElements();
}
