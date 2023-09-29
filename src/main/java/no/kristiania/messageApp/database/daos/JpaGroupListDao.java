package no.kristiania.messageApp.database.daos;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import no.kristiania.messageApp.database.entities.GroupListEntity;
import no.kristiania.messageApp.database.interfaces.GroupListDao;
import java.sql.SQLException;
import java.util.List;

public class JpaGroupListDao implements GroupListDao {

    private final EntityManager entityManager;

    @Inject
    public JpaGroupListDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveGroup(GroupListEntity groupList) {
    entityManager.persist(groupList);
    }

    @Override
    public GroupListEntity retrieveGroup(long id) {
    return entityManager.find(GroupListEntity.class, id);
    }

    @Override
    public List<GroupListEntity> listAllGroups() {
        return entityManager.createQuery(
                entityManager.getCriteriaBuilder().createQuery(GroupListEntity.class)
        ).getResultList();
    }
}
