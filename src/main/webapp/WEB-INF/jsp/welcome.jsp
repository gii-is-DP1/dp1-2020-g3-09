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
			<div class="alert alert-info">
				<a href="/citas" class="alert-link">Quedan 2 dias para su proxima cita</a>
				<button type="button" class="close" data-dismiss="alert"></button>
			</div>
			<nav class="navbar navbar-expand-sm bg-light">
				<ul class="navbar-nav">
					<li class="nav-item">
						<a class="nav-link" href="/citas">Mis citas</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/pacientes">Pacientes</a>
					</li>
				</ul>
			</nav>
		</div>
	</div>
	</div>
</petclinic:layout>