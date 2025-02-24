package com.bencesoft.commons.object;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ObjectMergerTest {

    private final ObjectMerger objectMerger = new ObjectMerger(new ObjectMapper());

    @Test
    public void mergeInto_ShouldMergeNonNulls() {
        // GIVEN
        var copyNulls = false;
        var target = User.builder()
            .id(1L)
            .email("targetEmail")
            .password("targetPassword")
            .roles(List.of("TARGET_ROLE"))
            .build();
        var source = User.builder()
            .id(null)
            .email("sourceEmail")
            .password(null)
            .roles(List.of("SOURCE_ROLE"))
            .build();
        // WHEN
        var merged = objectMerger.mergeInto(target, source, User.class, copyNulls);
        // THEN
        Assertions.assertEquals(target.getId(), merged.getId());
        Assertions.assertEquals(source.getEmail(), merged.getEmail());
        Assertions.assertEquals(target.getPassword(), merged.getPassword());
        Assertions.assertEquals(source.getRoles(), merged.getRoles());
    }

    @Test
    public void mergeInto_ShouldMergeNulls() {
        // GIVEN
        var copyNulls = true;
        var target = User.builder()
            .id(1L)
            .email("targetEmail")
            .password("targetPassword")
            .roles(List.of("TARGET_ROLE"))
            .build();
        var source = User.builder()
            .id(null)
            .email("sourceEmail")
            .password("sourcePassword")
            .roles(null)
            .build();
        // WHEN
        var merged = objectMerger.mergeInto(target, source, User.class, copyNulls);
        // THEN
        Assertions.assertEquals(source.getId(), merged.getId());
        Assertions.assertEquals(source.getEmail(), merged.getEmail());
        Assertions.assertEquals(source.getPassword(), merged.getPassword());
        Assertions.assertEquals(source.getRoles(), merged.getRoles());
    }
}
