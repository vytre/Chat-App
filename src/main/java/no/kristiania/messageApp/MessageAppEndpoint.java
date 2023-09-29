package no.kristiania.messageApp;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.messageApp.database.entities.*;
import no.kristiania.messageApp.database.interfaces.*;
import java.util.List;

@Path("/")
public class MessageAppEndpoint {

    @Inject
    private UserDao userDao;

    @Inject
    private MessageDao messageDao;

    @Inject
    private GroupListDao groupListDao;

    @Inject
    private UserGroupDao userGroupDao;

    // GET
    @Path("/user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> listAllUsers() {
        return userDao.listAllUsers();
    }

    @GET
    @Path("/message")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MessageEntity> listMessages() {
        return messageDao.listAllMessages();
    }

    @GET
    @Path("/group")
    @Produces(MediaType.APPLICATION_JSON)
    public List<GroupListEntity> listAllGroups() {
        return groupListDao.listAllGroups();
    }

    @GET
    @Path("/usergroup")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserGroupEntity> listAllUserGroups() {
        return userGroupDao.listAllUserGroups();
    }

    // Post
    @Path("/user")
    @POST
    public void addUser(UserEntity user) {
        userDao.saveUser(user);
    }

    @Path("/user/{userId}")
    @POST
    public void update(@PathParam("userId") long userId, UserEntity user) {
        userDao.updateUser(user);
    }

    @POST
    @Path("/group")
    public void createNewGroup(GroupListEntity group) {
        groupListDao.saveGroup(group);
    }

    @POST
    @Path("/message")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addMessage(MessageEntity message) {
        messageDao.saveMessage(message);
    }

    @POST
    @Path("/usergroup")
    @Produces(MediaType.APPLICATION_JSON)
    public void listAllUserGroupsBasedOnUserId(UserGroupEntity userGroupEntity){
        userGroupDao.saveUserGroup(userGroupEntity);
    }

    // Get By ID
    @GET
    @Path("/usergroup/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserGroupEntity> listAllUserGroupsBasedOnUserId(@PathParam("userId")long userId){
        return userGroupDao.retrieveByUserId(userId);
    }

    @GET
    @Path("/usergroup/group/{groupId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserGroupEntity> listAllUserGroupsBasedOnGroupId(@PathParam("groupId")long groupId) {
        return userGroupDao.retrieveByGroupId(groupId);
    }

    @GET
    @Path("/message/{groupId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MessageEntity> listAllMessageGroupsBasedOnGroupId(@PathParam("groupId")long groupId){
        return messageDao.retrieveAllBasedOnGroupId(groupId);
    }

    @GET
    @Path("/user/{userId}")
    public List<UserEntity> showUserById(@PathParam("userId")long id) {
        return userDao.findUserById(id);
    }
}