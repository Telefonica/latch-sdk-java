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
 * This class model the API for a Latch User. Every action here is related to a User. This
 * means that a LatchUser object should use a pair UserId/Secret obtained from the Latch Website.
 */
public class LatchUser extends LatchAuth {

    /**
     * Create an instance of the class with the User ID and secret obtained from Eleven Paths
     * @param userId
     * @param secretKey
     */
    public LatchUser(String userId, String secretKey){
        this.appId = userId;
        this.secretKey = secretKey;
    }

    public LatchResponse getSubscription() {
        return HTTP_GET_proxy(new StringBuilder(API_SUBSCRIPTION_URL).toString());
    }

    public LatchResponse createApplication(String name, String twoFactor, String lockOnRequest, String contactPhone, String contactEmail) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("two_factor", twoFactor);
        data.put("lock_on_request", lockOnRequest);
        data.put("contactEmail", contactEmail);
        data.put("contactPhone", contactPhone);
        return HTTP_PUT_proxy(new StringBuilder(API_APPLICATION_URL).toString(), data);
    }

    public LatchResponse removeApplication(String applicationId) {
        return HTTP_DELETE_proxy(new StringBuilder(API_APPLICATION_URL).append("/").append(applicationId).toString());
    }

    public LatchResponse getApplications() {
        return HTTP_GET_proxy(new StringBuilder(API_APPLICATION_URL).toString());
    }

    public LatchResponse updateApplication(String applicationId, String name, String twoFactor, String lockOnRequest, String contactPhone, String contactEmail) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("two_factor", twoFactor);
        data.put("lock_on_request", lockOnRequest);
        data.put("contactPhone", contactPhone);
        data.put("contactEmail", contactEmail);
        return HTTP_POST_proxy(new StringBuilder(API_APPLICATION_URL).append("/").append(applicationId).toString(), data);
    }
}
