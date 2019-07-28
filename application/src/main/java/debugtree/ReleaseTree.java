package debugtree;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 2019/7/28.
 *
 * @author 郑少鹏
 * @desc ReleaseTree
 */
public class ReleaseTree extends ThreadAwareDebugTree {
    /**
     * Return whether a message at {@code priority} or {@code tag} should be logged.
     *
     * @param tag      标志
     * @param priority 优先级
     */
    @Override
    protected boolean isLoggable(@Nullable String tag, int priority) {
        return priority != Log.VERBOSE && priority != Log.DEBUG && priority != Log.INFO;
    }

    /**
     * Break up {@code message} into maximum-length chunks (if needed) and send to either
     * {@link Log#println(int, String, String) Log.println()} or
     * {@link Log#wtf(String, String) Log.wtf()} for logging.
     * <p>
     * {@inheritDoc}
     *
     * @param priority 优先级
     * @param tag      标志
     * @param message  消息
     * @param t        抛出
     */
    @Override
    protected void log(int priority, String tag, @NotNull String message, Throwable t) {
        if (!isLoggable(tag, priority)) {
            return;
        }
        super.log(priority, tag, message, t);
    }
}
