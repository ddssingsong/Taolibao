package com.jhs.taolibao.entity;

/**
 * Created by dds on 2016/7/14.
 *
 * @TODO 今天已经猜的数据，如果没猜就没有
 */
public class TodayGuess {


    /**
     * ID : 4
     * UserID : 1184
     * GamePoint : 6806
     * CreateDate : /Date(1468479733447)/
     * Type : 4
     * TypeName : 中小板
     */

    private int ID;
    private int UserID;
    private int GamePoint;
    private String CreateDate;
    private int Type;
    private String TypeName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getGamePoint() {
        return GamePoint;
    }

    public void setGamePoint(int GamePoint) {
        this.GamePoint = GamePoint;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }
}
