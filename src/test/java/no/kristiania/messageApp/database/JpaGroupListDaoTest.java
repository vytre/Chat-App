package no.kristiania.messageApp.database;

import jakarta.persistence.EntityManager;

class JpaGroupListDaoTest extends AbstractGroupListDaoTest {
    private final EntityManager entityManager;

    public JpaGroupListDaoTest(@TestEntityManager EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
