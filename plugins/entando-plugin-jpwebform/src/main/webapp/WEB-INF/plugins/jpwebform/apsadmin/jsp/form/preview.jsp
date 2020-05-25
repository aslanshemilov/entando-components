<%@ taglib prefix="jacms" uri="/jacms-apsadmin-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<form id="jpwebformStepPreview" action="<wp:info key="systemParam" paramName="applicationBaseURL" />do/jpwebform/Config/Gui/editGui.action" method="post">
<s:hidden name="_csrf" value="%{csrfToken}"/>
<p style="border-bottom: 0.1em solid #000000; background-color: #eeeeee;">
	<span style="font-size: 1em; font-weight: bold; color: #000000"><s:text name="label.preview" /></span>
	<wpsf:hidden name="entityTypeCode" />
	<wpsf:hidden name="stepCode" value="%{getRootStepCode()}"/>
	<wpsf:hidden name="userGui" />
	<wpsf:hidden name="userCss" />
	<wpsf:hidden name="rootStepCode" />
	<wpsf:submit type="button" cssClass="btn btn-default">
            <s:text name="%{getText('jpwebform.preview.back.to.editStep')}"/>
        </wpsf:submit>
</p>
</form>

<wp:internalServlet actionPath="/ExtStr2/do/jpwebform/Config/Gui/showPreview" />

<form action="<wp:info key="systemParam" paramName="applicationBaseURL" />do/jpwebform/Config/Gui/editGui.action" method="post">
<s:hidden name="_csrf" value="%{csrfToken}"/>
<p style="border-top: 0.1em solid #000000; background-color: #eeeeee;">
	<span style="font-size: 1em; font-weight: bold; color: #000000"><s:text name="label.preview" /></span>
	<wpsf:hidden name="entityTypeCode" />
	<wpsf:hidden name="stepCode" value="%{getRootStepCode()}"/>
	<wpsf:hidden name="userGui" />
	<wpsf:hidden name="userCss" />
	<wpsf:hidden name="rootStepCode" />
	<wpsf:submit type="button" cssClass="btn btn-default">
            <s:text name="%{getText('jpwebform.preview.back.to.editStep')}"/>
        </wpsf:submit>
</p>
</form>