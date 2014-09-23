package com.thomas.orkutextract.action;

import com.google.api.services.orkut.Orkut;
import com.google.api.services.orkut.model.*;
import com.google.api.services.orkut.model.Community;
import com.thomas.orkutextract.authorize.GoogleLoginHandler;
import com.thomas.orkutextract.config.Inputs;
import com.thomas.orkutextract.model.*;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tx00375 on 9/19/2014.
 */
public class OrkutCommunityArchive {

    private GoogleLoginHandler googleLoginHandler = GoogleLoginHandler.getInstance();
    //Global instance or Orkut
    private Orkut orkut;

    private void loginAndSetupGlobalOrkutInstance() {
        orkut = new Orkut.Builder(GoogleLoginHandler.HTTP_TRANSPORT, GoogleLoginHandler.JSON_FACTORY, googleLoginHandler.getCredential()).setApplicationName(
                Inputs.APP_NAME).build();
    }

    private List<com.thomas.orkutextract.model.Community> fetchAllCommunitiesToBeExported() throws IOException {
        List<com.thomas.orkutextract.model.Community> communityModelList = new ArrayList<>();
        Orkut.Communities.List communityListHandle = orkut.communities().list("me");
        communityListHandle.setMaxResults(Inputs.MAX_RESULTS);
        communityListHandle.setFields("items(id,name)");
        //Default set of fileds are not enough
        CommunityList myCommunityList = communityListHandle.execute();
        for(Community community: myCommunityList.getItems()){
            if(Inputs.COMMUNITY_LIST.contains(community.getName())){
                communityModelList.add(fetchCommunityDetailsAndTopics(community));
            }
        }
        return communityModelList;
    }

    private com.thomas.orkutextract.model.Community fetchCommunityDetailsAndTopics(Community community) throws IOException {
        //Default set of fields fetched by CommunityList is not enough. Need to make API call on community to get additional details
        Orkut.Communities.Get  communityPlusHandle = orkut.communities().get(community.getId());
        Community communityPlus = communityPlusHandle.execute();
        //Start construction of application model for this community;
        com.thomas.orkutextract.model.Community communityModel = new com.thomas.orkutextract.model.Community(communityPlus);

        Orkut.CommunityMembers.List commMembersHandle = orkut.communityMembers().list(community.getId());
        commMembersHandle.setMaxResults(Inputs.MAX_RESULTS);
        CommunityMembersList commMembers = commMembersHandle.execute();
        communityModel.addMembers(commMembers);

        Orkut.CommunityTopics.List commTopicsHandle = orkut.communityTopics().list(community.getId());
        commTopicsHandle.setMaxResults(Inputs.MAX_RESULTS);
        CommunityTopicList commTopics = commTopicsHandle.execute();
        for(CommunityTopic topic: commTopics.getItems()){
            Orkut.CommunityMessages.List topicMsgListHandle = orkut.communityMessages().list(community.getId(),topic.getId());
            topicMsgListHandle.setMaxResults(Inputs.MAX_RESULTS);
            CommunityMessageList topicMsgList = topicMsgListHandle.execute();
            communityModel.addTopicMessages(topic,topicMsgList);
        }
        return communityModel;
    }

    public void renderView(List<com.thomas.orkutextract.model.Community> communityModelList) throws IOException, TemplateException {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(),"/");
        Template template = configuration.getTemplate("orkut_community_content.ftl");

        for(com.thomas.orkutextract.model.Community communityModel:communityModelList){
            System.out.println(System.getProperty("java.io.tmpdir"));
            Writer out = new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("java.io.tmpdir"), communityModel.name+".html")));
            //This is to enable freemarker ability to read public fields without having to define getter methods
            BeansWrapper beansWrapper = new BeansWrapper();
            beansWrapper.setExposeFields(true);
            template.process(communityModel,out,beansWrapper);
            out.flush();
        }
    }
    public static void main(String[] args) {
        OrkutCommunityArchive orkutCommunityArchive = new OrkutCommunityArchive();
        try {
            orkutCommunityArchive.loginAndSetupGlobalOrkutInstance();
            List<com.thomas.orkutextract.model.Community> communityModelList = orkutCommunityArchive.fetchAllCommunitiesToBeExported();
            orkutCommunityArchive.renderView(communityModelList);
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}
