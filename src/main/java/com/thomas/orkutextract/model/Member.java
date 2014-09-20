package com.thomas.orkutextract.model;

import com.google.api.services.orkut.model.OrkutActivitypersonResource;

/**
 * Created by Thomas on 9/20/2014.
 */
public class Member {
    String givenName;
    String familyName;
    String displayName;
    String gender;
    String birthday; //YYYY-MM-DD
    String photoUrl;

    public Member(OrkutActivitypersonResource orkutActivitypersonResource){
        givenName = orkutActivitypersonResource.getName().getGivenName();
        familyName = orkutActivitypersonResource.getName().getFamilyName();
        displayName = givenName.concat(" ").concat(familyName);
        gender = orkutActivitypersonResource.getGender();
        birthday = orkutActivitypersonResource.getBirthday();
        photoUrl = orkutActivitypersonResource.getImage().getUrl();
    }

}
