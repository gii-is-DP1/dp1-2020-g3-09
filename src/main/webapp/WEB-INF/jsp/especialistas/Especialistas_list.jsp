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
				<th>Especialista</th>
				<th>especialidad</th>
				<th>Opciones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${especialistas}" var="especialista">
				<tr>
					<td>${especialista.lastName}, ${especialista.firstName}</td>
					<td>${especialista.especialidad}</td>
					<td><form action="http://localhost:8080/especialistas/${especialista.id}/perfil">
						<input type="submit" value="Ver perfil" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form action="http://localhost:8080/especialistas/new">
		<input type="submit" value="nuevo especialista" />
	</form>

</petclinic:layout>
