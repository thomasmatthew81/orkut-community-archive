package com.thomas.orkutextract.action;

import com.google.api.services.orkut.Orkut;
import com.google.api.services.orkut.model.Community;
import com.google.api.services.orkut.model.CommunityList;
import com.thomas.orkutextract.authorize.GoogleLoginHandler;
import com.thomas.orkutextract.config.Inputs;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by tx00375 on 9/19/2014.
 */
public class OrkutCommunityArchive {

    private GoogleLoginHandler googleLoginHandler = GoogleLoginHandler.getInstance();
    //Global instance or Orkut
    private Orkut orkut;

    private void loginAndSetupGlobalOrkutInstance() throws GeneralSecurityException, IOException {
        orkut = new Orkut.Builder(googleLoginHandler.getHttpTransport(), GoogleLoginHandler.JSON_FACTORY, googleLoginHandler.getCredential()).setApplicationName(
                Inputs.APP_NAME).build();
    }

    private void fetchAllCommunitiesToBeExported() throws IOException {
        Orkut.Communities.List communityListHandle = orkut.communities().list("me");
        CommunityList myCommunityList = communityListHandle.execute();
        for(Community community: myCommunityList.getItems()){
            if(Inputs.COMMUNITY_LIST.contains(community.getName())){
                fetchCommunityDetailsAndTopics(community);
            }
        }

    }

    private void fetchCommunityDetailsAndTopics(Community community){
        Orkut.CommunityTopics.List commTopicsHandle = orkut.communityTopics().list(community.getId());
        CommunityTopicList commTopics = commTopicsHandle.execute();
        for(CommunityTopic topic: commTopics.getItems()){
            Orkut.CommunityMessages.List topicMsgListHandle = orkut.communityMessages().list(community.getId(),topic.getId());
            CommunityMessageList topicMsgList = topicMsgListHandle.execute();
        }
        
    }
    public static void main(String[] args) {
        OrkutCommunityArchive orkutCommunityArchive = new OrkutCommunityArchive();
        try {
            orkutCommunityArchive.loginAndSetupGlobalOrkutInstance();
        } catch (GeneralSecurityException| IOException e ) {
            e.printStackTrace();
        }
    }
}
