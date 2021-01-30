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
    </table>

    <h2><form action="http://localhost:8080/especialistas/${especialista.id}/edit">
    <input type="submit" value="editar" />
    </form>
    <form action="http://localhost:8080/especialistas/${especialista.id}/delete">
        <input type="submit" value="borrar" />
    </form></h2>

   <!--<spring:url value="{ownerId}/edit" var="editUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Owner</a>

    <spring:url value="{ownerId}/pets/new" var="addUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Pet</a>
    -->
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
                    <form action="http://localhost:8080/citas/${cita.id}/delete">
                        <input type="submit" value="borrar cita" />
                    </form>

                    <form action="http://localhost:8080/citas/${cita.id}/edit">
                        <input type="submit" value="editar cita" />
                    </form>

                    <form action="http://localhost:8080/citas/${especialista.id}/${cita.paciente.id}">
                        <input type="submit" value="Ver citas de este paciente" />
                    </form>
					</td>
                <!--<td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Visit Date</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${pet.visits}">
                            <tr>
                                <td><petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/></td>
                                <td><c:out value="${visit.description}"/></td>
                            </tr>
                        </c:forEach>
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
                    </table>
                </td>-->
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
