/*Latch Java SDK - Set of  reusable classes to  allow developers integrate Latch on their applications.
 Copyright (C) 2024 Telefonica Innovación Digital España S.L.

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.AssertJUnit.assertNull;

import com.elevenpaths.latch.LatchApp;
import com.elevenpaths.latch.LatchUser;
import com.elevenpaths.latch.LatchResponse;
import com.google.gson.JsonElement;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.Set;

class LatchTest {


    String account_id;
    String app_id;
    String secret_id;
    String operator_id;
    String user_id;
    String user_secret;

    LatchApp latchApp;

    @BeforeEach
    void setUp() {
        DotenvBuilder dotenv = Dotenv.configure()
                .directory("src/test/resources/")
                .filename(".env"); // instead of '.env', use 'env'

        this.account_id = dotenv.load().get("account_id");
        this.app_id = dotenv.load().get("app_id");
        this.secret_id = dotenv.load().get("secret_id");
        this.operator_id = dotenv.load().get("operator_id");
        this.user_id = dotenv.load().get("user_id");
        this.user_secret = dotenv.load().get("user_secret");
        this.latchApp = new LatchApp(this.app_id, this.secret_id);

    }

    @Test
    @DisplayName("First test latch")
    void testAppLatchPairInvalidToken() {
        LatchResponse response = this.latchApp.pair("fP9zpg");
        assertEquals (response.hasErrors(), true);
        assertEquals(response.getError().getMessage(), "Token not found or expired");


    }

    @Test
    @DisplayName("Test crud operation")
    void test_crud_operation() {
        LatchResponse response = this.latchApp.createOperation(this.app_id, "operation_test_1", "DISABLED", "DISABLED");
        String operation_id = response.getData().get("operationId").getAsString();
        response = this.latchApp.updateOperation(operation_id, "operation_test_1_v2", "MANDATORY", "MANDATORY");
        assertEquals(null, response.getData());
        assertEquals(null, response.getError());
        response = this.latchApp.createOperation(operation_id, "sub_operation_test_1", "DISABLED", "DISABLED");
        String sub_operation_id = response.getData().get("operationId").getAsString();
        response = this.latchApp.getOperations(operation_id);
        assertEquals("sub_operation_test_1", response.getData().get("operations").
                getAsJsonObject().get(sub_operation_id)
                .getAsJsonObject().get("name").getAsString());

        response = this.latchApp.removeOperation(sub_operation_id);
        assertEquals(null, response.getData());
        assertEquals(null, response.getError());

        response = this.latchApp.removeOperation(operation_id);
        assertEquals(null, response.getData());
        assertEquals(null, response.getError());


    }

    @Test
    @DisplayName("Test crud instance")
    void test_crud_instance() {
        LatchResponse response = this.latchApp.createOperation(this.app_id, "operation_test_1", "DISABLED", "DISABLED");
        String operation_id = response.getData().get("operationId").getAsString();
        response = this.latchApp.addInstance(this.account_id, operation_id, "Instance1");
        Set<Map.Entry<String, JsonElement>> entries = response.getData().get("instances").getAsJsonObject().entrySet();
        String instanceId = null;
        for (Map.Entry<String, JsonElement> entry: entries) {
            instanceId = entry.getKey();
        }
        response = this.latchApp.removeInstance(this.account_id, operation_id, instanceId);
        assertNull(null, response.getError());
    }

    @Test
    @DisplayName("Test latch user")
    void test_latch_user() {
        LatchUser latchUser = new LatchUser(this.user_id, this.user_secret);
        LatchResponse response = latchUser.createApplication("app2", "DISABLED", "DISABLED", "60000000", "mail@mailfake.com");
        String application_id = response.getData().get("applicationId").getAsString();

        response = latchUser.getApplications();
        Set<Map.Entry<String, JsonElement>> entries = response.getData().get("operations").getAsJsonObject().entrySet();
        boolean found = false;
        for (Map.Entry<String, JsonElement> entry: entries) {
            if(entry.getKey().equals(application_id)){
                found = true;
                break;
            }
        }
        assert found;
        response = latchUser.removeApplication(application_id);
        assertNull(null, response.getError());
        this.latchApp.status(this.account_id);
    }

    @Test
    @DisplayName("Test get status")
    void test_latch_status() {
        LatchResponse response = this.latchApp.status(this.account_id);
        String status_app = response.getData().get("operations").getAsJsonObject().get(this.app_id).getAsJsonObject().get("status").getAsString();
        assertEquals("on",status_app);
    }

    @Test
    @DisplayName("Test crud totp")
    void test_crud_totp() {
        LatchResponse response = this.latchApp.createTotp("TOTP_TEST","CommonName");
        String totpId = response.getData().get("totpId").getAsString();
        response = this.latchApp.getTotp(totpId);
        assertEquals(totpId,response.getData().get("totpId").getAsString());
        response = this.latchApp.validateTotp(totpId,"123456");
        assertEquals("Invalid totp code",response.getError().getMessage());
        assertEquals(306,response.getError().getCode());
        response = this.latchApp.removeTotp(totpId);
        assertNull(null, response.getError());
    }

    @Test
    @DisplayName("Test check control status")
    void test_control_status() {
        LatchResponse response = this.latchApp.checkControlStatus("12345");
        assertEquals("Authorization control not found",response.getError().getMessage());
        assertEquals(1100,response.getError().getCode());
    }
}
