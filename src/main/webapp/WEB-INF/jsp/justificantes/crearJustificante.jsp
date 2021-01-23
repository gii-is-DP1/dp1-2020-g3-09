<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="justificantes">
    
    <form:form modelAttribute="justificante" class="form-horizontal" id="form-justificante">
        <div class="form-group has-feedback">
            <label>Motivo del justificante:</label>
            <select name="motivo">
                <option value="TRABAJO">Trabajo</option>
                <option value="CENTRO ESCOLAR">Centro Escolar</option>
            </select>
        </div>
        <button class="btn btn-default" type="submit">Guardar justificante</button>
    </form:form>
</petclinic:layout>
