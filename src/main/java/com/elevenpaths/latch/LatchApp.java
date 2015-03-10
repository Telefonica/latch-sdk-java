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

    public LatchResponse status(String accountId, boolean silent, boolean nootp) {
        StringBuilder url = new StringBuilder(API_CHECK_STATUS_URL).append("/").append(accountId);
        if (nootp) {
            url.append("/nootp");
        }
        if (silent) {
            url.append("/silent");
        }
        return HTTP_GET_proxy(url.toString());
    }

    public LatchResponse status(String accountId) {
        return status(accountId, false, false);
    }

    public LatchResponse operationStatus(String accountId, String operationId, boolean silent, boolean nootp) {
        StringBuilder url = new StringBuilder(API_CHECK_STATUS_URL).append("/").append(accountId).append("/op/").append(operationId);
        if (nootp) {
            url.append("/nootp");
        }
        if (silent) {
            url.append("/silent");
        }
        return HTTP_GET_proxy(url.toString());
    }

    public LatchResponse operationStatus(String accountId, String operationId) {
        return operationStatus(accountId, operationId, false, false);
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
