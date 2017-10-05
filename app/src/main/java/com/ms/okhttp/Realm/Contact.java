package com.ms.okhttp.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Contact extends RealmObject {
    public String name;
    public RealmList<Email> emails;
}