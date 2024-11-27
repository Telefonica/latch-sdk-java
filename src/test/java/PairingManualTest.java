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

public class PairingManualTest {

    public static String appId = "<Your appId>";
    public static String secretId = "<your secret id>";

    public static String readInput(String msg) {
        System.out.print(msg);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String pair() {
        LatchApp latchApp = new LatchApp(appId, secretId);
        String token = readInput("Enter the pairing code (generated from mobile app): ");

        String accountId = null;
        LatchResponse latchResponse = latchApp.pair(token);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error pairing: %s", latchResponse.getError().getMessage()));
        } else {
            accountId = latchResponse.getData().get("accountId").getAsString();
        }
        return accountId;
    }

    public static String pairWithCommonName() {
        LatchApp latchApp = new LatchApp(appId, secretId);
        String token = readInput("Enter the pairing code (generated from mobile app): ");
        String commonName = readInput("Enter the common name: ");

        String accountId = null;
        LatchResponse latchResponse = latchApp.pair(token, commonName);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error pairing: %s", latchResponse.getError().getMessage()));
        } else {
            accountId = latchResponse.getData().get("accountId").getAsString();
        }
        return accountId;
    }

    public static String pairWithWeb3() {
        LatchApp latchApp = new LatchApp(appId, secretId);
        String token = readInput("Enter the pairing code (generated from mobile app): ");
        String web3Wallet = readInput("Enter the wallet for web3: ");
        String web3Signature = readInput("Enter the signature for web3: ");

        String accountId = null;
        LatchResponse latchResponse = latchApp.pair(token, web3Wallet, web3Signature);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error pairing: %s", latchResponse.getError().getMessage()));
        } else {
            accountId = latchResponse.getData().get("accountId").getAsString();
        }
        return accountId;
    }

    public static String pairWithWeb3CommonName() {
        LatchApp latchApp = new LatchApp(appId, secretId);
        String token = readInput("Enter the pairing code (generated from mobile app): ");
        String commonName = readInput("Enter the common name: ");
        String web3Wallet = readInput("Enter the wallet for web3: ");
        String web3Signature = readInput("Enter the signature for web3: ");

        String accountId = null;
        LatchResponse latchResponse = latchApp.pair(token, commonName, web3Wallet, web3Signature);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error pairing: %s", latchResponse.getError().getMessage()));
        } else {
            accountId = latchResponse.getData().get("accountId").getAsString();
        }
        return accountId;
    }

    public static void unpair(String accountId) {
        LatchApp latchApp = new LatchApp(appId, secretId);
        LatchResponse latchResponse = latchApp.unpair(accountId);
        if (latchResponse.hasErrors()) {
            System.out.println(String.format("Error unpairing: %s", latchResponse.getError().getMessage()));
        } else {
            System.out.println("Succesfull Unpairing");
        }
    }

    public static void main(String[] args) {
        String accountId;
        accountId = pair();
        unpair(accountId);
        accountId = pairWithCommonName();
        unpair(accountId);
        accountId = pairWithWeb3();
        unpair(accountId);
        accountId = pairWithWeb3CommonName();
        unpair(accountId);
    }
}
