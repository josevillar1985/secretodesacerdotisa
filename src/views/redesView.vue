<template>
  <div class="pagina">
    <section class="redes">
      <div class="cartas">
        <a
          v-for="(red, i) in redes"
          :key="i"
          :href="red.url"
          target="_blank"
          class="carta"
          ref="cartas"
        >
          <div class="carta-inner">
            <span class="halo"></span>

            <div class="icono">
              <img :src="red.icono" :alt="red.nombre" />
            </div>

            <h3>{{ red.nombre }}</h3>
            <p>{{ red.texto }}</p>
          </div>
        </a>
      </div>
    </section>

    <!-- FOOTER -->
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from '@/components/FooterComponent.vue'

export default {
  name: 'RedesView',

  components: {
    FooterComponent,
  },

  data() {
    return {
      redes: [
        {
          nombre: 'TikTok',
          texto:
            'Mensajes breves, señales y fragmentos que aparecen cuando deben.',
          icono:
            'https://cdn-icons-png.flaticon.com/512/3046/3046127.png',
          url: 'https://www.tiktok.com/@secretodesacerdotisa',
        },
        {
          nombre: 'Instagram',
          texto:
            'Rituales visuales, imágenes y momentos compartidos.',
          icono:
            'https://cdn-icons-png.flaticon.com/512/3955/3955024.png',
          url: 'https://www.instagram.com/secretodesacerdotisa',
        },
        {
          nombre: 'YouTube',
          texto:
            'Lecturas completas, contenido profundo y espacios largos.',
          icono:
            'https://cdn-icons-png.flaticon.com/512/1384/1384060.png',
          url: 'https://www.youtube.com/channel/UC0eBRZfm56yucIc-F5Rz82A',
        },
        {
          nombre: 'Facebook',
          texto:
            'Comunidad, encuentros y comunicación directa.',
          icono:
            'https://cdn-icons-png.flaticon.com/512/145/145802.png',
          url: 'https://www.facebook.com/secretodesacerdotisa',
        },
      ],
    }
  },

  mounted() {
    this.$nextTick(() => {
      const cartas = this.$refs.cartas
      if (!cartas) return

      const observer = new IntersectionObserver(
        (entries) => {
          entries.forEach((entry) => {
            if (entry.isIntersecting) {
              entry.target.classList.add('visible')
              observer.unobserve(entry.target)
            }
          })
        },
        { threshold: 0.25 }
      )

      cartas.forEach((c) => observer.observe(c))
    })
  },
}
</script>

<style scoped>
.pagina {
  background: #120016;
}

/* ===== REDES ===== */
.redes {
  position: relative;
  padding: 160px 10% 120px;
  color: #f6dc91;
  text-align: center;
  overflow: hidden;
}

/* IMAGEN DE FONDO (FIJA, SIN PARALLAX) */
.redes::before {
  content: '';
  position: absolute;
  inset: 0;
  background-image: url('https://m.media-amazon.com/images/S/pv-target-images/ec76dd0a3bec55fe0a268dd3d24137c8d87162196af3fc316daf3ca668ced09c._SX1080_FMjpg_.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  filter: brightness(0.35);
  z-index: 0;
}

/* OVERLAY OSCURO */
.redes::after {
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

/* CONTENIDO */
.cartas {
  position: relative;
  z-index: 2;
  display: grid;
  grid-template-columns: repeat(4, minmax(240px, 280px));
  justify-content: center;
  gap: 70px;
}

.carta {
  text-decoration: none;
  color: inherit;
  opacity: 0;
  transform: translateY(80px) scale(0.96);
  transition: all 1s cubic-bezier(0.16, 1, 0.3, 1);
  perspective: 1200px;
}

.carta.visible {
  opacity: 1;
  transform: translateY(0) scale(1);
}

.carta-inner {
  position: relative;
  height: 420px;
  padding: 60px 30px;
  border-radius: 30px;
  background: linear-gradient(
    180deg,
    rgba(120, 60, 180, 0.42),
    rgba(18, 0, 28, 0.97)
  );
  border: 1px solid rgba(255, 215, 130, 0.28);
  backdrop-filter: blur(12px);
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
  transition: transform 0.6s ease, box-shadow 0.6s ease;
}

.halo {
  position: absolute;
  inset: -60%;
  background: conic-gradient(
    from 0deg,
    transparent,
    rgba(255, 215, 120, 0.9),
    transparent 25%
  );
  animation: rotar 7s linear infinite;
  opacity: 0.14;
}

.carta-inner:hover .halo {
  opacity: 0.22;
}

.icono {
  width: 88px;
  height: 88px;
  margin-bottom: 34px;
}

.icono img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

h3 {
  letter-spacing: 3px;
  margin-bottom: 22px;
  font-size: 1.4rem;
}

p {
  font-size: 1rem;
  line-height: 1.75;
  opacity: 0.9;
  max-width: 220px;
}

.carta-inner:hover {
  transform: translateY(-8px) rotateX(3deg) rotateY(-2deg);
  box-shadow: 0 20px 45px rgba(255, 211, 120, 0.22);
}

@keyframes rotar {
  to {
    transform: rotate(360deg);
  }
}

/* RESPONSIVE */
@media (max-width: 1100px) {
  .cartas {
    grid-template-columns: repeat(2, 280px);
  }
}

@media (max-width: 600px) {
  .cartas {
    grid-template-columns: 1fr;
  }

  .redes {
    padding: 130px 8% 100px;
  }
}

</style>
