<template>
  <div class="pagina">
    <section class="blog">
      <header class="blog-header">
        <h1>Blog & Eventos</h1>
        <p>Eventos, rituales y encuentros vividos</p>
      </header>

      <div class="eventos">
        <article
          v-for="evento in eventos"
          :key="evento.id"
          class="evento"
        >
          <div class="media">
            <v-carousel
              class="carousel"
              cycle
              hide-delimiters
              show-arrows
              :interval="3000"
            >
              <v-carousel-item
                v-for="(img, i) in evento.imagenes"
                :key="i"
                class="slide"
              >
                <img :src="img" :alt="evento.titulo" />
              </v-carousel-item>
            </v-carousel>
          </div>

          <div class="contenido">
            <span class="fecha">{{ evento.fecha }}</span>
            <h2>{{ evento.titulo }}</h2>
            <p class="descripcion">{{ evento.descripcion }}</p>
          </div>
        </article>
      </div>
    </section>

    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from '@/components/FooterComponent.vue'

export default {
  name: 'BlogView',

  components: {
    FooterComponent
  },

  data() {
    return {
      eventos: []
    }
  },

  async mounted() {
    await this.cargarEventos()
  },

  methods: {
    async cargarEventos() {
      try {
        const res = await fetch(
          'https://api-secretodesacerdotisa.josevillar.com/galerias'
        )

        const data = await res.json()

        // ✅ ORDEN CORRECTO: el último creado sale el primero
        this.eventos = data
          .sort((a, b) => b.id - a.id)
          .map(g => ({
            id: g.id,
            titulo: g.titulo,
            fecha: g.fecha,
            descripcion: g.descripcion,
            imagenes: Array.isArray(g.fotos)
              ? g.fotos.map(f => f.imagen)
              : []
          }))

      } catch (e) {
        console.error('Error cargando eventos', e)
      }
    }
  }
}
</script>

<style scoped>
.pagina {
  background: #120016;
}

/* ================= BLOG ================= */
.blog {
  position: relative;
  color: var(--color-gold);
  padding-bottom: 160px;
  overflow: hidden;
}

/* FONDO */
.blog::before {
  content: '';
  position: absolute;
  inset: 0;
  background-image: url('https://t3.ftcdn.net/jpg/03/17/79/80/360_F_317798025_rTTpQGs1UjMAV0H4g3ljx7Jws5RUzCVh.jpg');
  background-size: cover;
  background-position: center;
  filter: brightness(0.35);
  z-index: 0;
}

/* OVERLAY */
.blog::after {
  content: '';
  position: absolute;
  inset: 0;
  background: radial-gradient(
    circle at top,
    rgba(50, 10, 80, 0.65),
    #120016 75%
  );
  z-index: 1;
}

.blog-header,
.eventos {
  position: relative;
  z-index: 2;
}

/* ================= HEADER ================= */
.blog-header {
  padding: 180px 10% 120px;
  text-align: center;
}

.blog-header h1 {
  font-family: var(--font-title);
  font-size: 3rem;
  letter-spacing: 4px;
  margin-bottom: 16px;
}

.blog-header p {
  max-width: 640px;
  margin: 0 auto;
  opacity: 0.9;
}

/* ================= EVENTOS ================= */
.eventos {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 6%;
  display: flex;
  flex-direction: column;
  gap: 140px;
}

.evento {
  display: grid;
  grid-template-columns: 1.4fr 1fr;
  grid-template-areas: "media contenido";
  gap: 90px;
  align-items: center;
}

.evento:nth-child(even) {
  grid-template-columns: 1fr 1.4fr;
  grid-template-areas: "contenido media";
}

/* ================= MEDIA / CARROUSEL ================= */
.media {
  grid-area: media;
  width: 100%;
  aspect-ratio: 18 / 9;     /* 🔒 el carrusel manda */
  overflow: hidden;
  border-radius: 28px;
  background: #0b000d;
  box-shadow: 0 35px 80px rgba(0, 0, 0, 0.55);
}

/* Carrusel ocupa TODO el media */
.carousel {
  width: 100%;
  height: 100% !important;
}

/* Slide controla alineación */
.slide {
  width: 100%;
  height: 100% !important;
  display: flex;                 /* 🔑 */
  align-items: center;           /* 🔑 centra vertical */
  justify-content: center;       /* 🔑 centra horizontal */
  overflow: hidden;
}

/* 🔥 IMAGEN BIEN HECHA */
.slide img {
  width: 100%;
  height: 100%;
  object-fit: contain;           /* se adapta al carrusel */
}

/* ================= CONTENIDO ================= */
.contenido {
  grid-area: contenido;
  max-width: 520px;
}

.fecha {
  display: block;
  font-size: 0.75rem;
  letter-spacing: 3px;
  opacity: 0.75;
  margin-bottom: 16px;
}

.contenido h2 {
  font-family: var(--font-title);
  font-size: 2.2rem;
  letter-spacing: 3px;
  margin-bottom: 24px;
}

.descripcion {
  color: #f5e7b0;
  font-size: 1rem;
  line-height: 1.8;
  max-width: 480px;
}

/* ================= RESPONSIVE ================= */
@media (max-width: 900px) {
  .eventos {
    gap: 80px;
  }

  .evento {
    display: flex;
    flex-direction: column;
    gap: 32px;
  }

  .evento:nth-child(even) {
    flex-direction: column;
  }

  .media {
    aspect-ratio: 16 / 9;
  }

  .contenido {
    max-width: 100%;
  }

  .blog-header {
    padding: 140px 8% 80px;
  }
}
</style>

