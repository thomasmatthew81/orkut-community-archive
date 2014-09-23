package com.thomas.orkutextract.model;

import com.google.api.services.orkut.model.CommunityMessage;

import java.util.Date;

/**
 * Created by Thomas on 9/21/2014.
 */
public class TopicMessage extends BaseModel{

    public Member author;
    public Date timeAdded;
    public String messageBody;
//    public String subject;

    public TopicMessage(CommunityMessage communityMessage){
        author = new Member(communityMessage.getAuthor());
        timeAdded = new Date(communityMessage.getAddedDate().getValue());
        messageBody = communityMessage.getBody();
//        subject = communityMessage.getSubject();
        checkIfFieldsHaveNullValues(this);
    }
}
