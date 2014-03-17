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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.JsonElement;
import com.ning.http.util.Base64;

public class Latch {
	private static final String API_VERSION = "0.6";
    public static String API_HOST = "https://latch.elevenpaths.com";
	public static final String API_CHECK_STATUS_URL = "/api/"+API_VERSION+"/status";
	public static final String API_PAIR_URL = "/api/"+API_VERSION+"/pair";
	public static final String API_PAIR_WITH_ID_URL = "/api/"+API_VERSION+"/pairWithId";
	public static final String API_UNPAIR_URL = "/api/"+API_VERSION+"/unpair";

	public static final String X_11PATHS_HEADER_PREFIX = "X-11paths-";
	private static final String X_11PATHS_HEADER_SEPARATOR = ":";

	public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
	public static final String DATE_HEADER_NAME = X_11PATHS_HEADER_PREFIX + "Date";
	public static final String AUTHORIZATION_METHOD = "11PATHS";
	private static final String AUTHORIZATION_HEADER_FIELD_SEPARATOR = " ";

	public static final String UTC_STRING_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final String HMAC_ALGORITHM = "HmacSHA1";


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


	private String appId;
	private String secretKey;

	/**
	 * Create an instance of the class with the Application ID and secret obtained from Eleven Paths
	 * @param appId
	 * @param secretKey
	 */
	public Latch (String appId, String secretKey){
		this.appId = appId;
		this.secretKey = secretKey;
	}

	public JsonElement HTTP_GET(String URL, Map<String, String>headers) {
		throw new RuntimeException("To use this method you need to specify how to perform an HTTP request. Extend this class and overload the HTTP_GET method.");
	}

	private LatchResponse HTTP_GET_proxy(String url) {
		return new LatchResponse(HTTP_GET(API_HOST + url, authenticationHeaders("GET", url, null)));
	}
	public LatchResponse pairWithId(String id) {
		return HTTP_GET_proxy(new StringBuilder(API_PAIR_WITH_ID_URL).append("/").append(id).toString());

	}

	public LatchResponse pair(String token) {
		return HTTP_GET_proxy(new StringBuilder(API_PAIR_URL).append("/").append(token).toString());
	}

	public LatchResponse status(String accountId) {
		return HTTP_GET_proxy(new StringBuilder(API_CHECK_STATUS_URL).append("/").append(accountId).toString());
	}

	public LatchResponse operationStatus(String accountId, String operationId) {
		return HTTP_GET_proxy(new StringBuilder(API_CHECK_STATUS_URL).append("/").append(accountId).append("/op/").append(operationId).toString());
	}

	public LatchResponse unpair(String id) {
		return HTTP_GET_proxy(new StringBuilder(API_UNPAIR_URL).append("/").append(id).toString());
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
			return Base64.encode(mac.doFinal(data.getBytes()));
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * Calculate the authentication headers to be sent with a request to the API
	 * @param HTTPMethod the HTTP Method, currently only GET is supported
	 * @param queryString the urlencoded string including the path (from the first forward slash) and the parameters
	 * @param xHeaders HTTP headers specific to the 11-paths API, excluding X-11Paths-Date. null if not needed.
	 * @return a map with the Authorization and X-11Paths-Date headers needed to sign a Latch API request
	 */
	public final Map<String, String> authenticationHeaders(String HTTPMethod, String queryString, Map<String,String>xHeaders) {
		String currentUTC=getCurrentUTC();
		return authenticationHeaders(HTTPMethod, queryString, xHeaders, currentUTC);
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
	//TODO: nonce
	public final Map<String, String> authenticationHeaders(String HTTPMethod, String queryString, Map<String,String>xHeaders, String utc) {
		StringBuilder stringToSign = new StringBuilder();
		stringToSign.append(HTTPMethod.toUpperCase().trim());
		stringToSign.append("\n");
		stringToSign.append(utc);
		stringToSign.append("\n");
		stringToSign.append(getSerializedHeaders(xHeaders));
		stringToSign.append("\n");
		stringToSign.append(queryString.trim());

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
	 *
	 * @return a string representation of the current time in UTC to be used in a Date HTTP Header
	 */
	private final String getCurrentUTC() {
		final SimpleDateFormat sdf = new SimpleDateFormat(UTC_STRING_FORMAT);
	    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    return sdf.format(new Date());

	}
}
