package com.thomas.orkutextract.model;

import com.thomas.orkutextract.exception.ApplicationException;

import java.lang.reflect.Field;

/**
 * Created by Thomas on 9/21/2014.
 */
public abstract class BaseModel {

    public void checkIfFieldsHaveNullValues(Object object){
        Field[] fields = object.getClass().getFields();
        for (int i = 0; i < fields.length ; i++) {
            try {
                if(fields[i].get(object) == null) {
                    throw new ApplicationException(fields[i].getName() + " cannot be null");
                }
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
    }
}
