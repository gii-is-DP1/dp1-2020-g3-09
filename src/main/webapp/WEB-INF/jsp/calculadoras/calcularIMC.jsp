<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="calculadoras">
    <html>

    <head>

      <title>Calculadora de Indice de Masa Corporal</title>

    </head>

    <body>

    <h1>Calculadora de IMC: Indice de Masa Corporal</h1>

    <!-- FORMULARIO HTML -->

    <form:form modelAttribute="calculadora" class="form-horizontal" id="add-calculadora">
    <p>Ingrese peso (kg): <input type="text" id="peso"></p><br>

    <p>Ingrese Altura (m): <input type="text" id="altura"></p><br>
    <div class="col-sm-offset-2 col-sm-10">
    <button id="calc">Calcular</button><br>
    <br>
    </div>

    <p>IMC: <span id="imc"></span></p><br>

    <p>Resultado: <span id="lectura"></span></p><br>
    </form:form>

 

    <!-- SCRIPT PARA CALCULAR IMC -->

    <script type="text/javascript">

        calc = document.getElementById("calc");

        kg = document.getElementById("peso");

        m = document.getElementById("altura");

        imc = document.getElementById("imc");

        lectura = document.getElementById("lectura");

 

        calc.onclick = function(){

             if(kg.value!="" && m.value!=""){

                   imcx = (kg.value / (m.value* m.value));

                   imc.innerHTML = imcx

                   console.log(imcx);

 

                   if(imcx<18.5){ lectura.innerHTML = "Peso inferior al normal"; }

                   else if(imcx>=18.5 && imcx<=24.9){ lectura.innerHTML = "Peso normal"; }

                   else if(imcx>=25 && imcx<=29.9){ lectura.innerHTML = "Peso superior al normal"; }

                   else if(imcx>30){ lectura.innerHTML = "Obesidad"; }

 

             }else{

                   alert("Debes ingresar peso y altura.")

             }
        };

    </script>

    </body>

    </html>
</petclinic:layout>