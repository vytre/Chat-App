package no.kristiania.messageApp.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import no.kristiania.messageApp.database.daos.JpaUserGroupDao;
import no.kristiania.messageApp.database.interfaces.UserGroupDao;
import org.eclipse.jetty.plus.jndi.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractUserGroupDaoTest {
    private EntityManager entityManager;
    private UserGroupDao dao;

    @BeforeEach
    void setUp() throws NamingException {
        new Resource("jdbc/dataSource", InMemoryDataSource.createDataSource());
        entityManager = Persistence.createEntityManagerFactory("messageApp")
                .createEntityManager();
        dao = new JpaUserGroupDao(entityManager);
        entityManager.getTransaction().begin();
    }
    @Test
    void shouldRetrieveNullForMissingUserGroup() {
        assertThat(dao.retrieveUserGroup(-1)).isNull();
    }

    protected void flush() {
        entityManager.flush();
        entityManager.clear();
    }
    /*-------------------------------------------------------------------------------------------------
        @Test
        void shouldRetrieveSavedUserGroup() {
            var group = SampleData.sampleUserGroup();
            dao.save(group);
            flush();
            var assertedGroup = dao.retrieve(group.getId());

            assertThat(assertedGroup)
                    .hasNoNullFieldsOrProperties()
                    .usingRecursiveComparison()
                    .isEqualTo(group);
        }
        @Test
        void shouldFindAllGroups() {
            var group1 = SampleData.sampleUserGroup();
            var group2 = SampleData.sampleUserGroup();

            dao.save(group1);
            dao.save(group2);
            flush();

            assertThat(dao.listAll())
                    .extracting(UsersGroupEntity::getId)
                    .contains(group1.getId(), group2.getId());
        }
    */
}
