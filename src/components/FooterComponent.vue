<template>
  <footer class="footer">
    <div class="footer-wrapper">
      <div class="marca">
        <h3>Secreto de la Sacerdotisa</h3>
        <p>
          Lecturas conscientes y respetuosas.
          Un espacio íntimo donde la intuición guía
          y la claridad emerge.
        </p>
      </div>

      <div class="bloque">
        <span class="titulo">Contacto directo</span>
        <a href="tel:+34600000000" class="contacto">+34 600 000 000</a>
        <a href="https://wa.me/34600000000" target="_blank" class="contacto">WhatsApp</a>
        <a href="mailto:contacto@secretodesacerdotisa.com" class="contacto">
          contacto@secretodesacerdotisa.com
        </a>
      </div>

      <div class="bloque">
        <span class="titulo">Intención</span>
        <p class="mensaje">
          Si sientes el impulso de escribir o llamar,
          confía en él. Nada sucede por casualidad.
        </p>
      </div>
    </div>

    <div class="bottom">
      <span>© {{ year }} Secreto de la Sacerdotisa</span>
      <span class="firma">Creado con intención y respeto</span>
    </div>

    <button 
      v-if="!estaLogueado" 
      class="admin-trigger" 
      @click="mostrarAdmin = true"
    >•</button>

    <div v-if="mostrarAdmin" class="admin-panel">
      <div class="admin-box">
        <h4>Acceso administrador</h4>

        <input
          type="password"
          v-model="password"
          placeholder="Contraseña"
          @keyup.enter="validarAdmin"
        />

        <button @click="validarAdmin">Entrar</button>
        <button class="cancel" @click="cerrarAdmin">Cancelar</button>

        <p v-if="error" class="error">Contraseña incorrecta</p>
      </div>
    </div>
  </footer>
</template>

<script>
import { EventBus } from '@/eventBus'

export default {
  name: 'FooterComponent',
  data () {
    return {
      mostrarAdmin: false,
      password: '',
      error: false,
      // Comprobamos el estado inicial
      estaLogueado: !!localStorage.getItem('admin')
    }
  },
  computed: {
    year () {
      return new Date().getFullYear()
    }
  },
  created () {
    // Escuchar cuando el admin entra
    EventBus.$on('admin-login', () => {
      this.estaLogueado = true
    })

    // Escuchar cuando el admin sale
    EventBus.$on('admin-logout', () => {
      this.estaLogueado = false
    })
  },
  methods: {
    async validarAdmin () {
      try {
        const res = await fetch(
          'http://localhost:8081/api/admin/login?password=' + this.password,
          { method: 'POST' }
        )

        if (!res.ok) throw new Error()

        localStorage.setItem('admin', 'true')
        // Avisamos al resto de la app y a nosotros mismos
        EventBus.$emit('admin-login')

        this.mostrarAdmin = false
        this.password = ''
        this.error = false
      } catch (e) {
        this.error = true
      }
    },
    cerrarAdmin () {
      this.mostrarAdmin = false
      this.password = ''
      this.error = false
    }
  }
}
</script>

<style scoped>
.footer { position: relative; background:#0b000d; color:#ffd36a; padding:120px 10% 40px }
.footer-wrapper { display:grid; grid-template-columns:2fr 1fr 1fr; gap:80px }
.marca h3 { font-size:1.6rem; margin-bottom:20px }
.bloque { display:flex; flex-direction:column; gap:14px }
.bottom { margin-top:60px; display:flex; justify-content:space-between; opacity:.6 }

.admin-trigger {
  position:absolute;
  bottom:15px;
  right:20px;
  background:transparent;
  border:none;
  color:rgba(255,211,106,.3);
  font-size:18px;
  cursor:pointer;
  z-index: 10;
}

.admin-panel {
  position:fixed;
  inset:0;
  background:rgba(0,0,0,.75);
  display:flex;
  justify-content:center;
  align-items:center;
  z-index:9999;
}

.admin-box {
  background:#120016;
  padding:30px;
  border:1px solid rgba(255,211,106,.4);
  text-align:center;
}

.admin-box input {
  width:100%;
  padding:10px;
  margin-bottom:15px;
  background:transparent;
  border:1px solid rgba(255,211,106,.4);
  color:#ffd36a;
}

.admin-box button {
  width:100%;
  padding:10px;
  margin-top:8px;
  background:rgba(255,211,106,.2);
  border:none;
  color:#ffd36a;
  cursor:pointer;
}

.cancel { background:transparent; opacity:.6 }
.error { color:#e57373; margin-top:10px }

.contacto {
  color: #ffd36a;
  text-decoration: none;
  opacity: 0.9;
  transition: opacity 0.3s ease;
}

.contacto:hover {
  opacity: 1;
}
</style>