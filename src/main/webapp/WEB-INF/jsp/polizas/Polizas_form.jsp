<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Poliza">
    <h2>
    Nueva Poliza
    </h2>
    <form:form modelAttribute="poliza" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">

            <petclinic:inputField label="Name" name="name"/>
            <petclinic:inputField label="Precio" name="precio"/>

            <label for="coberturas">Cobertura:</label>
            <select name = "Cobertura" id="coberturas">
                <c:forEach items="${coberturas}" var="cobertura" varStatus="loop">
                <option value="${cobertura}">
                    ${cobertura}
                </option>
                </c:forEach>
                </select><br>
            
            <label for="duracion">Fecha de expiracion</label>
            <input type="date" name="duracion" id="duracion"><br>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-default" type="submit">Añadir poliza</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>