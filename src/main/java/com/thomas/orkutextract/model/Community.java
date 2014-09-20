package com.thomas.orkutextract.model;

import com.google.api.services.orkut.model.CommunityMembers;
import com.google.api.services.orkut.model.CommunityMembersList;
import com.thomas.orkutextract.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 9/20/2014.
 */
public class Community {
    String name;
    String description;
    Date creationDate;
    Integer orkutId;
    Integer memberCount;
    String photoUrl;
    List<Member> members = new ArrayList<>();

    public Community(com.google.api.services.orkut.model.Community community){
        name = community.getName();
        description = community.getDescription();
//        creationDate = new Date(community.getCreationDate().getValue());
        orkutId = community.getId();
        memberCount = community.getMemberCount();
        photoUrl = community.getPhotoUrl();
    }

    public void addMembers(CommunityMembersList membersList){
        for(CommunityMembers commMember: membersList.getItems()){
            Member member = new Member(commMember.getPerson());
            members.add(member);
        }
        if(members.size() != memberCount)
            throw new ApplicationException("Retrieved member count "+ members.size() + " does not match actual member count " + memberCount);
    }
}
