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

import com.elevenpaths.latch.response.*;

/**
 * This class model the API for Applications. Every action here is related to an Application. This
 * means that a LatchApp object should use a pair ApplicationId/Secret obtained from the Application page of the Latch Website.
 * */
@Deprecated
public class LatchApp extends LatchAppAPI {

    /**
     * Create an instance of the class with the Application ID and secret obtained from Eleven Paths
     * @param appId
     * @param secretKey
     */

    public LatchApp(String appId, String secretKey) {
        super(appId, secretKey);
    }

    @Override
    public LatchResponse<PairResponse> pairWithId(String id) {
        return new LatchResponse<PairResponse>(super.pairWithId(id));
    }

    @Override
    public LatchResponse<PairResponse> pair(String token) {
        return new LatchResponse<PairResponse>(super.pair(token));
    }

    /**
     * Return application status for a given accountId
     * @param accountId The accountId which status is going to be retrieved
     * @return APIResponse containing the status
     */
    @Override
    public LatchResponse<StatusResponse> status(String accountId) {
        return new LatchResponse<StatusResponse>(super.status(accountId));
    }

    /**
     * Return application status for a given accountId
     * @param accountId The accountId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param noOtp True for not generating a OTP if needed.
     * @return APIResponse containing the status
     */
    @Override
    public LatchResponse<StatusResponse> status(String accountId, boolean silent, boolean noOtp) {
        return new LatchResponse<StatusResponse>(super.status(accountId, silent, noOtp));
    }

    /**
     * Return operation status for a given accountId and operationId
     * @param accountId The accountId which status is going to be retrieved
     * @param operationId The operationId which status is going to be retrieved
     * @return APIResponse containing the status
     */
    @Override
    public LatchResponse<StatusResponse> status(String accountId, String operationId) {
        return new LatchResponse<StatusResponse>(super.status(accountId, operationId));
    }

    /**
     * Return operation status for a given accountId and operationId
     * @param accountId The accountId which status is going to be retrieved
     * @param operationId The operationId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param noOtp True for not generating a OTP if needed.
     * @return APIResponse containing the status
     */
    @Override
    public LatchResponse<StatusResponse> status(String accountId, String operationId, boolean silent, boolean noOtp) {
        return new LatchResponse<StatusResponse>(super.status(accountId, operationId, silent, noOtp));
    }

    /**
     * Return application status for a given accountId while sending some custom data (Like OTP token or a message)
     * @param accountId The accountId which status is going to be retrieved
     * @param otpToken This will be the OTP sent to the user instead of generating a new one
     * @param otpMessage To attach a custom message with the OTP to the user
     * @return APIResponse containing the status
     */
    @Override
    public LatchResponse<StatusResponse> status(String accountId, String otpToken, String otpMessage) {
        return new LatchResponse<StatusResponse>(status(accountId, null, false, otpToken, otpMessage));
    }

    /**
     * Return application status for a given accountId while sending some custom data (Like OTP token or a message)
     * @param accountId The accountId which status is going to be retrieved
     * @param silent True for not sending lock/unlock push notifications to the mobile devices, false otherwise.
     * @param otpToken This will be the OTP sent to the user instead of generating a new one
     * @param otpMessage To attach a custom message with the OTP to the user
     * @return APIResponse containing the status
     */
    @Override
    public LatchResponse<StatusResponse> status(String accountId, boolean silent, String otpToken, String otpMessage) {
        return new LatchResponse<StatusResponse>(status(accountId, null, silent, otpToken, otpMessage));
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
    public LatchResponse<StatusResponse> status(String accountId, String operationId, boolean silent, String otpToken, String otpMessage) {
        return new LatchResponse<StatusResponse>(super.status(accountId, operationId, silent, otpToken, otpMessage));
    }

    @Deprecated
    public LatchResponse<StatusResponse> operationStatus(String accountId, String operationId) {
        return new LatchResponse<StatusResponse>(super.operationStatus(accountId, operationId));
    }

    @Deprecated
    public LatchResponse<StatusResponse> operationStatus(String accountId, String operationId, boolean silent, boolean noOtp) {
        return new LatchResponse<StatusResponse>(super.operationStatus(accountId, operationId, silent, noOtp));
    }

    @Override
    public LatchResponse<UnpairResponse> unpair(String id) {
        return new LatchResponse<UnpairResponse>(super.unpair(id));
    }

    @Override
    public LatchResponse<LockResponse> lock(String accountId) {
        return new LatchResponse<LockResponse>(super.lock(accountId));
    }

    @Override
    public LatchResponse<LockResponse> lock(String accountId, String operationId) {
        return new LatchResponse<LockResponse>(super.lock(accountId, operationId));
    }

    @Override
    public LatchResponse<UnlockResponse> unlock(String accountId) {
        return new LatchResponse<UnlockResponse>(super.unlock(accountId));
    }

    @Override
    public LatchResponse<UnlockResponse> unlock(String accountId, String operationId) {
        return new LatchResponse<UnlockResponse>(super.unlock(accountId, operationId));
    }

    @Override
    public LatchResponse<HistoryResponse> history(String accountId) {
        return new LatchResponse<HistoryResponse>(super.history(accountId));
    }

    @Override
    public LatchResponse<HistoryResponse> history(String accountId, Long from, Long to) {
        return new LatchResponse<HistoryResponse>(super.history(accountId, from, to));
    }

    @Override
    public LatchResponse<OperationsResponse> createOperation(String parentId, String name, String twoFactor, String lockOnRequest) {
        return new LatchResponse<OperationsResponse>(super.createOperation(parentId, name, twoFactor, lockOnRequest));
    }

    @Override
    public LatchResponse<OperationsResponse> removeOperation(String operationId) {
        return new LatchResponse<OperationsResponse>(super.removeOperation(operationId));
    }

    @Override
    public LatchResponse<OperationsResponse> getOperations() {
        return new LatchResponse<OperationsResponse>(super.getOperations());
    }

    @Override
    public LatchResponse<OperationsResponse> getOperations(String operationId) {
        return new LatchResponse<OperationsResponse>(super.getOperations(operationId));
    }

    @Override
    public LatchResponse<OperationsResponse> updateOperation(String operationId, String name, String twoFactor, String lockOnRequest) {
        return new LatchResponse<OperationsResponse>(super.updateOperation(operationId, name, twoFactor, lockOnRequest));
    }
}
