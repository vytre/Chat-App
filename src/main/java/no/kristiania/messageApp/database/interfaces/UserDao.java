package no.kristiania.messageApp.database.interfaces;

import no.kristiania.messageApp.database.entities.UserEntity;
import java.util.List;

public interface UserDao {
    void saveUser(UserEntity users);

    void updateUser(UserEntity users);

    UserEntity retrieveUser(long id);

    List<UserEntity> listAllUsers();

    List<UserEntity> findUserById(long id);
}
