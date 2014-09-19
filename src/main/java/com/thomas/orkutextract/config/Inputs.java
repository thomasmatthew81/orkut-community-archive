package com.thomas.orkutextract.config;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tx00375 on 9/19/2014.
 */
public interface Inputs {
    // Subdirectory under User home directory to store user credentials
    public static final String USER_CRED_DIR = ".store/orkut_community_archive";
    //Application Name
    public static final String APP_NAME = "OrkutCommunityArchive";
    //List of Communities to Archive
    public static final List<String> COMMUNITY_LIST = Arrays.asList("");
}
