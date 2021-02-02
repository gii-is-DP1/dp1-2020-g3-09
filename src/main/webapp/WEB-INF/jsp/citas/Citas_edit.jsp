<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Citas">
    <h2>
        Editar cita:
    </h2>
    <form:form modelAttribute="cita" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">

            <h3>Anteriormente el dato seleccionado era: ${cita.tipo}</h3>
            <label for="tipo">Tipo de cita:</label>
            <select name = "Tipo" id="tipo">
                <option value = "ASEGURADO"> Asegurado</option>
                <option value = "PRIVADO"> Privado </option>
            </select>

            <h3>Anteriormente el dato seleccionado era: ${cita.formato}</h3>
            <label for="formato">Formato de la cita:</label>
            <select name = "Formato" id="formato">
                <option value = "PRESENCIAL"> Presencial</option>
                <option value = "ONLINE"> Online </option>
            </select>

            <h3>Anteriormente el dato seleccionado era: ${cita.especialista}</h3>
            <label for="especialistas">Especialista:</label>
            <select name = "Especialista" id="especialistas">
            <c:forEach items="${especialistas}" var="especialista" varStatus="loop">
            <option value="${loop.index + 1}">
                ${especialista}
            </option>
            </c:forEach>
            </select>

            <h3>Anteriormente el dato seleccionado era: ${cita.especialidad}</h3>
            <label for="especialidades">Especialidad:</label>
            <select name = "Especialidad" id="especialidades">
                <c:forEach items="${especialidad}" var="especialidad" varStatus="loop">
                <option value="${especialidad}">
                    ${especialidad}
                </option>
                </c:forEach>
                </select>

            <h3>Anteriormente el dato seleccionado era: ${cita.fecha}</h3>
            <label for="fecha">Fecha de la cita:</label>
            <input type="date" pattern="yyyy-mm-dd" name="fecha" id="fecha">
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