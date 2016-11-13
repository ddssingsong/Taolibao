package com.jhs.taolibao.entity;

/**
 * Created by dds on 2016/7/14.
 *
 * @TODO 奖励区间
 */
public class Interval {


    /**
     * effDate : /Date(1473728400000)/
     * ID : 82
     * MinPoint : 3000
     * MaxPoint : 3030
     * Type : 1
     * CreateDate : /Date(1473664883947)/
     * CreateAccountID : 1
     * Odds : 30
     * EffDate : /Date(1473728400000)/
     * AccountName : null
     * TypeName : 上证
     */

    private int ID;
    private int MinPoint;
    private int MaxPoint;
    private int Type;
    private String CreateDate;
    private int CreateAccountID;
    private int Odds;
    private String effDate;
    private String TypeName;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMinPoint() {
        return MinPoint;
    }

    public void setMinPoint(int MinPoint) {
        this.MinPoint = MinPoint;
    }

    public int getMaxPoint() {
        return MaxPoint;
    }

    public void setMaxPoint(int MaxPoint) {
        this.MaxPoint = MaxPoint;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public int getCreateAccountID() {
        return CreateAccountID;
    }

    public void setCreateAccountID(int CreateAccountID) {
        this.CreateAccountID = CreateAccountID;
    }

    public int getOdds() {
        return Odds;
    }

    public void setOdds(int Odds) {
        this.Odds = Odds;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }



    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }
}
