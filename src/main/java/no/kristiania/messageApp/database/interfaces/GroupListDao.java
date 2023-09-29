package no.kristiania.messageApp.database.interfaces;

import no.kristiania.messageApp.database.entities.GroupListEntity;

import java.util.List;

public interface GroupListDao {

    void saveGroup(GroupListEntity groupList);

    GroupListEntity retrieveGroup(long id);

    List<GroupListEntity> listAllGroups();
}
