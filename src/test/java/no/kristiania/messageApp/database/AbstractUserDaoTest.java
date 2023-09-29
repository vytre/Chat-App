package no.kristiania.messageApp.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import no.kristiania.messageApp.database.daos.JpaUserDao;
import no.kristiania.messageApp.database.entities.UserEntity;
import no.kristiania.messageApp.database.interfaces.UserDao;
import org.eclipse.jetty.plus.jndi.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractUserDaoTest {
    private EntityManager entityManager;
    private UserDao dao;

    @BeforeEach
    void setUp() throws NamingException {
        new Resource("jdbc/dataSource", InMemoryDataSource.createDataSource());
        entityManager = Persistence.createEntityManagerFactory("messageApp")
                .createEntityManager();
        dao = new JpaUserDao(entityManager);
        entityManager.getTransaction().begin();
    }

    @Test
    void shouldRetrieveSavedUser() {
        var user = SampleData.sampleUser();
        dao.saveUser(user);
        flush();
        var assertedUser = dao.retrieveUser(user.getId());

        assertThat(assertedUser)
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(user);
    }
    @Test
    void shouldFindAllUsers() {
        var user1 = SampleData.sampleUser();
        var user2 = SampleData.sampleUser();

        dao.saveUser(user1);
        dao.saveUser(user2);
        flush();

        assertThat(dao.listAllUsers())
                .extracting(UserEntity::getId)
                .contains(user1.getId(), user2.getId());
    }


    @Test
    void shouldRetrieveNullForMissingUser() {
        assertThat(dao.retrieveUser(-1)).isNull();
    }
    protected void flush() {
        entityManager.flush();
        entityManager.clear();
    }
}
