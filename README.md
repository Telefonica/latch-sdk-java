### LATCH JAVA SDK ###


#### PREREQUISITES ####

* Java 1.5 or above.

* Read API documentation (https://latch.elevenpaths.com/www/developers/doc_api).

* To get the "Application ID" and "Secret", (fundamental values for integrating Latch in any application), it’s necessary to register a developer account in Latch's website: https://latch.elevenpaths.com. On the upper right side, click on "Developer area".


#### USING THE SDK IN JAVA ####

* Include all SDK files and dependencies in your project.

* Create a Latch object with the "Application ID" and "Secret" previously obtained.
```
     Latch latch = new Latch(APP_ID, SECRET);
```

* Call to Latch Server. Pairing will return an account id that you should store for future api calls
```
     LatchResponse pairResponse = latch.pair(TOKEN);
     LatchResponse statusResponse = latch.status(ACCOUNT_ID);
     LatchResponse opStatusResponse = latch.operationStatus(ACCOUNT_ID, OPERATION_ID);
     LatchResponse unpairResponse = latch.unpair(ACCOUNT_ID);
```

* After every API call, get Latch response data and errors and handle them.
```
     JsonObject jObject = latchResponse.getData();
     com.elevenpaths.latch.Error error = latchResponse.getError();
```
