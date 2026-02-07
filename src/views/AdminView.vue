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
            <input v-model="nuevaGaleria.titulo" type="text" placeholder="Ej: Ritual de Luna Llena" class="text-center" />
          </div>

          <div class="input-group">
            <label>Fecha del Encuentro</label>
            <input v-model="nuevaGaleria.fecha" type="text" placeholder="Ej: 21 Marzo 2026" class="text-center" />
          </div>

          <div class="input-group">
            <label>Descripción del Evento</label>
            <textarea v-model="nuevaGaleria.descripcion" rows="3" placeholder="Describe la energía vivida..." class="text-center"></textarea>
          </div>
        </div>

        <div class="admin-card flex-center">
          <label class="btn-subir">
            <v-icon class="mr-2" color="#120016">mdi-camera-plus</v-icon>
            Seleccionar Fotos del Dispositivo
            <input type="file" multiple accept="image/*" @change="manejarSubida" hidden />
          </label>
          
          <p class="count" v-if="nuevaGaleria.fotos.length > 0">
            {{ nuevaGaleria.fotos.length }} imágenes preparadas
          </p>

          <div class="lista-fotos" v-if="nuevaGaleria.fotos.length > 0">
            <div v-for="(f, index) in nuevaGaleria.fotos" :key="index" class="foto-item">
              <img :src="f.imagen" />
              <button class="remove-btn" @click="nuevaGaleria.fotos.splice(index, 1)">✕</button>
            </div>
          </div>
        </div>

        <div class="acciones-finales">
          <button @click="guardarTodo" class="btn-principal" :disabled="cargando || nuevaGaleria.fotos.length === 0">
            {{ cargando ? 'Elevando plegaria...' : 'Manifestar en el Blog' }}
          </button>
          <transition name="fade">
            <p v-if="mensaje" :class="['status', mensaje.tipo]">{{ mensaje.texto }}</p>
          </transition>
        </div>
      </div>
    </section>

    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from '@/components/FooterComponent.vue'
import { EventBus } from '@/eventBus'

export default {
  name: 'AdminPanel',
  components: { FooterComponent },
  data() {
    return {
      cargando: false,
      mensaje: null,
      nuevaGaleria: { titulo: '', descripcion: '', fecha: '', fotos: [] }
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('admin');
      EventBus.$emit('admin-logout');
      this.$router.push('/');
    },
    
    async manejarSubida(event) {
      const archivos = event.target.files;
      if (!archivos.length) return;

      for (let archivo of archivos) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.nuevaGaleria.fotos.push({
            imagen: e.target.result,
            titulo: archivo.name,
            descripcion: '',
            fecha: this.nuevaGaleria.fecha
          });
        };
        reader.readAsDataURL(archivo);
      }
    },

    async guardarTodo() {
      if (!this.nuevaGaleria.titulo) {
        this.mostrarMensaje('El evento necesita un nombre', 'error');
        return;
      }
      this.cargando = true;
      try {
        const res = await fetch('https://api-secretodesacerdotisa.josevillar.com/api/galerias/crear', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(this.nuevaGaleria)
        });

        // Error 413: El archivo pesa demasiado
        if (res.status === 413) {
          throw new Error('Las fotos pesan demasiado para el servidor');
        }

        if (!res.ok) {
          // Intentamos obtener el mensaje de error del backend
          let errorMsg = 'Error en el servidor';
          try {
            const errorData = await res.json();
            errorMsg = errorData.message || errorMsg;
          } catch (e) { /* no JSON */ }
          throw new Error(errorMsg);
        }

        this.mostrarMensaje('El evento ha sido publicado con éxito', 'success');
        this.nuevaGaleria = { titulo: '', descripcion: '', fecha: '', fotos: [] };
      } catch (e) {
        this.mostrarMensaje(`Error: ${e.message}`, 'error');
        console.error('Detalle del error:', e);
      } finally { 
        this.cargando = false; 
      }
    },

    mostrarMensaje(texto, tipo) {
      this.mensaje = { texto, tipo };
      setTimeout(() => this.mensaje = null, 5000);
    }
  }
}
</script>

<style scoped>
.admin-page { background: #120016; min-height: 100vh; }
.admin-container { padding: 120px 5% 80px; color: #ffd36a; max-width: 800px; margin: 0 auto; display: flex; flex-direction: column; align-items: center; position: relative; }

.logout-top { position: absolute; top: 40px; right: 5%; background: rgba(255, 138, 128, 0.1); border: 1px solid rgba(255, 138, 128, 0.4); color: #ff8a80; padding: 8px 16px; border-radius: 4px; font-size: 0.75rem; cursor: pointer; display: flex; align-items: center; transition: all 0.3s; }
.logout-top:hover { background: rgba(255, 138, 128, 0.2); }

.admin-header { text-align: center; margin-bottom: 50px; }
.admin-header h1 { font-family: serif; font-size: 2.8rem; letter-spacing: 4px; margin-bottom: 10px; }

.form-wrapper { width: 100%; display: flex; flex-direction: column; gap: 25px; }
.admin-card { background: rgba(255, 211, 106, 0.03); border: 1px solid rgba(255, 211, 106, 0.1); padding: 40px; border-radius: 20px; display: flex; flex-direction: column; align-items: center; }

.flex-center { align-items: center; text-align: center; }
.input-group { width: 100%; margin-bottom: 25px; display: flex; flex-direction: column; align-items: center; }
.text-center { text-align: center; }

label { font-size: 0.75rem; text-transform: uppercase; letter-spacing: 3px; margin-bottom: 12px; opacity: 0.7; }

input, textarea { width: 100%; max-width: 500px; background: rgba(0, 0, 0, 0.4); border: 1px solid rgba(255, 211, 106, 0.2); padding: 15px; color: #fff; border-radius: 8px; outline: none; transition: border 0.3s; }
input:focus { border-color: #ffd36a; }

.btn-subir { background: #ffd36a; color: #120016; padding: 15px 30px; border-radius: 10px; cursor: pointer; font-weight: bold; font-size: 0.9rem; transition: transform 0.2s; margin-top: 10px; display: flex; align-items: center; }
.btn-subir:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(255, 211, 106, 0.2); }

.count { margin-top: 15px; font-size: 0.85rem; letter-spacing: 1px; }

.lista-fotos { display: flex; flex-wrap: wrap; justify-content: center; gap: 15px; margin-top: 30px; }
.foto-item { position: relative; width: 100px; height: 100px; border-radius: 10px; overflow: hidden; border: 2px solid rgba(255, 211, 106, 0.3); }
.foto-item img { width: 100%; height: 100%; object-fit: cover; }
.remove-btn { position: absolute; top: 0; right: 0; background: #ff5252; border: none; color: white; width: 25px; height: 25px; cursor: pointer; font-size: 14px; display: flex; align-items: center; justify-content: center; }

.acciones-finales { text-align: center; padding: 20px 0; }
.btn-principal { background: transparent; color: #ffd36a; padding: 18px 60px; border: 1px solid #ffd36a; border-radius: 50px; font-weight: bold; font-size: 1.2rem; cursor: pointer; transition: all 0.4s; letter-spacing: 2px; }
.btn-principal:hover:not(:disabled) { background: #ffd36a; color: #120016; box-shadow: 0 0 30px rgba(255, 211, 106, 0.3); }
.btn-principal:disabled { opacity: 0.3; cursor: not-allowed; }

.status { margin-top: 25px; font-size: 1rem; letter-spacing: 1px; }
.success { color: #81c784; }
.error { color: #ff8a80; border: 1px solid #ff8a80; padding: 10px; border-radius: 8px; }

.fade-enter-active, .fade-leave-active { transition: opacity 0.5s; }
.fade-enter, .fade-leave-to { opacity: 0; }

@media (max-width: 600px) { .admin-header h1 { font-size: 2rem; } .btn-principal { width: 100%; padding: 15px; } .logout-top { top: 20px; right: 5%; } }
</style>