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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperationsResponse implements JsonResponse {

    private static List<Operation> parseOperations(JsonObject json) {
        List<Operation> operations = new ArrayList<Operation>();
        if (json.has("operations")) {
            JsonObject operationsJson = json.getAsJsonObject("operations");
            for (Map.Entry<String, JsonElement> operationJson : operationsJson.entrySet()) {
                operations.add(Operation.create(operationJson.getKey(), (JsonObject) operationJson.getValue()));
            }
        }
        return operations;
    }

    private List<Operation> operations;
    private boolean operationNotFound;
    private boolean missingParameter;
    private boolean subscriptionLimits;

    @Override
    public void build(JsonElement json) {
        if (json.getAsJsonObject().has("data")) {
            JsonObject data = json.getAsJsonObject().getAsJsonObject("data");
            if (data.has("operations")) {
                operations = parseOperations(data);
            } else if (data.has("operationId")) {
                operations = new ArrayList<Operation>();
                operations.add(Operation.create(data.get("operationId").getAsString(), new JsonObject()));
            }
        }

        if (json.getAsJsonObject().has("error")) {
            JsonObject error = json.getAsJsonObject().getAsJsonObject("error");
            if (error.has("code")) {
                this.operationNotFound = error.get("code").getAsInt() == 301;
                this.missingParameter = error.get("code").getAsInt() == 401;
                this.subscriptionLimits = error.get("code").getAsInt() == 703;
            }
        }
    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public boolean isOperationNotFound() {
        return operationNotFound;
    }

    public boolean isMissingParameter() {
        return missingParameter;
    }

    public boolean isSubscriptionLimits() {
        return subscriptionLimits;
    }
}
