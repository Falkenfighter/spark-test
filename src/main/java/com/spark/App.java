package com.spark;

import com.spark.routes.Auth;
import com.spark.routes.Events;
import com.spark.routes.Users;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        // Server config
        port(3000);

        // Wrap runtime exceptions in json
        exception(RuntimeException.class, (e, req, res) -> {
            res.status(500);
            res.body("{\"error\":\"" + e.getMessage() + "\"}");
        });

        // Authentication
        before((req, res) -> {
            // On a new session set the initial auth state to false
            if (req.session().isNew()) {
                req.session().attribute(Auth.BASE, false);
            }
        });
        before("*/session", (req, res) -> {
            // Protect any /session endpoints
            if (!(boolean) req.session().attribute(Auth.BASE)) {
                halt("You must be authenticated to reach that endpoint!");
            }
        });

        // Init routes:
        Events.init();
        Users.init();
        Auth.init();

        // Sample loading html (FreeMarker)
        get("/", (req, res) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("routes", Arrays.asList(Events.BASE, Users.BASE, Auth.BASE));
            return new ModelAndView(data, "index.ftl");
        }, new FreeMarkerEngine());
    }
}
