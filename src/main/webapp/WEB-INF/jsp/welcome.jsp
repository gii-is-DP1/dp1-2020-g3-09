<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="page-header">
				<h1>
					TEMPURA 17
				</h1>
			</div>
			<img src="https://www.amepaorg.com/wp-content/uploads/2019/06/importancia-de-la-consulta-medica.jpg" alt="Inicio"> 
		</div>
	</div>
	</div>
	<script type="text/javascript">
		console.log(getElementByXPath("/html/body/nav/div/div[2]/ul[2]/li/a/strong"));
	</script>
</petclinic:layout>
