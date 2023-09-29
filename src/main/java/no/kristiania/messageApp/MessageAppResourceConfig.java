package no.kristiania.messageApp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import no.kristiania.messageApp.database.daos.JpaGroupListDao;
import no.kristiania.messageApp.database.daos.JpaMessageDao;
import no.kristiania.messageApp.database.daos.JpaUserDao;
import no.kristiania.messageApp.database.daos.JpaUserGroupDao;
import no.kristiania.messageApp.database.interfaces.*;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

public class MessageAppResourceConfig extends ResourceConfig {

    private ThreadLocal<EntityManager> requestEntityManager = new ThreadLocal<>();

    private EntityManagerFactory entityManagerFactory;

    public MessageAppResourceConfig(EntityManagerFactory entityManagerFactory) {

        super(MessageAppEndpoint.class);

        this.entityManagerFactory = entityManagerFactory;
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JpaGroupListDao.class).to(GroupListDao.class);
                bind(JpaMessageDao.class).to(MessageDao.class);
                bind(JpaUserDao.class).to(UserDao.class);
                bind(JpaUserGroupDao.class).to(UserGroupDao.class);
                bindFactory(() -> requestEntityManager.get())
                        .to(EntityManager.class)
                        .in(RequestScoped.class);
            }
        });
    }

    public EntityManager createEntityManagerForRequest() {
        requestEntityManager.set(entityManagerFactory.createEntityManager());

        return requestEntityManager.get();
    }

    public void cleanRequestEntityManager() {
        requestEntityManager.remove();
    }
}
