<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="polizas">
	
	<table id="diagnosesTable" class="table table-striped">
		<thead>
			<tr>			
				<th>Nombre</th>
				<th>Cobertura</th>
				<th>Precio</th>
				<th>Fecha de expiracion</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${polizas}" var="poliza">
				<tr>
					<td>${poliza.name}</td>
					<td>${poliza.cobertura}</td>
					<td>${poliza.precio}</td>
					<td>${poliza.duracion}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>
