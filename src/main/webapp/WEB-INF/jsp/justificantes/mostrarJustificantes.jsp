<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


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
    
    <label>Justificante de la cita:</label>
    <div id="imprimir">

    <p>El paciente con DNI <c:out value="${justificante.cita.paciente.dni}"/> tiene una cita en la fecha <c:out value="${justificante.cita.fecha}"/> de forma 
        <c:out value="${justificante.cita.formato}"/> con el doctor/a <c:out value="${justificante.cita.especialista}"/> 
        para <c:out value="${justificante.cita.especialidad}"/></p>

    </div><br>
    <a href="javascript:JustificantePdf()" class="button">Descargar justificante</a>

</petclinic:layout>
