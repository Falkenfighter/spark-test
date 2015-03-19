package com.spark.routes;

import static spark.Spark.*;

public class Users {
    public static final String BASE = "users";

    public static void init() {
        get(BASE, (req, res) -> "Information about users");

        get(BASE + "/:userId", (req, res) -> "User[" + req.params(":userId") + "]");
    }
}
