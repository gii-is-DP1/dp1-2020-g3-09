<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="pacientes">

    <h2>Perfil de paciente</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${paciente.firstName} ${paciente.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Aseguradora contratada</th>
            <td><b><c:out value="${paciente.aseguradora.nombre}"/></b></td>
        </tr>
            
            
        </tr>
    </table>
    <sec:authorize access="hasAuthority('paciente')">
        <form action="http://localhost:8080/pacientes/${paciente.id}/edit">
        <input type="submit" value="editar" />
        </form>

        <form action="http://localhost:8080/pacientes/${paciente.id}/delete">
            <input type="submit" value="borrar" />
        </form>

        <form action="http://localhost:8080/citas/new/${paciente.id}">
            <input type="submit" value="pedir una cita" />
        </form>

        <form action="/calculadoras/new/${paciente.id}">
            <input type="submit" value="calcular imc"/>
        </form>
    </sec:authorize>
    <sec:authorize access="hasAuthority('especialista')">
        <h2><form action="http://localhost:8080/citas/new/${paciente.id}">
            <input type="submit" value="Crear una cita" />
        </h2>
    </sec:authorize>
    <br/>
    <br/>
    <br/>
    <h2>Citas</h2>

    <table class="table table-striped">
        <c:forEach var="cita" items="${citas}">

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
                    <spring:url value="/pacientes/cita/delete/{citaId}/{pacienteId}" var="perfilPaciente">
                        <spring:param name="citaId" value="${cita.id}"/>
                        <spring:param name="pacienteId" value="${paciente.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(perfilPaciente)}">borrar cita</a>

                    <form action="http://localhost:8080/justificantes/new/${cita.id}">
                        <input type="submit" value="generar justificante" />
                    </form>
                    <form action="http://localhost:8080/alarmas/new/${cita.id}">
                        <input type="submit" value="generar alarma" />
                    </form>
					</td>
                </td>
            </tr>
        </c:forEach>
    </table>

</petclinic:layout>
