package com.jhs.taolibao.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by dds on 2016/7/15.
 *
 * @TODO
 */
public class GuessHistory {


    private int TotalCount;
    private List<DirectoryResultsEntity> DirectoryResults;
    public static GuessHistory objectFromData(String str) {
        return new Gson().fromJson(str, GuessHistory.class);
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public List<DirectoryResultsEntity> getDirectoryResults() {
        return DirectoryResults;
    }

    public void setDirectoryResults(List<DirectoryResultsEntity> DirectoryResults) {
        this.DirectoryResults = DirectoryResults;
    }

    public static class DirectoryResultsEntity {
        private int ID;
        private int UserID;
        private String GameInfoID;
        private int Amount;
        private int Type;
        private String CreateDate;
        private String Notres;
        private String Mobile;
        private int GamePoint;
        private int EndPoint;
        private String DataStr;

        private List<GameListEntity> GameList;

        public static DirectoryResultsEntity objectFromData(String str) {

            return new Gson().fromJson(str, DirectoryResultsEntity.class);
        }

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

        public String getGameInfoID() {
            return GameInfoID;
        }

        public void setGameInfoID(String GameInfoID) {
            this.GameInfoID = GameInfoID;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
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

        public String getNotres() {
            return Notres;
        }

        public void setNotres(String Notres) {
            this.Notres = Notres;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public int getGamePoint() {
            return GamePoint;
        }

        public void setGamePoint(int GamePoint) {
            this.GamePoint = GamePoint;
        }

        public int getEndPoint() {
            return EndPoint;
        }

        public void setEndPoint(int EndPoint) {
            this.EndPoint = EndPoint;
        }


        public String getDataStr() {
            return DataStr;
        }

        public void setDataStr(String DataStr) {
            this.DataStr = DataStr;
        }

        public List<GameListEntity> getGameList() {
            return GameList;
        }

        public void setGameList(List<GameListEntity> GameList) {
            this.GameList = GameList;
        }

        public static class GameListEntity {
            private int ID;
            private int UserID;
            private int GamePoint;
            private String CreateDate;
            private double EndPoint;
            private int Type;
            private int Amount;
            private String TypeName;
            private int MinPoint;
            private int MaxPoint;

            public static GameListEntity objectFromData(String str) {

                return new Gson().fromJson(str, GameListEntity.class);
            }

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

            public double getEndPoint() {
                return EndPoint;
            }

            public void setEndPoint(double EndPoint) {
                this.EndPoint = EndPoint;
            }

            public int getType() {
                return Type;
            }

            public void setType(int Type) {
                this.Type = Type;
            }


            public int getAmount() {
                return Amount;
            }

            public void setAmount(int Amount) {
                this.Amount = Amount;
            }

            public String getTypeName() {
                return TypeName;
            }

            public void setTypeName(String TypeName) {
                this.TypeName = TypeName;
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
        }
    }
}
