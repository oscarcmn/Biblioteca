<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:directive.include file="../includes/includefile.jspf" />
<title>Modificar Socio</title>
</head>
<body>
	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="../WEB-INF/menu.jspf" />
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
		<div id="formBusquedaSocio" class="formulariogeneral">
			<form name="frmBusquedaSocio" id="frmBusquedaSocio" method="post"
				action="${pageContext.request.contextPath}/controllerAdmin">
				<fieldset id="datosSocio">
					<legend>
						<img
							src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Introduzca
						parte del nombre del Socio a modificar
					</legend>
					<div class="etiquetas">
						<label for="nombre">Nombre:</label>
					</div>
					<div class="campos">
						<input type="search" name="frmbusquedanombre"
							id="frmbusquedanombre" value="${iniciales}" size="25"
							maxlength="40"> <input name="operacion" type="hidden"
							id="operacion" value="busquedasocio">
					</div>
					<div class="cb"></div>
					<div class="botones">
						<input type="submit" name="Submit" value="Buscar">
					</div>
				</fieldset>
			</form>
		</div>
		<c:if test="${listadoSociosBusqueda!=null}">

			<c:choose>
				<c:when test="${not empty listadoSociosBusqueda}">
					<div class="w-75 ma">
						<table class="table tablaconborde tablacebra tabla-hover">
							<caption>Listado de Socios</caption>
							<thead>
								<tr>
									<th scope="col">IDSOCIO</th>
									<th scope="col">NOMBRE</th>
									<th scope="col">DIRECCION</th>
									<th scope="col">EDITAR</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listadoSociosBusqueda}" var="socio">
									<tr>
										<td class="txtderecha">${socio.idsocio}</td>
										<td>${socio.nombre}</td>
										<td>${socio.direccion}</td>
										<td class="txtcentrado"><a
											href="${pageContext.request.contextPath}/controllerAdmin?operacion=editarsocio&socio=${socio.idsocio}">Editar</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:when>
				<c:otherwise>
					<p align="center" class="advertencia">
						<c:out
							value="Ningún registro coincide con el patrón: ${iniciales}"></c:out>
					</p>
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
</body>
</html>