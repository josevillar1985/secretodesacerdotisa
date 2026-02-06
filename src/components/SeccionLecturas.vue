<template>
  <section class="lecturas">
    <h2>Lecturas Disponibles</h2>
    <p class="subtitulo">Cada carta despierta lo que aún no ves</p>

    <div class="cartas">
      <div
        v-for="(item, i) in lecturas"
        :key="i"
        class="carta"
        ref="cartas"
      >
        <div class="carta-inner">
          <span class="glow"></span>

          <div class="img-wrapper">
            <img :src="item.imagen" :alt="item.titulo" />
          </div>

          <h3>{{ item.titulo }}</h3>
          <p>{{ item.texto }}</p>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'SeccionLecturas',

  data() {
    return {
      lecturas: [
        {
          titulo: 'Lecturas Generales',
          texto: 'Un reflejo profundo de tu presente y de las energías que te rodean.',
          imagen:
            'https://content25.lecturas.com/medio/2023/11/03/tarot-significado-de-las-78-cartas-arcanos-mayores-y-menores-y-como-interpretarlas_ba0b7645_231103205235_1280x720.jpg',
        },
        {
          titulo: 'Amor y Relaciones',
          texto: 'Descubre verdades ocultas sobre vínculos, decisiones y sentimientos.',
          imagen:
            'https://thumbs.dreamstime.com/b/cartas-del-tarot-para-atraer-el-amor-y-la-suerte-magia-tarjeta-en-mesa-220552252.jpg',
        },
        {
          titulo: 'Camino Espiritual',
          texto: 'Una guía para conectar con tu propósito y evolución interior.',
          imagen:
            'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQW4vFOQoYtuqvdDQ3SKQM6Yi110U59FZoX5A&s',
        },
        {
          titulo: 'Trabajo y Destino',
          texto: 'Descubre oportunidades y bloqueos en tu camino profesional.',
          imagen:
            'https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?w=600',
        },
        {
          titulo: 'Energía Personal',
          texto: 'Lectura sobre tu estado emocional, mental y vital.',
          imagen:
            'https://images.unsplash.com/photo-1519681393784-d120267933ba?w=600',
        },
        {
          titulo: 'Decisiones Importantes',
          texto: 'Las cartas iluminan el camino cuando hay dudas.',
          imagen:
            'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYqp5SOEOuxuruq4YO6OHjRGhhWm4fkCOcKQ&s',
        },
      ],
    }
  },

  mounted() {
    // ⚠️ CLAVE: esperar a que Vue pinte TODO
    this.$nextTick(() => {
      const cartas = this.$refs.cartas

      // seguridad extra
      if (!cartas || !cartas.length) return

      const observer = new IntersectionObserver(
        (entries) => {
          entries.forEach((entry) => {
            if (entry.isIntersecting) {
              entry.target.classList.add('visible')
              observer.unobserve(entry.target)
            }
          })
        },
        {
          threshold: 0.2,
        }
      )

      cartas.forEach((carta) => observer.observe(carta))
    })
  },
}
</script>

<style scoped>
/* ===== SECCIÓN ===== */
.lecturas {
  padding: 120px 12%;
  background: radial-gradient(circle at top, #1d0629, #120016);
  text-align: center;
  color: #f3d98b;
}

/* ===== TÍTULOS ===== */
h2 {
  font-size: 2.6rem;
  letter-spacing: 4px;
}

.subtitulo {
  margin-top: 12px;
  margin-bottom: 90px;
  opacity: 0.85;
}

/* ===== GRID 3 Y 3 ===== */
.cartas {
  display: grid;
  grid-template-columns: repeat(3, 300px);
  gap: 56px;
  justify-content: center;
}

/* ===== CARTA (INICIAL OCULTA) ===== */
.carta {
  opacity: 0;
  transform: translateY(70px);
  transition: opacity 0.9s ease, transform 0.9s ease;
  perspective: 900px;
}

/* ===== AL ENTRAR EN VIEWPORT ===== */
.carta.visible {
  opacity: 1;
  transform: translateY(0);
}

/* ===== INTERIOR ===== */
.carta-inner {
  position: relative;
  padding: 28px 26px 46px;
  background: linear-gradient(
    180deg,
    rgba(90, 30, 140, 0.35),
    rgba(30, 5, 45, 0.95)
  );
  border-radius: 22px;
  border: 1px solid rgba(255, 215, 120, 0.25);
  backdrop-filter: blur(8px);
  overflow: hidden;
  transition: transform 0.45s ease, box-shadow 0.45s ease;
}

/* ===== IMAGEN ===== */
.img-wrapper {
  width: 100%;
  height: 180px;
  overflow: hidden;
  border-radius: 14px;
  margin-bottom: 26px;
}

.img-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transform: scale(1.25);
  filter: brightness(0.7);
  transition: transform 1.1s ease, filter 1.1s ease;
}

/* cuando aparece por scroll */
.carta.visible img {
  transform: scale(1);
  filter: brightness(1);
}

/* ===== TEXTO ===== */
.carta-inner h3 {
  margin-bottom: 18px;
  letter-spacing: 2px;
}

.carta-inner p {
  font-size: 0.96rem;
  line-height: 1.6;
  opacity: 0.9;
}

/* ===== BRILLO ===== */
.glow {
  position: absolute;
  inset: -60%;
  background: conic-gradient(
    from 0deg,
    transparent,
    rgba(255, 215, 120, 0.8),
    transparent 30%
  );
  animation: girar 6s linear infinite;
  opacity: 0.18;
}

/* ===== HOVER ===== */
.carta-inner:hover {
  transform: translateY(-14px) rotateX(6deg) rotateY(-6deg);
  box-shadow: 0 25px 55px rgba(255, 211, 106, 0.35);
}

.carta-inner:hover img {
  transform: scale(1.12);
}

/* ===== ANIMACIONES ===== */
@keyframes girar {
  to {
    transform: rotate(360deg);
  }
}

/* ===== RESPONSIVE ===== */
@media (max-width: 900px) {
  .cartas {
    grid-template-columns: repeat(2, 300px);
    gap: 36px;
  }

  .lecturas {
    padding: 100px 8%;
  }
}

@media (max-width: 600px) {
  .cartas {
    grid-template-columns: 300px;
  }
}
</style>
