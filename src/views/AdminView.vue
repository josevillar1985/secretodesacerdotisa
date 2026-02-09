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
            <input
              type="file"
              multiple
              accept="image/*"
              hidden
              @change="manejarSubida"
            />
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
              <button
                class="remove-btn"
                @click="nuevaGaleria.fotos.splice(index, 1)"
              >
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

          <!-- 🔴 ALERTA SIMPLE, COMO ESTABA -->
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
import ListAdmin from '@/components/ListAdmin.vue'
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
            throw new Error()
          }

          this.nuevaGaleria.fotos.push({
            imagen: data.secure_url
          })

        } catch (e) {
          this.mostrarMensaje('Error al subir imagen', 'error')
        }
      }
    },

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
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(this.nuevaGaleria)
          }
        )

        if (!res.ok) throw new Error()

        this.mostrarMensaje('Publicado con éxito', 'success')
        this.nuevaGaleria = {
          titulo: '',
          descripcion: '',
          fecha: '',
          fotos: []
        }

      } catch (e) {
        this.mostrarMensaje('Error al guardar galería', 'error')
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
/* =======================
   BASE
======================= */
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
  width: 100%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}

/* =======================
   LOGOUT
======================= */
.logout-top {
  position: absolute;
  top: 30px;
  right: 20px;
  background: rgba(255, 138, 128, 0.12);
  border: 1px solid rgba(255, 138, 128, 0.4);
  color: #ff8a80;
  padding: 8px 14px;
  border-radius: 20px;
  font-size: 0.7rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 6px;
  z-index: 10;
}

/* =======================
   HEADER
======================= */
.admin-header {
  text-align: center;
  margin-bottom: 50px;
}

.admin-header h1 {
  font-family: serif;
  font-size: 2.6rem;
  letter-spacing: 4px;
  margin-bottom: 10px;
}

.admin-header p {
  opacity: 0.85;
}

/* =======================
   FORM
======================= */
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

label {
  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 3px;
  margin-bottom: 12px;
  opacity: 0.7;
  text-align: center;
}

/* =======================
   INPUTS
======================= */
input,
textarea {
  width: 100%;
  max-width: 520px;
  background: rgba(0, 0, 0, 0.4);
  border: 1px solid rgba(255, 211, 106, 0.25);
  padding: 14px 16px;
  color: #fff;
  border-radius: 10px;
  outline: none;
  text-align: center;
  font-size: 0.95rem;
}

/* =======================
   UPLOAD
======================= */
.btn-subir {
  background: #ffd36a;
  color: #120016;
  padding: 14px 32px;
  border-radius: 14px;
  cursor: pointer;
  font-weight: bold;
  font-size: 0.9rem;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.count {
  margin-top: 12px;
  font-size: 0.85rem;
  opacity: 0.85;
}

/* =======================
   IMÁGENES
======================= */
.lista-fotos {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(80px, 1fr));
  gap: 14px;
  width: 100%;
  max-width: 520px;
  margin-top: 28px;
}

.foto-item {
  position: relative;
  aspect-ratio: 1 / 1;
  border-radius: 12px;
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
  top: 6px;
  right: 6px;
  background: rgba(255, 82, 82, 0.95);
  border: none;
  color: white;
  width: 22px;
  height: 22px;
  border-radius: 50%;
  cursor: pointer;
  font-size: 0.7rem;
}

/* =======================
   BOTÓN FINAL
======================= */
.acciones-finales {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 18px;
  margin-top: 10px;
}

.btn-principal {
  background: transparent;
  color: #ffd36a;
  padding: 16px 42px;
  border: 1px solid #ffd36a;
  border-radius: 50px;
  font-weight: bold;
  font-size: 1rem;
  cursor: pointer;
}

.btn-principal:disabled {
  opacity: 0.45;
  cursor: not-allowed;
}

.btn-principal:hover:not(:disabled) {
  background: #ffd36a;
  color: #120016;
}

/* =======================
   MENSAJES
======================= */
.status {
  text-align: center;
  font-size: 0.9rem;
}

.success {
  color: #81c784;
}

.error {
  color: #ff8a80;
}

/* =======================
   RESPONSIVE
======================= */
@media (max-width: 768px) {

  .admin-container {
    padding: 100px 20px 60px;
  }

  .admin-header h1 {
    font-size: 2rem;
    letter-spacing: 3px;
  }

  .admin-card {
    padding: 30px 20px;
  }

  input,
  textarea {
    font-size: 0.9rem;
    padding: 12px 14px;
  }

  .btn-principal {
    width: 100%;
    max-width: 360px;
    padding: 14px;
  }

  .logout-top {
    top: 16px;
    right: 16px;
    padding: 6px 12px;
    font-size: 0.65rem;
  }
}
</style>
