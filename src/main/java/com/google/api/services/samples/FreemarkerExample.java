package com.google.api.services.samples;

import com.google.api.client.util.DateTime;
import com.google.api.services.orkut.model.CommunityMessage;
import com.google.api.services.orkut.model.CommunityTopic;
import com.google.api.services.orkut.model.OrkutAuthorResource;
import com.thomas.orkutextract.model.Community;
import com.thomas.orkutextract.model.Member;
import com.thomas.orkutextract.model.Topic;
import com.thomas.orkutextract.model.TopicMessage;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tx00375 on 9/22/2014.
 */
public class FreemarkerExample {
    public void test() throws IOException, TemplateException{
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), "/");
        Template template = configuration.getTemplate("orkut_community_content.ftl");

        com.google.api.services.orkut.model.Community orkutCommunity = new com.google.api.services.orkut.model.Community();
        orkutCommunity.setName("Vaalikal");
        orkutCommunity.setDescription("Vaalikal");
        orkutCommunity.setCreationDate(new DateTime(System.currentTimeMillis()));
        orkutCommunity.setId(123456);
        orkutCommunity.setMemberCount(6);
        orkutCommunity.setPhotoUrl("ABC");

        Community community = new Community(orkutCommunity);
        OrkutAuthorResource p1 = new OrkutAuthorResource();
        p1.setDisplayName("Thomas Mathew");
        p1.setImage(new OrkutAuthorResource.Image());
        Member m1 = new Member(p1);
        p1.setDisplayName("Kiran Kumar");
        Member m2 = new Member(p1);
        p1.setDisplayName("Aswani");
        Member m3 = new Member(p1);
        community.members = Arrays.asList(m1,m2,m3);

        CommunityTopic communityTopic = new CommunityTopic();
        communityTopic.setAuthor(p1);
        communityTopic.setTitle("Testing");
        communityTopic.setNumberOfReplies(2);
        Topic topic = new Topic(communityTopic);

        CommunityMessage c1 = new CommunityMessage();
        c1.setAuthor(p1);
        c1.setBody("Hello Test");
        c1.setAddedDate(new DateTime(System.currentTimeMillis()));
        TopicMessage t1 = new TopicMessage(c1);
        CommunityMessage c2 = new CommunityMessage();
        c2.setAuthor(p1);
        c2.setBody("Looks Good");
        c2.setAddedDate(new DateTime(System.currentTimeMillis()));
        TopicMessage t2 = new TopicMessage(c2);

        topic.messageList.add(t1);
        topic.messageList.add(t2);
        community.topicList.add(topic);

        System.out.println(System.getProperty("java.io.tmpdir"));
        Writer out = new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("java.io.tmpdir"), community.name+".html")));
        BeansWrapper beansWrapper = new BeansWrapper();
        beansWrapper.setExposeFields(true);
        template.process(community, out,beansWrapper);
        out.flush();
    }
    public static void main(String[] args) throws IOException, TemplateException {
        FreemarkerExample ex = new FreemarkerExample();
        ex.test();
    }
}
