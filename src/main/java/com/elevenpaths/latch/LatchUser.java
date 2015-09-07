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

import com.elevenpaths.latch.response.ApplicationResponse;
import com.elevenpaths.latch.response.OperationsResponse;

/**
 * This class model the API for a Latch User. Every action here is related to a User. This
 * means that a LatchUser object should use a pair UserId/Secret obtained from the Latch Website.
 */
@Deprecated
public class LatchUser extends LatchUserAPI {


    /**
     * Create an instance of the class with the User ID and secret obtained from Eleven Paths
     *
     * @param userId
     * @param secretKey
     */
    public LatchUser(String userId, String secretKey) {
        super(userId, secretKey);
    }

    public LatchResponse<ApplicationResponse> getSubscription() {
        return new LatchResponse<ApplicationResponse>(super.getSubscription());
    }

    public LatchResponse<ApplicationResponse> createApplication(String name, String twoFactor, String lockOnRequest, String contactPhone, String contactEmail) {
        return new LatchResponse<ApplicationResponse>(super.createApplication(name, twoFactor, lockOnRequest, contactPhone, contactEmail));
    }

    public LatchResponse<ApplicationResponse> removeApplication(String applicationId) {
        return new LatchResponse<ApplicationResponse>(super.removeApplication(applicationId));
    }

    public LatchResponse<OperationsResponse> getApplications() {
        return new LatchResponse<OperationsResponse>(super.getApplications());
    }

    public LatchResponse<ApplicationResponse> updateApplication(String applicationId, String name, String twoFactor, String lockOnRequest, String contactPhone, String contactEmail) {
        return new LatchResponse<ApplicationResponse>(super.updateApplication(applicationId, name, twoFactor, lockOnRequest, contactPhone, contactEmail));
    }
}
