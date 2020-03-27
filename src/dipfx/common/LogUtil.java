package dipfx.common;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtil {
    private static final Level defaultLevel = Level.FINEST;

    public static Logger getLogger(String name, Level level) {
        Logger logger = Logger.getLogger(name);

        // ensure a custom handler is used, not build defined
        Handler handlerObj = new ConsoleHandler();
        handlerObj.setLevel(level);
        logger.addHandler(handlerObj);
        logger.setLevel(level);
        logger.setUseParentHandlers(false);
        return logger;
    }

    public static Logger getLogger(String name) {
        return getLogger(name, defaultLevel);
    }
}
