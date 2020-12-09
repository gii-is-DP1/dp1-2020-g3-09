<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="alarmas">
	
	<table id="diagnosesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Fecha de la cita</th>
				<th>Dias de antelacion</th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${alarmas}" var="alarmas">
				<tr>
					<td>${citas.formato}</td>
					<td>${alarmas.diasAntelacion}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>
