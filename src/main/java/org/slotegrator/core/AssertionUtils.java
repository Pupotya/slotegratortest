package org.slotegrator.core;

import io.qameta.allure.Step;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionUtils {

    @Step
    public static void assertContainsAll(String message, List<String> actual, List<String> expected) {
        assertThat(actual).as(message).containsAll(expected);
    }

    @Step
    public static <T> void assertThatEquals(String message, T actual, T expected) {
        assertThat(actual).as(message).isEqualTo(expected);
    }

    @Step
    public static <T> void assertThatNotEquals(String message, T actual, T expected) {
        assertThat(actual).as(message).isNotEqualTo(expected);
    }

    @Step
    public static <T> void assertThatNotNull(String message, T actual) {
        assertThat(actual).as(message).isNotNull();
    }

    @Step
    public static <T> void assertThatListIsEmpty(String message, List<T> actual) {
        assertThat(actual).as(message).isEmpty();
    }

}
