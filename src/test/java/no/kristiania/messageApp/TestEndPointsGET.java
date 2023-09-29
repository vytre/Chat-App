package no.kristiania.messageApp;

import no.kristiania.messageApp.database.InMemoryDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class TestEndPointsGET {

    private MessageAppServer messageAppServer;

    // GET
    @Test
    void shouldServeHomePage() throws Exception {
        var connection = getConnection("/");

        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<title>Your favourite messenger application</title>");
    }

    @BeforeEach
    void setUp() throws Exception {
        messageAppServer = new MessageAppServer(0, InMemoryDataSource.createDataSource());
        messageAppServer.start();
    }

    @Test
    void getUsers() throws IOException {
        var connection = getConnection("/api/user");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
    }

    @Test
    void getMessages() throws IOException {
        var connection = getConnection("/api/message");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
    }

    @Test
    void getGroups() throws IOException {
        var connection = getConnection("/api/group");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
    }

    @Test
    void getUserGroups() throws IOException {
        var connection = getConnection("/api/usergroup");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
    }

    @Test
    void shouldReturn404() throws IOException {
        var connection = getConnection("/bullshit");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(404);
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        return (HttpURLConnection) new URL(messageAppServer.getURL(),url).openConnection();
    }
}