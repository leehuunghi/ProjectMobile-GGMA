package com.example.admin.config;

/**
 * Created by Windows 10 on 5/3/2018.
 */

public class Configuaration {
    public static String SERVER_URL="http://192.168.1.227/foodn/WebServiceFoodN.asmx";
    public static String NAME_SPACE="http://tempuri.org/";
    public static String METHOD_GET_LIST_FOOD_STORE = "GetListFoodStore";
    public static String METHOD_SIGN_UP="signUp";
    public static String METHOD_LOGIN = "login";
    public static String METHOD_WRITE_FAV = "WriteFav";
    public static String METHOD_SEARCH_NAME = "SearchName";
    public static String METHOD_REMOVE_FAV = "RemoveFav";
    public static String METHOD_CHECK_FAV = "CheckFav";
    public static String METHOD_GET_LIST_FAV_STORE = "GetListFavStore";
    public static String METHOD_GET_LIST_POSITION = "GetListPointStore";
    public static String METHOD_GET_STORE_BY_ID = "GetStoreByID";
    public static String METHOD_GET_USER = "GetUser";
    public static String METHOD_UPDATE_USER = "UpdateUser";
    public static String METHOD_ADD_CMT = "AddCmt";
    public static String METHOD_ADD_RATE = "AddRate";
    public static String METHOD_CHECK_RATE = "CheckRate";
    public static String PARAMETER_IDUser="iduser";
    public static String PARAMETER_IDUserF="iduserf";
    public static String PARAMETER_IDStore="idstore";
    public static String PARAMETER_IDFStore="idfstore";
    public static String PARAMETER_ID="id";
    public static String PARAMETER_SDT="username";
    public static String PARAMETER_NAME="name";
    public static String PARAMETER_PHONE="phone";
    public static String PARAMETER_DATE="date";
    public static String PARAMETER_SEX="sex";
    public static String PARAMETER_PASSWORD="password";
    public static String PARAMETER_AVATAR="avatar";
    public static String PARAMETER_hoten="displayName";
    public static String PARAMETER_matKhau="password";
    public static String PARAMETER_gioiTinh="sex";
    public static String PARAMETER_ngaySinh="date";
    public static String PARAMETER_CMT="cmt";
    public static String PARAMETER_RATE="rate";
    public static String SOAP_ACTION_SIGN_UP=NAME_SPACE+METHOD_SIGN_UP;
    public static String SOAP_ACTION_LOGIN=NAME_SPACE+METHOD_LOGIN;
    public static String SOAP_ACTION_GET_LIST_POSITION=NAME_SPACE+METHOD_GET_LIST_POSITION;
    public static String SOAP_ACTION_GET_STORE_BY_ID=NAME_SPACE+METHOD_GET_STORE_BY_ID;
    public static String SOAP_ACTION_GET_FOOD_BY_IDSTORE=NAME_SPACE+METHOD_GET_LIST_FOOD_STORE;
    public static String SOAP_ACTION_WRITE_FAV=NAME_SPACE+METHOD_WRITE_FAV;
    public static String SOAP_ACTION_SEARCH_BY_NAME=NAME_SPACE+METHOD_SEARCH_NAME;
    public static String SOAP_ACTION_REMOVE_FAV=NAME_SPACE+METHOD_REMOVE_FAV;
    public static String SOAP_ACTION_CHECK_FAV=NAME_SPACE+METHOD_CHECK_FAV;
    public static String SOAP_ACTION_GET_LIST_FAV_STORE=NAME_SPACE+METHOD_GET_LIST_FAV_STORE;
    public static String SOAP_ACTION_GET_USER=NAME_SPACE+METHOD_GET_USER;
    public static String SOAP_ACTION_UPDATE_USER=NAME_SPACE+METHOD_UPDATE_USER;
    public static String SOAP_ACTION_ADD_CMT=NAME_SPACE+METHOD_ADD_CMT;
    public static String SOAP_ACTION_ADD_RATE=NAME_SPACE+METHOD_ADD_RATE;
    public static String SOAP_ACTION_CHECK_RATE=NAME_SPACE+METHOD_CHECK_RATE;
    public static Integer SpaceLimit = 3;
}
