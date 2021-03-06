package genius.utils.cache;

import android.support.v4.util.LruCache;

/**
 * Created by Hongsec on 2016-07-21.
 */
public class UtilCache<T> extends LruCache<String,T> {

    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        return cacheSize;
    }


    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public UtilCache(int maxSize) {
        super(maxSize);
    }

    public UtilCache() {
        super(getDefaultLruCacheSize());

    }

}
