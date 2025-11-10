package com.isma.controller;

import com.isma.dao.ProductoDAO;
import com.isma.model.Producto;
import com.isma.util.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ProductoController")
public class ProductoController extends HttpServlet {

    private ProductoDAO productoDAO;

    @Override
    public void init() {
        Connection connection = DbConnection.getConnection();
        productoDAO = new ProductoDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "nuevo":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "insertar":
                    insertarProducto(request, response);
                    break;
                case "editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "actualizar":
                    actualizarProducto(request, response);
                    break;
                case "eliminar":
                    eliminarProducto(request, response);
                    break;
                default:
                    listarProductos(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Producto> lista = productoDAO.listarProductos();
        request.setAttribute("listaProductos", lista);
        RequestDispatcher dispatcher = request.getRequestDispatcher("productos.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertarProducto(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        String imagen = request.getParameter("imagen");

        Producto nuevo = new Producto(0, nombre, descripcion, precio, imagen);
        productoDAO.agregarProducto(nuevo);
        response.sendRedirect("ProductoController?action=listar");
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Pendiente para agregar si deseas editar en el futuro
        listarProductos(request, response);
    }

    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Pendiente para agregar si deseas editar
        response.sendRedirect("ProductoController?action=listar");
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productoDAO.eliminarProducto(id);
        response.sendRedirect("ProductoController?action=listar");
    }
}
