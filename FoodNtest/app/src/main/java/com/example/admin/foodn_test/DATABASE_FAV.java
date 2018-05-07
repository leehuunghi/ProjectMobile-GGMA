package com.example.admin.foodn_test;

import java.util.ArrayList;

public class DATABASE_FAV {
    public String[] text1array = { "data-item1-01", "data-item1-02",
            "data-item1-03", "data-item1-04", "data-item1-01", "data-item1-02",
            "data-item1-03", "data-item1-04" };
    public String[] text2array = { "data-item2-01", "data-item2-02",
            "data-item2-03", "data-item2-04", "data-item1-01", "data-item1-02",
            "data-item1-03", "data-item1-04" };
    public Integer[] icon1array = { R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail,};
    public class DbRecord {
        public String text1;
        public String text2;
        public Integer img1;
        public DbRecord(String text1, String text2, Integer img1) {
            this.text1 = text1;
            this.text2 = text2;
            this.img1 = img1;
        }
    }//dbRecord
    // dbList is a 'database' holding a list of DbRecods:[string,string,int]
    public ArrayList<DbRecord> dbList = new ArrayList<DbRecord>();
    // populate the 'database' with data items
    public DATABASE_FAV () {
        for(int i=0; i<text1array.length; i++){
            dbList.add(new DbRecord(text1array[i], text2array[i], icon1array[i]) );
        }
    }
}// DATABASE

