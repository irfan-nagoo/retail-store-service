package com.sample.retailstore.cache;

import com.sample.retailstore.entity.Type;
import com.sample.retailstore.repository.TypeRepository;
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
public class TypeCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeCache.class);

    private final TypeRepository typeRepository;

    private final List<Type> typeCache = new ArrayList<>();

    @PostConstruct
    private void load() {
        typeRepository.findAll().forEach(type -> typeCache.add(type));
        LOGGER.info("Type cache initialized Successfully");
    }

    public Type getType(String type, String value) {
        return typeCache.stream()
                .filter(t -> t.equals(new Type(0l, type, value)))
                .findAny()
                .orElse(null);
    }

}
