package no.kristiania.messageApp.database.daos;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import no.kristiania.messageApp.database.entities.MessageEntity;
import no.kristiania.messageApp.database.interfaces.MessageDao;
import java.util.ArrayList;
import java.util.List;

public class JpaMessageDao implements MessageDao {
    private final EntityManager entityManager;

    @Inject
    public JpaMessageDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveMessage(MessageEntity message) {
        entityManager.persist(message);
    }

    @Override
    public List<MessageEntity> retrieveAllBasedOnGroupId(long groupId) {
        List<MessageEntity> result = new ArrayList<>();
        List<MessageEntity> me = entityManager.createQuery(
                entityManager.getCriteriaBuilder().createQuery(MessageEntity.class)
        ).getResultList();

        for (MessageEntity m:
                me) {
            if (m.getGroupId().getId() == groupId){
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public MessageEntity retrieveMessage(long id) {
        return entityManager.find(MessageEntity.class, id);
    }

    @Override
    public List<MessageEntity> listAllMessages() {
        return entityManager.createQuery(
                entityManager.getCriteriaBuilder().createQuery(MessageEntity.class)
        ).getResultList();
    }
}
