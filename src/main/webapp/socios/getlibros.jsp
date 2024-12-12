<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado de libros</title>
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
		<div id="formBusquedaLibro" class="formulariogeneral">
			<form name="frmBusquedaLibro" id="frmBusquedaLibro" method="post"
				action="${pageContext.request.contextPath}/controllersocio">
				<fieldset id="datosLibros">
					<legend>
						<img
							src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;
						Busqueda sencilla
					</legend>
					<div class="etiquetas">
						<label for="nombre">Nombre:</label>
					</div>
					<div class="campos">
						<input type="search" name="iniciales"
							id="iniciales" value="${iniciales}" size="25"
							maxlength="40"> 
						<select id="criteriobusqueda" name="criteriobusqueda">
							<option value="autor">autor</option>
							<option value="titulo">titulo</option>
							<option value="isbn">isbn</option>
						</select>
					</div>
					<div class="cb"></div>
					<div class="botones">
					<input name="operacion" type="hidden"
							id="operacion" value="listadolibros">
						<input type="submit" name="Submit" value="Buscar">
					</div>
				</fieldset>
			</form>
		</div>
		<c:if test="${not empty listadolibros}">
			<div class="w-75 ma py-2">
				<table class="table tablaconborde tablacebra tabla-hover">
					<caption>Listado de Libros</caption>
					<thead>
						<tr>
							<th scope="col">TITULO</th>
							<th scope="col">AUTOR</th>
							<th scope="col">TOTALES</th>
							<th scope="col">PRESTADOS</th>
							<th scope="col">DISPONIBLES</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listadolibros}" var="libro">
							<tr>
								<td class="txtcentrado">${libro.titulo}</td>
								<td class="txtcentrado">${libro.nombreAutor}</td>
								<td class="txtcentrado">${libro.ejemplaresTotales}</td>
								<td class="txtcentrado">${libro.ejemplaresEnPrestamo}</td>
								<td class="txtcentrado">${libro.ejemplaresDisponibles}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>
</body>
</html>