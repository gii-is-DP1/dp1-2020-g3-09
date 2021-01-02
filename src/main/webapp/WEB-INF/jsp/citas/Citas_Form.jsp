<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Citas">
    <h2>
        <c:if test="${cita['new']}">Nueva </c:if> Cita
    </h2>
    <form:form modelAttribute="cita" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <select name = "Tipo">
                <option value = "ASEGURADO"> Asegurado</option>
                <option value = "PRIVADO"> Privado </option>
            </select>
            <select name = "Formato">
                <option value = "PRESENCIAL"> Presencial</option>
                <option value = "ONLINE"> Online </option>
            </select>
            <petclinic:inputField label="Especialista" name="especialista"/>
            <petclinic:inputField label="Especialidad" name="Especialidad"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${cita['new']}">
                        <button class="btn btn-default" type="submit">Add Cita</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Cita</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>