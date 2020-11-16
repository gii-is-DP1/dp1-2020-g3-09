<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pacientes">
    <h2>Pacientes</h2>

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Apellidos</th>
            <th>email</th>
            <th style="width: 120px">direccion</th>
            <th>sexo</th>
            <th>edad</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pacientes}" var="paciente">
            <tr>
                <td>
                    <c:out value="${paciente.firstName}"/></a>
                </td>
                <td>
                    <c:out value="${paciente.lastName}"/></a>
                </td>
                <td>
                    <c:out value="${paciente.email}"/>
                </td>
                <td>
                    <c:out value="${paciente.direccion}"/>
                </td>
                <td>
                    <c:out value="${paciente.sexo}"/>
                </td>
                <td>
                    <c:out value="${paciente.edad}"/>
                </td>
                
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
