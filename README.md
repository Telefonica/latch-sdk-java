### LATCH JAVA SDK ###


#### PREREQUISITES ####

* Java 1.6 or above.

* Read API documentation (https://latch.elevenpaths.com/www/developers/doc_api).

* To get the "Application ID" and "Secret", (fundamental values for integrating Latch in any application), it’s necessary to register a developer account in Latch's website: https://latch.elevenpaths.com. On the upper right side, click on "Developer area".


#### USING THE SDK IN JAVA ####

* Include all SDK files and dependencies in your project.

* Create a new class (for example LatchSDK.java) extending Latch.java. This class should override HTTP_GET method with your own implementation to perform HTTP requests as you usually perform them in your project.
```
     public class LatchSDK extends Latch{

          @Override
          public JsonElement HTTP_GET(String URL, Map<String, String>headers) {
               //Your implementation here
          }
     }
```

* Create a Latch object with the "Application ID" and "Secret" previously obtained.
```
     LatchSDK latch = new LatchSDK(APP_ID, SECRET);
```

* Call to Latch Server. Pairing will return an account id that you should store for future api calls
```
     LatchResponse pairResponse = latch.pair(ACCOUNT_ID);
     LatchResponse statusResponse = latch.status(ACCOUNT_ID);
     LatchResponse unpairResponse = latch.unpair(ACCOUNT_ID);
```

* After every API call, get Latch response data and errors and handle them.
```
     JsonObject jObject = latchResponse.getData();
     com.elevenpaths.latch.Error error = latchResponse.getError();
```
