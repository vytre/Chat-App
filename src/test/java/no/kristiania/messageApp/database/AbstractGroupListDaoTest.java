package no.kristiania.messageApp.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import no.kristiania.messageApp.database.daos.JpaGroupListDao;
import no.kristiania.messageApp.database.entities.GroupListEntity;
import no.kristiania.messageApp.database.interfaces.GroupListDao;
import org.eclipse.jetty.plus.jndi.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractGroupListDaoTest {
    private EntityManager entityManager;
    private GroupListDao dao;

    @BeforeEach
    void setUp() throws NamingException {
        new Resource("jdbc/dataSource", InMemoryDataSource.createDataSource());
        entityManager = Persistence.createEntityManagerFactory("messageApp")
                .createEntityManager();
        dao = new JpaGroupListDao(entityManager);
        entityManager.getTransaction().begin();
    }

    @Test
    void shouldRetrieveSavedGroup() {
        var group = SampleData.sampleGroup();
        dao.saveGroup(group);
        flush();
        var assertedGroup = dao.retrieveGroup(group.getId());

        assertThat(assertedGroup)
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(group);
    }
    @Test
    void shouldFindAllGroups() {
        var group1 = SampleData.sampleGroup();
        var group2 = SampleData.sampleGroup();

        dao.saveGroup(group1);
        dao.saveGroup(group2);
        flush();

        assertThat(dao.listAllGroups())
                .extracting(GroupListEntity::getId)
                .contains(group1.getId(), group2.getId());
    }

    @Test
    void shouldRetrieveNullForMissingGroup() {
        assertThat(dao.retrieveGroup(-1)).isNull();
    }
    protected void flush() {
        entityManager.flush();
        entityManager.clear();
    }
}
