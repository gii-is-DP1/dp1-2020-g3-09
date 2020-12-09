<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="alarmas">

    <h2>
        <c:if test="${alarma['new']}">Nueva </c:if>Alarma
    </h2>
    <form:form modelAttribute="alarma" class="form-horizontal" id="add-alarma-form">
    <!--<c:forEach items="${citas}" var="citas">
        <h2>Informacion de la cita:</h2>
		<label>${citas.formato}</label>
		<label>${citas.tipo}</label>
		<label>${citas.especialidad}</label>
        <label>${citas.especialista}</label>
    </c:forEach>-->
    <h3>Configurar alerta</h3>
        <petclinic:inputField label="Dias de antelacion:" name="diasAntelacion"></petclinic:inputField><br>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Guardar Recordatorio</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>