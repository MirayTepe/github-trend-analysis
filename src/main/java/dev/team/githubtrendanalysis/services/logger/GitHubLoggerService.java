package dev.team.githubtrendanalysis.services.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GitHubLoggerService implements LoggerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubLoggerService.class);

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void info(String format, Object... arguments) {
        LOGGER.info(format, arguments);
    }

    @Override
    public void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }

    @Override
    public void error(String message) {
        LOGGER.error(message);
    }

    @Override
    public void warn(String message) {
        LOGGER.warn(message);
    }

    @Override
    public void debug(String message) {
        LOGGER.debug(message);
    }
}
