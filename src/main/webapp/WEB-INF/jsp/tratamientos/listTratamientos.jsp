<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="tratamientos">
	

	<table id="diagnosesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Descripcion</th>
				<th>Duracion</th>
				<th>Editar tratamiento</th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tratamientos}" var="tratamientos">
				<tr>
					<td>${tratamientos.descripcion}</td>
					<td>${tratamientos.duracion}</td>
					<td>
						<spring:url value="/tratamientos/edit/{tratamientoId}" var="editTratamiento">
							<spring:param name="tratamientoId" value="${tratamientos.id}"/>
						</spring:url>
						<a href="${fn:escapeXml(editTratamiento)}">Editar Tratamiento</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>
