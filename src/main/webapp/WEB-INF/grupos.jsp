<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Grupos | Agenda de Contactos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contactos.css">
</head>
<body>
    <main class="page-shell">
        <header class="site-header">
            <a class="brand" href="${pageContext.request.contextPath}/">Agenda<span>.</span></a>
            <nav class="main-nav" aria-label="Navegación principal">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Inicio</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/contactos">Contactos</a>
                <a class="nav-link is-active" href="${pageContext.request.contextPath}/grupos">Grupos</a>
            </nav>
        </header>

        <section class="page-heading">
            <div>
                <p class="eyebrow">ORGANIZACIÓN</p>
                <h1>Grupos</h1>
                <p class="subtitle">Crea y administra grupos para organizar tus contactos.</p>
            </div>
        </section>

        <jsp:include page="/WEB-INF/formulario-grupo.jsp" />

        <section aria-labelledby="lista-grupos">
            <h2 id="lista-grupos">Lista de grupos</h2>
            <c:choose>
                <c:when test="${empty listaGrupos}">
                    <section class="empty-state">
                        <p>No hay grupos registrados.</p>
                    </section>
                </c:when>
                <c:otherwise>
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre del grupo</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="grupo" items="${listaGrupos}">
                                    <tr>
                                        <td><c:out value="${grupo.id}" /></td>
                                        <td>
                                            <form method="post" action="${pageContext.request.contextPath}/grupos">
                                                <input type="hidden" name="action" value="editar">
                                                <input type="hidden" name="id" value="${grupo.id}">
                                                <label>
                                                    <span class="visually-hidden">Nombre del grupo</span>
                                                    <input type="text" name="nombre" value="<c:out value='${grupo.nombre}' />" required>
                                                </label>
                                                <button class="text-action" type="submit">Guardar</button>
                                            </form>
                                        </td>
                                        <td class="row-actions">
                                            <form method="post" action="${pageContext.request.contextPath}/grupos">
                                                <input type="hidden" name="action" value="eliminar">
                                                <input type="hidden" name="id" value="${grupo.id}">
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
        </section>
    </main>
</body>
</html>
