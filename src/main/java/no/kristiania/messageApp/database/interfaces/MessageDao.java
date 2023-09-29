package no.kristiania.messageApp.database.interfaces;

import no.kristiania.messageApp.database.entities.MessageEntity;
import java.util.List;

public interface MessageDao {

    void saveMessage(MessageEntity messageEntity);

    List<MessageEntity> retrieveAllBasedOnGroupId(long id);

    MessageEntity retrieveMessage(long id);

    List<MessageEntity> listAllMessages();
}
