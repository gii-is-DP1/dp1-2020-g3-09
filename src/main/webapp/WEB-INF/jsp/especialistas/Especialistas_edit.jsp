<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    <h2>
        Editar especialista
    </h2>
    <form:form modelAttribute="especialista" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Direccion" name="direccion"/>
            <petclinic:inputField label="Correo" name="correo"/>
            <petclinic:inputField label="Telefono" name="telefono"/>
            <petclinic:inputField label="firstName" name="firstName"/>
            <petclinic:inputField label="lastName" name="lastName"/>
            <petclinic:inputField label="dni" name="dni"/>
            <petclinic:inputField label="especialidad" name="especialidad"/>
            <petclinic:inputField label="citas" name="citas"/>
            <petclinic:inputField label="actas" name="actas"/>
            <petclinic:inputField label="aseguradoras" name="aseguradoras"/>

        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">  
            <button class="btn btn-default" type="submit">Actualizar especialista</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
