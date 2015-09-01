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

public class LockResponse implements JsonResponse {

    private boolean lock;
    private boolean accountNotPaired;
    private boolean userNotAuthorized;
    private boolean missingParameter;

    @Override
    public void build(JsonElement json) {
        this.lock = !json.getAsJsonObject().has("data");

        if (json.getAsJsonObject().has("error")) {
            JsonObject error = json.getAsJsonObject().getAsJsonObject("error");
            if (error.has("code")) {
                this.userNotAuthorized = error.get("code").getAsInt() == 111;
                this.accountNotPaired = error.get("code").getAsInt() == 201;
                this.missingParameter = error.get("code").getAsInt() == 401;
            }
        }
    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    public boolean isLock() {
        return lock;
    }

    public boolean isAccountNotPaired() {
        return accountNotPaired;
    }

    public boolean isUserNotAuthorized() {
        return userNotAuthorized;
    }

    public boolean isMissingParameter() {
        return missingParameter;
    }
}