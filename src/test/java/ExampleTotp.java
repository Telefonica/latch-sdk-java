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

public class ExampleTotp {

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

    public static void createTotp() {
        LatchApp latchApp = new LatchApp(appId, secretId);
        String id = readInput("Enter the name displayed for the totp: ");
        String commonName = readInput("Enter name ");
        LatchResponse latchResponse = latchApp.createTotp(id,commonName);
        if (latchResponse.hasErrors()) {
            System.out.printf("Error creating the TOTP: %s%n", latchResponse.getError().getMessage());
        } else {
            System.out.println("Successful totp creating");
            System.out.printf("Totp Id (Save it, you'll need it later): %s%n", latchResponse.getData().get("totpId"));
            System.out.printf("QR (Scan the QR with the app, you can open it with any browse): %s%n", latchResponse.getData().get("qr"));
        }
    }

    public static void validateTotp() {
        LatchApp latchApp = new LatchApp(appId, secretId);
        String totpId = readInput("Enter the identifier for the totp: ");
        String code = readInput("Enter the code generated ");
        LatchResponse latchResponse = latchApp.validateTotp(totpId,code);
        if (latchResponse.hasErrors()) {
            System.out.printf("Error validating the TOTP: %s%n", latchResponse.getError().getMessage());
        } else {
            System.out.println("Successful totp validating");
        }
    }


    public static void main(String[] args) {
        createTotp();
        validateTotp();
    }
}
