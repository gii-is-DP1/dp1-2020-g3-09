<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="aseguradoras">

    <h2>Perfil de aseguradora</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${aseguradora.nombre}"/></b></td>
        </tr>
    </table>

    <h2><form action="http://localhost:8080/aseguradoras/${aseguradora.id}/edit">
    <input type="submit" value="editar" />
    </form>
    <form action="http://localhost:8080/aseguradoras/${aseguradora.id}/delete">
        <input type="submit" value="borrar" />
    </form></h2>
    <br/>
    <br/>
    <br/>
    <h2>Especialista de esta aseguradora</h2>

    <table class="table table-striped">
        <c:forEach var="especialista" items="${aseguradora.especialistas}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Especialista</dt>
                        <dd><c:out value="${especialista.firstName} ${especialista.lastName}"/></dd>
                        <dt>Especialidad</dt>
                        <dd><c:out  value="${especialista.especialidad}"/></dd>
                        <dt>Opciones:</dt>
                        <dd><spring:url value="/aseguradoras/delete/{especialistaId}/{aseguradoraId}" var="deleteEspecialista">
                            <spring:param name="aseguradoraId" value="${aseguradora.id}"/>
                            <spring:param name="especialistaId" value="${especialista.id}"/>
						</spring:url>
						<a href="${fn:escapeXml(deleteEspecialista)}">borrar especialista</a></dd>
                    </dl>
                </td>
            </tr>
        </c:forEach>
    </table>
    <spring:url value="/polizas/new/{aseguradoraId}" var="newPoliza">
    <spring:param name="aseguradoraId" value="${aseguradora.id}"/>
    </spring:url>
<a href="${fn:escapeXml(newPoliza)}">Nueva poliza</a>
    <h2>Polizas y pacientes de esta aseguradora</h2>
    <table class="table table-striped">
        <c:forEach var="poliza" items="${aseguradora.polizas}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Poliza:</dt>
                        <dd><c:out value="${poliza.cobertura}"/></dd>
                        <dt>Precio:</dt>
                        <dd><c:out  value="${poliza.precio}"/></dd>
                        <dt>Opciones:</dt>
                        <dd><spring:url value="/polizas/edit/{aseguradoraId}/{polizaId}" var="editPoliza">
                            <spring:param name="aseguradoraId" value="${aseguradora.id}"/>
                            <spring:param name="polizaId" value="${poliza.id}"/>
						</spring:url>
                        <a href="${fn:escapeXml(editPoliza)}">editar poliza</a></dd>

                        <dd><spring:url value="/aseguradoras/deletePoliza/{aseguradoraId}/{polizaId}" var="borrarPoliza">
                            <spring:param name="aseguradoraId" value="${aseguradora.id}"/>
                            <spring:param name="polizaId" value="${poliza.id}"/>
						</spring:url>
                        <a href="${fn:escapeXml(borrarPoliza)}">borrar poliza</a></dd>
                    </dl>
                </td>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <c:forEach var="paciente" items="${poliza.pacientes}">
                            <dt>Paciente: </dt>
                            <dd><c:out value="${paciente.firstName} ${paciente.lastName}"/></dd>
                            <dt>Opciones:</dt>
                            <dd><spring:url value="/polizas/delete/{pacienteId}/{aseguradoraId}/" var="deletePaciente">
                                <spring:param name="pacienteId" value="${paciente.id}"/>
                                <spring:param name="aseguradoraId" value="${aseguradora.id}"/>
                                
                            </spring:url>
                            <a href="${fn:escapeXml(deletePaciente)}">borrar paciente de poliza</a></dd>
                        </c:forEach>
                        </dl>
                </td>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <c:forEach var="tratamiento" items="${poliza.tratamientos}">
                            <dt>Descripcion:  </dt>
                            <dd><c:out value="${tratamiento.descripcion}"/></dd>
                            <dt>duracion:</dt>
                            <dd><c:out value="${tratamiento.duracion}"/></dd>   
                        </c:forEach>
                        </dl>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
