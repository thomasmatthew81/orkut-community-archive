package com.thomas.orkutextract.model;

import com.google.api.services.orkut.Orkut;
import com.google.api.services.orkut.model.*;
import com.thomas.orkutextract.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 9/20/2014.
 */
public class Community extends BaseModel {
    public String name;
    public String description;
    public Date creationDate;
    public Integer orkutId;
    public Integer memberCount;
    public String photoUrl;
    public List<Member> members = new ArrayList<>();
    public List<Topic> topicList = new ArrayList<>();

    public Community(com.google.api.services.orkut.model.Community community) {
        name = community.getName();
        description = community.getDescription();
        creationDate = new Date(community.getCreationDate().getValue());
        orkutId = community.getId();
        memberCount = community.getMemberCount();
        photoUrl = community.getPhotoUrl();
        checkIfFieldsHaveNullValues(this);
    }

    public void addMembers(CommunityMembersList membersList) {
        for (CommunityMembers commMember : membersList.getItems()) {
//            Orkut.CommunityMembers.Get commMemberPlus =
            Member member = new Member(commMember.getPerson());
            members.add(member);
        }
        if (members.size() != memberCount)
            throw new ApplicationException("Retrieved member count " + members.size() + " does not match actual member count " + memberCount);
    }

    public void addTopicMessages(CommunityTopic communityTopic, CommunityMessageList communityMessageList) {
        Topic topic = new Topic(communityTopic);
        for (CommunityMessage communityMessage : communityMessageList.getItems()) {
            TopicMessage topicMessage = new TopicMessage(communityMessage);
            topic.messageList.add(topicMessage);
        }
        if (topic.noOfMessages != topic.messageList.size())
            throw new ApplicationException("Not able to retrieve all messages for Topic : " + topic.title + ". Expected " + topic.noOfMessages + ", but got only " + topic.messageList.size());
        topicList.add(topic);
    }
}
