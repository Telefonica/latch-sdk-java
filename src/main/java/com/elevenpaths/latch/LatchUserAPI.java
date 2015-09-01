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
import com.elevenpaths.latch.response.ApplicationResponse;
import com.elevenpaths.latch.response.OperationsResponse;

import java.util.HashMap;
import java.util.Map;

public class LatchUserAPI extends APIBase {

    protected static final String API_VERSION = "1.0";
    public static String API_HOST = "https://latch.elevenpaths.com";

    public static final String API_APPLICATION_URL = "/api/" + API_VERSION + "/application";
    public static final String API_SUBSCRIPTION_URL = "/api/" + API_VERSION + "/subscription";

    public static void setHost(String apiHost) {
        API_HOST = apiHost;
    }

    /**
     * Create an instance of the class with the User ID and secret obtained from Eleven Paths
     * @param userId
     * @param secretKey
     */
    public LatchUserAPI(String userId, String secretKey){
        super(userId, secretKey);
    }

    @Override
    protected String getApiHost() {
        return API_HOST;
    }

    public APIResponse getSubscription() {
        return httpGetProxy(new StringBuilder(API_SUBSCRIPTION_URL).toString());
    }

    public APIResponse<ApplicationResponse> createApplication(String name, String twoFactor, String lockOnRequest, String contactPhone, String contactEmail) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("two_factor", twoFactor);
        data.put("lock_on_request", lockOnRequest);
        data.put("contactEmail", contactEmail);
        data.put("contactPhone", contactPhone);
        return httpPutProxy(new StringBuilder(API_APPLICATION_URL).toString(), data, ApplicationResponse.class);
    }

    public APIResponse<ApplicationResponse> removeApplication(String applicationId) {
        return httpDeleteProxy(new StringBuilder(API_APPLICATION_URL).append("/").append(applicationId).toString(), ApplicationResponse.class);
    }

    public APIResponse<OperationsResponse> getApplications() {
        return httpGetProxy(new StringBuilder(API_APPLICATION_URL).toString(), OperationsResponse.class);
    }

    public APIResponse<ApplicationResponse> updateApplication(String applicationId, String name, String twoFactor, String lockOnRequest, String contactPhone, String contactEmail) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", name);
        data.put("two_factor", twoFactor);
        data.put("lock_on_request", lockOnRequest);
        data.put("contactPhone", contactPhone);
        data.put("contactEmail", contactEmail);
        return httpPostProxy(new StringBuilder(API_APPLICATION_URL).append("/").append(applicationId).toString(), data, ApplicationResponse.class);
    }
}
