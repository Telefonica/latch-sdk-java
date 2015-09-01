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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Operation {

    protected static Operation create(String id, JsonObject json) {
        String name = "";
        TwoFactorStatus twoFactor = null;
        LockOnRequestStatus lockOnRequest = null;
        List<Operation> operations = new ArrayList<Operation>();
        if (json.has("name")) {
            name = json.get("name").getAsString();
        }
        if (json.has("two_factor")) {
            twoFactor = TwoFactorStatus.valueOf(json.get("two_factor").getAsString().toUpperCase());
        }
        if (json.has("lock_on_request")) {
            lockOnRequest = LockOnRequestStatus.valueOf(json.get("lock_on_request").getAsString().toUpperCase());
        }
        if (json.has("operations")) {
            JsonObject operationsJson = json.getAsJsonObject("operations");
            for (Map.Entry<String, JsonElement> operationJson : operationsJson.entrySet()) {
                operations.add(create(operationJson.getKey(), (JsonObject) operationJson.getValue()));
            }
        }
        return new Operation(id, name, twoFactor, lockOnRequest, operations);
    }

    private String id;
    private String name;
    private TwoFactorStatus twoFactor;
    private LockOnRequestStatus lockOnRequest;
    private List<Operation> operations;


    private Operation(String id, String name, TwoFactorStatus twoFactor, LockOnRequestStatus lockOnRequest, List<Operation> operations) {
        this.id = id;
        this.name = name;
        this.twoFactor = twoFactor;
        this.lockOnRequest = lockOnRequest;
        this.operations = operations;
    }
}
