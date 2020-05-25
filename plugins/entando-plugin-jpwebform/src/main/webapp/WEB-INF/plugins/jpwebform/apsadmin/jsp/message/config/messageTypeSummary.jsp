<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/apsadmin-form" prefix="wpsf" %>
<h1 class="panel panel-default title-page">
    <span class="panel-body display-block">
        <s:text name="title.emailNotifier.config" />&#32;/&#32;
        <s:text name="label.messageTypes" />
    </span>
</h1>
<div id="main">

    <s:if test="%{messageTypes.size() > 0}">
        <div class="panel panel-default">
            <div class="panel-body">
                <s:text name="messageTypes.intro" />
            </div>
        </div>

        <s:form action="edit">
            <s:hidden name="_csrf" value="%{csrfToken}"/>
            <fieldset class="col-xs-12">
                <legend><s:text name="label.info" /></legend>
                <div class="form-group">
                    <label for="jpwebform_message_type"><s:text name="label.messageType" /></label>
                    <wpsf:select id="jpwebform_message_type" name="typeCode" list="%{messageTypes}" listKey="code" listValue="descr" cssClass="form-control"/>
                </div>
            </fieldset>
                <div class="form-horizontal">
                    <div class="form-group">
                        <div class="col-xs-12 col-sm-4 col-md-3 margin-small-vertical">
                            <s:submit type="button" cssClass="btn btn-primary btn-block">
                                <span class="icon fa fa-play-circle-o"></span>&#32;
                                <s:text name="%{getText('label.continue')}" />
                            </s:submit>
                        </div>
                    </div>
                </div>
        </s:form>
    </s:if>
    <s:else>
        <div class="alert alert-info"><s:text name="no.messageType.to.configure" /></div>
    </s:else>
</div>