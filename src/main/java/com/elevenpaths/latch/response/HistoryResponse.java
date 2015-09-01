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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HistoryResponse implements JsonResponse {

    private static Application parseApplication(JsonElement json) {
        Application application = null;
        if (json.getAsJsonObject().has("data")) {
            JsonObject data = json.getAsJsonObject().getAsJsonObject("data");
            for (Map.Entry<String, JsonElement> applicationJson : data.entrySet()) {
                if (isApplicationID(applicationJson.getKey())) {
                    application = Application.create(applicationJson.getKey(), (JsonObject) applicationJson.getValue());
                    break;
                }
            }
        }
        return application;
    }

    private static boolean isApplicationID(String key) {
        return !key.equals("lastSeen") && !key.equals("clientVersion") && !key.equals("count") && !key.equals("history");
    }

    private Application application;
    private Date lastSeen;
    private List<ClientVersion> clientVersions;
    private List<HistoryEntry> history;
    private boolean userNotAuthorized;
    private boolean accountNotPaired;
    private boolean missingParameter;
    private boolean responseLimit;

    @Override
    public void build(JsonElement json) {
        if (json.getAsJsonObject().has("data")) {
            JsonObject data = json.getAsJsonObject().getAsJsonObject("data");
            if (data.has("lastSeen")) {
                lastSeen = new Date(data.get("lastSeen").getAsLong());
            }
            if (data.has("clientVersion")) {
                clientVersions = new ArrayList<ClientVersion>();
                JsonArray clientVersionsJson = data.getAsJsonArray("clientVersion");
                for (JsonElement clientVersionJson : clientVersionsJson) {
                    clientVersions.add(ClientVersion.create(clientVersionJson.getAsJsonObject()));
                }
            }
            if (data.has("history")) {
                history = new ArrayList<HistoryEntry>();
                JsonArray historyJson = data.getAsJsonArray("history");
                for (JsonElement historyEntryJson : historyJson) {
                    history.add(HistoryEntry.create(historyEntryJson.getAsJsonObject()));
                }
            }
            application = parseApplication(json);
        }

        if (json.getAsJsonObject().has("error")) {
            JsonObject error = json.getAsJsonObject().getAsJsonObject("error");
            if (error.has("code")) {
                this.userNotAuthorized = error.get("code").getAsInt() == 111;
                this.accountNotPaired = error.get("code").getAsInt() == 201;
                this.missingParameter = error.get("code").getAsInt() == 401;
                this.responseLimit = error.get("code").getAsInt() == 405;
            }
        }
    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    public Application getApplication() {
        return application;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public List<ClientVersion> getClientVersions() {
        return clientVersions;
    }

    public List<HistoryEntry> getHistory() {
        return history;
    }

    public int getCount() {
        return history != null ? history.size() : 0;
    }

    public boolean isUserNotAuthorized() {
        return userNotAuthorized;
    }

    public boolean isAccountNotPaired() {
        return accountNotPaired;
    }

    public boolean isMissingParameter() {
        return missingParameter;
    }

    public boolean isResponseLimit() {
        return responseLimit;
    }
}
