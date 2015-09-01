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
package com.elevenpaths.latch.response;

import com.elevenpaths.api.JsonResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class StatusResponse implements JsonResponse {

    private static String parseOperationId(JsonObject json) {
        if (json.has("operations")) {
            JsonObject operations = json.getAsJsonObject("operations");
            return operations.entrySet().iterator().next().getKey();
        }
        return null;
    }

    private static LatchStatus parseOperationStatus(String operationId, JsonObject json) {
        if (json.has("operations")) {
            JsonObject operations = json.getAsJsonObject("operations");
            if (operations.has(operationId)) {
                JsonObject operation = operations.get(operationId).getAsJsonObject();
                if (operation.has("status")) {
                    return operation.get("status").getAsString().equals("on") ? LatchStatus.ON :  LatchStatus.OFF;
                }
            }
        }
        return null;
    }

    private static TwoFactor parseTwoFactor(String operationId, JsonObject json) {
        if (json.has("operations")) {
            JsonObject operations = json.getAsJsonObject("operations");
            if (operations.has(operationId)) {
                JsonObject operation = operations.get(operationId).getAsJsonObject();
                if (operation.has("two_factor")) {
                    return TwoFactor.create(operation.get("two_factor").getAsJsonObject());
                }
            }
        }
        return null;
    }

    private String operationId;
    private LatchStatus status;
    private TwoFactor twoFactor;
    private boolean accountNotPaired;
    private boolean missingParameter;
    private boolean subscriptionLimits;

    @Override
    public void build(JsonElement json) {
        if (json.getAsJsonObject().has("data")) {
            JsonObject data = json.getAsJsonObject().getAsJsonObject("data");
            operationId = parseOperationId(data);
            status = parseOperationStatus(operationId, data);
            twoFactor = parseTwoFactor(operationId, data);
        }

        if (json.getAsJsonObject().has("error")) {
            JsonObject error = json.getAsJsonObject().getAsJsonObject("error");
            if (error.has("code")) {
                this.accountNotPaired = error.get("code").getAsInt() == 201;
                this.missingParameter = error.get("code").getAsInt() == 401;
                this.subscriptionLimits = error.get("code").getAsInt() == 704;
            }
        }
    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    public String getOperationId() {
        return operationId;
    }

    public LatchStatus getStatus() {
        return status;
    }

    public TwoFactor getTwoFactor() {
        return twoFactor;
    }

    public boolean isAccountNotPaired() {
        return accountNotPaired;
    }

    public boolean isMissingParameter() {
        return missingParameter;
    }

    public boolean isSubscriptionLimits() {
        return subscriptionLimits;
    }
}
