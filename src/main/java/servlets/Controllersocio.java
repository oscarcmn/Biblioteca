package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.DaoAutor;
import dao.DaoLibro;
import dao.DaoSocio;
import entidades.Autor;
import entidades.Libro;
import entidades.Socio;

/**
 * Servlet implementation class ControllerSocio
 */
@WebServlet("/controllersocio")
public class Controllersocio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controllersocio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operacion = request.getParameter("operacion");
		System.out.println(operacion);
		DaoAutor daoAutor=null;
		DaoLibro daoLibro=null;
		HttpSession session = request.getSession();		
		switch (operacion) {
		
		case "listarAutores":
			System.out.println("Comenzando listado");
			daoAutor=new DaoAutor();
			try {
				List<Autor> listadoautores = daoAutor.listadoAutores();
				request.setAttribute("listadoautores", listadoautores);
				request.getRequestDispatcher("socios/listadoautores.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarError(request, response, e,"socios/listadoautores.jsp");}
			  catch (Exception e) {
				procesarError(request, response, e,"socios/listadoautores.jsp");}
			break;
		
			
			
			
		case "listarAutoresPaginado":
			System.out.println("Comenzando listado");
			daoAutor=new DaoAutor();
			int totalRegistros = 0;
			int pagina = 0;
			int numregpag = 5; //5 por defecto
			int paginamasalta = 0;
			List<Autor> listadoAutores = null;
			
			if (request.getParameter("pag") !=null) {
				pagina = Integer.parseInt(request.getParameter("pag"));
			}
			if (request.getParameter("nrp") !=null) {
				numregpag = Integer.parseInt(request.getParameter("nrp"));
			}
			//calcular la pagina mas alta
			try {
				totalRegistros = daoAutor.getTotalRegistros();
				paginamasalta = totalRegistros / numregpag;
				if(totalRegistros % numregpag ==0)paginamasalta--;
				listadoAutores = daoAutor.listadoAutoresPaginado(pagina,numregpag);
				request.setAttribute("pagina", pagina);
				request.setAttribute("numregpag", numregpag);
				request.setAttribute("paginamasalta", paginamasalta);
				request.setAttribute("totalRegistros", totalRegistros);
				request.setAttribute("listadoAutores", listadoAutores);
				request.getRequestDispatcher("socios/listadoautorespaginado.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarError(request, response, e,"socios/listadoautorespaginado.jsp");
			} catch (Exception e) {
				procesarError(request, response, e,"socios/listadoautorespaginado.jsp");
			}
			break;
			
		case "listadolibros":
			System.out.println("Comenzando listado");
			daoLibro=new DaoLibro();
			String criteriobusqueda = request.getParameter("criteriobusqueda");
			System.out.println(criteriobusqueda);
			
			String iniciales = request.getParameter("iniciales");
			System.out.println(iniciales);
			try {
				List<Libro> listadolibros = daoLibro.listadoLibros(criteriobusqueda,iniciales);
				request.setAttribute("listadolibros", listadolibros);
				System.out.println(listadolibros);
				request.getRequestDispatcher("socios/getlibros.jsp").forward(request, response);
				
			} catch (SQLException e) {
				procesarError(request, response, e,"socios/getlibros.jsp");}
			  catch (Exception e) {
				procesarError(request, response, e,"socios/getlibros.jsp");}
			
			break;
			
		case "logout":
				session.invalidate();
				response.sendRedirect("index.jsp");
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
