<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Contactos | Agenda</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contactos.css">
</head>
<body>
    <main class="page-shell">
        <header class="site-header">
            <a class="brand" href="${pageContext.request.contextPath}/contactos">Agenda<span>.</span></a>
            <span class="header-label">GESTIÓN DE CONTACTOS</span>
        </header>

        <section class="page-heading">
            <div>
                <p class="eyebrow">DIRECTORIO</p>
                <h1>Contactos</h1>
                <p class="subtitle">Consulta y administra tus contactos.</p>
            </div>
            <a class="button button-primary" href="${pageContext.request.contextPath}/contactos?nuevo=1">+ Nuevo contacto</a>
        </section>

        <c:if test="${param.mensaje == 'creado'}">
            <p class="notice" role="status">Contacto creado.</p>
        </c:if>
        <c:if test="${param.mensaje == 'actualizado'}">
            <p class="notice" role="status">Contacto actualizado.</p>
        </c:if>
        <c:if test="${param.mensaje == 'eliminado'}">
            <p class="notice" role="status">Contacto eliminado.</p>
        </c:if>

        <form class="search-form" method="get" action="${pageContext.request.contextPath}/contactos" role="search">
            <label class="search-label" for="busqueda">Explora tu agenda</label>
            <div class="search-controls">
                <input id="busqueda" name="busqueda" type="search" value="<c:out value='${busqueda}' />" placeholder="Escribe para buscar">
                <button class="button button-primary" type="submit">Buscar</button>
                <c:if test="${not empty busqueda}">
                    <a class="button button-secondary" href="${pageContext.request.contextPath}/contactos">Limpiar</a>
                </c:if>
            </div>
        </form>

        <c:choose>
            <c:when test="${empty contactos}">
                <section class="empty-state">
                    <c:choose>
                        <c:when test="${not empty busqueda}">
                            <h2>No se encontraron contactos</h2>
                            <p>Prueba con otro nombre, teléfono, correo o dirección.</p>
                        </c:when>
                        <c:otherwise>
                            <h2>Aún no hay contactos</h2>
                            <p>Registra el primero para empezar tu directorio.</p>
                            <a class="button button-primary" href="${pageContext.request.contextPath}/contactos?nuevo=1">Registrar contacto</a>
                        </c:otherwise>
                    </c:choose>
                </section>
            </c:when>
            <c:otherwise>
                <div class="table-wrap">
                    <table>
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Teléfono</th>
                                <th>Correo</th>
                                <th><span class="visually-hidden">Acciones</span></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="contacto" items="${contactos}">
                                <tr>
                                    <td class="contact-name"><c:out value="${contacto.nombre}" /> <c:out value="${contacto.apellido}" /></td>
                                    <td><c:out value="${contacto.telefono}" default="—" /></td>
                                    <td><c:out value="${contacto.correo}" default="—" /></td>
                                    <td class="row-actions">
                                        <a class="text-action" href="${pageContext.request.contextPath}/contactos?id=${contacto.id}">Editar</a>
                                        <form method="post" action="${pageContext.request.contextPath}/contactos"
                                                onsubmit="return confirm('¿Eliminar este contacto?');">
                                            <input type="hidden" name="accion" value="eliminar">
                                            <input type="hidden" name="id" value="${contacto.id}">
                                            <button class="text-action text-danger" type="submit">Eliminar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </main>
</body>
</html>