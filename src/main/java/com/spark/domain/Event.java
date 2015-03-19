package com.spark.domain;

import lombok.Value;

@Value
public class Event {
    long id;
    String name;
    String location;
}
