<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="alarmas">
	<c:forEach items="${alarmas}" var="alarmas">
	<c:choose>
	<c:when test="${alarmas.dias <= 15}">
	<div class="alert alert-info">
		Quedan ${alarmas.dias}
		 dias para su proxima <a href="/citas" class="alert-link">cita</a>
		<button type="button" class="close" data-dismiss="alert">&times;</button>
	</div>
	</c:when>
	</c:choose>
	</c:forEach>	

	<table id="diagnosesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Especialista</th>
				<th>Especialidad</th>
				<th>Fecha de la cita</th>
				<th>Dias restantes</th>
				<th></th>	
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${alarmas}" var="alarmas">
				<tr>
					<td>${alarmas.cita.especialista}</td>
					<td>${alarmas.cita.especialidad}</td>
					<td>${alarmas.cita.fecha}</td>
					<td>${alarmas.dias}</td>
					<td> 
						<spring:url value="/alarmas/{id}/delete" var="deleteAlarma">
							<spring:param name="id" value="${alarmas.id}"/>
						</spring:url>
						<a href="${fn:escapeXml(deleteAlarma)}">Borrar alarma</a>
						</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>
