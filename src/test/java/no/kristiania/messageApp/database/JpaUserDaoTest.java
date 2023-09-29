package no.kristiania.messageApp.database;

import jakarta.persistence.EntityManager;

class JpaUserDaoTest extends AbstractUserDaoTest {
    private final EntityManager entityManager;

    public JpaUserDaoTest(@TestEntityManager EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
