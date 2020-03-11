package sample;

import java.util.Date;

public class Logger {
    private LogLevel level;

    Logger(LogLevel level) {
        this.level = level;
    }

    public void info(Object text) {
        _print(LogLevel.INFO, text);
    }

    public void debug(Object text) {
        _print(LogLevel.DEBUG, text);
    }

    public void error(Object text) {
        _print(LogLevel.ERROR, text);
    }

    protected void _print(LogLevel level, Object text) {
        text = this.handleText(text);
        Date date = new Date();
        String output = date.toString() + " " + level + " - " + text.toString();
        System.out.println(output);
    }

    protected Object handleText(Object text) {
        if (text instanceof Exception) {
            if (this.level == LogLevel.DEBUG) {
                ((Exception) text).printStackTrace();
            }
            return ((Exception) text).getMessage();
        }

        return text;
    }
}
