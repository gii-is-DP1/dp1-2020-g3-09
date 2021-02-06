<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="tratamientos">
    <h2>
        <c:if test="${tratamiento['new']}">Nuevo </c:if> Tratamiento
    </h2>
    <form:form modelAttribute="tratamiento" class="form-horizontal" id="add-tratamiento-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Descripcion" name="descripcion"/>
            <petclinic:inputField label="Duracion(en dias)" name="duracion"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${tratamiento['new']}">
                        <button class="btn btn-default" type="submit">Guardar Tratamiento</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Modificar Tratamiento</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>