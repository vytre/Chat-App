package no.kristiania.messageApp.database.daos;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import no.kristiania.messageApp.database.entities.UserGroupEntity;
import no.kristiania.messageApp.database.interfaces.UserGroupDao;
import java.util.ArrayList;
import java.util.List;

public class JpaUserGroupDao implements UserGroupDao {

    private final EntityManager entityManager;

    @Inject
    public JpaUserGroupDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveUserGroup(UserGroupEntity usersGroup) {
    entityManager.persist(usersGroup);
    }

    @Override
    public List<UserGroupEntity> retrieveByUserId(long userId) {
        List<UserGroupEntity> result = new ArrayList<>();

        List<UserGroupEntity> usersGroupEntities = entityManager.createQuery(
                entityManager.getCriteriaBuilder().createQuery(UserGroupEntity.class)
        ).getResultList();

        for (UserGroupEntity u:
                usersGroupEntities) {
            if (u.getUserId().getId()==userId){
                result.add(u);
            }
        }
        return result;
    }

    @Override
    public List<UserGroupEntity> listAllUserGroups() {
        return entityManager.createQuery(
                entityManager.getCriteriaBuilder().createQuery(UserGroupEntity.class)
        ).getResultList();
    }

    @Override
    public List<UserGroupEntity> retrieveByGroupId(long groupId) {
        List<UserGroupEntity> result = new ArrayList<>();

        List<UserGroupEntity> usersGroupEntities = entityManager.createQuery(
                entityManager.getCriteriaBuilder().createQuery(UserGroupEntity.class)
        ).getResultList();

        for (UserGroupEntity u:
                usersGroupEntities) {
            if (u.getGroupId().getId()==groupId){
                result.add(u);
            }
        }
        return result;
    }

    @Override
    public UserGroupEntity retrieveUserGroup(long id) {
        return entityManager.find(UserGroupEntity.class, id);
    }
}
