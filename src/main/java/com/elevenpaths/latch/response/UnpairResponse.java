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

public class UnpairResponse implements JsonResponse {

    private boolean accountNotPaired;
    private boolean missingParameter;
    private boolean unpaired;

    @Override
    public void build(JsonElement json) {
        this.unpaired = !json.getAsJsonObject().has("data");

        if (json.getAsJsonObject().has("error")) {
            JsonObject error = json.getAsJsonObject().getAsJsonObject("error");
            if (error.has("code")) {
                this.accountNotPaired = error.get("code").getAsInt() == 204 || error.get("code").getAsInt() == 201;
                this.missingParameter = error.get("code").getAsInt() == 401;
            }
        }
    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    public boolean isAccountNotPaired() {
        return accountNotPaired;
    }

    public boolean isMissingParameter() {
        return missingParameter;
    }

    public boolean isUnpaired() {
        return unpaired;
    }
}
