package org.julienLempereur.visualGame;

public class CommonClass {
    private static final CommonClass instance = new CommonClass();

    private String uuid;

    private CommonClass(){};

    public static CommonClass getInstance(){
        return instance;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public String getUuid(){
        return uuid;
    }
}
