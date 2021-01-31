<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="Aseguradoras">
	
	<table id="diagnosesTable" class="table table-striped">
		<thead>
			<tr>			
				<th>Aseguradora</th>
				<th>Opciones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${aseguradoras}" var="aseguradora">
				<tr>
					<td>${aseguradora.nombre}</td>
					<td><form action="http://localhost:8080/aseguradoras/${aseguradora.id}/perfil">
						<input type="submit" value="Ver perfil" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form action="http://localhost:8080/aseguradoras/new">
		<input type="submit" value="nueva aseguradora" />
	</form>

</petclinic:layout>
