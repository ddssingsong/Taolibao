package com.jhs.taolibao.code.my.model;

/**
 * Created by dds on 2016/7/15.
 *
 * @TODO
 */
public interface BaobiModel {


    /**
     * 获取订单信息
     *
     * @param userID  用户id
     * @param amount  支付金额
     * @param payType 支付方式  1 苹果 2 Android
     * @param bankID  支付渠道 默认0
     * @param notes   描述
     */

    void Pay(String userID, int amount, int payType, int bankID, String notes,BaobiModelImpl.onPayListener listener);

    /**
     * 获取订单流水号
     *
     * @param orderID  订单id
     * @param txnTime  时间 yyyyMMddHHmmss
     * @param amount   支付金额
     * @param listener
     */

    void getOrderWaterTn(String orderID, String txnTime, int amount, BaobiModelImpl.onGetOrderWaterTnListener listener);

    void notifyAddBaobi(String id,BaobiModelImpl.onotifyAddBaobiListener listener);

    /**
     * 兑换京东卡
     * @param userid
     * @param pointType
     * @param amount
     * @param month
     * @param listener
     */
    void exchangeJingdong(String userid, int pointType, int amount, int month, BaobiModelImpl.onExchangeJingdongListener listener);
}
