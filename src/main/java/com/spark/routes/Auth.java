package com.spark.routes;

import static spark.Spark.*;

public class Auth {
    public static final String BASE = "auth";

    public static void init() {
        get(BASE, (req, res) -> {
            req.session(true);
            req.session().attribute(BASE, true);
            return "You've been authenticated! You are now able to hit the auth/session endpoint";
        });

        get(BASE + "/session", (req, res) -> "Congrats you made it!");
    }
}
