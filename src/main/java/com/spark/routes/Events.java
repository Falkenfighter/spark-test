package com.spark.routes;

import com.spark.domain.Event;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import static com.spark.util.JsonUtil.json;
import static spark.Spark.get;
import static spark.Spark.post;

public class Events {
    public static final String BASE = "events";

    // Fake DB with initial events
    private static final List<Event> events = new Vector<>();
    static {
        events.add(new Event(1234l, "event1", "location1"));
        events.add(new Event(4321l, "event2", "location2"));
    }

    public static void init() {
        get(BASE, (req, res) -> events, json());

        get(BASE + "/:eventId", (req, res) -> {
            return events.stream()
                    .filter(e -> e.getId() == new Long(req.params(":eventId")))
                    .findAny()
                    .orElseThrow(() ->
                            new RuntimeException("Unable to find event with id=" + req.params(":eventId")));
        }, json());

        post(BASE, (req, res) -> {
            final long id = UUID.randomUUID().getMostSignificantBits();
            final Event event = new Event(id, req.queryParams("name"), req.queryParams("location"));
            events.add(event);
            return event;
        }, json());
    }
}
