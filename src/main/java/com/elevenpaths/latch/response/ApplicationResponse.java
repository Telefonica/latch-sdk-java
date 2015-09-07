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

public class ApplicationResponse implements JsonResponse {

    private String id;
    private String secret;
    private boolean notAuthorized;
    private boolean applicationNotFound;
    private boolean missingParameter;
    private boolean invalidParameterValue;
    private boolean subscriptionLimits;

    @Override
    public void build(JsonElement json) {
        if (json.getAsJsonObject().has("data")) {
            JsonObject data = json.getAsJsonObject().getAsJsonObject("data");
            if (data.has("applicationId")) {
                id = data.get("applicationId").getAsString();
            }
            if (data.has("secret")) {
                secret = data.get("secret").getAsString();
            }
        }

        if (json.getAsJsonObject().has("error")) {
            JsonObject error = json.getAsJsonObject().getAsJsonObject("error");
            if (error.has("code")) {
                this.notAuthorized = error.get("code").getAsInt() == 111;
                this.applicationNotFound = error.get("code").getAsInt() == 301;
                this.missingParameter = error.get("code").getAsInt() == 401;
                this.invalidParameterValue = error.get("code").getAsInt() == 402;
                this.subscriptionLimits = error.get("code").getAsInt() == 703;
            }
        }
    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public boolean isNotAuthorized() {
        return notAuthorized;
    }

    public boolean isApplicationNotFound() {
        return applicationNotFound;
    }

    public boolean isMissingParameter() {
        return missingParameter;
    }

    public boolean isInvalidParameterValue() {
        return invalidParameterValue;
    }

    public boolean isSubscriptionLimits() {
        return subscriptionLimits;
    }
}
