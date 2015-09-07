/*Latch Java SDK - Set of  reusable classes to  allow developers integrate Latch on their applications.
Copyright (C) 2013 Eleven Paths

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA*/
package com.elevenpaths.latch;

import com.elevenpaths.api.APIBase;
import com.elevenpaths.api.APIResponse;
import com.elevenpaths.latch.response.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LatchAppAPI extends APIBase {

    protected static final String API_VERSION = "1.0";
    public static String API_HOST = "https://latch.elevenpaths.com";

    public static final String API_CHECK_STATUS_URL = "/api/" + API_VERSION + "/status";
    public static final String API_PAIR_URL = "/api/" + API_VERSION + "/pair";
    public static final String API_PAIR_WITH_ID_URL = "/api/" + API_VERSION + "/pairWithId";
    public static final String API_UNPAIR_URL = "/api/" + API_VERSION + "/unpair";
    public static final String API_LOCK_URL = "/api/" + API_VERSION + "/lock";
    public static final String API_UNLOCK_URL = "/api/" + API_VERSION + "/unlock";
    public static final String API_HISTORY_URL = "/api/" + API_VERSION + "/history";
    public static final String API_OPERATION_URL = "/api/" + API_VERSION + "/operation";

    public static void setHost(String apiHost) {
        API_HOST = apiHost;
    }

    /**
     * Create an instance of the class with the Application ID and secret obtained from Eleven Paths
     * @param appId
     * @param secretKey
     */
    public LatchAppAPI(String appId, String secretKey){
        super(appId, secretKey);
    }

    @Override
    protected String getApiHost() {
        return API_HOST;
    }

    public APIResponse<PairResponse> pairWithId(String id) {
        return httpGetProxy(new StringBuilder(API_PAIR_WITH_ID_URL).append("/").append(id).toString(), PairResponse.class);
    }

    public APIResponse<PairResponse> pair(String token) {
        return httpGetProxy(new StringBuilder(API_PAIR_URL).append("/").append(token).toString(), PairResponse.class);
    }

    /**
     * Return application status for a given accountId
     * @param accountId The accountId which status is going to be retrieved
     * @return APIResponse containing the status
     */
    public APIResponse<StatusResponse> status(String accountId) {
        return status(accountId, null, false, false);
    }

    /**
     * Return application status for a given accountId
     * @param accountId The accountId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param noOtp True for not generating a OTP if needed.
     * @return APIResponse containing the status
     */
    public APIResponse<StatusResponse> status(String accountId, boolean silent, boolean noOtp) {
        return status(accountId, null, silent, noOtp);
    }

    /**
     * Return operation status for a given accountId and operationId
     * @param accountId The accountId which status is going to be retrieved
     * @param operationId The operationId which status is going to be retrieved
     * @return APIResponse containing the status
     */
    public APIResponse<StatusResponse> status(String accountId, String operationId) {
        return status(accountId, operationId, false, false);
    }

    /**
     * Return operation status for a given accountId and operationId
     * @param accountId The accountId which status is going to be retrieved
     * @param operationId The operationId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param noOtp True for not generating a OTP if needed.
     * @return APIResponse containing the status
     */
    public APIResponse<StatusResponse> status(String accountId, String operationId, boolean silent, boolean noOtp){
        StringBuilder url = new StringBuilder(API_CHECK_STATUS_URL).append("/").append(accountId);
        if (operationId != null && !operationId.isEmpty()){
            url.append("/op/").append(operationId);
        }

        if (noOtp) {
            url.append("/nootp");
        }
        if (silent) {
            url.append("/silent");
        }

        return httpGetProxy(url.toString(), StatusResponse.class);
    }

    /**
     * Return application status for a given accountId while sending some custom data (Like OTP token or a message)
     * @param accountId The accountId which status is going to be retrieved
     * @param otpToken This will be the OTP sent to the user instead of generating a new one
     * @param otpMessage To attach a custom message with the OTP to the user
     * @return APIResponse containing the status
     */
    public APIResponse<StatusResponse> status(String accountId, String otpToken, String otpMessage){
        return status(accountId, null, false, otpToken, otpMessage);
    }

    /**
     * Return application status for a given accountId while sending some custom data (Like OTP token or a message)
     * @param accountId The accountId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param otpToken This will be the OTP sent to the user instead of generating a new one
     * @param otpMessage To attach a custom message with the OTP to the user
     * @return APIResponse containing the status
     */
    public APIResponse<StatusResponse> status(String accountId, boolean silent, String otpToken, String otpMessage){
        return status(accountId, null, silent, otpToken, otpMessage);
    }

    /**
     * Return operation status for a given accountId and operation while sending some custom data (Like OTP token or a message)
     * @param accountId The accountId which status is going to be retrieved
     * @param operationId The operationId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param otpToken This will be the OTP sent to the user instead of generating a new one
     * @param otpMessage To attach a custom message with the OTP to the user
     * @return APIResponse containing the status
     */
    public APIResponse<StatusResponse> status(String accountId, String operationId, boolean silent, String otpToken, String otpMessage){
        StringBuilder url = new StringBuilder(API_CHECK_STATUS_URL).append("/").append(accountId);
        if (operationId != null && !operationId.isEmpty()){
            url.append("/op/").append(operationId);
        }

        if (silent) {
            url.append("/silent");
        }

        Map<String, String> data = new HashMap<String, String>();
        if (otpToken != null && !otpToken.isEmpty()) {
            data.put("otp", otpToken);
        }
        if (otpMessage != null && !otpMessage.isEmpty()) {
            data.put("msg", otpMessage);
        }
        return httpPostProxy(url.toString(), data, StatusResponse.class);
    }

    @Deprecated
    public APIResponse<StatusResponse> operationStatus(String accountId, String operationId) {
        return status(accountId, operationId, false, false);
    }

    @Deprecated
    public APIResponse<StatusResponse> operationStatus(String accountId, String operationId, boolean silent, boolean noOtp) {
        return status(accountId, operationId, silent, noOtp);
    }

    public APIResponse<UnpairResponse> unpair(String id) {
        return httpGetProxy(new StringBuilder(API_UNPAIR_URL).append("/").append(id).toString(), UnpairResponse.class);
    }

    public APIResponse<LockResponse> lock(String accountId) {
        return httpPostProxy(new StringBuilder(API_LOCK_URL).append("/").append(accountId).toString(), LockResponse.class);
    }

    public APIResponse<LockResponse> lock(String accountId, String operationId) {
        return httpPostProxy(new StringBuilder(API_LOCK_URL).append("/").append(accountId).append("/op/").append(operationId).toString(), LockResponse.class);
    }

    public APIResponse<UnlockResponse> unlock(String accountId) {
        return httpPostProxy(new StringBuilder(API_UNLOCK_URL).append("/").append(accountId).toString(), UnlockResponse.class);
    }

    public APIResponse<UnlockResponse> unlock(String accountId, String operationId) {
        return httpPostProxy(new StringBuilder(API_UNLOCK_URL).append("/").append(accountId).append("/op/").append(operationId).toString(), UnlockResponse.class);
    }

    public APIResponse<HistoryResponse> history(String accountId) {
        return httpGetProxy(new StringBuilder(API_HISTORY_URL).append("/").append(accountId).toString(), HistoryResponse.class);
    }

    public APIResponse<HistoryResponse> history(String accountId, Long from, Long to) {
        return httpGetProxy(new StringBuilder(API_HISTORY_URL).append("/").append(accountId)
                .append("/").append(from != null ? String.valueOf(from) : "0")
                .append("/").append(to != null ? String.valueOf(to) : String.valueOf(new Date().getTime())).toString(), HistoryResponse.class);
    }

    public APIResponse<OperationsResponse> createOperation(String parentId, String name, String twoFactor, String lockOnRequest) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("parentId", parentId);
        data.put("name", name);
        data.put("two_factor", twoFactor);
        data.put("lock_on_request", lockOnRequest);
        return httpPutProxy(new StringBuilder(API_OPERATION_URL).toString(), data, OperationsResponse.class);
    }

    public APIResponse<OperationsResponse> removeOperation(String operationId) {
        return httpDeleteProxy(new StringBuilder(API_OPERATION_URL).append("/").append(operationId).toString(), OperationsResponse.class);
    }

    public APIResponse<OperationsResponse> getOperations() {
        return httpGetProxy(new StringBuilder(API_OPERATION_URL).toString(), OperationsResponse.class);
    }

    public APIResponse<OperationsResponse> getOperations(String operationId) {
        return httpGetProxy(new StringBuilder(API_OPERATION_URL).append("/").append(operationId).toString(), OperationsResponse.class);
    }

    public APIResponse<OperationsResponse> updateOperation(String operationId, String name, String twoFactor, String lockOnRequest) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("two_factor", twoFactor);
        data.put("lock_on_request", lockOnRequest);
        return httpPostProxy(new StringBuilder(API_OPERATION_URL).append("/").append(operationId).toString(), data, OperationsResponse.class);
    }
}
