<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nuevo Socio</title>
<jsp:directive.include file="includes/includefile.jspf" />
</head>
<body>
<script src="https://www.google.com/recaptcha/api.js?render=6LfpmYoqAAAAAJ5VKi5U--STyIPX57Og_ukogule"></script>
<script>
grecaptcha.ready(function() {
grecaptcha.execute('6LfpmYoqAAAAAJ5VKi5U--STyIPX57Og_ukogule', {action:
'altasocio'})
.then(function(token) {
var recaptchaResponse = document.getElementById('g-recaptcha-response');
recaptchaResponse.value = token;
});
});
</script>
	<div class="container">
		<div class="header"></div>
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
		<div id="formAutor" class="formulariogeneral">
			<form name="frmAutor" method="post"
				action="${pageContext.request.contextPath}/Controller">
				<fieldset id="datosSocio">
					<legend>
						<img
							src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Nuevo
						Socio
					</legend>
					<div class="etiquetas">
						<label for="email">Email:</label>
					</div>
					<div class="campos">
						<input type="text" id="email" name="email"
							value="${nuevosocio.email}" />
					</div>
					<div class="etiquetas">
						<label for="nombre">Nombre:</label>
					</div>
					<div class="campos">
						<input type="text" id="nombre" name="nombre"
							value="${nuevosocio.nombre}" />
					</div>
					<div class="etiquetas">
						<label for="direccion">Direccion:</label>
					</div>
					<div class="campos">
						<input type="text" id="direccion" name="direccion"
							value="${nuevosocio.direccion}" />
					</div>
					<div class="etiquetas">
						<label for="clave">Clave:</label>
					</div>
					<div class="campos">
						<input type="password" id="clave" name="clave"
							 /> 
					</div>
							<div class="etiquetas">
						<label for="telefono">Telefono:</label>
					</div>
					<div class="campos">
						<input type="text" id="telefono" name="telefono"
							value="${nuevosocio.telefono}" /><input name="operacion"
							type="hidden" id="operacion" value="registrarse">
					</div>
					
					<div class="botones">
						<input type="submit" name="Submit" value="Guardar">
					</div>
				</fieldset>
				<input type="hidden" id="g-recaptcha-response" name="g-recaptcha-response">
			</form>
		</div>
		<div id="separacion">
			<br>
		</div>
	</div>
</body>
</html>