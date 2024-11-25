/*Latch Java SDK - Set of  reusable classes to  allow developers integrate Latch on their applications.
 Copyright (C) 2024 Telefonica Innovación Digital

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

import com.elevenpaths.latch.LatchApp;
import com.elevenpaths.latch.LatchResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ExampleWeb3 {

    public static String appId = "<Your appId>";


    public static String secretId = "<your secret id>";

    public static String wallet = "<Your public key of your wallet>";

    public static String signature = "<Sign the message \"Latch-Web3\" with your wallet >";

    public static String accountName = "<Your account id>";

    public static String operationId =  "<The opperation id>";


    public static String readInput() {
        System.out.print("Enter token: ");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        try {
            String name = reader.readLine();
            return name;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String pair(String app_id, String secret_id) {
        LatchApp latchApp = new LatchApp(app_id, secret_id);
        String token = readInput();
        String accountId = null;
        LatchResponse latchResponse = latchApp.pair(token, wallet, signature);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error pairing: %s", latchResponse.getError().getMessage()));
        } else {
            accountId = latchResponse.getData().get("accountId").getAsString();
        }
        return accountId;

    }

    public static String pairWithId(String app_id, String secret_id) {
        LatchApp latchApp = new LatchApp(app_id, secret_id);
        LatchResponse latchResponse = latchApp.pairWithId(accountName, wallet, signature);
        String accountId = null;
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error pairing: %s", latchResponse.getError().getMessage()));
        } else {
            accountId = latchResponse.getData().get("accountId").getAsString();
        }
        return accountId;
    }

    public static void unPair(String app_id, String secret_id, String accountId) {
        LatchApp latchApp = new LatchApp(app_id, secret_id);
        LatchResponse latchResponse = latchApp.unlock(accountId);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error unpairing: %s", latchResponse.getError().getMessage()));
        } else {
            System.out.println("Succesfull Unpairing");
        }


    }

    public static void getStatus(String app_id, String secret_id, String accountId) {
        LatchApp latchApp = new LatchApp(app_id, secret_id);
        LatchResponse latchResponse = latchApp.status(accountId);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error unpairing: %s", latchResponse.getError().getMessage()));
        } else {
            System.out.println(String.format("Status %s", latchResponse.getData().toString()));
        }


    }

    public static void getOperationStatus(String app_id, String secret_id, String accountId) {
        LatchApp latchApp = new LatchApp(app_id, secret_id);
        LatchResponse latchResponse = latchApp.operationStatus(accountId, operationId);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error unpairing: %s", latchResponse.getError().getMessage()));
        } else {
            System.out.println(String.format("Status %s", latchResponse.getData().toString()));
        }
    }

    public static void lock(String app_id, String secret_id, String accountId) {
        LatchApp latchApp = new LatchApp(app_id, secret_id);
        LatchResponse latchResponse = latchApp.lock(accountId);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error locking: %s", latchResponse.getError().getMessage()));
        } else {
            System.out.println("Succesfull locking");
        }

    }

    public static void unlock(String app_id, String secret_id, String accountId) {
        LatchApp latchApp = new LatchApp(app_id, secret_id);
        LatchResponse latchResponse = latchApp.unlock(accountId);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error unlocking: %s", latchResponse.getError().getMessage()));
        } else {
            System.out.println("Succesfull unlocking");
        }

    }

    public static void unpair(String app_id, String secret_id, String accountId) {
        LatchApp latchApp = new LatchApp(app_id, secret_id);
        LatchResponse latchResponse = latchApp.unpair(accountId);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error unpairing: %s", latchResponse.getError().getMessage()));
        } else {
            System.out.println("Succesfull unpairing");
        }

    }


    public static void main(String[] args) {
        String accountId = pair(appId, secretId);
        getStatus(appId, secretId, accountId);
        unPair(appId, secretId, accountId);

    }
}
