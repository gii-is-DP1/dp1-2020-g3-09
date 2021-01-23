<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="actas">
    <h2>
        <c:if test="${acta['new']}">Nueva </c:if> Acta
    </h2>
    <form:form modelAttribute="acta" class="form-horizontal" id="add-acta-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Descripcion" name="descripcion"/>
            <petclinic:inputField label="Exploracion" name="exploracion"/>
            <petclinic:inputField label="Diagnostico" name="diagnostico"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${acta['new']}">
                        <button class="btn btn-default" type="submit">Guardar Acta</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Modificar Acta</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>