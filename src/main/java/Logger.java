public class Logger {
    private int currentLine = 0;
    private static Logger logger = null;

    private Logger() {
    }

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }

        return logger;
    }

    public void log(String message) {
        currentLine++;
        System.out.println(currentLine + "::" + message);
    }
}