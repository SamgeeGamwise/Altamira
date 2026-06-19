package com.samwise.altamira.common;

import java.time.Instant;

public record Meta(
        Instant timestamp
) {
    public static Meta of() {
        return new Meta(Instant.now());
    }
}
