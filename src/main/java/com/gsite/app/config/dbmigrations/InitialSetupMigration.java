package com.gsite.app.config.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.util.Date;

@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(author = "initiator", id = "01-addMoreWebsites", order = "01")
    public void addWebsites(DB db) {
        DBCollection templateCollection = db.getCollection("website");
        templateCollection.createIndex("name");
        templateCollection.insert(BasicDBObjectBuilder
            .start("_id", "website-1")
            .add("name", "The basic one")
            .add("template", "basic-template")
            .add("domain", "webone")
            .add("user_id", "user-1")
            .add("is_paid", true)
            .add("created", new Date())
            .get());
        templateCollection.insert(BasicDBObjectBuilder
            .start("_id", "website-2")
            .add("name", "The latest one")
            .add("template", "latest-template")
            .add("domain", "webtwo")
            .add("user_id", "user-1")
            .add("is_paid", true)
            .add("created", new Date())
            .get());
        templateCollection.insert(BasicDBObjectBuilder
            .start("_id", "website-3")
            .add("name", "The beauty")
            .add("template", "beautiful-template")
            .add("domain", "webthree")
            .add("user_id", "user-1")
            .add("is_paid", true)
            .add("created", new Date())
            .get());

    }
}
