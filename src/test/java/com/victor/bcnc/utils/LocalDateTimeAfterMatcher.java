package com.victor.bcnc.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class LocalDateTimeAfterMatcher extends BaseMatcher<LocalDateTime> {

    private LocalDateTime expected;
    private DateTimeFormatter formatter;

    public LocalDateTimeAfterMatcher(LocalDateTime expected, String format) {
        this.expected = expected;
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public boolean matches(Object actual) {
        var actualStr = (String) actual;
        LocalDateTime parsed = LocalDateTime.parse(actualStr, this.formatter);
        if (!expected.isAfter(parsed)) {
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("LocalDateTime isn't after %s", expected));
    }

}
