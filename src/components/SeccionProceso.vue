<template>
  <section class="editorial" ref="editorial">
    <div class="wrapper">
      <!-- ================= IZQUIERDA ================= -->
      <div class="texto">
        <span class="detalle">La experiencia</span>

        <h2>
          Una lectura<br />
          creada para ti
        </h2>

        <div class="divider"></div>

        <p>
          Cada sesión es un espacio íntimo donde las cartas no dictan,
          sino acompañan. Una lectura consciente, profunda y honesta,
          diseñada para ayudarte a comprender lo que ya sabes,
          pero aún no has mirado.
        </p>
      </div>

      <!-- ================= DERECHA ================= -->
      <div class="imagen">
        <div class="imagen-inner">
          <img
            src="https://estudiarastrologia.com/wp-content/uploads/como-son-las-lecturas-de-tarot.png"
            alt="Lectura espiritual"
          />
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'SeccionEditorial',
  mounted() {
    const section = this.$refs.editorial

    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          section.classList.add('visible')
          observer.disconnect()
        }
      },
      { threshold: 0.3 }
    )

    observer.observe(section)
  },
}
</script>

<style scoped>
/* ================= SECCIÓN ================= */
.editorial {
  padding: 200px 12%;
  background: linear-gradient(
    to bottom,
    #120016 0%,
    #16001d 50%,
    #120016 100%
  );
  overflow: hidden;
}

/* ================= GRID ================= */
.wrapper {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 120px;
  align-items: center;
  max-width: 1300px;
  margin: 0 auto;
}

/* ================= TEXTO ================= */
.texto {
  transform: translateY(60px);
  transition: transform 1s ease;
}

.editorial.visible .texto {
  transform: translateY(0);
}

.detalle {
  display: inline-block;
  font-family: var(--font-ui);
  font-size: 0.8rem;
  letter-spacing: 4px;
  opacity: 0.75;
  margin-bottom: 24px;
}

.texto h2 {
  font-size: 2.8rem;
  line-height: 1.2;
  margin-bottom: 30px;
  color: var(--color-gold);
}

/* divider */
.divider {
  width: 90px;
  height: 2px;
  background: linear-gradient(
    90deg,
    transparent,
    var(--color-gold),
    transparent
  );
  margin-bottom: 34px;
}

/* 🔥 PÁRRAFO (YA NO SE OCULTA) */
.texto p {
  max-width: 460px;
  font-family: var(--font-text);
  font-size: 1.05rem;
  line-height: 1.85;
  opacity: 0.95;
  color: var(--color-gold);
}

/* ================= IMAGEN ================= */
.imagen {
  position: relative;
  height: 480px;
  overflow: hidden;
  border-radius: 28px;
}

.imagen-inner {
  position: absolute;
  inset: 0;
  transform: translateY(50px) scale(1.12);
  transition: transform 1.4s ease;
}

.editorial.visible .imagen-inner {
  transform: translateY(0) scale(1);
}

.imagen img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  filter: brightness(0.75);
  transition: filter 1.2s ease;
}

.editorial.visible img {
  filter: brightness(1);
}

/* ================= RESPONSIVE ================= */
@media (max-width: 900px) {
  .editorial {
    padding: 140px 8%;
  }

  .wrapper {
    grid-template-columns: 1fr;
    gap: 80px;
  }

  .imagen {
    height: 340px;
  }

  .texto h2 {
    font-size: 2.3rem;
  }
}
</style>
