<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="calculadoras">
    <html>

    <head>

      <title>Calculadora de Indice de Masa Corporal</title>

    </head>

    <body>

    <!--<h1>Calculadora de IMC: Indice de Masa Corporal</h1>-->

    <!-- FORMULARIO HTML -->
    <h2>
      <c:if test="${calculadora['new']}">Nueva </c:if> Calculadora
    </h2>

    <form:form modelAttribute="calculadora" class="form-horizontal" id="add-calculadora-form">
    <div class="form-group has-feedback">
        <petclinic:inputField label="Ingrese peso (kg):" name="peso"/>

        <petclinic:inputField label="Ingrese altura(m):" name="altura"/>
      
        <button id="calc" type="hidden">Calcular</button><br>
        <br>
        <p>IMC: <span id="imc"></span></p><br>
        
        <p>Resultado: <span id="resultado"></span></p><br>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
          <c:choose>
              <c:when test="${calculadora['new']}">
                  <button class="btn btn-default" type="submit">Guardar Calculadora</button>
              </c:when>
          </c:choose>
      </div>
    </div>
    </form:form>
</petclinic:layout>
    

 

    <!-- SCRIPT PARA CALCULAR IMC -->

    <script type="text/javascript">

        calc = document.getElementById("calc");

        kg = document.getElementById("peso");

        m = document.getElementById("altura");

        imc = document.getElementById("imc");

        resultado = document.getElementById("resultado");

 

        calc.onclick = function(){

             if(kg.value!="" && m.value!=""){

                   imcx = (kg.value / (m.value* m.value));

                   imc.innerHTML = imcx

                   console.log(imcx);

 

                   if(imcx<18.5){ resultado.innerHTML = "Peso inferior al normal"; }

                   else if(imcx>=18.5 && imcx<=24.9){ resultado.innerHTML = "Peso normal"; }

                   else if(imcx>=25 && imcx<=29.9){ resultado.innerHTML = "Peso superior al normal"; }

                   else if(imcx>30){ resultado.innerHTML = "Obesidad"; }

 

             }else{

                   alert("Debes ingresar peso y altura.")

             }
        };

    </script>
    

    </body>

    </html>
