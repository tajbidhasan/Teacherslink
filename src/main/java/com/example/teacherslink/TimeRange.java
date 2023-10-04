package com.example.teacherslink;

import java.time.LocalTime;

public final class TimeRange {
    private final LocalTime start;
    private final LocalTime end;

    public TimeRange(int h1, int m1, int h2, int m2) {
        this(LocalTime.of(h1, m1), LocalTime.of(h2, m2));
    }

    public TimeRange(LocalTime start, LocalTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public boolean isOverlapping(TimeRange other) {
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    @Override
    public String toString() {
        return "TimeRange [start=" + start + ", end=" + end + "]";
    }
}
