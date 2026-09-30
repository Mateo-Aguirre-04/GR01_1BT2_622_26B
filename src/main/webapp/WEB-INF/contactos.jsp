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
            <a class="brand" href="${pageContext.request.contextPath}/">Agenda<span>.</span></a>
            <nav class="main-nav" aria-label="Navegación principal">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Inicio</a>
                <a class="nav-link is-active" href="${pageContext.request.contextPath}/contactos">Contactos</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/grupos">Grupos</a>
            </nav>
        </header>

        <section class="page-heading">
            <div>
                <p class="eyebrow">DIRECTORIO</p>
                <h1>Contactos</h1>
                <p class="subtitle">Consulta y administra tus contactos.</p>
            </div>
            <a class="button button-primary" href="${pageContext.request.contextPath}/contactos?nuevo=1">+ Nuevo contacto</a>
        </section>

        <c:if test="${mensaje == 'creado'}">
            <p class="notice notice-success" role="status">Contacto creado.</p>
        </c:if>
        <c:if test="${mensaje == 'actualizado'}">
            <p class="notice notice-success" role="status">Contacto actualizado.</p>
        </c:if>
        <c:if test="${mensaje == 'eliminado'}">
            <p class="notice notice-success" role="status">Contacto eliminado.</p>
        </c:if>

        <c:choose>
            <c:when test="${empty contactos}">
                <section class="empty-state">
                    <h2>Aún no hay contactos</h2>
                    <p>Registra el primero para empezar tu directorio.</p>
                    <a class="button button-primary" href="${pageContext.request.contextPath}/contactos?nuevo=1">Registrar contacto</a>
                </section>
            </c:when>
            <c:otherwise>
                <div class="table-wrap">
                    <table>
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Teléfono</th>
                                <th>Correo</th>
                                <th>Dirección</th>
                                <th>Grupo</th>
                                <th><span class="visually-hidden">Acciones</span></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="contacto" items="${contactos}">
                                <tr>
                                    <td class="contact-name"><c:out value="${contacto.nombre}" /></td>
                                    <td><c:out value="${contacto.apellido}" /></td>
                                    <td><c:out value="${contacto.telefono}" default="—" /></td>
                                    <td><c:out value="${contacto.correo}" default="—" /></td>
                                    <td><c:out value="${contacto.direccion}" default="—" /></td>
                                    <td>
                                        <c:if test="${not empty contacto.grupoId}">Grupo </c:if>
                                        <c:out value="${contacto.grupoId}" default="Sin grupo" />
                                    </td>
                                    <td class="row-actions">
                                        <a class="text-action" href="${pageContext.request.contextPath}/contactos?id=${contacto.id}">Editar</a>
                                        <form method="post" action="${pageContext.request.contextPath}/contactos">
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