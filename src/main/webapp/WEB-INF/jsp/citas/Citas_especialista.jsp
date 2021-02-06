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

            <label for="tipo">Tipo de cita:</label>
            <select name = "Tipo" id="tipo">
                <option value = "ASEGURADO"> Asegurado</option>
                <option value = "PRIVADO"> Privado </option>
            </select><br>

            <label for="formato">Formato de la cita:</label>
            <select name = "Formato" id="formato">
                <option value = "PRESENCIAL"> Presencial</option>
                <option value = "ONLINE"> Online </option>
            </select><br>

            <label for="especialidades">Especialidad:</label>
            <select name = "Especialidad" id="especialidades">
                <c:forEach items="${especialidad}" var="especialidad" varStatus="loop">
                <option value="${especialidad}">
                    ${especialidad}
                </option>
                </c:forEach>
                </select><br>
            
            <label for="fecha">Fecha de la cita:</label>
            <input type="date" name="fecha" id="fecha"><br>
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