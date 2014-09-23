package com.thomas.orkutextract.model;

import com.google.api.services.orkut.model.OrkutActivitypersonResource;
import com.google.api.services.orkut.model.OrkutAuthorResource;

/**
 * Created by Thomas on 9/20/2014.
 */
public class Member extends BaseModel{
    public String givenName;
    public String familyName;
    public String displayName;
    public String gender;
    public String photoUrl;

    public Member(OrkutActivitypersonResource orkutActivitypersonResource){
        givenName = orkutActivitypersonResource.getName().getGivenName();
        familyName = orkutActivitypersonResource.getName().getFamilyName();
        displayName = givenName.concat(" ").concat(familyName);
        gender = orkutActivitypersonResource.getGender();
        photoUrl = orkutActivitypersonResource.getImage().getUrl();
        checkIfFieldsHaveNullValues(this);
    }

    public Member(OrkutAuthorResource  orkutAuthorResource){
        displayName = orkutAuthorResource.getDisplayName();
        photoUrl = orkutAuthorResource.getImage().getUrl();
    }

}
