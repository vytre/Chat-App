package no.kristiania.messageApp;

import jakarta.persistence.Persistence;
import jakarta.servlet.DispatcherType;
import no.kristiania.messageApp.database.DatabaseSetup;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;
import java.util.Optional;

public class MessageAppServer {

    private static final Logger logger = LoggerFactory.getLogger(MessageAppServer.class);

    private final Server server;

    public MessageAppServer(int port, DataSource dataSource) throws NamingException, IOException {

        this.server = new Server(port);

        var handler = new WebAppContext();
        handler.setContextPath("/");
        var baseResource = org.eclipse.jetty.util.resource.Resource.newClassPathResource("/messengerApp");
        org.eclipse.jetty.util.resource.Resource sourceResource = getSourceResource(baseResource);

        if (sourceResource != null) {
            handler.setBaseResource(sourceResource);
            handler.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            handler.setBaseResource(baseResource);
        }

        new Resource("jdbc/dataSource", dataSource);
        var entityManagerFactory = Persistence.createEntityManagerFactory("messageApp");

        var config = new MessageAppResourceConfig(entityManagerFactory);
        handler.addServlet(new ServletHolder(new ServletContainer(config)), "/api/*");
        handler.addFilter(new FilterHolder(new EntityManagerFilter(config)), "/api/*", EnumSet.of(DispatcherType.REQUEST));
        server.setHandler(handler);
    }

    private org.eclipse.jetty.util.resource.Resource getSourceResource(org.eclipse.jetty.util.resource.Resource baseResource) throws IOException {

        var file = baseResource.getFile();
        if (file == null) {
            return null;
        }
        var sourceDirectory = new File(file.toString()
                .replace('\\', '/')
                .replace("target/classes", "src/main/resources"));
        if (sourceDirectory.exists()) {
            return org.eclipse.jetty.util.resource.Resource.newResource(sourceDirectory);
        } else {
            return null;
        }
    }

    public void start() throws Exception {
        server.start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public static void main(String[] args) throws Exception {
        var port = Optional.ofNullable(System.getenv("HTTP_PLATFORM_PORT"))
                .map(Integer::parseInt)
                .orElse(8080);
        var server = new MessageAppServer(port, DatabaseSetup.getDataSource());
        server.start();
        logger.warn("Server started on {}", server.getURL());
    }
}
