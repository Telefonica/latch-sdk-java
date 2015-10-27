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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ning.http.util.Base64;

/**
 * This class models an allows the user to make signed request the Latch API
 * Use the methods inside LatchAPI and LatchUser class.
 */
public class LatchAuth {
    protected static final String API_VERSION = "1.0";
    public static String API_HOST = "https://latch.elevenpaths.com";

    //App API
    public static final String API_CHECK_STATUS_URL = "/api/"+API_VERSION+"/status";
    public static final String API_PAIR_URL = "/api/"+API_VERSION+"/pair";
    public static final String API_PAIR_WITH_ID_URL = "/api/"+API_VERSION+"/pairWithId";
    public static final String API_UNPAIR_URL = "/api/"+API_VERSION+"/unpair";
    public static final String API_LOCK_URL = "/api/"+API_VERSION+"/lock";
    public static final String API_UNLOCK_URL = "/api/"+API_VERSION+"/unlock";
    public static final String API_HISTORY_URL = "/api/"+API_VERSION+"/history";
    public static final String API_OPERATION_URL = "/api/"+API_VERSION+"/operation";

    //User API
    public static final String API_APPLICATION_URL = "/api/"+API_VERSION+"/application";
    public static final String API_SUBSCRIPTION_URL = "/api/"+API_VERSION+"/subscription";

    public static final String X_11PATHS_HEADER_PREFIX = "X-11paths-";
    private static final String X_11PATHS_HEADER_SEPARATOR = ":";

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String DATE_HEADER_NAME = X_11PATHS_HEADER_PREFIX + "Date";
    public static final String AUTHORIZATION_METHOD = "11PATHS";
    private static final String AUTHORIZATION_HEADER_FIELD_SEPARATOR = " ";

    public static final String UTC_STRING_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String HMAC_ALGORITHM = "HmacSHA1";

    private static final String CHARSET_ASCII = "US-ASCII";
    private static final String CHARSET_ISO_8859_1 = "ISO-8859-1";
    private static final String CHARSET_UTF_8 = "UTF-8";
    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_DELETE = "DELETE";
    private static final String HTTP_HEADER_CONTENT_LENGTH  = "Content-Length";
    private static final String HTTP_HEADER_CONTENT_TYPE  = "Content-Type";
    private static final String HTTP_HEADER_CONTENT_TYPE_FORM_URLENCODED  = "application/x-www-form-urlencoded";
    private static final String PARAM_SEPARATOR = "&";
    private static final String PARAM_VALUE_SEPARATOR = "=";

    public static void setHost(String host) {
        API_HOST = host;
    }

    /**
     * The custom header consists of three parts, the method, the appId and the signature
     * This method returns the specified part if it exists.
     * @param part The zero indexed part to be returned
     * @param header The HTTP header value from which to extract the part
     * @return the specified part from the header or an empty string if not existent
     */
    private static final String getPartFromHeader(int part, String header) {
        if (header != null) {
            String[] parts = header.split(AUTHORIZATION_HEADER_FIELD_SEPARATOR);
            if(parts.length > part) {
                return parts[part];
            }
        }
        return "";
    }

    /**
     *
     * @param authorizationHeader Authorization HTTP Header
     * @return the Authorization method. Typical values are "Basic", "Digest" or "11PATHS"
     */
    public static final String getAuthMethodFromHeader(String authorizationHeader) {
        return getPartFromHeader(0, authorizationHeader);
    }

    /**
     *
     * @param authorizationHeader Authorization HTTP Header
     * @return the requesting application Id. Identifies the application using the API
     */
    public static final String getAppIdFromHeader(String authorizationHeader) {
        return getPartFromHeader(1, authorizationHeader);
    }

    /**
     *
     * @param authorizationHeader Authorization HTTP Header
     * @return the signature of the current request. Verifies the identity of the application using the API
     */
    public static final String getSignatureFromHeader(String authorizationHeader) {
        return getPartFromHeader(2, authorizationHeader);
    }


    protected String appId;
    protected String secretKey;


    public JsonElement HTTP_GET(String URL, Map<String, String> headers) {
        return HTTP(URL, "GET", headers, null);
    }

    public JsonElement HTTP_POST(String URL, Map<String, String> headers, Map<String, String> data) {
        return HTTP(URL, "POST", headers, data);
    }

    public JsonElement HTTP_DELETE(String URL, Map<String, String> headers) {
        return HTTP(URL, "DELETE", headers, null);
    }

    public JsonElement HTTP_PUT(String URL, Map<String, String> headers, Map<String, String> data) {
        return HTTP(URL, "PUT", headers, data);
    }

    protected LatchResponse HTTP_GET_proxy(String url) {
        try {
            return new LatchResponse(HTTP_GET(API_HOST + url, authenticationHeaders("GET", url, null, null)));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    protected LatchResponse HTTP_POST_proxy(String url){
        return HTTP_POST_proxy(url, new HashMap<String, String>());
    }

    protected LatchResponse HTTP_POST_proxy(String url, Map<String, String> data) {
        try {
            return new LatchResponse(HTTP_POST(API_HOST + url, authenticationHeaders("POST", url, null, data), data));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    protected LatchResponse HTTP_DELETE_proxy(String url) {
        try {
            return new LatchResponse(HTTP_DELETE(API_HOST + url, authenticationHeaders("DELETE", url, null, null)));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    protected LatchResponse HTTP_PUT_proxy(String url, Map<String, String> data) {
        try {
            return new LatchResponse(HTTP_PUT(API_HOST + url, authenticationHeaders("PUT", url, null, data), data));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     *
     * @param data the string to sign
     * @return base64 encoding of the HMAC-SHA1 hash of the data parameter using {@code secretKey} as cipher key.
     */
    private String signData (String data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(keySpec);
            return Base64.encode(mac.doFinal(data.getBytes(CHARSET_ISO_8859_1))); // data is ASCII except HTTP header values which can be ISO_8859_1
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Calculates the headers to be sent with a request to the API so the server
     * can verify the signature
     * <p>
     * Calls {@link #authenticationHeaders(String, String, Map, Map, String)}
     * with the current date as {@code utc}.
     * @param method The HTTP request method.
     * @param querystring The urlencoded string including the path (from the
     *        first forward slash) and the parameters.
     * @param xHeaders The HTTP request headers specific to the API, excluding
     *        X-11Paths-Date. null if not needed.
     * @param params The HTTP request params. Must be only those to be sent in
     *        the body of the request and must be urldecoded. null if not
     *        needed.
     * @return A map with the {@value AUTHORIZATION_HEADER_NAME} and {@value
     *         DATE_HEADER_NAME} headers needed to be sent with a request to the
     *         API.
     * @throws UnsupportedEncodingException If {@value CHARSET_UTF_8} charset is
     *         not supported.
     */
    private final Map<String, String> authenticationHeaders(String method, String querystring, Map<String, String> xHeaders, Map<String, String> params) throws UnsupportedEncodingException {
        return authenticationHeaders(method, querystring, xHeaders, params, getCurrentUTC());
    }

    /**
     *
     * Calculate the authentication headers to be sent with a request to the API
     * @param HTTPMethod the HTTP Method, currently only GET is supported
     * @param queryString the urlencoded string including the path (from the first forward slash) and the parameters
     * @param xHeaders HTTP headers specific to the 11-paths API, excluding X-11Paths-Date. null if not needed.
     * @param utc the Universal Coordinated Time for the X-11Paths-Date HTTP header
     * @return a map with the Authorization and X-11Paths-Date headers needed to sign a Latch API request
     */
    private final Map<String, String> authenticationHeaders(String HTTPMethod, String queryString, Map<String,String>xHeaders, Map<String, String> params, String utc) throws UnsupportedEncodingException {
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append(HTTPMethod.toUpperCase().trim());
        stringToSign.append("\n");
        stringToSign.append(utc);
        stringToSign.append("\n");
        stringToSign.append(getSerializedHeaders(xHeaders));
        stringToSign.append("\n");
        stringToSign.append(queryString.trim());
        if (params != null && !params.isEmpty()) {
            String serializedParams = getSerializedParams(params);
            if (serializedParams != null && serializedParams.length() != 0) {
                stringToSign.append("\n");
                stringToSign.append(serializedParams);
            }
        }
        String signedData = signData(stringToSign.toString());
        String authorizationHeader = new StringBuilder(AUTHORIZATION_METHOD)
            .append(AUTHORIZATION_HEADER_FIELD_SEPARATOR)
            .append(this.appId)
            .append(AUTHORIZATION_HEADER_FIELD_SEPARATOR)
            .append(signedData)
            .toString();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(AUTHORIZATION_HEADER_NAME, authorizationHeader);
        headers.put(DATE_HEADER_NAME, utc);
        return headers;
    }

    /**
     * Prepares and returns a string ready to be signed from the 11-paths specific HTTP headers received
     * @param xHeaders a non necessarily ordered map of the HTTP headers to be ordered without duplicates.
     * @return a String with the serialized headers, an empty string if no headers are passed, or null if there's a problem
     * such as non specific 11paths headers
     */
    private String getSerializedHeaders(Map<String, String> xHeaders) {
        if(xHeaders != null) {
            TreeMap<String,String> sortedMap = new TreeMap<String,String>();
            for(String key : xHeaders.keySet()) {
                if(!key.toLowerCase().startsWith(X_11PATHS_HEADER_PREFIX.toLowerCase())) {
                    //TODO: Log this better
                    System.err.println("Error serializing headers. Only specific " + X_11PATHS_HEADER_PREFIX + " headers need to be singed");
                }
                sortedMap.put(key.toLowerCase(), xHeaders.get(key).replace("\n", " "));
            }
            StringBuilder serializedHeaders = new StringBuilder();
            for(String key : sortedMap.keySet()) {
                serializedHeaders.append(key).append(X_11PATHS_HEADER_SEPARATOR).append(sortedMap.get(key)).append(" ");
            }
            return serializedHeaders.toString().trim();
        } else {
            return "";
        }
    }

    /**
     * Prepares and returns a string ready to be signed from the params of an
     * HTTP request
     * <p>
     * The params must be only those included in the body of the HTTP request
     * when its content type is application/x-www-urlencoded and must be
     * urldecoded.
     * @param params The params of an HTTP request.
     * @return A serialized representation of the params ready to be signed.
     *         null if there are no valid params.
     * @throws UnsupportedEncodingException If {@value CHARSET_UTF_8} charset is
     *         not supported.
     */
    private String getSerializedParams(Map<String, String> params) throws UnsupportedEncodingException {
        String rv = null;
        if (params != null && !params.isEmpty()) {
            TreeMap<String, String> sortedParams = new TreeMap<String, String>();
            for (String key : params.keySet()) {
                if (key != null && params.get(key) != null) {
                    sortedParams.put(key, params.get(key));
                }
            }
            StringBuilder serializedParams = new StringBuilder();
            for (String key : sortedParams.keySet()) {
                serializedParams.append(URLEncoder.encode(key, CHARSET_UTF_8));
                serializedParams.append(PARAM_VALUE_SEPARATOR);
                serializedParams.append(URLEncoder.encode(sortedParams.get(key), CHARSET_UTF_8));
                if (!key.equals(sortedParams.lastKey())) {
                    serializedParams.append(PARAM_SEPARATOR);
                }
            }
            if (serializedParams.length() > 0) {
                rv = serializedParams.toString();
            }
        }
        return rv;
    }
    
    /**
     *
     * @return a string representation of the current time in UTC to be used in a Date HTTP Header
     */
    private final String getCurrentUTC() {
        final SimpleDateFormat sdf = new SimpleDateFormat(UTC_STRING_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());

    }
    
    /**
     * Makes an HTTP request
     * @param URL The request URL.
     * @param method The request method.
     * @param headers Headers to add to the HTTP request.
     * @param data Parameters to add to the HTTP request body.
     * @return The server's JSON response or null if something has gone wrong.
     */
    private JsonElement HTTP(String URL, String method, Map<String, String> headers, Map<String, String> data) {

        JsonElement rv = null;
        InputStream is = null;
        OutputStream os = null;
        InputStreamReader isr = null;

        try {

            URL theURL = new URL(URL);
            HttpURLConnection theConnection = (HttpURLConnection) theURL.openConnection();

            theConnection.setRequestMethod(method);

            if (headers != null && !headers.isEmpty()) {
                Iterator<String> iterator = headers.keySet().iterator();
                while (iterator.hasNext()) {
                    String headerName = iterator.next();
                    theConnection.setRequestProperty(headerName, headers.get(headerName));
                }
            }

            if (!(HTTP_METHOD_GET.equals(method) || HTTP_METHOD_DELETE.equals(method))) {
                StringBuilder sb = new StringBuilder();
                if (data != null && !data.isEmpty()) {
                    String[] paramNames = new String[data.size()];
                    data.keySet().toArray(paramNames);
                    for (int i = 0; i < paramNames.length; i++) {
                        sb.append(URLEncoder.encode(paramNames[i], CHARSET_UTF_8));
                        sb.append(LatchAuth.PARAM_VALUE_SEPARATOR);
                        sb.append(URLEncoder.encode(data.get(paramNames[i]), CHARSET_UTF_8));
                        if (i < paramNames.length - 1) {
                            sb.append(PARAM_SEPARATOR);
                        }
                    }
                }
                byte[] body = sb.toString().getBytes(CHARSET_ASCII);
                theConnection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, HTTP_HEADER_CONTENT_TYPE_FORM_URLENCODED);
                theConnection.setRequestProperty(HTTP_HEADER_CONTENT_LENGTH, String.valueOf(body.length));
                theConnection.setDoOutput(true);
                os = theConnection.getOutputStream();
                os.write(body);
                os.flush();
            }

            JsonParser parser = new JsonParser();
            is = theConnection.getInputStream();
            isr = new InputStreamReader(is, CHARSET_UTF_8);
            rv = parser.parse(isr);

        } catch (MalformedURLException e) {
            System.err.println("The URL is malformed (" + URL + ")");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("An exception has been thrown when communicating with Latch backend");
            e.printStackTrace();
        } finally {

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    System.err.println("An exception has been thrown when trying to close the output stream");
                    e.printStackTrace();
                }
            }

            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    System.err.println("An exception has been thrown when trying to close the input stream reader");
                    e.printStackTrace();
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.err.println("An exception has been thrown when trying to close the input stream");
                    e.printStackTrace();
                }
            }

        }

        return rv;

    }
    
}
