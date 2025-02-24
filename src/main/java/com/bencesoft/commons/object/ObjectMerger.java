package com.bencesoft.commons.object;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class ObjectMerger {

    private final ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    public <T> T mergeInto(T target, T source, Class<T> cls, boolean copyNulls) {
        final Map<String, Object> sourceFields = objectMapper.convertValue(source, Map.class);
        final Map<String, Object> targetFields = objectMapper.convertValue(target, Map.class);
        for (Map.Entry<String, Object> entry : sourceFields.entrySet()) {
            if (Objects.nonNull(entry.getValue()) || copyNulls) {
                targetFields.put(entry.getKey(), entry.getValue());
            }
        }
        return objectMapper.convertValue(targetFields, cls);
    }
}
