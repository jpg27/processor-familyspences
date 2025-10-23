package com.familyspences.processorfamilyapi.utils.gson;

import java.util.Optional;

public interface MapperJsonObject {

    Optional<String> execute(Object object);

    <T> Optional<T> execute(String json, Class<T> targetClass);
}
