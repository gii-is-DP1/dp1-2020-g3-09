<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pacientes">

    <h2>¿Desea acceder a su perfil?</h2>
    <spring:url value="/pacientes/{id}" var="verPaciente">
		<spring:param name="id" value="${pacientes.id}"/>
	</spring:url>
	<a href="${fn:escapeXml(verPaciente)}">Acceder al perfil</a>
</petclinic:layout>