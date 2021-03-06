/*
* The MIT License
*
* Copyright 2017 Entando Inc..
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
 */
package org.entando.entando.plugins.jpkiebpm.aps.system.services.kie;

import org.entando.entando.plugins.jpkiebpm.KieTestParameters;
import org.entando.entando.plugins.jpkiebpm.aps.ApsPluginBaseTestCase;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.helper.BpmToFormHelper;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.helper.FormToBpmHelper;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.model.*;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.model.override.DefaultValueOverride;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.model.override.DropDownOverride;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.model.override.OverrideList;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.model.override.PlaceHolderOverride;
import org.json.JSONObject;

import java.util.*;

import static org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.TestKieFormOverrideManager.*;

public class MortgageDemoTest extends ApsPluginBaseTestCase implements KieTestParameters {


    private IKieFormManager formManager;
    private IKieFormOverrideManager overrideManager;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        final String expected = SCHEMA + "://" + HOSTNAME + ":" + PORT + "/" + WEBAPP;
        KIEAuthenticationCredentials credentials = new KIEAuthenticationCredentials(USERNAME, PASSWORD);
        KieClient client = new KieClient(credentials);

        client.setHostname(HOSTNAME);
        client.setPort(PORT);
        client.setSchema(SCHEMA);
        client.setWebapp(WEBAPP);

        assertEquals(expected, client.getBaseUrl());
        // get manager
        formManager = (IKieFormManager) this.getService(IKieFormManager.BEAN_NAME_ID);
        assertNotNull(formManager);
        overrideManager = (IKieFormOverrideManager) this.getService(IKieFormOverrideManager.BEAN_ID);
        assertNotNull(overrideManager);
    }

    public void testHumanTaskList() throws Throwable {
        KieBpmConfig config = getConfigForTests();

        if (!TEST_ENABLED) {
            return;
        }
        try {
            
            
            // invoke the manager
            List<KieTask> list = formManager.getHumanTaskList(config, "", null);
            assertNotNull(list);
            if (TEST_ENABLED) {
                assertFalse(list.isEmpty());
            } else {
                assertTrue(list.isEmpty());
            }
        } catch (Throwable t) {
            throw t;
        } finally {
            
        }
    }

    public void testGetProcInstDiagramImage() throws Throwable {
        KieBpmConfig config = getConfigForTests();

        if (!TEST_ENABLED) {
            return;
        }
        try {
            
           
            // invoke the manager
            String image = formManager.getProcInstDiagramImage(config, TARGET_CONTAINER_ID, TARGET_PROCESS_INSTANCE_ID);
            assertNotNull(image);

        } catch (Throwable t) {
            throw t;
        } finally {
            
        }
    }

    public void testGetTaskForm() throws Throwable {
        KieBpmConfig config = getConfigForTests();

        if (!TEST_ENABLED) {
            return;
        }
        try {
            
           
            // invoke the manager
            List<KieTask> tasks = formManager.getHumanTaskList(config,  "", null);
            assertNotNull(tasks);
            assertFalse(tasks.isEmpty());
            KieTask task = tasks.get(0);
            // get the form related to the first task
            KieProcessFormQueryResult form = formManager.getTaskForm(config,  task.getContainerId(), task.getId());
            assertNotNull(form);
            for (KieProcessFormField field : form.getFields()) {
                System.out.println("field name:  " + field.getName());
                System.out.println("      label: " + field.getId());
                System.out.println("      class: " + field.getType() + "\n");
            }
        } catch (Throwable t) {
            throw t;
        } finally {
            
        }
    }

    public void testProcessForm() throws Throwable {
        KieBpmConfig config = getConfigForTests();

        try {
            
           
            // invoke the manager
            KieProcessFormQueryResult form = formManager.getProcessForm(config, TARGET_CONTAINER_ID, TARGET_PROCESS_ID);
            if (TEST_ENABLED) {
                assertNotNull(form);
                assertNotNull(form.getNestedForms());
                assertNotNull(form.getFields());
                assertNotNull(form.getHolders());
                assertFalse(form.getNestedForms().isEmpty());
                assertFalse(form.getFields().isEmpty());
                assertFalse(form.getHolders().isEmpty());
            } else {
                assertNull(form);
            }
        } finally {
            
        }
    }

    // Effectively test the mashup of the overrides with the form returned by the BPM
    public void testProcessFormWithOverrides() throws Throwable {
        KieBpmConfig config = getConfigForTests();
        KieFormOverride kfo1 = new KieFormOverride();
        KieFormOverride kfo2 = new KieFormOverride();

        if (!TEST_ENABLED) {
            return;
        }
        try {
            
           
            Date now = new Date();
            final String FORM_FIELD_1 = "applicant_income";
            final String FORM_FIELD_2 = "property_price";
            OverrideList ol = new OverrideList();

            // field 1 - default value override for 'down_payments'
            ol.addOverride(createDefaultOverrideForTest());
            kfo1.setContainerId(TARGET_CONTAINER_ID);
            kfo1.setDate(now);
            kfo1.setField(FORM_FIELD_1);
            kfo1.setId(0);
            kfo1.setOverrides(ol);
            kfo1.setProcessId(TARGET_PROCESS_ID);

            ol = new OverrideList();
            // field 2 - placeholder override for 'down_payments'
            ol.addOverride(createPlaceholderOverrideForTest());
            kfo2.setContainerId(TARGET_CONTAINER_ID);
            kfo2.setDate(now);
            kfo2.setField(FORM_FIELD_2);
            kfo2.setId(0);
            kfo2.setOverrides(ol);
            kfo2.setProcessId(TARGET_PROCESS_ID);

            // persist overrides
            overrideManager.addKieFormOverride(kfo1);
            overrideManager.addKieFormOverride(kfo2);

            KieProcessFormQueryResult form = formManager.getProcessForm(config, TARGET_CONTAINER_ID, TARGET_PROCESS_ID);
            if (TEST_ENABLED) {
                assertNotNull(form);
                assertNotNull(form.getNestedForms());
                assertNotNull(form.getFields());
                assertNotNull(form.getHolders());
                assertFalse(form.getNestedForms().isEmpty());
                assertFalse(form.getFields().isEmpty());
                assertFalse(form.getHolders().isEmpty());
            } else {
                assertNull(form);
            }

            final KieProcessFormField field1 = BpmToFormHelper.getFormField(form, FORM_FIELD_1);
            assertNotNull(field1);
            assertNotNull(field1.getProperties());
            assertFalse(field1.getProperties().isEmpty());
            boolean ok = false;
            for (KieProcessProperty prop : field1.getProperties()) {
                if (prop.getName().equals(DefaultValueOverride.OVERRIDE_TYPE_NAME) && prop.getValue().equals(DEFAULTVALUE)) {
                    ok = true;
                }
            }
            assertTrue(ok);
            final KieProcessFormField field2 = BpmToFormHelper.getFormField(form, FORM_FIELD_2);
            assertNotNull(field2);
            assertNotNull(field2.getProperties());
            assertFalse(field2.getProperties().isEmpty());
            ok = false;
            for (KieProcessProperty prop : field2.getProperties()) {
                if (prop.getName().equals(PlaceHolderOverride.OVERRIDE_TYPE_NAME) && prop.getValue().equals(PLACEHOLDER)) {
                    ok = true;
                }
            }
            assertTrue(ok);
        } catch (Throwable t) {
            throw t;
        } finally {
            overrideManager.deleteKieFormOverride(kfo1.getId());
            overrideManager.deleteKieFormOverride(kfo2.getId());
            
        }
    }

    public void testFormSubmit() throws Throwable {
        KieBpmConfig config = getConfigForTests();

        if (!TEST_ENABLED) {
            return;
        }

        // form submitted values
        Map<String, Object> input = createValiDPayloadForTest();

        try {

            String processId = formManager.startProcessSubmittingForm(config, TARGET_CONTAINER_ID, TARGET_PROCESS_ID, input);
            if (TEST_ENABLED) {
                assertNotNull(processId);
                System.out.println("Process created: " + processId);
            } else {
                assertNull(processId);
            }
        } finally {
            
        }
    }

    public void testGetTaskFormData() throws Throwable {
        KieBpmConfig config = getConfigForTests();

        if (!TEST_ENABLED) {
            return;
        }
        try {
            
           
            // invoke the manager
            List<KieTask> tasks = formManager.getHumanTaskList(config, null, null);
            assertNotNull(tasks);
            assertFalse(tasks.isEmpty());
            KieTask task = tasks.get(0);
            // get the data form related to the first task
            JSONObject result = formManager.getTaskFormData(config, task.getContainerId(), task.getId(), null);
            assertNotNull(result);
        } finally {
            
        }
    }

    public void testCompleteHumanFormTask() throws Throwable {
        KieBpmConfig config = getConfigForTests();

        if (!TEST_ENABLED) {
            return;
        }
        try {
            
           
            // create a process that will need correction
//            String processId = SubmitInvalidForm();
//            assertNotNull(processId);
//            assertFalse(processId.equals("0"));
//            System.out.println("PROCESS CREATED: " + processId);
            String processId = "260";
            // check the tasks for the processes

            KieTask task = formManager.getHumanTask(config, processId);

            assertNotNull(task);
            System.out.println("TASK ID: " + task.getId());
            System.out.println("CONTAINER ID: " + task.getContainerId());
            System.out.println("PROCESS ID: " + task.getProcessInstanceId());
            System.out.println("PROCESS DEFINITION ID: " + task.getProcessDefinitionId());
            // get answer prototype (to generate the payload to submit)
            //            KieProcessFormQueryResult answerProto = formManager
            //                    .getProcessForm(task.getContainerId(), task.getProcessDefinitionId());
            //            assertNotNull(answerProto);
            // get task data
            JSONObject task_data = formManager.getTaskFormData(config, task.getContainerId(), task.getId(), null);

            KieProcessFormQueryResult form = formManager.getTaskForm(config, task.getContainerId(), task.getId());
            /*
			 * Map<String, Object> result = new HashMap<String, Object>();
			 * BpmToFormHelper.getHumanTaskFormData(answerProto, task_data,
			 * result); System.out.println("TASK DATA"); for (Map.Entry<String,
			 * Object> entry: result.entrySet()) {
			 * System.out.println(entry.getKey() + " : " + entry.getValue()); }
			 * // modify existing data result.put("application_appraisal",
			 * 2677);
			 *
			 * // prepare valid input Map<String, Object> input =
			 * createValiDPayloadForTest(); String json =
			 * FormToBpmHelper.generateHumanTaskFormJson(answerProto, task_data,
			 * result); System.out.println("PAYLOAD: " + json); // get human
			 * task form to show to user KieProcessFormQueryResult form =
			 * formManager.getTaskForm(task.getContainerId(),
			 * String.valueOf(task.getId())); // from the from above we have to
			 * take the only field that matters (for that task)
             */
            Map<String, Object> formInput = new HashMap<String, Object>();
            formInput.put("downPayment", 50001);

            JSONObject payload = FormToBpmHelper.generateHumanTaskFormJson(form, task_data, formInput);
            System.out.println("PAYLOAD: " + payload);

        } finally {
            
        }
    }

    private String _testSubmitInvalidForm() throws Throwable {
        KieBpmConfig config = getConfigForTests();

        if (!TEST_ENABLED) {
            return "0";
        }

        // form submitted values
        Map<String, Object> input = createSuggestIncreaseDownpaymentForTest(); // createCorrectDataTaskForTest();

        try {
            
           
            // invoke the manager
            String processId = formManager.startProcessSubmittingForm(config, TARGET_CONTAINER_ID, TARGET_PROCESS_ID, input);
            if (TEST_ENABLED) {
                assertNotNull(processId);
//                System.out.println("Process created: " + processId);
            } else {
                assertNull(processId);
            }
            return processId;
        } finally {
            
        }
    }

    private void completeHumanFormTask() throws Throwable {
        KieBpmConfig config = getConfigForTests();

        if (!TEST_ENABLED) {
            return;
        }
        try {
            
            Map<String, String> input = new HashMap<String, String>();
            formManager.completeHumanFormTask(config, TARGET_CONTAINER_ID, TARGET_PROCESS_ID, 243l, input);
        } finally {
            
        }
    }

    private Map<String, Object> createValiDPayloadForTest() {
        Map<String, Object> input = new HashMap<String, Object>();

        input.put("applicant_name", "Matteo");
        input.put("applicant_ssn", 123456789);
        input.put("applicant_income", 71234);
        input.put("property_address", "San Jose ave. #5678");
        input.put("property_price", 245678);
        input.put("application_downPayment", 43210);
        input.put("application_amortization", 10);
        return input;
    }

    // result in correct data
    private Map<String, Object> createCorrectDataTaskForTest() {
        Map<String, Object> input = new HashMap<String, Object>();

        input.put("applicant_name", "Emanuele");
        input.put("applicant_ssn", 123456789);
        input.put("applicant_income", 70000);
        input.put("property_address", "San Jose ave. #1234");
        input.put("property_price", 240000);
        input.put("application_downPayment", 40000);
        input.put("application_amortization", 10);
        return input;
    }

    // result in suggest down payment increase
    private Map<String, Object> createSuggestIncreaseDownpaymentForTest() {
        Map<String, Object> input = new HashMap<String, Object>();

        input.put("applicant_name", "Oettam Eleuname");
        input.put("applicant_ssn", 123456789);
        input.put("applicant_income", 70000);
        input.put("property_address", "San Jose ave. #9012");
        input.put("property_price", 200000);
        input.put("application_downPayment", 30000);
        input.put("application_amortization", 10);
        return input;
    }

    public static OverrideList createOverrideListForTests() {
        final OverrideList ol = new OverrideList();

        ol.addOverride(createDefaultOverrideForTest());
        ol.addOverride(createPlaceholderOverrideForTest());
        ol.addOverride(createDropDownOverrideForTest());
        return ol;
    }

    public static DefaultValueOverride createDefaultOverrideForTest() {
        final DefaultValueOverride df = new DefaultValueOverride();

        df.setDefaultValue(DEFAULTVALUE);
        return df;
    }

    public static PlaceHolderOverride createPlaceholderOverrideForTest() {
        final PlaceHolderOverride ph = new PlaceHolderOverride();

        ph.setPlaceHolder(PLACEHOLDER);
        return ph;
    }

    public static DropDownOverride createDropDownOverrideForTest() {
        final DropDownOverride dd = new DropDownOverride();
        final String values[] = {VAL1, VAL2};

        dd.setValues(Arrays.asList(values));
        return dd;
    }

    private KieBpmConfig getConfigForTests() {
        KieBpmConfig cfg = new KieBpmConfig();

        cfg.setActive(TEST_ENABLED);
        cfg.setHostname(HOSTNAME);
        cfg.setPassword(PASSWORD);
        cfg.setPort(PORT);
        cfg.setSchema(SCHEMA);
        cfg.setUsername(USERNAME);
        cfg.setWebapp(WEBAPP);
        cfg.setTimeoutMsec(TIMEOUT);

        return cfg;
    }


}
