<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">

    <h2>Perfil de especialista</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${especialista.firstName} ${especialista.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Direccion</th>
            <td><c:out value="${especialista.direccion}"/></td>
        </tr>
        <tr>
            <th>Correo</th>
            <td><c:out value="${especialista.correo}"/></td>
        </tr>
        <tr>
            <th>Telefono</th>
            <td><c:out value="${especialista.telefono}"/></td>
        </tr>
        <tr>
            <th>Especialidad</th>
            <td><c:out value="${especialista.especialidad}"/></td>
        </tr>
        <c:forEach var="aseguradora" items="${especialista.aseguradoras}">
        <tr>
            <th>Aseguradora: </th>
            <td><dd><spring:url value="/aseguradoras/{aseguradoraId}/perfil" var="perfilAseguradora">
                <spring:param name="aseguradoraId" value="${aseguradora.id}"/>
            </spring:url>
            <a href="${fn:escapeXml(perfilAseguradora)}">${aseguradora.nombre} </a>
             |         
            <spring:url value="/especialistas/aseguradora/delete/{aseguradoraId}/{especialistaId}" var="borrarAseguradora">
                <spring:param name="aseguradoraId" value="${aseguradora.id}"/>
                <spring:param name="especialistaId" value="${especialista.id}"/>
            </spring:url>
            <a href="${fn:escapeXml(borrarAseguradora)}"> borrar de esta aseguradora</a></dd></td>
            
        </tr>
        </c:forEach>
    </table>

    <h2>
    <form action="http://localhost:8080/especialistas/${especialista.id}/edit">
    <input type="submit" value="editar" />
    </form>
    <form action="http://localhost:8080/especialistas/${especialista.id}/delete">
        <input type="submit" value="borrar" />
    </form>
    </h2>

    <br/>
    <br/>
    <br/>
    <h2>Citas</h2>

    <table class="table table-striped">
        <c:forEach var="cita" items="${especialista.citas}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Paciente</dt>
                        <dd><c:out value="${cita.paciente.firstName} ${cita.paciente.lastName}"/></dd>
                        <dt>Especialidad</dt>
                        <dd><c:out  value="${cita.especialidad}"/></dd>
                        <dt>Tipo</dt>
                        <dd><c:out value="${cita.tipo}"/></dd>
                        <dt>Formato</dt>
                        <dd><c:out value="${cita.formato}"/></dd>
                    </dl>
                </td>
                <td>
                    <dd><spring:url value="/especialistas/cita/delete/{citaId}/{especialistaId}" var="deleteCita">
                        <spring:param name="citaId" value="${cita.id}"/>
                        <spring:param name="especialistaId" value="${especialista.id}"/>
                        
                    </spring:url>
                    <a href="${fn:escapeXml(deleteCita)}">borrar cita</a></dd>

                    <form action="http://localhost:8080/citas/${cita.id}/edit">
                        <input type="submit" value="editar cita" />
                    </form>

                    <form action="http://localhost:8080/citas/${especialista.id}/${cita.paciente.id}">
                        <input type="submit" value="Ver citas de este paciente" />
                    </form>
					</td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
