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

import java.util.*;

/**
 * This class model the API for Applications. Every action here is related to an Application. This
 * means that a LatchApp object should use a pair ApplicationId/Secret obtained from the Application page of the Latch Website.
 */
public class LatchApp extends LatchAuth {

    /**
     * Create an instance of the class with the Application ID and secret obtained from Eleven Paths
     * @param appId
     * @param secretKey
     */
    public LatchApp(String appId, String secretKey){
        this.appId = appId;
        this.secretKey = secretKey;
    }

    public LatchResponse pairWithId(String id) {
        return HTTP_GET_proxy(new StringBuilder(API_PAIR_WITH_ID_URL).append("/").append(id).toString());
    }

    public LatchResponse pair(String token) {
        return HTTP_GET_proxy(new StringBuilder(API_PAIR_URL).append("/").append(token).toString());
    }

    /**
     * Return application status for a given accountId
     * @param accountId The accountId which status is going to be retrieved
     * @return LatchResponse containing the status
     */
    public LatchResponse status(String accountId) {
        return status(accountId, null, false, false);
    }

    /**
     * Return application status for a given accountId
     * @param accountId The accountId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param noOtp True for not generating a OTP if needed.
     * @return LatchResponse containing the status
     */
    public LatchResponse status(String accountId, boolean silent, boolean noOtp) {
        return status(accountId, null, silent, noOtp);
    }

    /**
     * Return operation status for a given accountId and operationId
     * @param accountId The accountId which status is going to be retrieved
     * @param operationId The operationId which status is going to be retrieved
     * @return LatchResponse containing the status
     */
    public LatchResponse status(String accountId, String operationId) {
        return status(accountId, operationId, false, false);
    }

    /**
     * Return operation status for a given accountId and operationId
     * @param accountId The accountId which status is going to be retrieved
     * @param operationId The operationId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param noOtp True for not generating a OTP if needed.
     * @return LatchResponse containing the status
     */
    public LatchResponse status(String accountId, String operationId, boolean silent, boolean noOtp){
        StringBuilder url = new StringBuilder(API_CHECK_STATUS_URL).append("/").append(accountId);
        if (operationId != null && operationId.length() != 0){
        	
            url.append("/op/").append(operationId);
        }

        if (noOtp) {
            url.append("/nootp");
        }
        if (silent) {
            url.append("/silent");
        }

        return HTTP_GET_proxy(url.toString());
    }

    /**
     * Return application status for a given accountId while sending some custom data (Like OTP token or a message)
     * @param accountId The accountId which status is going to be retrieved
     * @param otpToken This will be the OTP sent to the user instead of generating a new one
     * @param otpMessage To attach a custom message with the OTP to the user
     * @return LatchResponse containing the status
     */
    public LatchResponse status(String accountId, String otpToken, String otpMessage){
        return status(accountId, null, false, otpToken, otpMessage);
    }

    /**
     * Return application status for a given accountId while sending some custom data (Like OTP token or a message)
     * @param accountId The accountId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param otpToken This will be the OTP sent to the user instead of generating a new one
     * @param otpMessage To attach a custom message with the OTP to the user
     * @return LatchResponse containing the status
     */
    public LatchResponse status(String accountId, boolean silent, String otpToken, String otpMessage){
        return status(accountId, null, silent, otpToken, otpMessage);
    }

    /**
     * Return operation status for a given accountId and operation while sending some custom data (Like OTP token or a message)
     * @param accountId The accountId which status is going to be retrieved
     * @param operationId The operationId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param otpToken This will be the OTP sent to the user instead of generating a new one
     * @param otpMessage To attach a custom message with the OTP to the user
     * @return LatchResponse containing the status
     */
    public LatchResponse status(String accountId, String operationId, boolean silent, String otpToken, String otpMessage){
        StringBuilder url = new StringBuilder(API_CHECK_STATUS_URL).append("/").append(accountId);
        if (operationId != null && operationId.length() != 0){
            url.append("/op/").append(operationId);
        }

        if (silent) {
            url.append("/silent");
        }

        Map<String, String> data = new HashMap<String, String>();
        if (otpToken != null && otpToken.length() != 0) {
            data.put("otp", otpToken);
        }
        if (otpMessage != null && otpMessage.length() != 0) {
            data.put("msg", otpMessage);
        }
        return HTTP_POST_proxy(url.toString(), data);
    }

    @Deprecated
    public LatchResponse operationStatus(String accountId, String operationId) {
        return status(accountId, operationId, false, false);
    }

    @Deprecated
    public LatchResponse operationStatus(String accountId, String operationId, boolean silent, boolean noOtp) {
        return status(accountId, operationId, silent, noOtp);
    }

    public LatchResponse unpair(String id) {
        return HTTP_GET_proxy(new StringBuilder(API_UNPAIR_URL).append("/").append(id).toString());
    }

    public LatchResponse lock(String accountId) {
        return HTTP_POST_proxy(new StringBuilder(API_LOCK_URL).append("/").append(accountId).toString());
    }

    public LatchResponse lock(String accountId, String operationId) {
        return HTTP_POST_proxy(new StringBuilder(API_LOCK_URL).append("/").append(accountId).append("/op/").append(operationId).toString());
    }

    public LatchResponse unlock(String accountId) {
        return HTTP_POST_proxy(new StringBuilder(API_UNLOCK_URL).append("/").append(accountId).toString());
    }

    public LatchResponse unlock(String accountId, String operationId) {
        return HTTP_POST_proxy(new StringBuilder(API_UNLOCK_URL).append("/").append(accountId).append("/op/").append(operationId).toString());
    }

    public LatchResponse history(String accountId) {
        return HTTP_GET_proxy(new StringBuilder(API_HISTORY_URL).append("/").append(accountId).toString());
    }

    public LatchResponse history(String accountId, Long from, Long to) {
        return HTTP_GET_proxy(new StringBuilder(API_HISTORY_URL).append("/").append(accountId)
                                                                .append("/").append(from != null ? String.valueOf(from) :"0")
                                                                .append("/").append(to != null ? String.valueOf(to) : String.valueOf(new Date().getTime())).toString());
    }

    public LatchResponse createOperation(String parentId, String name, String twoFactor, String lockOnRequest) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("parentId", parentId);
        data.put("name", name);
        data.put("two_factor", twoFactor);
        data.put("lock_on_request", lockOnRequest);
        return HTTP_PUT_proxy(new StringBuilder(API_OPERATION_URL).toString(), data);
    }

    public LatchResponse removeOperation(String operationId) {
        return HTTP_DELETE_proxy(new StringBuilder(API_OPERATION_URL).append("/").append(operationId).toString());
    }

    public LatchResponse getOperations() {
        return HTTP_GET_proxy(new StringBuilder(API_OPERATION_URL).toString());
    }

    public LatchResponse getOperations(String operationId) {
        return HTTP_GET_proxy(new StringBuilder(API_OPERATION_URL).append("/").append(operationId).toString());
    }

    public LatchResponse updateOperation(String operationId, String name, String twoFactor, String lockOnRequest) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("two_factor", twoFactor);
        data.put("lock_on_request", lockOnRequest);
        return HTTP_POST_proxy(new StringBuilder(API_OPERATION_URL).append("/").append(operationId).toString(), data);
    }

}
