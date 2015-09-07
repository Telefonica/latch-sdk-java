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
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Application {

    protected static Application create(String id, JsonObject json) {
        Application application = new Application();
        application.setId(id);
        if (json.has("status")) {
            application.setStatus(LatchStatus.valueOf(json.get("status").getAsString().toUpperCase()));
        }
        if (json.has("pairedOn")) {
            application.setPairedOn(new Date(json.get("pairedOn").getAsLong()));
        }
        if (json.has("name")) {
            application.setName(json.get("name").getAsString());
        }
        if (json.has("description")) {
            application.setDescription(json.get("description").getAsString());
        }
        if (json.has("imageURL")) {
            application.setImageUrl(json.get("imageURL").getAsString());
        }
        if (json.has("contactPhone")) {
            application.setContactPhone(json.get("contactPhone").getAsString());
        }
        if (json.has("contactEmail")) {
            application.setContactEmail(json.get("contactEmail").getAsString());
        }
        if (json.has("two_factor")) {
            application.setTwoFactor(TwoFactorStatus.valueOf(json.get("two_factor").getAsString().toUpperCase()));
        }
        if (json.has("lock_on_request")) {
            application.setLockOnRequest(LockOnRequestStatus.valueOf(json.get("lock_on_request").getAsString().toUpperCase()));
        }
        if (json.has("operations")) {
            List<Operation> operations = new ArrayList<Operation>();
            JsonObject operationsJson = json.getAsJsonObject("operations");
            for (Map.Entry<String, JsonElement> operationJson : operationsJson.entrySet()) {
                operations.add(Operation.create(operationJson.getKey(), (JsonObject) operationJson.getValue()));
            }
            application.setOperations(operations);
        }
        return application;
    }

    private String id;
    private LatchStatus status;
    private Date pairedOn;
    private String name;
    private String description;
    private String imageUrl;
    private String contactPhone;
    private String contactEmail;
    private TwoFactorStatus twoFactor;
    private LockOnRequestStatus lockOnRequest;
    private List<Operation> operations;

    protected Application() {}

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LatchStatus getStatus() {
        return status;
    }

    public void setStatus(LatchStatus status) {
        this.status = status;
    }

    public Date getPairedOn() {
        return pairedOn;
    }

    public void setPairedOn(Date pairedOn) {
        this.pairedOn = pairedOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public TwoFactorStatus getTwoFactor() {
        return twoFactor;
    }

    public void setTwoFactor(TwoFactorStatus twoFactor) {
        this.twoFactor = twoFactor;
    }

    public LockOnRequestStatus getLockOnRequest() {
        return lockOnRequest;
    }

    public void setLockOnRequest(LockOnRequestStatus lockOnRequest) {
        this.lockOnRequest = lockOnRequest;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
