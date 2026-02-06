<template>
  <div class="pagina">
    <section class="lecturas-view">
      <!-- HERO -->
      <header class="hero">
        <div class="hero-bg"></div>
        <div class="hero-overlay"></div>

        <div class="hero-content">
          <h1>Lecturas & Rituales</h1>
          <p>Un espacio moderno para explorar, sentir y transformar</p>
        </div>
      </header>

      <!-- CARDS -->
      <section class="cards">
        <article
          v-for="(item, i) in items"
          :key="i"
          class="card"
        >
          <div
            class="card-bg"
            :style="{ backgroundImage: `url(${item.imagen})` }"
          ></div>

          <div class="card-content">
            <h2>{{ item.titulo }}</h2>
            <p>{{ item.texto }}</p>
            <button>{{ item.boton }}</button>
          </div>
        </article>
      </section>
    </section>

    <!-- FOOTER -->
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from '@/components/FooterComponent.vue'

export default {
  name: 'LecturasView',

  components: {
    FooterComponent,
  },

  data() {
    return {
      items: [
        {
          titulo: 'Lecturas de Cartas',
          texto:
            'Tarot y oráculos para comprender decisiones, bloqueos y procesos vitales.',
          boton: 'Ver lecturas',
          imagen:
            'https://images.unsplash.com/photo-1519681393784-d120267933ba',
        },
        {
          titulo: 'Rituales Energéticos',
          texto:
            'Aperturas, cierres y limpiezas que acompañan momentos de cambio.',
          boton: 'Explorar rituales',
          imagen:
            'https://images.unsplash.com/photo-1500530855697-b586d89ba3ee',
        },
        {
          titulo: 'Acompañamiento Espiritual',
          texto:
            'Procesos conscientes para quienes necesitan claridad y sostén.',
          boton: 'Saber más',
          imagen:
            'https://images.unsplash.com/photo-1501785888041-af3ef285b470',
        },
      ],
    }
  },
}
</script>

<style scoped>
.pagina {
  background: #0d0013;
}

/* ===== HERO ===== */
.hero {
  position: relative;
  height: calc(100vh - 120px);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.hero-bg {
  position: absolute;
  inset: 0;
  background-image: url('https://img.freepik.com/free-vector/hand-drawn-tarot-cards-background_79603-2055.jpg');
  background-size: cover;
  background-position: center;
  transform: scale(1.1);
  filter: brightness(0.45);
}

.hero-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(
    180deg,
    rgba(13, 0, 19, 0.25),
    #0d0013 85%
  );
}

.hero-content {
  position: relative;
  z-index: 2;
  max-width: 820px;
  padding: 0 8%;
  text-align: center;
  color: #f6dc91;
}

.hero h1 {
  font-family: var(--font-title);
  font-size: 4.4rem;
  letter-spacing: 8px;
  margin-bottom: 28px;
}

.hero p {
  font-size: 1.2rem;
  line-height: 1.8;
  opacity: 0.9;
}

/* ===== CARDS ===== */
.cards {
  padding: 160px 8% 200px;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 90px;
}

.card {
  position: relative;
  height: 520px;
  border-radius: 36px;
  overflow: hidden;
}

.card-bg {
  position: absolute;
  inset: 0;
  background-size: cover;
  background-position: center;
  filter: brightness(0.55);
  transition: transform 0.9s ease;
}

.card:hover .card-bg {
  transform: scale(1.08);
}

.card-content {
  position: relative;
  z-index: 1;
  height: 100%;
  padding: 60px 50px;
  background: linear-gradient(
    180deg,
    rgba(13, 0, 19, 0.15),
    rgba(13, 0, 19, 0.9)
  );
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  color: #f6dc91;
}

.card h2 {
  font-family: var(--font-title);
  font-size: 2.2rem;
  letter-spacing: 4px;
  margin-bottom: 22px;
}

.card p {
  font-size: 1rem;
  line-height: 1.7;
  opacity: 0.92;
  margin-bottom: 36px;
}

.card button {
  align-self: flex-start;
  padding: 14px 44px;
  border: 1px solid #f6dc91;
  background: transparent;
  color: #f6dc91;
  letter-spacing: 3px;
  cursor: pointer;
}

.card button:hover {
  background: #f6dc91;
  color: #0d0013;
}

/* ===== RESPONSIVE ===== */
@media (max-width: 1000px) {
  .cards {
    grid-template-columns: 1fr;
  }
}
</style>
