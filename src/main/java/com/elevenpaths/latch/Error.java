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

import com.google.gson.JsonObject;

public enum Error {

	/*********************** E1xx codes refer to apps authentication problems ***********************/
	/**
	 * Invalid Authorization header format
	 */
	E101(101, "Invalid Authorization header format"),
	/**
	 * Invalid application signature
	 */
	E102(102, "Invalid application signature"),
	/**
	 * Authorization header missing
	 */
	E103(103, "Authorization header missing"),
	/**
	 * Date header missing
	 */
	E104(104, "Date header missing"),
	/**
	 * Invalid credentials
	 */
	E105(105, "Invalid credentials"),
	/**
	 * Outdated client version. The app still works, but is not the latest one available for this platform
	 */
	E106(106, "Outdated client version. The app still works, but is not the latest one available for this platform"),
	/**
	 * Unsupported client version
	 */
	E107(107, "Unsupported client version"),
	/**
	 * Invalid date format
	 */
	E108(108, "Invalid date format"),
	/**
	 * Request expired, date is too old
	 */
	E109(109, "Request expired, date is too old"),


	/*********************** E2xx codes refer to problems with user accounts ***********************/
	/**
	 * Account not paired
	 */
	E201(201, "Account not paired"),
	/**
	 * Invalid account name
	 */
	E202(202, "Invalid account name"),
	/**
	 * Error pairing account
	 */
	E203(203, "Error pairing account"),
	/**
	 * Error unpairing account
	 */
	E204(204, "Error unpairing account"),
	/**
	 * Account and application already paired
	 */
	E205(205, "Account and application already paired"),
	/**
	 * Token not found or expired
	 */
	E206(206, "Token not found or expired"),
	/**
	 * Account not activated or disabled
	 */
	E207(207, "Token not found or expired"),
	/**
	 * Generic account error. Replace with custom message
	 */
	E208(208, "Generic account error. Replace with custom message"),
	/*********************** E3xx codes refer to application or operation problems ***********************/
	/**
	 * Application or Operation not found
	 */
	E301(301, "Application or Operation not found"),

	/*********************** E4xx codes refer to generic API problems ***********************/
	/**
	 * Missing parameter in API call
	 */
	E401(401, "Missing parameter in API call"),
	/**
	 * Invalid parameter value
	 */
	E402(402, "Invalid parameter value"),
	/*********************** E5xx codes refer to two factor authentication API problems ***********************/
	/**
	 * There isn't a token generated for this operation and user account
	 */
	E501(501, "There isn't a token generated for this operation and user account"),
	/*********************** E6xx codes refer to notification API problems ***********************/
    /**
     * Mozilla SimplePush: There's no information for this endpoint and version for this user.
     */
    E601(601, "Mozilla SimplePush: There's no information for this endpoint and version for this user.");

	private final int code;
	private String message;

	private Error(int code, String msg) {
		this.code = code;
		this.message = msg;
	}

	/**
	 *
	 * @param code
	 * @return the error corresponding to the received code
	 * @throws IllegalArgumentException if the code is not valid
	 */
	public static Error fromCode(int code) {
		return Error.fromCode(Integer.toString(code));
	}

	/**
	 *
	 * @param code
	 * @return the error corresponding to the received code
	 * @throws IllegalArgumentException if the code is not valid
	 * @throws
	 */
	public static Error fromCode(String code) {
		return Error.valueOf("E"+code);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * Customize the message of an error. Use this method sparsely, usually only
	 * when indicated, i.e. Error E208
	 * @param message The custom message to include in the error
	 */
	public Error customizeMessage(String message) {
		this.message = message;
		return this;
	}
	/**
	 *
	 * @return a JsonObject with the code and message of the error
	 */
	public JsonObject toJson() {
		JsonObject error = new JsonObject();
		error.addProperty("code", code);
		error.addProperty("message", message);
		return error;
	}

	@Override
	public String toString() {
		return toJson().toString();
	}
}
