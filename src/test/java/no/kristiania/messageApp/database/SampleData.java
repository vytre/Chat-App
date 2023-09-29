package no.kristiania.messageApp.database;

import no.kristiania.messageApp.database.entities.*;

import java.util.Random;

public class SampleData {

    private static final Random random = new Random();

    static UserEntity sampleUser() {
        var user = new UserEntity();
        user.setName(pickOne(
                "Tom",
                "Rune ",
                "Hans"
        ));
        user.setEmail(pickOne(
                "MasterChief235325325@messageApp.no",
                "noSpamHereOK25353252@messageApp.no",
                "throwAway2352532@messageApp.no"
        ));
        user.setPhoneNumber(Long.parseLong(pickOne(
                "42069666",
                "92299229",
                "96969696"
        )));

        return user;
    }

    public static UserGroupEntity sampleUserGroup() {
        var group = sampleGroup();
        group.setId(69);

        var user = sampleUser();
        user.setId(69);

        var userGroup =  new UserGroupEntity();
        userGroup.setGroupId(group);
        userGroup.setUserId(user);
        return userGroup;


    }

    public static MessageEntity sampleMessage() {
        var user = sampleUser();
        user.setId(69);

        var group = sampleGroup();
        group.setId(69);

        var message = new MessageEntity();
        message.setBody(pickOne(
                "Hei på deg :):)",
                "Hva synes du om testene våre?",
                "Jeg er litt sliten, tror jeg tar en pause"
        ));

        message.setUserId(user);
        message.setGroupId(group);
        return message;
    }


    public static GroupListEntity sampleGroup() {
        var group = new GroupListEntity();
        group.setName(pickOne(
                "GYMGUTTAAAA",
                "Nerd chatten",
                "pgr209 eksamen chat"
        ));
        group.setId(0);
        return group;
    }
/*
    public static MessageGroupEntity sampleMessageGroup() {
        var group = sampleGroup();
        group.setId(69);

        var message = sampleMessage();
        message.setId(69);

        var messageGroup =  new MessageGroupEntity();
        messageGroup.setGroupId(group);
        messageGroup.setMessageId(message);

        return messageGroup;
    }

 */

    private static String pickOne(String... alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }

}
