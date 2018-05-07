package com.example.ldo.barcodeproject;

import java.util.Date;

public class ScanBarCode {
    private String code;
    private String nameUser;
    private Date dtCreate;
    ScanBarCode(String code,String nameUser,Date dtCreate)
    {
        this.code=code;
        this.nameUser=nameUser;
        this.dtCreate=dtCreate;
    }
    public String getCode()
    {
        return code;
    }
    public String getNameUser()
    {
        return nameUser;
    }
    public Date getDtCreate()
    {
        return dtCreate;
    }
    public void setCode(String code)
    {
        this.code=code;
    }
    public void setNameUser(String nameUser)
    {
        this.nameUser=nameUser;
    }
    public void setDtCreate(Date dtCreate)
    {
        this.dtCreate=dtCreate;
    }
    @Override
    public String toString() {
        return this.code + " : " + this.nameUser + " : " + dtCreate.toString();
    }
}