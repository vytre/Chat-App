package no.kristiania.messageApp.database;

import jakarta.persistence.EntityManager;

class JpaMessageDaoTest extends AbstractUserDaoTest {
    private final EntityManager entityManager;

    public JpaMessageDaoTest(@TestEntityManager EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
