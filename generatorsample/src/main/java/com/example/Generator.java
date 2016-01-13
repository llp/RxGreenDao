package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1, "de.greenrobot.dao.sample.dao");//生成的Dao的包名
        Entity note = schema.addEntity("Note");
        note.setTableName("note");
        note.addIdProperty().autoincrement().primaryKey();
        note.addStringProperty("text").notNull();

        new DaoGenerator().generateAll(schema, "./app/src/main/java/");//生成的文件的路径
    }
}
