<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Especialista">
    <h2>
        <c:if test="${especialista['new']}">Nuevo </c:if> Especialista
    </h2>
    <form:form modelAttribute="especialista" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Direccion" name="direccion"/>
            <petclinic:inputField label="Correo" name="correo"/>
            <petclinic:inputField label="Telefono" name="telefono"/>
            <petclinic:inputField label="firstName" name="firstName"/>
            <petclinic:inputField label="lastName" name="lastName"/>
            <petclinic:inputField label="dni" name="dni"/>

            <label for="especialidades">Especialidad:</label>
            <select name = "Especialidad" id="especialidades">
                <c:forEach items="${especialidad}" var="especialidad" varStatus="loop">
                <option value="${especialidad}">
                    ${especialidad}
                </option>
                </c:forEach>
                </select>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Añadir especialista</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>