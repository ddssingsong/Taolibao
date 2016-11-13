package com.jhs.taolibao.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by dds on 2016/7/27.
 *
 * @TODO
 */
public class Jingu {




    private int code;
    private String Errinfo;

    private List<DataEntity> data;
    public static Jingu objectFromData(String str) {
        return new Gson().fromJson(str, Jingu.class);
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String Errinfo) {
        this.Errinfo = Errinfo;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        private int ID;
        private String StockName;
        private String StockCode;
        private String EffDate;
        private String CreateDate;
        private Object Notes;
        private String EffStr;
        private String CrteateStr;

        public static DataEntity objectFromData(String str) {

            return new Gson().fromJson(str, DataEntity.class);
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getStockName() {
            return StockName;
        }

        public void setStockName(String StockName) {
            this.StockName = StockName;
        }

        public String getStockCode() {
            return StockCode;
        }

        public void setStockCode(String StockCode) {
            this.StockCode = StockCode;
        }

        public String getEffDate() {
            return EffDate;
        }

        public void setEffDate(String EffDate) {
            this.EffDate = EffDate;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public Object getNotes() {
            return Notes;
        }

        public void setNotes(Object Notes) {
            this.Notes = Notes;
        }

        public String getEffStr() {
            return EffStr;
        }

        public void setEffStr(String EffStr) {
            this.EffStr = EffStr;
        }

        public String getCrteateStr() {
            return CrteateStr;
        }

        public void setCrteateStr(String CrteateStr) {
            this.CrteateStr = CrteateStr;
        }
    }
}
