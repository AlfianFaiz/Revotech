package com.alfianfaiz.app.revotech.DBClass;

/**
 * Created by USRER on 3/20/2017.
 */

public class Constants {

    static final String TB_ATTACH="attach_TB";

    static final String ATTACH_ID ="attach_id";
    static final String VISIT_ID ="visit_id";
    static final String IMAGE_URL ="image_url";
    static final String ATM_ID ="atm_id";
    static final String HUB = "hub";
    static final String TANGGAL ="tanggal";

    //DB PROPERTIES
    static final String DB_NAME="alfian_DB";
    static final int DB_VERSION='1';
    //CREATE TABLE
    static final String CREATE_TB_ATTACH = "CREATE TABLE attach_TB(attach_id TEXT PRIMARY KEY NOT NULL," +
            " visit_id TEXT NOT NULL," +
            " image_url TEXT NOT NULL," +
            " atm_id TEXT NOT NULL," +
            " hub TEXT NOT NULL," +
            " tanggal TEXT NOT NULL);";

}
