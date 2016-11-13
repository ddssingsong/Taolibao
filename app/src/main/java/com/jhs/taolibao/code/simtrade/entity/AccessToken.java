package com.jhs.taolibao.code.simtrade.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xujingbo on 2016/7/11.
 */
public class AccessToken {
    /**
     *授权服务授权颁发的访问令牌.
     */
    private  String accessToken;
    /**
     *令牌类型告诉客户端一个信息，即当访问一个受保护资源时，访问令牌应该如何被使用
     */
    private  String tokenType;
    /**
     *表示访问令牌生命周期的秒数
     */
    private  String expiresIn;
    /**
     *用于换取新的访问令牌的刷新令牌，只有在刷新令牌时必须有此参数
     */
    private  String refreshToken;
    /**
     *用于指定授权访问的范围，多个授权范围可以使用逗号（,）连接
     */
    private  String scope;
    private String authId;
    private String fundAccount;
    private String clientId;
    private String clientName;
    private String fundAccout;
    /**
     * @return the fund_accout.
     */
    public String getFundAccout() {
        return fundAccout;
    }
    /**
     * @param fundAccout the fund_accout to set.A
     */
    public void setFundAccout(String fundAccout) {
        this.fundAccout = fundAccout;
    }
    /**
     * @return the authId.
     */
    public String getAuthId() {
        return authId;
    }
    /**
     * @param authId the authId to set.
     */
    public void setAuthId(String authId) {
        this.authId = authId;
    }
    /**
     * @return the fundAccount.
     */
    public String getFundAccount() {
        return fundAccount;
    }
    /**
     * @param fundAccount the fund_account to set.
     */
    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }
    /**
     * @return the clientId.
     */
    public String getClientId() {
        return clientId;
    }
    /**
     * @param clientId the client_id to set.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    /**
     * @return the clientName.
     */
    public String getClientName() {
        return clientName;
    }
    /**
     * @param clientName the client_name to set.
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    /**
     * @return the accessToken.
     */
    public String getAccessToken() {
        return accessToken;
    }
    /**
     * @param accessToken the access_token to set.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    /**
     * @return the tokenType.
     */
    public String getTokenType() {
        return tokenType;
    }
    /**
     * @param tokenType the token_type to set.
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    /**
     * @return the expiresIn.
     */
    public String getExpiresIn() {
        return expiresIn;
    }
    /**
     * @param expiresIn the expires_in to set.
     */
    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
    /**
     * @return the refreshToken.
     */
    public String getRefreshToken() {
        return refreshToken;
    }
    /**
     * @param refreshToken the refresh_token to set.
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    /**
     * @return the scope.
     */
    public String getScope() {
        return scope;
    }
    /**
     * @param scope the scope to set.
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    public AccessToken refreshFromJson(JSONObject json) throws JSONException {
        if (!json.isNull("token_type")) {
            this.setTokenType(json.getString("token_type"));
        }
        if (!json.isNull("access_token")) {
            this.setAccessToken(json.getString("access_token"));
        }
        if (!json.isNull("expires_in")) {
            this.setExpiresIn(json.getString("expires_in"));
        }
        if (!json.isNull("refresh_token")) {
            this.setRefreshToken(json.getString("refresh_token"));

        }
        if (!json.isNull("scope")) {
            this.setScope(json.getString("scope"));
        }
        if (!json.isNull("auth_id")) {
            this.setAuthId(json.getString("auth_id"));
        }
        if (!json.isNull("fund_account")) {
            this.setFundAccount(json.getString("fund_account"));
        }
        if (!json.isNull("client_id")) {
            this.setClientId(json.getString("client_id"));

        }

        if (!json.isNull("client_name")) {
            this.setClientName(json.getString("client_name"));
        }
        if (!json.isNull("fund_accout")) {
            this.setFundAccount(json.getString("fund_accout"));

        }
        return this;
    }
}
