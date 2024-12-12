package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import dao.DaoAutor;
import dao.DaoPrestamo;
import dao.DaoSocio;
import entidades.Autor;
import entidades.Prestamo;
import entidades.Socio;

/**
 * Servlet implementation class ControllerAdmin
 */
public class ControllerAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerAdmin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//obtenemos la operación que han seleccionado en el menu
		String operacion = request.getParameter("operacion");	
		
		HttpSession session = request.getSession();		
		DaoAutor daoAutor = null;  //variable dao para autores
		DaoSocio daoSocio = null;
		DaoPrestamo daoPrestamo = null;
		Socio socio = new Socio();
		
		
		switch (operacion) {
		
		case "insertaautor":
			//me han pedido insertar un autor. Lo primero será crear el autor con los campos que vengan en la request
			String nombre =request.getParameter("nombre");
			String strFechaNacimiento = request.getParameter("fechaNacimiento");
			
			//transformamos la fecha a java.sql.Date
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(strFechaNacimiento,formato);
			Date  fechaEnSql = java.sql.Date.valueOf(localDate);
			
			//creamos el objeto Autor
			Autor autor = new Autor();
			autor.setNombre(nombre);
			autor.setFechaNacimiento(fechaEnSql);
			
			//creamos el objeto DAO
			daoAutor = new DaoAutor();
			
			try {
				//insertamos
				daoAutor.insertaAutor(autor);
				
				//ofrezco una respuesta
				request.setAttribute("confirmaroperacion", "Autor creado satisfactoriamente");
				request.getRequestDispatcher("admin/altaautor.jsp").forward(request, response); //redirecciono
			} catch (SQLException e) {
				procesarError(request, response, e, "admin/altaautor.jsp");
			} catch (Exception e) {
				procesarError(request, response, e, "admin/altaautor.jsp");
			}
			
			break;

		/*case "insertasocio":
			String email = request.getParameter("email");
			String nombresocio =request.getParameter("nombre");
			String direccion = request.getParameter("direccion");
			String clave = request.getParameter("password");
			
			Socio socio = new Socio();
			socio.setEmail(email);
			socio.setNombre(nombresocio);
			socio.setDireccion(direccion);
			socio.setClave(clave);
			
			
			daoSocio = new DaoSocio();
			
			try {
				
				daoSocio.insertarSocio(socio);
				socio=daoSocio.findSocioByEmail(socio.getEmail());
				request.setAttribute("socio", socio);
				
				
				request.setAttribute("confirmaroperacion", "Socio creado satisfactoriamente");
				request.getRequestDispatcher("admin/altasocio.jsp").forward(request, response); //redirecciono
			} catch (SQLException e) {
				procesarError(request, response, e, "admin/altasocio.jsp");
			} catch (Exception e) {
				procesarError(request, response, e, "admin/altasocio.jsp");
			}
			break;
		*/	
		case "listadoSociosPaginado":
			System.out.println("Comenzando listado");
			daoSocio=new DaoSocio();
			int totalRegistros = 0;
			int pagina = 0;
			int numregpag = 3;
			int paginamasalta = 0;
			List<Socio> listadoSocios = null;
			
			if (request.getParameter("pag") !=null) {
				pagina = Integer.parseInt(request.getParameter("pag"));
			}
			if (request.getParameter("nrp") !=null) {
				numregpag = Integer.parseInt(request.getParameter("nrp"));
			}
			//calcular la pagina mas alta
			try {
				totalRegistros = daoSocio.getTotalRegistros();
				paginamasalta = totalRegistros / numregpag;
				if(totalRegistros % numregpag ==0)paginamasalta--;
				listadoSocios = daoSocio.listadoSocios(pagina,numregpag);
				request.setAttribute("pagina", pagina);
				request.setAttribute("numregpag", numregpag);
				request.setAttribute("paginamasalta", paginamasalta);
				request.setAttribute("totalRegistros", totalRegistros);
				request.setAttribute("listadoSocios", listadoSocios);
				request.getRequestDispatcher("admin/listadosocios.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarError(request, response, e,"admin/listadosocios.jsp");
			} catch (Exception e) {
				procesarError(request, response, e,"admin/listadosocios.jsp");
			}
			break;
			
		case "listadoSociosPaginadoNombre":
			System.out.println("Comenzando listado");
			daoSocio=new DaoSocio();
			String nombreSocio = request.getParameter("nombre");
			try {
				List<Socio> listadoSocioss = daoSocio.listadoSociosByNombre(nombreSocio);
				request.setAttribute("listadoSocios", listadoSocioss);
				request.getRequestDispatcher("admin/getsocio.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarError(request, response, e,"admin/getsocio.jsp");}
			  catch (Exception e) {
				procesarError(request, response, e,"admin/getsocio.jsp");}
			break;
		
		case "busquedasocio":
			String iniciales = request.getParameter("frmbusquedanombre");
			System.out.println(iniciales);
			daoSocio = new DaoSocio();
			try {
				listadoSocios = daoSocio.listadoSociosByNombre(iniciales);
				System.out.println(listadoSocios);
				request.setAttribute("iniciales", iniciales);
				request.setAttribute("listadoSociosBusqueda", listadoSocios);
				request.getRequestDispatcher("admin/getsocio.jsp").forward(request, response);
			} catch (SQLException e) {		
				procesarError(request, response, e, "admin/getsocio.jsp");
				//e.printStackTrace();
			} catch (Exception e) {
				procesarError(request, response, e, "admin/getsocio.jsp");
			}
			break;	
			
		case "editarsocio":
			long idsocio = Long.parseLong(request.getParameter("socio"));
			daoSocio = new DaoSocio();
			try {
				socio = daoSocio.findSocioById(idsocio);
				request.setAttribute("socioenproceso", socio);
				request.getRequestDispatcher("admin/editsocio.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarError(request, response, e, null);
			} catch (Exception e) {
				procesarError(request, response, e, null);
			}
			break;
			
		case "updatesocio":
			socio = new Socio();
			Long codigoSocio = Long.decode(request.getParameter("frmeditSocioIdSocio"));
			socio.setIdsocio(codigoSocio);
			socio.setNombre(request.getParameter("frmeditSocioNombre"));
			socio.setDireccion(request.getParameter("frmeditSocioDireccion"));
			daoSocio = new DaoSocio();
			try {
				request.setAttribute("socioenproceso", socio);
				daoSocio.updateSocio(socio);
				Socio sociomodificado=daoSocio.findSocioById(socio.getIdsocio());
				request.setAttribute("socioenproceso", sociomodificado);
				request.setAttribute("confirmaroperacion", "Socio modificado");
				request.getRequestDispatcher("admin/editsocio.jsp").forward(request, response);
			} catch (SQLException sqlexc) {
				System.out.println("que pasa en sqlexception....");
				procesarError(request, response, sqlexc, "admin/editsocio.jsp");
			} catch (Exception e) {
				procesarError(request,response,e,"admin/editsocio.jsp");
				// procesarError(request,response,e);
			}
			break;	
			
		case "socioslibrosfueraplazo":
			daoSocio = new DaoSocio();
			try {
				listadoSocios = daoSocio.listadoSociosMorosos();
				session.setAttribute("listadomorosos", listadoSocios);
				request.getRequestDispatcher("admin/listadosociosmorosos.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarError(request, response, e, null);
			} catch (Exception e) {
				procesarError(request, response, e, null);
			}
					
			break;
		case "librosdemorosos":
			/*daoSocio = new DaoSocio();
			try {
				listadoSocios = daoSocio.listadoSociosMorosos();
				request.setAttribute("listadomorosos", listadoSocios);
			} catch (SQLException e) {
				procesarError(request, response, e, null);
			} catch (Exception e) {
				procesarError(request, response, e, null);
			}*/
			daoPrestamo = new DaoPrestamo();
			idsocio = Long.parseLong(request.getParameter("socio"));
			try {
				List<Prestamo>listadoLibrosPrestados = daoPrestamo.listadoPrestamosFueraPlazo(idsocio);
				request.setAttribute("listadolibrosprestados", listadoLibrosPrestados);
				request.getRequestDispatcher("admin/listadosociosmorosos.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarError(request, response, e, null);
			} catch (Exception e) {
				procesarError(request, response, e, null);
			}
					
			break;
			
		case "insertaprestamo":
			int idejemplar =Integer.parseInt(request.getParameter("idejemplar"));
			idsocio = Integer.parseInt(request.getParameter("idsocio"));
			
			try {
				daoPrestamo = new DaoPrestamo();
				Prestamo prestamo = new Prestamo();
				prestamo.setIdejemplar(idejemplar);
				prestamo.setIdsocio(idsocio);
				daoPrestamo.insertaPrestamo(prestamo);
				
			} catch (SQLException e) {
				procesarError(request, response, e, "admin/prestamo.jsp");
			} catch (Exception e) {
				procesarError(request, response, e, "admin/prestamo.jsp");
			}
			request.setAttribute("confirmaroperacion", "Prestamo creado satisfactoriamente");
			request.getRequestDispatcher("admin/prestamo.jsp").forward(request, response); 
			break;
			
			
		case "devolucionprestamo":
			idejemplar =Integer.parseInt(request.getParameter("idejemplar"));
			
			try {
				daoPrestamo = new DaoPrestamo();
				daoPrestamo.devolucionPrestamo(idejemplar);
				
			} catch (SQLException e) {
				procesarError(request, response, e, "admin/devolucion.jsp");
			} catch (Exception e) {
				procesarError(request, response, e, "admin/devolucion.jsp");
			}
			request.setAttribute("confirmaroperacion", "Devolucion realizada satisfactoriamente");
			request.getRequestDispatcher("admin/devolucion.jsp").forward(request, response); 
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

	protected void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e, String url) throws ServletException, IOException {
		String mensajeError = e.getMessage();
		request.setAttribute("error", mensajeError);
		if(url == null) {
			url = "error.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
	

}
