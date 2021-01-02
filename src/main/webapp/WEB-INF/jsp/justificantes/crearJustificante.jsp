<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="justificantes">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>
    
    <script>
        function JustificantePdf() {
            var pdf = new jsPDF('p', 'pt', 'letter');
            source = $('#imprimir')[0];
    
            specialElementHandlers = {
                '#bypassme': function (element, renderer) {
                    return true
                }
            };
            margins = {
                top: 80,
                bottom: 60,
                left: 40,
                width: 522
            };
    
            pdf.fromHTML(
                source, 
                margins.left, // x coord
                margins.top, { // y coord
                    'width': margins.width, 
                    'elementHandlers': specialElementHandlers
                },
    
                function (dispose) {
                    pdf.save('Justificante.pdf');
                }, margins
            );
        }
    </script>
    
    
    <div id="imprimir">
        <form:form modelAttribute="justificante.cita" class="form-horizontal" id="form">
        <div class="form-group has-feedback">
            <label>Motivo del justificante:</label>
            <select name="motivo">
                <option value="TRABAJO">Trabajo</option>
                <option value="CENTRO ESCOLAR">Centro Escolar</option>
            </select>
            <label>Detalles de la cita:</label>
            <select name = "Tipo">
                <option value = "ASEGURADO"> Asegurado</option>
                <option value = "PRIVADO"> Privado </option>
            </select>
            <select name = "Formato">
                <option value = "PRESENCIAL"> Presencial</option>
                <option value = "ONLINE"> Online </option>
            </select>
            <petclinic:inputField label="Especialidad" name="Especialidad"/>
            
        </div>
        <button class="btn btn-default" type="submit">Guardar justificante</button>
    </form:form>
    </div>
</petclinic:layout>
