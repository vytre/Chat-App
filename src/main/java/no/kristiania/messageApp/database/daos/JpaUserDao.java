package no.kristiania.messageApp.database.daos;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import no.kristiania.messageApp.database.entities.UserEntity;
import no.kristiania.messageApp.database.interfaces.UserDao;
import java.util.ArrayList;
import java.util.List;

public class JpaUserDao implements UserDao {
    private final EntityManager entityManager;

    @Inject
    public JpaUserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveUser(UserEntity users) {
        entityManager.persist(users);
    }

    @Override
    public void updateUser(UserEntity users) {
        var a = entityManager.find(UserEntity.class,users.getId());
        a.setName(users.getName());
        a.setEmail(users.getEmail());
        a.setPhoneNumber(users.getPhoneNumber());

        entityManager.persist(a);
    }

    @Override
    public UserEntity retrieveUser(long id) {
        return entityManager.find(UserEntity.class, id);
    }

    @Override
    public List<UserEntity> listAllUsers() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
        Root<UserEntity> variableRoot = query.from(UserEntity.class);
        query.select(variableRoot);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<UserEntity> findUserById(long id) {
        List<UserEntity> result = new ArrayList<>();

        List<UserEntity> users = entityManager.createQuery(
                entityManager.getCriteriaBuilder().createQuery(UserEntity.class)
        ).getResultList();

        for (UserEntity u:
             users) {
            if (u.getId() == id){
                result.add(u);
            }
        }
        return result;
    }


}
