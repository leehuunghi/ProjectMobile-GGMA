package com.example.admin.foodn_test;

import java.util.ArrayList;

public class DATABASEcomment {
    public String[] tenUser = { "data-item1-01", "data-item1-02",
            "data-item1-03", "data-item1-04", "data-item1-01", "data-item1-02",
            "data-item1-03", "data-item1-04" };
    public String[] comment = { "data-item2-01", "data-item2-02",
            "data-item2-03", "data-item2-04", "data-item1-01", "data-item1-02",
            "data-item1-03", "data-item1-04" };
    public Integer[] avt = { R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,};
    public class DbRecord {
        public String tenUser;
        public String comment;
        public Integer avt;
        public DbRecord(String text1, String text2, Integer img1) {
            this.tenUser = text1;
            this.comment = text2;
            this.avt = img1;
        }
    }//dbRecord
    // dbList is a 'database' holding a list of DbRecods:[string,string,int]
    public ArrayList<DbRecord> dbList = new ArrayList<DbRecord>();
    // populate the 'database' with data items
    public DATABASEcomment () {
        for(int i=0; i<tenUser.length; i++){
            dbList.add(new DbRecord(tenUser[i], comment[i], avt[i]) );
        }
    }
}// DATABASE