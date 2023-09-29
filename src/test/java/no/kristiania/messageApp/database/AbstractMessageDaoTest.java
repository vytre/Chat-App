package no.kristiania.messageApp.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import no.kristiania.messageApp.database.daos.JpaMessageDao;
import no.kristiania.messageApp.database.interfaces.MessageDao;
import org.eclipse.jetty.plus.jndi.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import static org.assertj.core.api.Assertions.assertThat;

public class AbstractMessageDaoTest {
    private EntityManager entityManager;
    private MessageDao dao;

    @BeforeEach
    void setUp() throws NamingException {
        new Resource("jdbc/dataSource", InMemoryDataSource.createDataSource());
        entityManager = Persistence.createEntityManagerFactory("messageApp")
                .createEntityManager();
        dao = new JpaMessageDao(entityManager);
        entityManager.getTransaction().begin();
    }

    @Test
    void shouldRetrieveNullForMissingMessage() {
        assertThat(dao.retrieveMessage(-1)).isNull();
    }
    protected void flush() {
        entityManager.flush();
        entityManager.clear();
    }

    /*
    @Test
    void shouldRetrieveSavedMessage() {
        var message = SampleData.sampleMessage();
        dao.save(message);
        flush();
        var assertedMessage = dao.retrieve(message.getId());


        assertThat(assertedMessage)
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(message);
    }
    @Test
    void shouldFindAllMessages() {
        var message1 = SampleData.sampleMessage();
        var message2 = SampleData.sampleMessage();

        dao.save(message1);
        dao.save(message2);
        flush();

        assertThat(dao.listAll())
                .extracting(MessageEntity::getId)
                .contains(message1.getId(), message2.getId());
    }
    */
}