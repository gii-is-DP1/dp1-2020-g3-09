<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="alarmas">
    <div class="alert alert-info">
		Quedan 2 dias para su proxima<a href="/citas" class="alert-link"> cita</a>
		<button type="button" class="close" data-dismiss="alert">&times;</button>
	</div>
    <h2>
        <c:if test="${alarma['new']}">Nueva </c:if>Alarma
    </h2>
    <form:form modelAttribute="alarma" class="form-horizontal" id="add-alarma-form">
    <h3>Detalles de la cita</h3>
    <c:forEach items="${alarma.cita}" var="cita">
        <h2>Informacion de la cita:</h2>
		<label>${cita.formato}</label>
		<label>${cita.tipo}</label>
		<label>${cita.especialidad}</label>
        <label>${cita.especialista}</label>
    </c:forEach>
    

    <h3>Configurar alerta</h3>
        <petclinic:inputField label="Dias de antelacion:" name="diasAntelacion"></petclinic:inputField><br>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Guardar Recordatorio</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>