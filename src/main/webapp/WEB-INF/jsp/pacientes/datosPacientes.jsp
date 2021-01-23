<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pacientes">
    <h2>Datos personales del paciente</h2>
  
    <label>DNI:</label> ${pacientes.dni}<br>
    <label>Nombre:</label> ${pacientes.firstName}<br>
    <label>Apellidos:</label> ${pacientes.lastName}<br>
    <label>Direccion:</label> ${pacientes.direccion}<br>
    <label>Sexo:</label>${pacientes.sexo}<br>
    <label>Edad:</label>${pacientes.edad}<br>
    <label>Email:</label>${pacientes.email}<br><br>

    <h2>Datos del usuario</h2>
    <label>Usuario:</label>${pacientes.user.username}<br>
    <label>Contrasena:</label>${pacientes.user.password}<br><br>

    <h2>Datos medicos del paciente</h2>
    <label>Aseguradora:</label>${pacientes.aseguradora}<br><br><br>

    <spring:url value="/pacientes/{id}/edit" var="editPaciente">
		<spring:param name="id" value="${pacientes.id}"/>
	</spring:url>
	<a href="${fn:escapeXml(editPaciente)}">Editar perfil</a>
        
</petclinic:layout>
