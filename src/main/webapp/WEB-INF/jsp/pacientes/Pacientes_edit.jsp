<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Pacientes">
    <h2>
        Editar paciente
    </h2>
    <form:form modelAttribute="paciente" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Direccion" name="direccion"/>
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="firstName" name="firstName"/>
            <petclinic:inputField label="lastName" name="lastName"/>
            <petclinic:inputField label="dni" name="dni"/>
            <petclinic:inputField label="Edad" name="edad"/>
            <petclinic:inputField label="Aseguradora" name="aseguradora"/>

        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">  
            <button class="btn btn-default" type="submit">Actualizar paciente</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
