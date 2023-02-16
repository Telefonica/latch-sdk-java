

import com.elevenpaths.latch.LatchApp;
import com.elevenpaths.latch.LatchUser;
import com.elevenpaths.latch.LatchResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;

public class TestExampleWeb3 {

    //public static String appId = "<Your appId>";
    public static String appId = "DXnMh3uCWL39upnNW2GE";

    public static String secretId = "DD3gGipwQ4FbZaFRgARUXCMCNfbrHii9hXiRgdxN";

    //public static String wallet = "<Your public key of your wallet>";
    public static String wallet = "0x7fd12f68757721e7671979db0eef8a8ddcfd69db";
    //public static String signature = "<Sign the message \"Latch-Web3\" with your wallet >";
    public static String signature = "0xe760de5774a623ced768c97d3e395e5a93f430b4f1e5407d0ebfd731a6f888612ac933f772807548fd81cb9672294994f35be9dc7ca59fb9c3455921438dae081b";


    public static String readInput(){
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

    public static void pair(String app_id, String secret_id){
        LatchApp latchApp = new LatchApp(app_id, secret_id);
        String token = readInput();
        LatchResponse latchResponse = latchApp.pair(token, wallet, signature);
        if (latchResponse.hasErrors()){
            System.out.println(String.format("Error pairing: %s", latchResponse.getError().getMessage()));
        }
        latchResponse.getData();

    }


    public static void main(String[] args) {
        pair(appId, secretId);
    }
}
