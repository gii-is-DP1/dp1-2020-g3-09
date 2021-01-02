<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="citas">
	
	<div class="alert alert-info">
		<script src="http://momentjs.com/downloads/moment.min.js"></script>
		<script>
		/*var fecha1 = document.getElementById("fecha");
		var fecha1b = fecha1.value;
		var fecha1c = new Date(fecha1b);*/
		var Date ='2020-12-30';
		var fecha2 = moment().format('DD/MM/YYYY');
		var diffhoras=moment().diff(moment(Date), 'days');
		</script>
		Quedan <script type="text/javascript">document.write(diffhoras)</script> dias para su proxima <a href="/citas" class="alert-link">cita</a>
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
				<th>Justificante de la cita</th>

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
					
					<td>
					<spring:url value="/justificantes/{id}/new" var="genJustificante">
						<spring:param name="id" value="${citas.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(genJustificante)}">Generar justificante</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>
