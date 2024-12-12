<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado de socios morosos</title>
<jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="/WEB-INF/menu.jspf" />
		</div>
		<c:if test="${error != null}">
			<div class="diverror">
				<p>
					<strong><c:out value="Error" /></strong> <br>
					<c:out value="${error}" />
				</p>
			</div>
		</c:if>
		<c:if test="${confirmaroperacion != null}">
			<div class="divconfirmacion">
				<p>
					<strong><c:out value="Mensaje" /></strong> <br>
					<c:out value="${confirmaroperacion}" />
				</p>
			</div>
		</c:if>
		<c:if test="${not empty listadomorosos}">
			<div class="w-75 ma py-2">
				<table class="table tablaconborde tablacebra tabla-hover">
					<caption>Listado de Socios</caption>
					<thead>
						<tr>
							<th scope="col">IDSOCIO</th>
							<th scope="col">NOMBRE</th>
							<th scope="col">Ver Libros</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listadomorosos}" var="socio">
							<tr>
								<td class="txtcentrado">${socio.idsocio}</td>
								<td class="txtcentrado">${socio.nombre}</td>
								<td class="txtcentrado"><a href="${pageContext.request.contextPath}/controllerAdmin?operacion=librosdemorosos&socio=${socio.idsocio}">Ver libros</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		<c:if test="${not empty listadolibrosprestados}">
			<div class="w-75 ma py-2">
				<table class="table tablaconborde tablacebra tabla-hover">
					<caption>Prestamos no devueltos del socio</caption>
					<thead>
						<tr>
							<th scope="col">TITULO</th>
							<th scope="col">FECHA DEL PRESTAMO</th>
							<th scope="col">DIAS DEMORA</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.listadolibrosprestados}" var="libro">
							<tr>
								<td class="txtcentrado">${libro.titulo}</td>
								<td class="txtcentrado">${libro.fechaprestamo}</td>
								<td class="txtcentrado">${libro.diasDemora}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>
</body>
</html>