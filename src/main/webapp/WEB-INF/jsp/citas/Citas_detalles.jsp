<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Citas">
    <h2>
        Detalles de la cita:
    </h2>
    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${cita.paciente.firstName} ${cita.paciente.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Especialista</th>
            <td><c:out value="${cita.especialista}"/></td>
        </tr>
        <tr>
            <th>Especialidad</th>
            <td><c:out value="${cita.especialidad}"/></td>
        </tr>
        <tr>
            <th>Fecha de la cita</th>
            <td><c:out value="${cita.fecha}"/></td>
        </tr>
    </table>
</petclinic:layout>