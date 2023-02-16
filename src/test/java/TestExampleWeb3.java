

import com.elevenpaths.latch.LatchApp;
import com.elevenpaths.latch.LatchUser;
import com.elevenpaths.latch.LatchResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;

public class TestExampleWeb3 {

    public static String appId = "<Your appId>";


    public static String secretId = "<your secret id>";

    public static String wallet = "<Your public key of your wallet>";

    public static String signature = "<Sign the message \"Latch-Web3\" with your wallet >";


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

    public static void main(String[] args) {
        String accountId = pair(appId, secretId);
        getStatus(appId, secretId, accountId);
        unPair(appId, secretId, accountId);

    }
}
