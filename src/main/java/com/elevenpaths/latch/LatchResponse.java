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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class models a response from any of the endpoints in the Latch API.
 * It consists of a "data" and an "error" elements. Although normally only one of them will be
 * present, they are not mutually exclusive, since errors can be non fatal, and therefore a response
 * could have valid information in the data field and at the same time inform of an error.
 */
public class LatchResponse {

    private JsonObject data = null;
    private Error error = null;

    public LatchResponse() {};

    /**
     *
     * @param json a json string received from one of the methods of the Latch API
     * @throws JsonParseException
     * @throws JsonSyntaxException
     */
    public LatchResponse(String json) {
        this(new JsonParser().parse(json));
    }

    /**
     * @param json a JsonElement created from the response of one of the methods of the Latch API
     * @throws NullPointerException when the json element is null, preventing the instantiation of the object
     */
    public LatchResponse(JsonElement json) {
        if (json.isJsonObject()) {
            if (json.getAsJsonObject().has("data")) {
                this.data = json.getAsJsonObject().getAsJsonObject("data");
            }
            if (json.getAsJsonObject().has("error")) {
                this.error = new Error(json.getAsJsonObject().getAsJsonObject("error").get("code").getAsInt(),
                            json.getAsJsonObject().getAsJsonObject("error").get("message").getAsString());
            }
        }
    }

    /**
     *
     * @return the data part of the API response
     */
    public JsonObject getData() {
        return data;
    }

    /**
     *
     * @param data the data to include in the API response
     */
    public void setData(JsonObject data) {
        this.data = data;
    }

    /**
     *
     * @return the error part of the API response, consisting of an error code and an error message
     */
    public Error getError() {
        return error;
    }

    /**
     *
     * @param error an error to include in the API response
     */
    public void setError(Error error) {
        this.error = error;
    }

    /**
     *
     * @return a JsonObject with the data and error parts set if they exist
     */
    public JsonObject toJSON() {
        JsonObject edition = new JsonObject();
        if(data != null) {
            edition.add("data", data);
        }
        if(error != null) {
            edition.add("error", getError().toJson());
        }
        return edition;
    }



}
