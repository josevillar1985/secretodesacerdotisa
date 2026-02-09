<template>
  <div>
    <v-app-bar
      app
      height="120"
      flat
      class="nav"
    >
      <v-btn
        icon
        class="hamburguesa"
        @click.stop="drawer = !drawer"
      >
        <v-icon class="icono">mdi-menu</v-icon>
      </v-btn>

      <router-link to="/" class="logo">
        <span class="linea-superior">Secreto</span>
        <span class="linea-inferior">de la Sacerdotisa</span>
      </router-link>

      <v-spacer></v-spacer>

      <nav class="links">
        <router-link to="/" exact class="link">Inicio</router-link>
        <router-link to="/contacto" class="link">Contacto</router-link>
        <router-link to="/lecturas" class="link">Lecturas</router-link>
        <router-link to="/redes" class="link">Redes</router-link>
        <router-link to="/blog" class="link">Blog</router-link>

        <router-link
          v-if="isAdmin"
          to="/admin"
          class="link admin"
        >
          Admin
        </router-link>
      </nav>

      <div class="acciones">
        <a
          href="https://wa.me/34675096928"
          target="_blank"
          rel="noopener"
          class="accion whatsapp"
        >
          <v-icon class="icono">mdi-whatsapp</v-icon>
        </a>

        <a
          href="tel:+34675096928"
          class="accion llamar"
        >
          <v-icon class="icono">mdi-phone</v-icon>
        </a>
      </div>
    </v-app-bar>

    <v-navigation-drawer
      app
      temporary
      v-model="drawer"
      class="drawer"
    >
      <v-list nav dense>
        <v-list-item to="/" exact link @click="drawer = false">
          <v-list-item-content>
            <v-list-item-title class="drawer-text">Inicio</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item to="/contacto" link @click="drawer = false">
          <v-list-item-content>
            <v-list-item-title class="drawer-text">Contacto</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item to="/lecturas" link @click="drawer = false">
          <v-list-item-content>
            <v-list-item-title class="drawer-text">Lecturas</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item to="/redes" link @click="drawer = false">
          <v-list-item-content>
            <v-list-item-title class="drawer-text">Redes</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item to="/blog" link @click="drawer = false">
          <v-list-item-content>
            <v-list-item-title class="drawer-text">Blog</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item
          v-if="isAdmin"
          to="/admin"
          link
          @click="drawer = false"
        >
          <v-list-item-content>
            <v-list-item-title class="drawer-text admin">
              Administrador
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
import { EventBus } from '@/eventBus'

export default {
  name: 'NavBar',
  data() {
    return {
      drawer: false,
      isAdmin: false
    }
  },
  // EL WATCHER: Esto vigila si cambias de página
  watch: {
    '$route'() {
      this.verificarSesion()
    }
  },
  methods: {
    verificarSesion() {
      // Forzamos la lectura del localStorage
      this.isAdmin = localStorage.getItem('admin') === 'true'
    }
  },
  created() {
    // 1. Verificación inicial al cargar
    this.verificarSesion()

    // 2. Escuchar eventos (por si el usuario no cambia de página)
    EventBus.$on('admin-login', () => {
      this.isAdmin = true
    })

    EventBus.$on('admin-logout', () => {
      this.isAdmin = false
    })
  },
  beforeDestroy() {
    // Es buena práctica limpiar los eventos
    EventBus.$off('admin-login')
    EventBus.$off('admin-logout')
  }
}
</script>

<style scoped>
/* Tu CSS se mantiene exactamente igual */
.nav {
  background: linear-gradient(
    180deg,
    rgba(18, 0, 22, 0.95),
    rgba(18, 0, 22, 0.9)
  ) !important;
  padding: 0 32px;
  border-bottom: 1px solid rgba(255, 211, 106, 0.25);
}
.icono { color: var(--color-gold) !important; }
.hamburguesa { display: none; }
.logo { display: flex; flex-direction: column; text-decoration: none; color: var(--color-gold); }
.linea-superior { font-family: var(--font-title); font-size: 1.8rem; letter-spacing: 4px; line-height: 1; }
.linea-inferior { font-family: var(--font-title); font-size: 1.05rem; letter-spacing: 6px; margin-top: 6px; opacity: 0.85; }
.links { display: flex; gap: 42px; }
.link { font-family: var(--font-ui); font-size: 0.85rem; letter-spacing: 3px; text-transform: uppercase; color: rgba(255, 215, 120, 0.85); text-decoration: none; position: relative; transition: color 0.3s ease; }
.link::after { content: ''; position: absolute; bottom: -10px; left: 50%; width: 0; height: 2px; background: linear-gradient(90deg, transparent, var(--color-gold), transparent); transform: translateX(-50%); transition: width 0.35s ease; }
.link:hover { color: #ffffff; }
.link:hover::after, .router-link-exact-active::after { width: 120%; box-shadow: 0 0 12px rgba(255, 215, 120, 0.9); }
.link.admin { color: #ff8a80; }
.link.admin::after { background: linear-gradient(90deg, transparent, rgba(255, 80, 80, 0.9), transparent); }
.acciones { display: flex; gap: 14px; margin-left: 28px; }
.accion { width: 36px; height: 36px; border-radius: 50%; display: flex; align-items: center; justify-content: center; }
.whatsapp { background: rgba(37, 211, 102, 0.2); }
.whatsapp .icono { color: #25d366 !important; }
.llamar { background: rgba(255, 215, 120, 0.2); }
.drawer { background: #120016; display: flex; align-items: center; }
.drawer .v-list { width: 100%; text-align: center; }
.drawer .v-list-item { justify-content: center; padding: 22px 0; }
.drawer-text { font-family: var(--font-title); font-size: 1.35rem; letter-spacing: 4px; color: var(--color-gold); text-align: center; }
.drawer-text.admin { color: #ff8a80; letter-spacing: 5px; }

@media (max-width: 1000px) {
  .links { display: none; }
  .hamburguesa { display: inline-flex; }
  .linea-superior { font-size: 1.4rem; }
  .linea-inferior { font-size: 0.9rem; }
}
</style>