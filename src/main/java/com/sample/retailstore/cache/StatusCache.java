package com.sample.retailstore.cache;

import com.sample.retailstore.entity.Status;
import com.sample.retailstore.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author irfan.nagoo
 */


@Component
@RequiredArgsConstructor
public class StatusCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusCache.class);

    private final StatusRepository statusRepository;

    private final List<Status> statusCache = new ArrayList<>();

    public Status getStatus(String type, String value) {
        return statusCache.stream()
                .filter(s -> s.equals(new Status(1l, type, value)))
                .findAny()
                .orElse(null);
    }

    @PostConstruct
    private void load() {
        statusRepository.findAll().forEach(status -> statusCache.add(status));
        LOGGER.info("Status cache initialized Successfully");
    }

}
