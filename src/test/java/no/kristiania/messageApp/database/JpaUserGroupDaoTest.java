package no.kristiania.messageApp.database;

import jakarta.persistence.EntityManager;

class JpaUserGroupDaoTest extends AbstractUserDaoTest {
    private final EntityManager entityManager;

    public JpaUserGroupDaoTest(@TestEntityManager EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
