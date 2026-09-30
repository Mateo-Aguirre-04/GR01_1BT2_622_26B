<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="esEdicion" value="${not empty contacto}" />
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:choose><c:when test="${esEdicion}">Editar contacto</c:when><c:otherwise>Nuevo contacto</c:otherwise></c:choose> | Agenda</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contactos.css">
</head>
<body>
    <main class="page-shell page-shell-narrow">
        <header class="site-header">
            <a class="brand" href="${pageContext.request.contextPath}/contactos">Agenda<span>.</span></a>
            <a class="header-link" href="${pageContext.request.contextPath}/contactos">Volver a contactos</a>
        </header>

        <section class="form-heading">
            <p class="eyebrow">DIRECTORIO</p>
            <h1><c:choose><c:when test="${esEdicion}">Editar contacto</c:when><c:otherwise>Nuevo contacto</c:otherwise></c:choose></h1>
            <p class="subtitle">Completa los datos del contacto.</p>
        </section>

        <form class="contact-form" method="post" action="${pageContext.request.contextPath}/contactos">
            <input type="hidden" name="accion" value="${esEdicion ? 'actualizar' : 'crear'}">
            <c:if test="${esEdicion}">
                <input type="hidden" name="id" value="${contacto.id}">
            </c:if>

            <div class="form-grid">
                <label>
                    <span>Nombre</span>
                    <input type="text" name="nombre" value="<c:out value='${contacto.nombre}' />">
                </label>
                <label>
                    <span>Apellido</span>
                    <input type="text" name="apellido" value="<c:out value='${contacto.apellido}' />">
                </label>
                <label>
                    <span>Teléfono</span>
                    <input type="tel" name="telefono" value="<c:out value='${contacto.telefono}' />">
                </label>
                <label>
                    <span>Correo</span>
                    <input type="text" name="correo" value="<c:out value='${contacto.correo}' />">
                </label>
                <label class="field-wide">
                    <span>Dirección</span>
                    <textarea name="direccion" rows="3"><c:out value="${contacto.direccion}" /></textarea>
                </label>
                <label>
                    <span>ID del grupo <small>(opcional)</small></span>
                    <input type="number" name="grupoId" min="1" value="<c:out value='${contacto.grupoId}' />">
                </label>
            </div>

            <div class="form-actions">
                <a class="button button-secondary" href="${pageContext.request.contextPath}/contactos">Cancelar</a>
                <button class="button button-primary" type="submit">
                    <c:choose><c:when test="${esEdicion}">Guardar cambios</c:when><c:otherwise>Crear contacto</c:otherwise></c:choose>
                </button>
            </div>
        </form>
    </main>
</body>
</html>