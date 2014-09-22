package com.thomas.orkutextract.model;

import com.google.api.services.orkut.model.CommunityTopic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 9/21/2014.
 */
public class Topic extends BaseModel{

    public String title;
    public Member author;
    public Integer noOfMessages;
    public List<TopicMessage> messageList = new ArrayList<>();

    public Topic(CommunityTopic communityTopic){
        title = communityTopic.getTitle();
        author = new Member(communityTopic.getAuthor());
        noOfMessages = communityTopic.getNumberOfReplies();
        checkIfFieldsHaveNullValues(this);
    }
}
