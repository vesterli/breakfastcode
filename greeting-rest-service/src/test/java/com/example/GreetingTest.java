package com.example;

import java.util.Locale;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GreetingTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = ServiceExecutor.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(ServiceExecutor.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    @Test
    public void testGetGreetingNoLang() {
        String responseMsg = target.path("greetings").request().get(String.class);
        assertEquals("{\"greeting\":\"Hallo!\"}", responseMsg);
    }

    @Test
    public void testGetGreetingDa() {
        Invocation.Builder theRequest = target.path("greetings").request();
        theRequest.acceptLanguage(new Locale("da"));
        String responseMsg = theRequest.get(String.class);
        assertEquals("{\"greeting\":\"Hallo!\"}", responseMsg);
    }

    @Test
    public void testGetGreetingEn() {
        Invocation.Builder theRequest = target.path("greetings").request();
        theRequest.acceptLanguage(new Locale("en"));
        String responseMsg = theRequest.get(String.class);
        assertEquals("{\"greeting\":\"Hello!\"}", responseMsg);
    }
}
