<template>
  <div class="pagina">
    <section class="contacto">
      <header class="hero">
        <div class="hero-bg"></div>
        <div class="hero-overlay"></div>

        <div class="hero-box">
          <div class="hero-text">
            <h1>Contacto</h1>
            <p>Escríbeme o llámame directamente</p>

            <div class="hero-acciones">
              <a href="tel:+34675096928" class="btn llamar">
                <svg viewBox="0 0 24 24" class="icon">
                  <path
                    fill="currentColor"
                    d="M6.6 10.8c1.5 3 3.6 5.1 6.6 6.6l2.2-2.2c.3-.3.7-.4 1.1-.3
                    1.2.4 2.5.6 3.9.6.6 0 1 .4 1 1v3.5c0 .6-.4 1-1 1
                    C9.8 21 3 14.2 3 6.5c0-.6.4-1 1-1H7.5c.6 0 1 .4 1 1
                    0 1.4.2 2.7.6 3.9.1.4 0 .8-.3 1.1L6.6 10.8z"
                  />
                </svg>
                Llamar
              </a>

              <a
                href="https://wa.me/34675096928"
                target="_blank"
                rel="noopener"
                class="btn whatsapp"
              >
                <svg viewBox="0 0 32 32" class="icon">
                  <path
                    fill="currentColor"
                    d="M16.02 2.67c-7.3 0-13.2 5.86-13.2 13.1
                    0 2.3.6 4.5 1.8 6.5L2 30l7.9-2.5
                    c1.9 1 4 1.6 6.1 1.6
                    7.3 0 13.2-5.9 13.2-13.2
                    0-7.2-5.9-13.1-13.2-13.1zm7.6 18.7
                    c-.3.9-1.6 1.7-2.2 1.8-.6.1-1.3.2-2.1-.1
                    -.5-.2-1.1-.4-1.9-.8-3.4-1.6-5.6-5.3-5.8-5.5
                    -.2-.2-1.4-1.9-1.4-3.6
                    0-1.7.9-2.5 1.2-2.8
                    .3-.3.7-.4 1-.4h.7
                    c.2 0 .5-.1.8.6.3.7 1 2.5
                    1.1 2.7.1.2.1.4 0 .6
                    -.1.2-.2.4-.4.6
                    -.2.2-.4.4-.6.6
                    -.2.2-.4.4-.2.8
                    .2.4 1.1 1.8 2.3 2.9
                    1.6 1.4 3 1.9 3.4 2.1
                    .4.2.6.2.8 0
                    .2-.2.9-1.1 1.2-1.4
                    .3-.3.5-.3.9-.2
                    .4.1 2.6 1.2 3.1 1.5
                    .4.2.7.3.8.5
                    .1.2.1 1-.2 1.9z"
                  />
                </svg>
                WhatsApp
              </a>
            </div>
          </div>

          <form class="formulario">
            <h3>Enviar mensaje</h3>

            <input type="text" placeholder="Nombre" />
            <input type="email" placeholder="Email" />
            <textarea rows="4" placeholder="Mensaje"></textarea>

            <button type="submit">Enviar</button>
          </form>
        </div>
      </header>
    </section>

    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from '@/components/FooterComponent.vue'

export default {
  name: 'ContactoView',
  components: { FooterComponent },
}
</script>

<style scoped>
.pagina {
  background: #0d0013;
  color: #f6dc91;
  overflow-x: hidden;
}

/* ===== HERO ===== */
.hero {
  position: relative;
  min-height: calc(100vh - 120px); /* desktop */
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

/* Fondo */
.hero-bg {
  position: absolute;
  inset: 0;
  background-image: url('https://www.univision.com/_next/image?url=https%3A%2F%2Fst1.uvnimg.com%2Ffe%2Ff9%2Fa6a81de24a26a967f79f0166cf8d%2Fshutterstock-512473990.jpg&w=1280&q=75');
  background-size: cover;
  background-position: center;
  filter: brightness(0.35);
}

.hero-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(
    180deg,
    rgba(13, 0, 19, 0.3),
    #0d0013 90%
  );
}

/* ===== CAJA PRINCIPAL ===== */
.hero-box {
  position: relative;
  z-index: 1;
  width: min(1100px, 92%);
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 80px;
  align-items: center;
}

/* ===== TEXTO ===== */
.hero-text h1 {
  font-family: var(--font-title);
  font-size: 3.5rem;
  letter-spacing: 6px;
  margin-bottom: 20px;
}

.hero-text p {
  line-height: 1.6;
  opacity: 0.9;
  margin-bottom: 40px;
}

/* ===== ACCIONES ===== */
.hero-acciones {
  display: flex;
  gap: 20px;
  flex-wrap: wrap; /* 🔑 evita overflow */
}

.btn {
  display: inline-flex;
  align-items: center;
  gap: 12px;
  padding: 16px 32px;
  border: 1px solid #f6dc91;
  color: #f6dc91;
  text-decoration: none;
  letter-spacing: 3px;
  border-radius: 18px;
  transition: all 0.3s ease;
}

.icon {
  width: 18px;
  height: 18px;
}

.llamar:hover {
  background: #f6dc91;
  color: #0d0013;
}

.whatsapp {
  border-color: #25d366;
  color: #25d366;
}

.whatsapp:hover {
  background: #25d366;
  color: #0d0013;
}

/* ===== FORMULARIO ===== */
.formulario {
  background: rgba(18, 0, 28, 0.7);
  backdrop-filter: blur(12px);
  border-radius: 28px;
  padding: 48px;
  display: flex;
  flex-direction: column;
  gap: 22px;
  box-shadow: 0 30px 80px rgba(0, 0, 0, 0.6);
}

.formulario h3 {
  font-family: var(--font-title);
  letter-spacing: 3px;
}

input,
textarea {
  background: transparent;
  border: 1px solid rgba(246, 220, 145, 0.4);
  padding: 14px 16px;
  color: #f6dc91;
  font-family: inherit;
  border-radius: 12px;
}

input::placeholder,
textarea::placeholder {
  color: rgba(246, 220, 145, 0.6);
}

button {
  padding: 16px;
  background: transparent;
  border: 1px solid #f6dc91;
  color: #f6dc91;
  letter-spacing: 3px;
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

button:hover {
  background: #f6dc91;
  color: #0d0013;
}

/* =======================
   TABLET
======================= */
@media (max-width: 1000px) {
  .hero {
    min-height: calc(100svh - 96px);
  }

  .hero-box {
    grid-template-columns: 1fr;
    gap: 60px;
  }

  .hero-text h1 {
    font-size: 2.6rem;
    letter-spacing: 4px;
  }

  .formulario {
    padding: 40px;
  }
}

/* =======================
   MÓVIL
======================= */
@media (max-width: 600px) {
  .hero-text h1 {
    font-size: 2rem;
    letter-spacing: 3px;
  }

  .hero-text p {
    font-size: 0.95rem;
    margin-bottom: 28px;
  }

  .btn {
    padding: 14px 24px;
    font-size: 0.85rem;
    letter-spacing: 2px;
  }

  .formulario {
    padding: 32px;
  }
}
</style>

