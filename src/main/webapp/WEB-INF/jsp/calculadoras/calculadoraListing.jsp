<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="calculadoras">
    <h2>Calculadora</h2>

    <table id="calculadorasTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Peso</th>
            <th style="width: 200px;">Altura</th>
            <th style="width: 120px">IMC</th>
            <th style="width: 120px">Resultado</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${calculadoras}" var="calculadora">
                <tr>
                    <td>${calculadora.peso}</td>
                    <td>${calculadora.altura}</td>
                    <td>${calculadora.imc}</td>
                    <td>${calculadora.resultado}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</petclinic:layout>