/*
The MIT License

Copyright 2018 Entando Inc..

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package org.entando.entando.plugins.jpkiebpm.apsadmin.portal.specialwidget.helper;

import com.agiletec.aps.util.FileTextReader;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.api.util.KieApiUtil;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.model.KieProcessFormField;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.model.KieProcessFormQueryResult;
import org.entando.entando.plugins.jpkiebpm.apsadmin.portal.specialwidget.helper.dataModels.Model;
import org.entando.entando.plugins.jpkiebpm.apsadmin.portal.specialwidget.helper.dataModels.Section;
import org.entando.entando.plugins.jpkiebpm.apsadmin.portal.specialwidget.helper.dataModels.DatePicker;
import org.entando.entando.plugins.jpkiebpm.apsadmin.portal.specialwidget.helper.dataModels.InputField;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import org.entando.entando.plugins.jpkiebpm.aps.system.services.kie.model.KieProcessProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

@Service
public class DataUXBuilder<T extends InputField> implements ServletContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DataUXBuilder.class);
    private Map<String, String> typeMapping = new HashMap<>();
    private Map<String, String> valueMapping = new HashMap<>();
    private ServletContext servletContext;

    // src/main/resources/templates 
    public static final String TEMPLATE_FOLDER = "/templates/";
    public static final String MAIN_FTL_TEMPLATE = "pageModel.ftl";

    public List fields = new ArrayList<DatePicker>();

    Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);

    public void init() throws Exception {
        logger.debug("{} ready.", this.getClass().getName());

        this.typeMapping.put("InputText", "text");
        this.typeMapping.put("InputTextInteger", "number");

        //this.typeMapping.put("HTML", "text");
        this.typeMapping.put("TextBox", "text");
        this.typeMapping.put("TextArea", "text");
        this.typeMapping.put("IntegerBox", "number");
        this.typeMapping.put("CheckBox", "checkbox");
        this.typeMapping.put("DecimalBox", "number");
        this.typeMapping.put("DatePicker", "text");
        this.typeMapping.put("CheckBox", "text");
        this.typeMapping.put("Slider", "number");
        this.typeMapping.put("RadioGroup", "number");
        this.typeMapping.put("MultipleInput", "number");
        this.typeMapping.put("MultipleSelector", "text");
        this.typeMapping.put("Document", "text");

        this.valueMapping.put("InputText", "$data.%s.text");
        this.valueMapping.put("InputTextInteger", "$data.%s.number");
        this.valueMapping.put("CheckBox", "$data.%s.checkbox");

        //this.valueMapping.put("HTML", "$data.%s.text");
        this.valueMapping.put("TextBox", "$data.%s.text");
        this.valueMapping.put("TextArea", "$data.%s.text");
        this.valueMapping.put("IntegerBox", "$data.%s.number");
        this.valueMapping.put("DecimalBox", "$data.%s.number");
        this.valueMapping.put("DatePicker", "$data.%s.text");
        this.valueMapping.put("CheckBox", "$data.%s.text");
        this.valueMapping.put("Slider", "$data.%s.number");
        this.valueMapping.put("RadioGroup", "$data.%s.number");
        this.valueMapping.put("MultipleSelector", "$data.%s.text");
        this.valueMapping.put("MultipleInput", "$data.%s.text");
        this.valueMapping.put("Document", "$data.%s.text");

        cfg.setDefaultEncoding("UTF-8");

        logger.debug("TEMPLATE_FOLDER: {}", TEMPLATE_FOLDER);
        cfg.setClassForTemplateLoading(this.getClass(), TEMPLATE_FOLDER);

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);

    }

    public DataUXBuilder() {

    }

    public String createDataUx(KieProcessFormQueryResult kpfr, String containerId, String processId, String title) throws Exception {
        logger.debug("CreateDataUx in DataUXBuilde for containerId {} with processId {} and title {} -> kpfr: {}", containerId, processId, title, kpfr);
        Template template = cfg.getTemplate(MAIN_FTL_TEMPLATE);
        Section section = this.addSection(kpfr);
        List fields = this.addFields(kpfr);
        Map<String, Object> root = new HashMap<>();
        Model model = new Model();
        model.setTitle(title);
        model.setContainerId(containerId);
        model.setProcessId(processId);
        root.put("model", model);
        root.put("section", section);
        root.put("fields", fields);
        Writer stringWriter = new StringWriter();
        template.process(root, stringWriter);
        return stringWriter.toString();

    }

    private Section addSection(KieProcessFormQueryResult kpfr) throws Exception {
        Section section = new Section();
        if (kpfr.getFields().size() > 0) {
            String formName = null;
            if (kpfr.getFields().get(0).getName().contains("_")) {
                formName = KieApiUtil.getFormNameFromField(kpfr.getFields().get(0));
            } else {
                if (KieApiUtil.getFieldProperty(kpfr.getProperties(), "name").contains(".")) {
                    formName = KieApiUtil.getFieldProperty(kpfr.getProperties(), "name")
                            .substring(0, KieApiUtil.getFieldProperty(kpfr.getProperties(), "name").indexOf("."));

                } else {
                    formName = KieApiUtil.getFieldProperty(kpfr.getProperties(), "name");
                }
            }

            section.setName(formName);
        }
        return section;
    }

    private List<T> addFields(KieProcessFormQueryResult kpfr) throws Exception {

        List<KieProcessFormField> fields = kpfr.getFields();

        List<T> inputFields = new ArrayList<T>();

        for (int i = 0; i < fields.size(); i++) {
            KieProcessFormField field = fields.get(i);
            inputFields.add(this.addField(field));
        }

        //TODO CHECK SUBFORMS
        List<KieProcessFormQueryResult> subForms = kpfr.getNestedForms();
        if (null != subForms && !subForms.isEmpty()) {
            for (int i = 0; i < subForms.size(); i++) {
                KieProcessFormQueryResult form = subForms.get(i);
                inputFields.addAll(this.addFields(form));
            }
        }
    }

        return inputFields;
    }

    private T addField(KieProcessFormField field) throws Exception {
        logger.debug("------------------------------------");
        logger.debug("Field getId          -> {}", field.getId());
        logger.debug("Field getName        -> {}", field.getName());
        logger.debug("Field getPosition    -> {}", field.getPosition());
        logger.debug("Field getType        -> {}", field.getType());
        logger.debug("Field getProperties -> ");
        field.getProperties().forEach(p
                -> logger.debug("  Property name:{} value: {}", p.getName(), p.getValue()));
        logger.debug("------------------------------------");
        T inputField;
        if (field.getType().equals("DatePicker")) {
            inputField = (T) new DatePicker();
            
        } else {
            inputField = (T) new InputField();
        }
        String fieldName = field.getName();

        //String label = (null != labelProperty) ? labelProperty.getValue() : null;
        String fieldTypeHMTL = this.typeMapping.get(field.getType());
        if (null == fieldTypeHMTL) {
            fieldTypeHMTL = "text";
        }

        String fieldTypePAM = field.getType();
        String fieldValueExpr = this.valueMapping.get(field.getType());
        String fieldValue = (null != fieldValueExpr) ? String.format(fieldValueExpr, field.getName()) : "";

        boolean required = Boolean.parseBoolean(field.getProperty("fieldRequired").getValue());

        inputField.setId(field.getId());
        inputField.setName(fieldName);
        inputField.setValue(fieldValue);
        inputField.setRequired(required);
        inputField.setTypePAM(fieldTypePAM);
        inputField.setTypeHTML(fieldTypeHMTL);
        
        if (inputField instanceof DatePicker) {
            DatePicker datePicker = (DatePicker) inputField;
            
            boolean showTime = Boolean.parseBoolean(field.getProperty("showTime").getValue());

            datePicker.setShowTime(showTime);
            return (T) datePicker;
        }
        
        return inputField;
  
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
