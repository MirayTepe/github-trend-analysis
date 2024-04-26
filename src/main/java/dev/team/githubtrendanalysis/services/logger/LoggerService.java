package dev.team.githubtrendanalysis.services.logger;

public interface LoggerService {

    void info(String message);

    void info(String format, Object... arguments);

    void error(String message, Throwable throwable);

    void error(String message);

    void warn(String message);

    void debug(String message);
}
