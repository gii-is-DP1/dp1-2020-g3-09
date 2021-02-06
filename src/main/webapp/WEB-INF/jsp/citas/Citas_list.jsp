<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="citas">
<sec:authorize access="hasAuthority('especialista')">
<h2>Citas para el especialista <c:out value="${especialista.firstName} ${especialista.lastName}"></c:out></h2>
</sec:authorize>


	<table id="diagnosesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Formato</th>
				<th>Tipo</th>				
				<th>Especialidad</th>
				<th>Fecha de la cita</th>
				<sec:authorize access="hasAuthority('paciente')">
					<th>Especialista</th>
					<th>Configurar Alerta</th>
					<th>Justificante de la cita</th>
					<th>Opciones</th>
				</sec:authorize>
				<sec:authorize access="hasAuthority('especialista')">
					<th>Paciente</th>
					<th>Crear acta</th>
				</sec:authorize>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${citas}" var="citas">
				<tr>
					<td>${citas.formato}</td>
					<td>${citas.tipo}</td>
					<td>${citas.especialidad}</td>
					<td>${citas.fecha}</td>
					<sec:authorize access="hasAuthority('paciente')">
					<td>${citas.especialista}</td>
					<td> 
					<spring:url value="/alarmas/new/{citaId}" var="addAlarma">
						<spring:param name="citaId" value="${citas.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(addAlarma)}">Anadir alarma</a>
					</td>
					
					<td>
					<spring:url value="/justificantes/new/{id}" var="genJustificante">
						<spring:param name="id" value="${citas.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(genJustificante)}">Generar justificante</a>
					</td>
					<td>
					<form action="http://localhost:8080/citas/${citas.id}/delete">
					<input type="submit" value="Borrar cita" />
					</form>

					</td>
					</sec:authorize>
					<sec:authorize access="hasAuthority('especialista')">
						<td>${citas.paciente.firstName}   ${citas.paciente.lastName}</td>
						<td> 
							<spring:url value="/actas/new/{citaId}/{especialistaId}" var="addActa">
								<spring:param name="citaId" value="${citas.id}"/>
								<spring:param name="especialistaId" value="${citas.especialista.id}"/>
							</spring:url>
							<a href="${fn:escapeXml(addActa)}">Anadir acta</a>
						</td>
					</sec:authorize>
				
				</tr>
				
			</c:forEach>

			
		</tbody>
</table>
<sec:authorize access="hasAuthority('especialista')">
		<spring:url value="/citas/new/{especialistaId}/{pacienteId}" var="addCita">
		<spring:param name="pacienteId" value="${paciente.id}"></spring:param>
		<spring:param name="especialistaId" value="${especialista.id}"></spring:param>
		</spring:url>
		<a href="${fn:escapeXml(addCita)}">Anadir cita</a>
</sec:authorize>
</petclinic:layout>
