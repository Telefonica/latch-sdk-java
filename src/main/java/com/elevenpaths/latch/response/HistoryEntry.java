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

import java.util.Date;

public class HistoryEntry {

    public static HistoryEntry create(JsonObject json) {
        HistoryEntry historyEntry = new HistoryEntry();
        if (json.has("t") && !json.get("t").isJsonNull()) {
            historyEntry.setTime(new Date(json.get("t").getAsLong()));
        }
        if (json.has("action") && !json.get("action").isJsonNull()) {
            historyEntry.setAction(json.get("action").getAsString());
        }
        if (json.has("what") && !json.get("what").isJsonNull()) {
            historyEntry.setWhat(json.get("what").getAsString());
        }
        if (json.has("value") && !json.get("value").isJsonNull()) {
            historyEntry.setValue(json.get("value").getAsString());
        }
        if (json.has("was") && !json.get("was").isJsonNull()) {
            historyEntry.setWas(json.get("was").getAsString());
        }
        if (json.has("name") && !json.get("name").isJsonNull()) {
            historyEntry.setName(json.get("name").getAsString());
        }
        if (json.has("userAgent") && !json.get("userAgent").isJsonNull()) {
            historyEntry.setUserAgent(json.get("userAgent").getAsString());
        }
        if (json.has("ip") && !json.get("ip").isJsonNull()) {
            historyEntry.setIp(json.get("ip").getAsString());
        }
        return historyEntry;
    }

    private Date time;
    private String action;
    private String what;
    private String value;
    private String was;
    private String name;
    private String userAgent;
    private String ip;

    public HistoryEntry() {}

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getWas() {
        return was;
    }

    public void setWas(String was) {
        this.was = was;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
