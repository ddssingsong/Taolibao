package com.jhs.taolibao.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Fund implements Serializable {

	/**
	 * auto generated
	 */
	private static final long serialVersionUID = 8552718442882797674L;

	private int id;

	private String fundCode; // 母基金代码
	private String fundName; // 母基金名称
	private double fundPrice; // 母基金实时价格
	private double fundConvertPrice; // 母基金折溢价率
	private double matchedValue; // 拟合价格
	private double fundPosition; // 母基金前一日仓位
	private double fundRatio; // 母基金涨幅跌
	private double fundNetValue; // 昨日母基净值
	private Double FundNeedRatio; // 母基金需跌幅

	private String fundACode; // A基金代码
	private String fundAName; // A基金名称
	private double fundAPrice; // A基金价格
	private double fundARealPrice; // A基金实时价格
	private double FundAConvertPrice; // A基金折溢价率
	private double FundARatio; // A涨跌幅
	private double fundARate; // A的规定利率
	private double fundABaseRate; // A的基本倍率
	private double fundAFloatRate; // A的浮动利率

	private String fundBCode; // B基金代码
	private String fundBName; // B基金名称
	private double fundBPrice; // B基金价格
	private double fundBRealPrice; // B基金实时价格
	private double fundBConvertPrice;// B基金折溢价率
	private double FundBRatio; // B涨跌幅
	private double NetValueLever;

	private String indexName;// 指数名称
	private String indexCode; // 指数代码
	private double indexRatio; // 指数涨跌幅
	private double Multiple; // 倍率

	private String realpath;

	private String daypath;

	private String weekpath;

	private String monthpath;

	private String convertDate; // 定折日的描述

	private String standardConvertDate; // 标准定折日

	private int isShow; // 是否显示在套利宝界面 1显示 0 不显示

	private String buyInfo; // 申购费
	private String saleInfo;// 赎回费

	private String realtradeNum; // 实时成交量

	public Double getFundNeedRatio() {
		return FundNeedRatio;
	}

	public void setFundNeedRatio(Double fundNeedRatio) {
		FundNeedRatio = fundNeedRatio;
	}

	public void setJsonObject(JSONObject json) throws JSONException {

		if (!json.isNull("ID")) {
			try {
				this.setId(json.getInt("ID"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundCode")) {
			this.setFundCode(json.getString("FundCode"));
		}
		if (!json.isNull("FundName")) {
			this.setFundName(json.getString("FundName"));
		}
		if (!json.isNull("IndexName")) {
			this.setIndexName(json.getString("IndexName"));
		}
		if (!json.isNull("IndexCode")) {
			this.setIndexCode(json.getString("IndexCode"));
		}
		if (!json.isNull("FundACode")) {
			this.setFundACode(json.getString("FundACode"));
		}
		if (!json.isNull("FundAName")) {
			this.setFundAName(json.getString("FundAName"));
		}
		if (!json.isNull("FundABaseRate")) {
			try {
				this.setFundABaseRate(json.getDouble("FundABaseRate"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundAFloatRate")) {
			try {
				this.setFundAFloatRate(json.getDouble("FundAFloatRate"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundACurrentPrice")) {
			try {
				this.setFundARealPrice(json.getDouble("FundACurrentPrice"));
			} catch (Exception e) {
			}
		}

		if (!json.isNull("FundAPrice")) {
			try {
				this.setFundAPrice(json.getDouble("FundAPrice"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundAInterestRate")) {
			try {
				this.setFundARate(json.getDouble("FundAInterestRate"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundBCode")) {
			this.setFundBCode(json.getString("FundBCode"));
		}
		if (!json.isNull("FundBName")) {
			this.setFundBName(json.getString("FundBName"));
		}
		if (!json.isNull("FundBCurrentPrice")) {
			try {
				this.setFundBRealPrice(json.getDouble("FundBCurrentPrice"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundBPrice")) {
			try {
				this.setFundBPrice(json.getDouble("FundBPrice"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundBDiscountPremium")) {
			try {
				this.setFundBConvertPrice(json.getDouble("FundBDiscountPremium"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundPrice")) {
			try {
				this.setFundPrice(json.getDouble("FundPrice"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundPosition")) {
			try {
				this.setFundPosition(json.getDouble("FundPosition"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundRiseAndFall")) {
			try {
				this.setFundRatio(json.getDouble("FundRiseAndFall"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("IndexRiseAndFall")) {
			try {
				this.setIndexRatio(json.getDouble("IndexRiseAndFall"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FittingPrice")) {
			try {
				this.setMatchedValue(json.getDouble("FittingPrice"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("DiscountPremium")) {
			try {
				this.setFundConvertPrice(json.getDouble("DiscountPremium"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundARiseAndFall")) {
			try {
				this.setFundARatio(json.getDouble("FundARiseAndFall"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundBRiseAndFall")) {
			try {
				this.setFundBRatio(json.getDouble("FundBRiseAndFall"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("Multiple")) {
			try {
				this.setMultiple(json.getDouble("Multiple"));
			} catch (Exception e) {
			}
		}

		///////////////////////////////////////
		if (!json.isNull("NetValueLever")) {
			try {
				this.setNetValueLever(json.getDouble("NetValueLever"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundADiscountPremium")) {
			try {
				this.setFundAConvertPrice(json.getDouble("FundADiscountPremium"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundNeedDownRate")) {
			try {
				this.setFundNeedRatio(json.getDouble("FundNeedDownRate"));
			} catch (Exception e) {
			}
		}

		if (!json.isNull("SBWHide")) {
			try {
				this.setIsShow(json.getInt("SBWHide"));
			} catch (Exception e) {
			}
		}
		if (!json.isNull("FundTReduce1Price")) {
			try {
				this.setFundNetValue(json.getDouble("FundTReduce1Price"));
			} catch (Exception e) {
				this.setFundNetValue(0);
			}
		}
		if (!json.isNull("FundBCurrentTotalTurnVolume")) {
			this.setRealtradeNum(json.getString("FundBCurrentTotalTurnVolume"));
		}

		if (!json.isNull("realpath")) {
			this.setRealpath(json.getString("realpath"));
		}
		if (!json.isNull("daypath")) {
			this.setDaypath(json.getString("daypath"));
		}
		if (!json.isNull("weekpath")) {
			this.setWeekpath(json.getString("weekpath"));
		}
		if (!json.isNull("monthpath")) {
			this.setMonthpath(json.getString("monthpath"));
		}
		if (!json.isNull("ConvertDate")) {
			this.setConvertDate(json.getString("ConvertDate"));
		}
		if (!json.isNull("StandardConvertDate")) {
			this.setStandardConvertDate(json.getString("StandardConvertDate"));
		}

		if (!json.isNull("ApplyForPurchasePrice")) {
			this.setBuyInfo(json.getString("ApplyForPurchasePrice"));
		}
		if (!json.isNull("AtoneForPrice")) {
			this.setSaleInfo(json.getString("AtoneForPrice"));
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getFundACode() {
		return fundACode;
	}

	public void setFundACode(String fundACode) {
		this.fundACode = fundACode;
	}

	public String getFundAName() {
		return fundAName;
	}

	public void setFundAName(String fundAName) {
		this.fundAName = fundAName;
	}

	public double getFundABaseRate() {
		return fundABaseRate;
	}

	public void setFundABaseRate(double fundABaseRate) {
		this.fundABaseRate = fundABaseRate;
	}

	public double getFundARealPrice() {
		return fundARealPrice;
	}

	public void setFundARealPrice(double fundARealPrice) {
		this.fundARealPrice = fundARealPrice;
	}

	public double getFundAPrice() {
		return fundAPrice;
	}

	public void setFundAPrice(double fundAPrice) {
		this.fundAPrice = fundAPrice;
	}

	public double getFundARate() {
		return fundARate;
	}

	public void setFundARate(double fundARate) {
		this.fundARate = fundARate;
	}

	public String getFundBCode() {
		return fundBCode;
	}

	public void setFundBCode(String fundBCode) {
		this.fundBCode = fundBCode;
	}

	public String getFundBName() {
		return fundBName;
	}

	public void setFundBName(String fundBName) {
		this.fundBName = fundBName;
	}

	public double getFundBRealPrice() {
		return fundBRealPrice;
	}

	public void setFundBRealPrice(double fundBRealPrice) {
		this.fundBRealPrice = fundBRealPrice;
	}

	public double getFundBPrice() {
		return fundBPrice;
	}

	public void setFundBPrice(double fundBPrice) {
		this.fundBPrice = fundBPrice;
	}

	public double getFundBConvertPrice() {
		return fundBConvertPrice;
	}

	public void setFundBConvertPrice(double fundBConvertPrice) {
		this.fundBConvertPrice = fundBConvertPrice;
	}

	public double getFundPrice() {
		return fundPrice;
	}

	public void setFundPrice(double fundPrice) {
		this.fundPrice = fundPrice;
	}

	public double getFundPosition() {
		return fundPosition;
	}

	public void setFundPosition(double fundPosition) {
		this.fundPosition = fundPosition;
	}

	public double getFundRatio() {
		return fundRatio;
	}

	public void setFundRatio(double fundRatio) {
		this.fundRatio = fundRatio;
	}

	public double getIndexRatio() {
		return indexRatio;
	}

	public void setIndexRatio(double indexRatio) {
		this.indexRatio = indexRatio;
	}

	public double getMatchedValue() {
		return matchedValue;
	}

	public void setMatchedValue(double matchedValue) {
		this.matchedValue = matchedValue;
	}

	public double getFundConvertPrice() {
		return fundConvertPrice;
	}

	public void setFundConvertPrice(double fundConvertPrice) {
		this.fundConvertPrice = fundConvertPrice;
	}

	public double getFundAFloatRate() {
		return fundAFloatRate;
	}

	public void setFundAFloatRate(double fundAFloatRate) {
		this.fundAFloatRate = fundAFloatRate;
	}

	public double getFundARatio() {
		return FundARatio;
	}

	public void setFundARatio(double fundARatio) {
		FundARatio = fundARatio;
	}

	public double getFundBRatio() {
		return FundBRatio;
	}

	public void setFundBRatio(double fundBRatio) {
		FundBRatio = fundBRatio;
	}

	public double getMultiple() {
		return Multiple;
	}

	public void setMultiple(double multiple) {
		Multiple = multiple;
	}

	public double getFundAConvertPrice() {
		return FundAConvertPrice;
	}

	public void setFundAConvertPrice(double fundAConvertPrice) {
		FundAConvertPrice = fundAConvertPrice;
	}

	public String getRealpath() {
		return realpath;
	}

	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}

	public String getDaypath() {
		return daypath;
	}

	public void setDaypath(String daypath) {
		this.daypath = daypath;
	}

	public String getWeekpath() {
		return weekpath;
	}

	public void setWeekpath(String weekpath) {
		this.weekpath = weekpath;
	}

	public String getMonthpath() {
		return monthpath;
	}

	public void setMonthpath(String monthpath) {
		this.monthpath = monthpath;
	}

	public String getConvertDate() {
		return convertDate;
	}

	public void setConvertDate(String convertDate) {
		this.convertDate = convertDate;
	}

	public String getStandardConvertDate() {
		return standardConvertDate;
	}

	public void setStandardConvertDate(String standardConvertDate) {
		this.standardConvertDate = standardConvertDate;
	}

	// public int getIsFav() {
	// return isFav;
	// }
	//
	// public void setIsFav(int isFav) {
	// this.isFav = isFav;
	// }
	//
	// public int getFavId() {
	// return favId;
	// }
	//
	// public void setFavId(int favId) {
	// this.favId = favId;
	// }

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public double getFundNetValue() {
		return fundNetValue;
	}

	public void setFundNetValue(double fundNetValue) {
		this.fundNetValue = fundNetValue;
	}

	public String getBuyInfo() {
		return buyInfo;
	}

	public void setBuyInfo(String buyInfo) {
		this.buyInfo = buyInfo;
	}

	public String getSaleInfo() {
		return saleInfo;
	}

	public void setSaleInfo(String saleInfo) {
		this.saleInfo = saleInfo;
	}

	public String getRealtradeNum() {
		return realtradeNum;
	}

	public void setRealtradeNum(String realtradeNum) {
		this.realtradeNum = realtradeNum;
	}

	public double getNetValueLever() {
		return NetValueLever;
	}

	public void setNetValueLever(double netValueLever) {
		NetValueLever = netValueLever;
	}

}
