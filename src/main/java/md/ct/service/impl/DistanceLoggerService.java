package md.ct.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DistanceLoggerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistanceLoggerService.class);

    public void log(Long courierId, String name) {
        LOGGER.info(String.format("Courier : %d , Store : %s", courierId, name));
    }
}
