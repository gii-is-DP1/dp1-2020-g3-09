<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="actas">

	<table id="actasTable" class="table table-striped">
		<thead>
			<tr>
				<th>Descripcion</th>
				<th>Exploracion</th>				
				<th>Diagnostico</th>
				<th>Editar acta</th>
				<th>Crear tratamiento</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${actas}" var="actas">
				<tr>
					<td>${actas.descripcion}</td>
					<td>${actas.exploracion}</td>
					<td>${actas.diagnostico}</td>
					<td>
						<spring:url value="/actas/{actaId}/edit" var="editActa">
							<spring:param name="actaId" value="${actas.id}"/>
						</spring:url>
						<a href="${fn:escapeXml(editActa)}">Editar acta</a>
					</td>
					<td>
						<spring:url value="/tratamientos/new/{actaId}/{polizaId}" var="newTratamiento">
							<spring:param name="actaId" value="${actas.id}"/>
							<spring:param name="polizaId" value="${actas.cita.paciente.poliza.id}"/>
						</spring:url>
						<a href="${fn:escapeXml(newTratamiento)}">Nuevo Tratamiento</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>