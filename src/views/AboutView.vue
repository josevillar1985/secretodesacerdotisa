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
              show-arrows="always"
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
            <p class="descripcion">
              {{ evento.descripcion }}
            </p>
          </div>
        </article>
      </div>
    </section>

    <!-- FOOTER -->
    <FooterComponent />
  </div>
</template>

<template>
  <div class="pagina">
    <section class="blog">
      <header class="blog-header">
        <h1>Blog & Eventos</h1>
        <p>Eventos, rituales y encuentros vividos</p>
      </header>

      <div class="eventos">
        <article v-for="evento in eventos" :key="evento.id" class="evento">
          <div class="media">
            <v-carousel class="carousel" cycle hide-delimiters show-arrows="always" :interval="3000">
              <v-carousel-item v-for="(img, i) in evento.imagenes" :key="i" class="slide">
                <img :src="img" :alt="evento.titulo" />
              </v-carousel-item>
            </v-carousel>
          </div>

          <div class="contenido">
            <span class="fecha">{{ evento.fecha }}</span>
            <h2>{{ evento.titulo }}</h2>
            <p class="descripcion">
              {{ evento.descripcion }}
            </p>
          </div>
        </article>
      </div>
    </section>

    <!-- FOOTER -->
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from '@/components/FooterComponent.vue'
import { EventBus } from '@/eventBus'

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

    // 🟢 NUEVA GALERÍA → arriba
    EventBus.$on('galeria-creada', galeria => {
      const evento = this.mapGaleria(galeria)
      this.eventos.unshift(evento)
    })

    // 🟢 GALERÍA EDITADA → MISMA POSICIÓN
    EventBus.$on('galeria-editada', galeria => {
      const index = this.eventos.findIndex(e => e.id === galeria.id)
      if (index !== -1) {
        this.eventos.splice(index, 1, this.mapGaleria(galeria))
      }
    })

    // 🟥 GALERÍA ELIMINADA
    EventBus.$on('galeria-eliminada', id => {
      this.eventos = this.eventos.filter(e => e.id !== id)
    })
  },

  beforeDestroy() {
    EventBus.$off('galeria-creada')
    EventBus.$off('galeria-editada')
    EventBus.$off('galeria-eliminada')
  },

  methods: {
    async cargarEventos() {
      try {
        const res = await fetch(
          'https://api-secretodesacerdotisa.josevillar.com/galerias'
        )

        const data = await res.json()

        // 🔥 solo aquí ordenamos
        this.eventos = data
          .reverse()
          .map(this.mapGaleria)

      } catch (e) {
        console.error('Error cargando eventos', e)
      }
    },

    mapGaleria(galeria) {
      return {
        id: galeria.id,
        titulo: galeria.titulo,
        fecha: galeria.fecha,
        descripcion: galeria.descripcion,
        imagenes: galeria.fotos.map(f => f.imagen)
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

/* IMAGEN DE FONDO FIJA (SIN PARALLAX) */
.blog::before {
  content: '';
  position: absolute;
  inset: 0;
  background-image: url('https://t3.ftcdn.net/jpg/03/17/79/80/360_F_317798025_rTTpQGs1UjMAV0H4g3ljx7Jws5RUzCVh.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  filter: brightness(0.35);
  z-index: 0;
}

/* OVERLAY OSCURO */
.blog::after {
  content: '';
  position: absolute;
  inset: 0;
  background: radial-gradient(circle at top,
      rgba(50, 10, 80, 0.65),
      #120016 75%);
  z-index: 1;
}

/* CONTENIDO POR ENCIMA */
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

/* ================= MEDIA ================= */
.media {
  grid-area: media;
  width: 100%;
  aspect-ratio: 18 / 9;
  overflow: hidden;
  border-radius: 28px;
  box-shadow: 0 35px 80px rgba(0, 0, 0, 0.55);
}

.carousel,
.slide {
  width: 100%;
  height: 100% !important;
}

.slide img {
  width: 100%;
  height: 100%;
  object-fit: cover;
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
    width: 100%;
    aspect-ratio: 16 / 9;
  }

  .carousel,
  .slide {
    width: 100%;
    height: 100% !important;
  }

  .contenido {
    max-width: 100%;
  }

  .blog-header {
    padding: 140px 8% 80px;
  }
}
</style>


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

/* IMAGEN DE FONDO FIJA (SIN PARALLAX) */
.blog::before {
  content: '';
  position: absolute;
  inset: 0;
  background-image: url('https://t3.ftcdn.net/jpg/03/17/79/80/360_F_317798025_rTTpQGs1UjMAV0H4g3ljx7Jws5RUzCVh.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  filter: brightness(0.35);
  z-index: 0;
}

/* OVERLAY OSCURO */
.blog::after {
  content: '';
  position: absolute;
  inset: 0;
  background: radial-gradient(circle at top,
      rgba(50, 10, 80, 0.65),
      #120016 75%);
  z-index: 1;
}

/* CONTENIDO POR ENCIMA */
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

/* ================= MEDIA ================= */
.media {
  grid-area: media;
  width: 100%;
  aspect-ratio: 18 / 9;
  overflow: hidden;
  border-radius: 28px;
  box-shadow: 0 35px 80px rgba(0, 0, 0, 0.55);
}

.carousel,
.slide {
  width: 100%;
  height: 100% !important;
}

.slide img {
  width: 100%;
  height: 100%;
  object-fit: cover;
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
    width: 100%;
    aspect-ratio: 16 / 9;
  }

  .carousel,
  .slide {
    width: 100%;
    height: 100% !important;
  }

  .contenido {
    max-width: 100%;
  }

  .blog-header {
    padding: 140px 8% 80px;
  }
}
</style>
