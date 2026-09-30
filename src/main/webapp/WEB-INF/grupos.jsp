<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestión de Grupos | Agenda</title>
    <style>
        body { font-family: sans-serif; margin: 2rem; }
        table { border-collapse: collapse; width: 100%; margin-top: 1rem; }
        th, td { border: 1px solid #ccc; padding: 0.5rem; text-align: left; }
        .btn { padding: 0.3rem 0.6rem; cursor: pointer; }
    </style>
</head>
<body>
    <header>
        <a href="${pageContext.request.contextPath}/contactos">Volver a Contactos</a>
    </header>

    <h1>Gestión de Grupos</h1>

    <section>
        <h2>Crear Nuevo Grupo</h2>
        <form method="post" action="${pageContext.request.contextPath}/grupos">
            <input type="hidden" name="action" value="crear">
            <label>Nombre del Grupo: 
                <input type="text" name="nombre" required>
            </label>
            <button class="btn" type="submit">Crear</button>
        </form>
    </section>

    <section>
        <h2>Lista de Grupos</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="grupo" items="${listaGrupos}">
                    <tr>
                        <td>${grupo.id}</td>
                        <td>
                            <!-- Formulario para editar directamente en la tabla -->
                            <form method="post" action="${pageContext.request.contextPath}/grupos" style="display:inline;">
                                <input type="hidden" name="action" value="editar">
                                <input type="hidden" name="id" value="${grupo.id}">
                                <input type="text" name="nombre" value="${grupo.nombre}" required>
                                <button class="btn" type="submit">Actualizar</button>
                            </form>
                        </td>
                        <td>
                            <!-- Formulario para eliminar -->
                            <form method="post" action="${pageContext.request.contextPath}/grupos" style="display:inline;">
                                <input type="hidden" name="action" value="eliminar">
                                <input type="hidden" name="id" value="${grupo.id}">
                                <button class="btn" type="submit" onclick="return confirm('¿Seguro que deseas eliminar este grupo? Si tiene contactos, la acción será denegada.');">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty listaGrupos}">
                    <tr>
                        <td colspan="3">No hay grupos registrados.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </section>
</body>
</html>