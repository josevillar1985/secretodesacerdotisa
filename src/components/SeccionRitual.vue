<template>
  <section class="ritual">
    <!-- PARALLAX FONDO -->
    <div class="parallax-bg"></div>

    <!-- VELO OSCURO -->
    <div class="velo"></div>

    <!-- CONTENIDO -->
    <div class="contenido">
      <span class="detalle">El Ritual</span>

      <h2>
        Nada es casual.<br />
        Las cartas hablan cuando es el momento.
      </h2>

      <p>
        Si has llegado hasta aquí, no es por azar.
        Cada lectura es un ritual de conexión,
        respeto y verdad.
      </p>

      <router-link to="/contacto" class="btn-ritual">
        Iniciar lectura
      </router-link>
    </div>
  </section>
</template>

<script>
export default {
  name: 'SeccionRitual',
}
</script>

<style scoped>
/* ===== SECCIÓN ===== */
.ritual {
  position: relative;
  min-height: 90vh;
  padding: 180px 12%;
  overflow: hidden;
  background: #120016;
  display: flex;
  align-items: center;
}

/* ===== PARALLAX ===== */
.parallax-bg {
  position: absolute;
  inset: 0;
  background-image: url('https://images.unsplash.com/photo-1519681393784-d120267933ba?w=1400');
  background-size: cover;
  background-position: center;
  background-attachment: fixed; /* PARALLAX REAL */
  transform: scale(1.1); /* evitar bordes */
  z-index: 1;
}

/* ===== VELO OSCURO ===== */
.velo {
  position: absolute;
  inset: 0;
  background: linear-gradient(
    to bottom,
    rgba(18, 0, 22, 0.95),
    rgba(18, 0, 22, 0.75),
    rgba(18, 0, 22, 0.95)
  );
  z-index: 2;
}

/* ===== CONTENIDO ===== */
.contenido {
  position: relative;
  z-index: 3;
  max-width: 720px;
  margin: 0 auto;
  text-align: center;
  color: #f3d98b;
}

/* detalle */
.detalle {
  display: inline-block;
  margin-bottom: 28px;
  font-size: 0.85rem;
  letter-spacing: 4px;
  opacity: 0.85;
}

/* título */
h2 {
  font-size: 2.6rem;
  line-height: 1.25;
  margin-bottom: 36px;
}

/* texto */
p {
  font-size: 1.05rem;
  line-height: 1.8;
  opacity: 0.9;
  margin-bottom: 56px;
}

/* ===== BOTÓN ===== */
.btn-ritual {
  display: inline-block;
  padding: 16px 56px;
  border-radius: 50px;
  text-decoration: none;
  text-transform: uppercase;
  letter-spacing: 3px;
  font-size: 0.9rem;
  font-weight: 600;
  color: #2b0a3d;
  background: linear-gradient(135deg, #ffd36a, #f3d98b);
  transition: all 0.35s ease;
}

.btn-ritual:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 28px rgba(255, 211, 106, 0.5);
}

/* ===== RESPONSIVE ===== */
@media (max-width: 900px) {
  .ritual {
    padding: 140px 8%;
    min-height: auto;
  }

  /* desactivar parallax real en móvil (mejora rendimiento) */
  .parallax-bg {
    background-attachment: scroll;
  }

  h2 {
    font-size: 2.2rem;
  }
}
</style>
