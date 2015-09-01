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

import com.google.gson.JsonObject;

public class ClientVersion {

    public static ClientVersion create(JsonObject json) {
        String platform = null;
        String app = null;
        if (json.has("platform")) {
            platform = json.get("platform").getAsString();
        }
        if (json.has("app")) {
            app = json.get("app").getAsString();
        }
        return new ClientVersion(platform, app);
    }

    private String platform;
    private String app;

    protected ClientVersion(String platform, String app) {
        this.platform = platform;
        this.app = app;
    }

    public String getPlatform() {
        return platform;
    }

    public String getApp() {
        return app;
    }
}
