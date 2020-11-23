<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="citas">
    <form:form modelAttribute="cita" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
        <h2>Pedir nueva cita:</h2><br>
        <br>
            <label>Tipologia:</label>
            <select name = "Tipo">
                <option value = "ASEGURADO"> Asegurado</option>
                <option value = "PRIVADO"> Privado </option>
            </select><br>
            <br>
            <label>Formato:</label>
            <select name = "Formato">
                <option value = "PRESENCIAL"> Presencial</option>
                <option value = "ONLINE"> Online </option>
            </select><br>
            <br>
            <label>Especialidad:</label>
            <select name = "Especialidad">
                <option value = "especialidad1"> Especialidad1</option>
                <option value = "especialidad2"> Especialidad2</option>
            </select><br>
            <br>
            <label>Especialista:</label>
            <select name = "Especialista">
                <option value = "especialista1"> Especialista1</option>
                <option value = "especialista2"> Especialista2</option>
            </select><br>
            <br>
            <label>Fecha de la cita:</label>
            <input type="date"><br>
            <br>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Pedir Cita</button>
                </div>
            </div>
        </div>
    </form:form>
    
</petclinic:layout>