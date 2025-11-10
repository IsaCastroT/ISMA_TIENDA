<%@ page import="java.util.List" %>
<%@ page import="com.isma.model.Producto" %>
<%@ page import="com.isma.dao.ProductoDAO" %>
<%@ page import="com.isma.dao.DbConnection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Productos - ISMA</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header>
        <h1>🐾 ISMA Tienda de Mascotas 🐾</h1>
        <nav>
            <a href="index.jsp">Inicio</a>
            <a href="productos.jsp">Productos</a>
            <a href="#">Carrito</a>
            <a href="#">Contacto</a>
        </nav>
    </header>

    <main>
        <h2>Lista de Productos</h2>

        <div class="productos">
            <%
                try {
                    ProductoDAO dao = new ProductoDAO(DbConnection.getConnection());
                    List<Producto> lista = dao.listarProductos();
                    for(Producto p : lista){
            %>
                <div class="producto">
                    <img src="imagenes/<%= p.getImagen() %>" alt="<%= p.getNombre() %>" width="150">
                    <h3><%= p.getNombre() %></h3>
                    <p><%= p.getDescripcion() %></p>
                    <p><strong>$<%= p.getPrecio() %></strong></p>
                    <a href="ProductoController?action=eliminar&id=<%= p.getId() %>">Eliminar</a>
                </div>
            <%
                    }
                } catch (Exception e) {
                    out.println("Error: " + e.getMessage());
                }
            %>
        </div>
    </main>

    <footer>
        <p>© 2025 ISMA Tienda de Mascotas</p>
    </footer>
</body>
</html>
