<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="owners">
	
	<table id="diagnosesTable" class="table table-striped">
		<thead>
			<tr>
				<th>formato</th>
				<th>tipo</th>				
				<th>especialidad</th>
				<th>Especialista</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${citas}" var="citas">
				<tr>
					<td>${citas.formato}</td>
					<td>${citas.tipo}</td>
					<td>${citas.especialidad}</td>
					<td>${citas.especialista}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>
