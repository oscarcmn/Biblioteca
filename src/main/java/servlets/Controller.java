package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import captcha.VerificarRecaptcha;
import dao.DaoAutor;
import dao.DaoLibro;
import dao.DaoSocio;
import entidades.Socio;

/**
 * Controller para gestionar solamente el registro de usuarios nuevos
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operacion = request.getParameter("operacion");
		DaoSocio daoSocio = new DaoSocio();
		switch (operacion) {
		
		case "registrarse":
			//recogemos los datos del formulario
			
			String nombre = request.getParameter("nombre");
			String email = request.getParameter("email");
			String clave = request.getParameter("clave");
			String telefono = request.getParameter("telefono");
			String direccion = request.getParameter("direccion");
			String grecaptcharesponse = request.getParameter("g-recaptcha-response");
			System.out.println("Parametro google recaptcha a validar: " + grecaptcharesponse);
			if (!VerificarRecaptcha.validate(grecaptcharesponse)) {
			String mensajeError = "Verifique que no es un robot";
			request.setAttribute("error", mensajeError);
			request.getRequestDispatcher("altasocio.jsp").forward(request, response);
			} else {
				//insertar una tupla en socio
				Socio socio = new Socio();
				socio.setNombre(nombre);
				socio.setEmail(email);
				socio.setClave(clave);
				socio.setDireccion(direccion);
				socio.setTelefono(telefono);
				try {
					daoSocio.insertarSocio(socio, clave);
					socio= daoSocio.findSocioByEmail(email);
					
				} catch (Exception e) {
					procesarError(request, response, e, "error.jsp");
				}
				request.setAttribute("socio", socio);
				request.getRequestDispatcher("socioregistrado.jsp").forward(request, response);
			}
			
			break;
			
			
		case "validacion":
				String token = request.getParameter("token");
				email = request.getParameter("email");
				daoSocio = new DaoSocio();
			try {
				daoSocio.activarCuenta(email, token);
				response.sendRedirect("cuentaactivada.jsp");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			default:
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e, String url)	throws ServletException, IOException {
		String mensajeError = e.getMessage();
		request.setAttribute("error", mensajeError);
		if (url == null) {
			url = "error.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);
	} 
	
}
