package no.kristiania.messageApp;

import jakarta.persistence.EntityManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.stream.Collectors;

public class EntityManagerFilter implements Filter {

    private final MessageAppResourceConfig config;

    public EntityManagerFilter(MessageAppResourceConfig config) {
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        EntityManager entityManager = config.createEntityManagerForRequest();

        if (((HttpServletRequest)request).getMethod().equals("GET")) {
            chain.doFilter(request, response);
        } else {
            entityManager.getTransaction().begin();

            chain.doFilter(request, response);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
        config.cleanRequestEntityManager();
    }
}
