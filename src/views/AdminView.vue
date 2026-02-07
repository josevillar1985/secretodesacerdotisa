<template>
  <div class="admin-page">
    <section class="admin-container">
      <button class="logout-top" @click="logout">
        <span>Cerrar Sesión</span>
        <v-icon small class="ml-2" color="#ff8a80">mdi-logout</v-icon>
      </button>

      <header class="admin-header">
        <h1>Gestión de Santuario</h1>
        <p>Publicar nuevos eventos y rituales en el Blog</p>
      </header>

      <div class="form-wrapper">
        <div class="admin-card">
          <div class="input-group">
            <label>Título del Evento</label>
            <input
              v-model="nuevaGaleria.titulo"
              type="text"
              placeholder="Ej: Ritual de Luna Llena"
              class="text-center"
            />
          </div>

          <div class="input-group">
            <label>Fecha del Encuentro</label>
            <input
              v-model="nuevaGaleria.fecha"
              type="text"
              placeholder="Ej: 21 Marzo 2026"
              class="text-center"
            />
          </div>

          <div class="input-group">
            <label>Descripción del Evento</label>
            <textarea
              v-model="nuevaGaleria.descripcion"
              rows="3"
              placeholder="Describe la energía vivida..."
              class="text-center"
            ></textarea>
          </div>
        </div>

        <div class="admin-card flex-center">
          <label class="btn-subir">
            <v-icon class="mr-2" color="#120016">mdi-camera-plus</v-icon>
            Seleccionar Fotos
            <input type="file" multiple accept="image/*" @change="manejarSubida" hidden />
          </label>

          <p class="count" v-if="nuevaGaleria.fotos.length > 0">
            {{ nuevaGaleria.fotos.length }} imágenes preparadas
          </p>

          <div class="lista-fotos" v-if="nuevaGaleria.fotos.length > 0">
            <div
              v-for="(f, index) in nuevaGaleria.fotos"
              :key="index"
              class="foto-item"
            >
              <img :src="f.imagen" />
              <button class="remove-btn" @click="nuevaGaleria.fotos.splice(index, 1)">
                ✕
              </button>
            </div>
          </div>
        </div>

        <div class="acciones-finales">
          <button
            @click="guardarTodo"
            class="btn-principal"
            :disabled="cargando || nuevaGaleria.fotos.length === 0"
          >
            {{ cargando ? 'Elevando plegaria...' : 'Manifestar en el Blog' }}
          </button>
          <transition name="fade">
            <p v-if="mensaje" :class="['status', mensaje.tipo]">
              {{ mensaje.texto }}
            </p>
          </transition>
        </div>
      </div>
    </section>
    <ListAdmin />
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from '@/components/FooterComponent.vue'
import ListAdmin from '@/components/ListAdmin.vue';
import { EventBus } from '@/eventBus'

const CLOUD_NAME = 'deknkhbmr'
const UPLOAD_PRESET = 'galerias_unsigned'

export default {
  name: 'AdminPanel',
  components: { FooterComponent, ListAdmin },

  data() {
    return {
      cargando: false,
      mensaje: null,
      nuevaGaleria: {
        titulo: '',
        descripcion: '',
        fecha: '',
        fotos: []
      }
    }
  },

  methods: {
    logout() {
      localStorage.removeItem('admin')
      EventBus.$emit('admin-logout')
      this.$router.push('/')
    },

    // 🔥 SUBIDA A CLOUDINARY (URLs, NO base64)
    async manejarSubida(event) {
      const files = event.target.files
      if (!files.length) return

      for (const file of files) {
        if (file.size > 5 * 1024 * 1024) {
          this.mostrarMensaje('Imagen demasiado grande (máx 5MB)', 'error')
          continue
        }

        const formData = new FormData()
        formData.append('file', file)
        formData.append('upload_preset', UPLOAD_PRESET)

        try {
          const res = await fetch(
            `https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`,
            {
              method: 'POST',
              body: formData
            }
          )

          const data = await res.json()

          if (!data.secure_url) {
            throw new Error('Error subiendo imagen a Cloudinary')
          }

          this.nuevaGaleria.fotos.push({
            imagen: data.secure_url
          })

        } catch (e) {
          this.mostrarMensaje('Error al subir imagen', 'error')
        }
      }
    },

    // 🔥 POST FINAL (solo URLs, backend feliz)
    async guardarTodo() {
      if (!this.nuevaGaleria.titulo || this.nuevaGaleria.fotos.length === 0) {
        this.mostrarMensaje('Faltan datos o fotos', 'error')
        return
      }

      this.cargando = true

      try {
        const res = await fetch(
          'https://api-secretodesacerdotisa.josevillar.com/galerias',
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.nuevaGaleria)
          }
        )

        if (!res.ok) {
          throw new Error('Error al guardar galería')
        }

        this.mostrarMensaje('Publicado con éxito', 'success')
        this.nuevaGaleria = {
          titulo: '',
          descripcion: '',
          fecha: '',
          fotos: []
        }

      } catch (e) {
        this.mostrarMensaje(e.message, 'error')
      } finally {
        this.cargando = false
      }
    },

    mostrarMensaje(texto, tipo) {
      this.mensaje = { texto, tipo }
      setTimeout(() => {
        this.mensaje = null
      }, 5000)
    }
  }
}
</script>


<style scoped>
/* Los estilos se mantienen iguales que el anterior */

/* 🔹 1. Centrar el layout general */
.admin-page {
  background: #120016;
  min-height: 100vh;

  display: flex;
  flex-direction: column;
  align-items: center;
}

.admin-container {
  padding: 120px 5% 80px;
  color: #ffd36a;
  max-width: 800px;
  margin: 0 auto;

  display: flex;
  flex-direction: column;
  align-items: center;

  position: relative;
}

.logout-top {
  position: absolute;
  top: 40px;
  right: 5%;
  background: rgba(255, 138, 128, 0.1);
  border: 1px solid rgba(255, 138, 128, 0.4);
  color: #ff8a80;
  padding: 8px 16px;
  border-radius: 4px;
  font-size: 0.75rem;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.admin-header {
  text-align: center;
  margin-bottom: 50px;
}

.admin-header h1 {
  font-family: serif;
  font-size: 2.8rem;
  letter-spacing: 4px;
  margin-bottom: 10px;
}

.form-wrapper {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 25px;
}

.admin-card {
  background: rgba(255, 211, 106, 0.03);
  border: 1px solid rgba(255, 211, 106, 0.1);
  padding: 40px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.input-group {
  width: 100%;
  margin-bottom: 25px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.text-center {
  text-align: center;
}

label {
  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 3px;
  margin-bottom: 12px;
  opacity: 0.7;
}

/* 🔹 2. Inputs centrados sin tocar estilos */
input,
textarea {
  width: 100%;
  max-width: 500px;
  background: rgba(0, 0, 0, 0.4);
  border: 1px solid rgba(255, 211, 106, 0.2);
  padding: 15px;
  color: #fff;
  border-radius: 8px;
  outline: none;

  margin: 0 auto;
  text-align: center;
}

.btn-subir {
  background: #ffd36a;
  color: #120016;
  padding: 15px 30px;
  border-radius: 10px;
  cursor: pointer;
  font-weight: bold;
}

.lista-fotos {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 15px;
  margin-top: 30px;
}

.foto-item {
  position: relative;
  width: 100px;
  height: 100px;
  border-radius: 10px;
  overflow: hidden;
  border: 2px solid rgba(255, 211, 106, 0.3);
}

.foto-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.remove-btn {
  position: absolute;
  top: 0;
  right: 0;
  background: #ff5252;
  border: none;
  color: white;
  width: 25px;
  height: 25px;
  cursor: pointer;
}

/* 🔹 3. Botón final y mensaje centrados */
.acciones-finales {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.btn-principal {
  background: transparent;
  color: #ffd36a;
  padding: 18px 60px;
  border: 1px solid #ffd36a;
  border-radius: 50px;
  font-weight: bold;
  font-size: 1.2rem;
  cursor: pointer;
}

.btn-principal:hover:not(:disabled) {
  background: #ffd36a;
  color: #120016;
}

.status {
  margin-top: 25px;
  text-align: center;
}

.success {
  color: #81c784;
}

.error {
  color: #ff8a80;
}

</style>