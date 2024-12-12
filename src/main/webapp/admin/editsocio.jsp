<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:directive.include file="../includes/includefile.jspf" />
<title>Edición Socio</title>
</head>
<body>
	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="../WEB-INF/menu.jspf" />
		</div>
 <c:if test="${requestScope.error != null}">
    <div class="diverror">
       <p>
       <strong><c:out value="Mensaje"/></strong>
       <br>
       <c:out value="${requestScope.error}"/>
       </p>
    </div>
  </c:if>   		
 <c:if test="${requestScope.confirmaroperacion != null}">
    <div class="divconfirmacion">
       <p>
       <strong><c:out value="Mensaje"/></strong>
       <br>
       <c:out value="${requestScope.confirmaroperacion}"/>
       </p>
    </div>
  </c:if>    
	<div id="formeditSocio" class="formulariogeneral">
		<form name="frmeditSocio" method="post" action="${pageContext.request.contextPath}/controllerAdmin">
			<fieldset id="datosSocio">
			<legend><img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Modifique los datos</legend>
			<table>
  			<tr>
    			<td>Nombre:</td>
    			<td><input name="frmeditSocioNombre" type="text" value="${socioenproceso.nombre}" id="frmeditSocioNombre" size="40" maxlength="40"></td>
  			</tr>
  			<tr>
    			<td>Direccion:</td>
    			<td><input name="frmeditSocioDireccion"  type="text" id="frmeditSocioDireccion" value="${socioenproceso.direccion}"size="40" maxlength="40"></td>
  			</tr>
  			<tr>
    			<td>Version:</td>
    			<td><input name="frmeditSocioVersion" type="text" id="frmeditSocioVersion" value="${socioenproceso.version}" readonly></td>
  			</tr>  			

  			<tr>
    			<td><input name="operacion" type="hidden" id="operacion" value="updatesocio"></td>
    			<td><input type="submit" name="Submit" value="Modificar"></td>
    			<td><input name="frmeditSocioIdSocio" type="hidden" id="frmeditSocioIdSocio" value="${socioenproceso.idsocio}"></td>
    			
  			</tr>
			</table>
			</fieldset>
		</form>	
	</div>		
	</div>
</body>
</html>