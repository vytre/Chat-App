package no.kristiania.messageApp.database.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users_group")
public class UserGroupEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private GroupListEntity groupId;

    //GETTERS AND SETTERS
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public GroupListEntity getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupListEntity groupId) {
        this.groupId = groupId;
    }
}
