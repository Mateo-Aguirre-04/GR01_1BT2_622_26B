<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<section class="form-panel" aria-labelledby="titulo-formulario-grupo">
    <h2 id="titulo-formulario-grupo">Crear grupo</h2>
    <form method="post" action="${pageContext.request.contextPath}/grupos">
        <input type="hidden" name="action" value="crear">
        <div class="form-grid">
            <label>
                <span>Nombre del grupo</span>
                <input type="text" name="nombre" required>
            </label>
        </div>
        <div class="form-actions">
            <button class="button button-primary" type="submit">Crear grupo</button>
        </div>
    </form>
</section>
