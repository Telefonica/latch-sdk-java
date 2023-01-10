### LATCH JAVA SDK ###


#### PREREQUISITES ####

* Java 1.5 or above.

* Read API documentation (https://latch.telefonica.com/www/developers/doc_api).

* To get the "Application ID" and "Secret", (fundamental values for integrating Latch in any application), it’s necessary to register a developer account in Latch's website: https://latch.telefonica.com. On the upper right side, click on "Developer area".


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

#### USING JAVA SDK FOR WEB3 SERVICES ####

For using the Java SDK within an Web3 service, you must complain with the following:

* In the Latch website, having an developer account, with the Web3 permissions activated. You must see a new button for creating a Web3 new app.

* Call to Latch Server for pairing as usual, but with the newly methods:
```
     LatchResponse pairResponse = latch.pair(token, web3wallet, web3Signature);
```
The two additional parameters are:
- web3Wallet: The Ethereum-based address wallet for the user that wants to pair the service.
- web3Signature: A proof-of-ownership signature of a constant, in order to verify that the user owns the private key of the wallet.
Example in Javascript: [[https://web3js.readthedocs.io/en/v1.2.9/web3-eth.html#sign]]


#### TROUBLESHOOTING ####

*A javax.net.ssl.SSLHandshakeException with a nested sun.security.validator.ValidatorException is thrown when invoking an API call.*

This exception is normally thrown when the JDK doesn't trust the CA that signs the digital certificate used in Latch's website (https://latch.elevenpaths.com). You may need to install the CA (http://www.startssl.com/certs/ca.pem) as a trusted certificate in your JDK's truststore (normally in jre/lib/security/cacerts) using the keytool utility.
