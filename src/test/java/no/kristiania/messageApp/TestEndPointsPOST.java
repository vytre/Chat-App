package no.kristiania.messageApp;

import jakarta.json.Json;
import no.kristiania.messageApp.database.InMemoryDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class TestEndPointsPOST {
    private MessageAppServer messageAppServer;

    @BeforeEach
    void setUp() throws Exception {
        messageAppServer = new MessageAppServer(0, InMemoryDataSource.createDataSource());
        messageAppServer.start();
    }

    @Test
    void shouldPostToUser() throws IOException {
        var postConnection = getConnection("/api/user");

        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        postConnection.setRequestProperty("Content-Type","application/json");

        postConnection.getOutputStream().write(
                Json.createObjectBuilder()
                        .add("name","Bengt")
                        .add("email","Bengt@kristiania.no")
                        .build()
                        .toString()
                        .getBytes(StandardCharsets.UTF_8)
        );

        assertThat(postConnection.getResponseCode())
                .as(postConnection.getResponseMessage() + " for " + postConnection.getURL())
                .isEqualTo(204);


        var connection = getConnection("/api/user");

        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("\"email\":\"Bengt@kristiania.no\"");
    }

    @Test
    void shouldPostToGroup() throws IOException {
        var postConnection = getConnection("/api/group");

        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        postConnection.setRequestProperty("Content-Type","application/json");

        postConnection.getOutputStream().write(
                Json.createObjectBuilder()
                        .add("id",2)
                        .add("name","Backend Chat")
                        .build()
                        .toString()
                        .getBytes(StandardCharsets.UTF_8)
        );

        assertThat(postConnection.getResponseCode())
                .as(postConnection.getResponseMessage() + " for " + postConnection.getURL())
                .isEqualTo(204);


        var connection = getConnection("/api/group");

        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("\"name\":\"Backend Chat\"");
    }

    @Test
    void shouldPostToMessage() throws IOException {
        var postConnection = getConnection("/api/message");

        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        postConnection.setRequestProperty("Content-Type","application/json");

        postConnection.getOutputStream().write(
                Json.createObjectBuilder()
                        .add("title","A very important Question")
                        .add("body","Anyone wanna go out for a Beer?")
                        .build()
                        .toString()
                        .getBytes(StandardCharsets.UTF_8)
        );

        assertThat(postConnection.getResponseCode())
                .as(postConnection.getResponseMessage() + " for " + postConnection.getURL())
                .isEqualTo(204);


        var connection = getConnection("/api/message");

        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("\"body\":\"Anyone wanna go out for a Beer?\"");
    }

    @Test
    void shouldPostToUserGroup() throws IOException {
        var postConnection = getConnection("/api/usergroup");

        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        postConnection.setRequestProperty("Content-Type","application/json");

        postConnection.getOutputStream().write(
                Json.createObjectBuilder()
                        .add("id",1)
                        .build()
                        .toString()
                        .getBytes(StandardCharsets.UTF_8)
        );

        assertThat(postConnection.getResponseCode())
                .as(postConnection.getResponseMessage() + " for " + postConnection.getURL())
                .isEqualTo(204);


        var connection = getConnection("/api/usergroup");

        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("\"id\":1");
    }

    @Test
    void shouldPostToNonExistingEndpoint() throws IOException {
        var postConnection = getConnection("/api/bullshit");

        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        postConnection.setRequestProperty("Content-Type","application/json");

        postConnection.getOutputStream().write(
                Json.createObjectBuilder()
                        .add("title","A very important Question")
                        .add("body","Anyone wanna go out for a Beer?")
                        .build()
                        .toString()
                        .getBytes(StandardCharsets.UTF_8)
        );

        assertThat(postConnection.getResponseCode())
                .as(postConnection.getResponseMessage() + " for " + postConnection.getURL())
                .isEqualTo(404);
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        return (HttpURLConnection) new URL(messageAppServer.getURL(),url).openConnection();
    }


}