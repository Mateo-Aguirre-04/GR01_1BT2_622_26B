<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Agenda de Contactos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contactos.css">
</head>
<body>
    <main class="page-shell page-shell-narrow">
        <header class="site-header">
            <a class="brand" href="${pageContext.request.contextPath}/">Agenda<span>.</span></a>
            <nav class="main-nav" aria-label="Navegación principal">
                <a class="nav-link is-active" href="${pageContext.request.contextPath}/">Inicio</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/contactos">Contactos</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/grupos">Grupos</a>
            </nav>
        </header>

        <section class="page-heading">
            <div>
                <p class="eyebrow">BIENVENIDO</p>
                <h1>Agenda de Contactos</h1>
                <p class="subtitle">Administra tus contactos y grupos desde un solo lugar.</p>
            </div>
        </section>

        <nav class="home-actions" aria-label="Accesos principales">
            <a class="button button-primary" href="${pageContext.request.contextPath}/contactos">Ir a contactos</a>
            <a class="button button-secondary" href="${pageContext.request.contextPath}/grupos">Gestionar grupos</a>
        </nav>
    </main>
</body>
</html>