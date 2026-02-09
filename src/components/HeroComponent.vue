<template>
  <section class="hero">
    <!-- VIDEO -->
    <video
      autoplay
      muted
      loop
      playsinline
      class="hero-video"
    >
      <source src="@/assets/videohero.mp4" type="video/mp4" />
      Tu navegador no soporta video.
    </video>

    <!-- OVERLAY -->
    <div class="overlay"></div>

    <!-- CONTENIDO -->
    <div class="hero-content">
      <h1>Secreto de Sacerdotisa</h1>
      <p>Lecturas que revelan lo oculto</p>

      <!-- CTA -->
      <router-link to="/contacto" class="cta-btn">
        Reservar cita
      </router-link>
    </div>
  </section>
</template>

<script>
export default {
  name: 'HeroComponent',
}
</script>

<style scoped>
/* ===== HERO ===== */
.hero {
  position: relative;
  width: 100%;
  height: calc(100vh - 120px); /* desktop */
  overflow: hidden;
  z-index: 1;
}

/* ===== VIDEO ===== */
.hero-video {
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* ===== OVERLAY ===== */
.overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(
    rgba(20, 5, 30, 0.6),
    rgba(20, 5, 30, 0.85)
  );
  z-index: 2;
}

/* ===== CONTENIDO ===== */
.hero-content {
  position: relative;
  z-index: 3;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  color: #f3d98b;
  padding: 0 20px; /* seguridad móvil */
}

.hero-content h1 {
  font-size: 3.2rem;
  letter-spacing: 4px;
  margin-bottom: 12px;
}

.hero-content p {
  font-size: 1.2rem;
  opacity: 0.9;
  margin-bottom: 32px;
}

/* ===== BOTÓN CTA ===== */
.cta-btn {
  display: inline-block;
  padding: 14px 48px;
  font-size: 1rem;
  letter-spacing: 3px;
  text-transform: uppercase;
  color: #2b0a3d;
  background: linear-gradient(135deg, #ffd36a, #f3d98b);
  border-radius: 50px;
  text-decoration: none;
  font-weight: 600;
  position: relative;
  overflow: hidden;
  transition: all 0.35s ease;
}

/* brillo */
.cta-btn::before {
  content: '';
  position: absolute;
  inset: 0;
  left: -100%;
  background: linear-gradient(
    120deg,
    transparent,
    rgba(255, 255, 255, 0.6),
    transparent
  );
  transition: left 0.5s ease;
}

.cta-btn:hover::before {
  left: 100%;
}

.cta-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 10px 30px rgba(255, 211, 106, 0.6);
}

/* =======================
   TABLET
======================= */
@media (max-width: 1000px) {
  .hero {
    height: calc(100svh - 96px); /* móvil real, sin barra navegador */
  }

  .hero-content h1 {
    font-size: 2.4rem;
    letter-spacing: 3px;
  }

  .hero-content p {
    font-size: 1.05rem;
    margin-bottom: 28px;
  }
}

/* =======================
   MÓVIL
======================= */
@media (max-width: 600px) {
  .hero-content h1 {
    font-size: 1.9rem;
    letter-spacing: 2px;
  }

  .hero-content p {
    font-size: 0.95rem;
    margin-bottom: 24px;
  }

  .cta-btn {
    padding: 12px 36px;
    font-size: 0.9rem;
    letter-spacing: 2px;
  }
}
</style>

