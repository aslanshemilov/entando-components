INSERT INTO pages (code, parentcode, pos, groupcode) VALUES ('seo_page_1', 'homepage', 7, 'free');
INSERT INTO pages (code, parentcode, pos, groupcode) VALUES ('seo_page_2', 'homepage', 8, 'free');

INSERT INTO pages_metadata_draft (code, titles, modelcode, showinmenu, extraconfig, updatedat) VALUES ('seo_page_1', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Seo Page 1</property>
<property key="it">Pagina Seo 1</property>
</properties>
', 'home', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
  <charset>utf-8</charset>
  <mimeType>text/html</mimeType>
  <useextradescriptions>false</useextradescriptions>
  <descriptions>
    <property key="en" />
    <property key="it" />
  </descriptions>
  <complexParameters>
    <parameter key="key1">VALUE_1</parameter>
    <parameter key="key2">VALUE_2</parameter>
    <parameter key="key5">
      <property key="fr">VALUE_5 FR</property>
      <property key="en">VALUE_5 EN</property>
      <property key="it">VALUE_5 IT</property>
    </parameter>
    <parameter key="key6">VALUE_6</parameter>
    <parameter key="key3">
      <property key="en">VALUE_3 EN</property>
      <property key="it">VALUE_3 IT</property>
    </parameter>
    <parameter key="key4">VALUE_4</parameter>
  </complexParameters>
</config>', '2018-06-20 16:31:31');
INSERT INTO pages_metadata_draft (code, titles, modelcode, showinmenu, extraconfig, updatedat) VALUES ('seo_page_2', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Seo Page 1</property>
<property key="it">Pagina Seo 1</property>
</properties>
', 'home', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
  <charset>utf-8</charset>
  <mimeType>text/html</mimeType>
  <useextradescriptions>false</useextradescriptions>
  <descriptions>
    <property key="en" />
    <property key="it" />
  </descriptions>
  <complexParameters>
    <lang code="it">
      <meta key="key5">VALUE_5_IT</meta>
      <meta key="key3" attributeName="name" useDefaultLang="false" >VALUE_3_IT</meta>
      <meta key="key2" attributeName="property" useDefaultLang="true" />
    </lang>
    <lang code="en">
      <meta key="key5">VALUE_5_IT</meta>
      <meta key="key3" attributeName="name" useDefaultLang="false" >VALUE_3_EN</meta>
      <meta key="key2" attributeName="property" useDefaultLang="true" />
    </lang>
  </complexParameters>
</config>', '2018-06-26 17:31:31');