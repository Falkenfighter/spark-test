package com.spark;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

@Test
public class AppIT {
    @BeforeClass
    public void setUp() throws Exception {
        App.main(null);
    }

    @AfterClass
    public void tearDown() throws Exception {
        Spark.stop();
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testEvents() throws Exception {
        String name = request("GET", "/events").get(0).getAsJsonObject().get("name").getAsString();
        assertEquals(name, "event1");
    }

    private JsonArray request(String method, String path) {
        try {
            URL url = new URL("http://localhost:3000" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new JsonParser().parse(body).getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }
}
