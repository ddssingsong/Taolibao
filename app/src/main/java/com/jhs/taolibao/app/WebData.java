package com.jhs.taolibao.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2016/6/1.
 */
public class WebData {

    /**
     * wsdl接口，获取负载均衡列表
     */

    // nameSpace
    public static final String NAMESPACE = "http://tempuri.org/";
    // host_url
    public static final String HOST_URL = "http://182.92.172.134:55555/MainServices";
    // 获取负载均衡接口
    public static final String FZJH_METHOD = "GetLoadBalancingServer";

    /**
     * 负载均衡的接口
     */
    public static String host = "182.92.172.134:8014";

    public static List<String> hostList = new ArrayList<String>();

    public static String Fuzaiurl = "http://" + host + "/ifoutservice.aspx";
    //public static final String Fuzaiurl = "http://192.168.1.20:8001/ifoutservice.aspx";
}
