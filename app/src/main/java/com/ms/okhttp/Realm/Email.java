package com.ms.okhttp.Realm;

import io.realm.RealmObject;

public class Email extends RealmObject {
    public String address;
    public boolean active;
}