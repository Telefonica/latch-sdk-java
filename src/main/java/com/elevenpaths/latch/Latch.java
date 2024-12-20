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


package com.elevenpaths.latch;

/**
 *
 * @deprecated This class is now deprecated. Use LatchApp or Latch user instead.
 */
@Deprecated
public class Latch extends LatchApp{

    /**
     * Create an instance of the class with the Application ID and secret obtained from Eleven Paths
     * @param appId
     * @param secretKey
     */
    @Deprecated
    public Latch(String appId, String secretKey){
        super(appId,secretKey);
    }
}
