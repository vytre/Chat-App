package no.kristiania.messageApp.database.interfaces;

import no.kristiania.messageApp.database.entities.UserGroupEntity;

import java.util.List;

public interface UserGroupDao {
    void saveUserGroup(UserGroupEntity userGroupEntity);

    List<UserGroupEntity> retrieveByUserId(long id);

    List<UserGroupEntity> listAllUserGroups();

    List<UserGroupEntity> retrieveByGroupId(long groupId);

    UserGroupEntity retrieveUserGroup(long id);
}
