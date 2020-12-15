<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="citas">
	
	<div class="alert alert-info">
		<a href="/citas" class="alert-link">Quedan 2 dias para su proxima cita</a>
		<button type="button" class="close" data-dismiss="alert">&times;</button>
	</div>
	<table id="diagnosesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Formato</th>
				<th>Tipo</th>				
				<th>Especialidad</th>
				<th>Especialista</th>
				<th>Configurar Alerta</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${citas}" var="citas">
				<tr>
					<td>${citas.formato}</td>
					<td>${citas.tipo}</td>
					<td>${citas.especialidad}</td>
					<td>${citas.especialista}</td>
					<td> 
					<spring:url value="/alarmas/new/{id}" var="addAlarma">
						<spring:param name="id" value="${citas.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(addAlarma)}">Anadir alerta</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>
