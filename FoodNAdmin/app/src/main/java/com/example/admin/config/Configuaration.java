package com.example.admin.config;

/**
 * Created by Windows 10 on 5/3/2018.
 */

public class Configuaration {
    public static String SERVER_URL="http://192.168.1.2/foodn/WebServiceFoodN.asmx";
    public static String NAME_SPACE="http://tempuri.org/";
    public static String METHOD_SIGN_UP="signUp";
    public static String METHOD_LOGIN = "login";
    public static String METHOD_GET_LIST_POSITION = "GetListPointStore";
    public static String PARAMETER_SDT="username";
    public static String PARAMETER_hoten="displayName";
    public static String PARAMETER_matKhau="password";
    public static String SOAP_ACTION_SIGN_UP=NAME_SPACE+METHOD_SIGN_UP;
    public static String SOAP_ACTION_LOGIN=NAME_SPACE+METHOD_LOGIN;
    public static String SOAP_ACTION_GET_LIST_POSITION=NAME_SPACE+METHOD_GET_LIST_POSITION;
    public static Integer SpaceLimit = 3;

}
