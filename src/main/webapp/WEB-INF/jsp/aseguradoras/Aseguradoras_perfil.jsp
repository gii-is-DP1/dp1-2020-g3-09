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
                    </dl>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h2>Polizas y pacientes de esta aseguradora</h2>
    <table class="table table-striped">
        <c:forEach var="poliza" items="${aseguradora.polizas}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Poliza</dt>
                        <dd><c:out value="${poliza.cobertura}"/></dd>
                        <dt>Precio</dt>
                        <dd><c:out  value="${poliza.precio}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Paciente</th>
                            <!--<th>Nombre</th>-->
                        </tr>
                        </thead>
                        <c:forEach var="paciente" items="${poliza.pacientes}">
                            <tr>
                                <td><c:out value="${paciente.firstName} ${paciente.lastName}"/></td>
                                <!--<td><c:out value="${visit.description}"/></td>-->
                            </tr>
                        </c:forEach>
                        <!--
                        <tr>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}">Edit Pet</a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(visitUrl)}">Add Visit</a>
                            </td>
                        </tr>
                        -->
                    </table>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
