<template>
  <div class="list-admin">
    <h2 class="titulo-principal">Galerías</h2>

    <div v-if="cargando" class="estado">Cargando galerías…</div>
    <div v-else-if="galerias.length === 0" class="estado">
      No hay galerías
    </div>

    <!-- LISTA EN COLUMNA -->
    <div class="lista">
      <div v-for="galeria in galerias" :key="galeria.id" class="celda">
        <h3 class="titulo-galeria">{{ galeria.titulo }}</h3>
        <p class="fecha">{{ galeria.fecha }}</p>

        <div class="fotos">
          <img
            v-for="(foto, i) in galeria.fotos.slice(0, 6)"
            :key="i"
            :src="foto.imagen"
          />
        </div>

        <span v-if="galeria.fotos.length > 6" class="extra">
          +{{ galeria.fotos.length - 6 }} imágenes
        </span>

        <div class="acciones">
          <button class="btn editar" @click="abrirEditar(galeria)">
            Editar
          </button>
          <button class="btn borrar" @click="abrirEliminar(galeria)">
            Eliminar
          </button>
        </div>
      </div>
    </div>

    <!-- ================= ELIMINAR ================= -->
    <div v-if="dialogEliminar" class="overlay">
      <div class="dialog">
        <p>¿Eliminar <b>{{ galeriaSeleccionada.titulo }}</b>?</p>

        <div class="dialog-acciones">
          <button class="btn cancelar" @click="cerrarDialogos">
            Cancelar
          </button>
          <button class="btn borrar" @click="confirmarEliminar">
            Eliminar
          </button>
        </div>
      </div>
    </div>

    <!-- ================= EDITAR ================= -->
    <div v-if="dialogEditar" class="overlay">
      <div class="dialog">
        <h3>Editar galería</h3>

        <input v-model="galeriaEditada.titulo" placeholder="Título" />
        <input v-model="galeriaEditada.fecha" placeholder="Fecha" />
        <textarea
          v-model="galeriaEditada.descripcion"
          placeholder="Descripción"
        ></textarea>

        <!-- FOTOS -->
        <div class="fotos-editar">
          <div class="fotos">
            <div
              v-for="(foto, i) in galeriaEditada.fotos"
              :key="i"
              class="foto-edit"
            >
              <img :src="foto.imagen" />
              <button class="foto-remove" @click="eliminarFotoEditada(i)">
                ✕
              </button>
            </div>
          </div>

          <label class="btn-subir-editar">
            Añadir fotos
            <input
              type="file"
              multiple
              accept="image/*"
              hidden
              @change="subirFotosEditar"
            />
          </label>
        </div>

        <div class="dialog-acciones">
          <button class="btn cancelar" @click="cerrarDialogos">
            Cancelar
          </button>
          <button class="btn editar" @click="guardarEdicion">
            Guardar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const CLOUD_NAME = 'deknkhbmr'
const UPLOAD_PRESET = 'galerias_unsigned'

export default {
  name: 'ListAdmin',

  data() {
    return {
      galerias: [],
      cargando: true,
      dialogEliminar: false,
      dialogEditar: false,
      galeriaSeleccionada: null,
      galeriaEditada: null
    }
  },

  mounted() {
    this.cargarGalerias()
  },

  methods: {
    async cargarGalerias() {
      this.cargando = true
      const res = await fetch(
        'https://api-secretodesacerdotisa.josevillar.com/galerias'
      )
      const data = await res.json()
      this.galerias = data.reverse()
      this.cargando = false
    },

    abrirEliminar(g) {
      this.galeriaSeleccionada = g
      this.dialogEliminar = true
    },

    abrirEditar(g) {
      this.galeriaEditada = JSON.parse(JSON.stringify(g))
      this.dialogEditar = true
    },

    cerrarDialogos() {
      this.dialogEliminar = false
      this.dialogEditar = false
      this.galeriaSeleccionada = null
      this.galeriaEditada = null
    },

    async confirmarEliminar() {
      await fetch(
        `https://api-secretodesacerdotisa.josevillar.com/galerias/${this.galeriaSeleccionada.id}`,
        { method: 'DELETE' }
      )
      this.galerias = this.galerias.filter(
        g => g.id !== this.galeriaSeleccionada.id
      )
      this.cerrarDialogos()
    },

    async subirFotosEditar(event) {
      const files = event.target.files
      if (!files.length) return

      for (const file of files) {
        const formData = new FormData()
        formData.append('file', file)
        formData.append('upload_preset', UPLOAD_PRESET)

        const res = await fetch(
          `https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`,
          { method: 'POST', body: formData }
        )

        const data = await res.json()
        if (data.secure_url) {
          this.galeriaEditada.fotos.push({ imagen: data.secure_url })
        }
      }
    },

    eliminarFotoEditada(index) {
      this.galeriaEditada.fotos.splice(index, 1)
    },

    async guardarEdicion() {
      await fetch(
        `https://api-secretodesacerdotisa.josevillar.com/galerias/${this.galeriaEditada.id}`,
        {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(this.galeriaEditada)
        }
      )

      const i = this.galerias.findIndex(
        g => g.id === this.galeriaEditada.id
      )
      this.galerias.splice(i, 1, this.galeriaEditada)

      this.cerrarDialogos()
    }
  }
}
</script>

<style scoped>
.list-admin {
  margin-top: 80px;
  max-width: 960px;
  margin-left: auto;
  margin-right: auto;
}

.titulo-principal {
  text-align: center;
  color: #ffd36a;
  letter-spacing: 4px;
  margin-bottom: 50px;
  font-size: 2rem;
}

.estado {
  text-align: center;
  opacity: 0.7;
  font-size: 1rem;
}

/* ===== LISTA EN COLUMNA (UNA ENCIMA DE OTRA) ===== */
.grid {
  display: flex;
  flex-direction: column;
  gap: 40px;
}

/* ===== TARJETA GRANDE ===== */
.celda {
  background: rgba(255, 211, 106, 0.06);
  border: 1px solid rgba(255, 211, 106, 0.25);
  border-radius: 26px;
  padding: 40px;
  text-align: center;
}

/* ===== TEXTO ===== */
.titulo-galeria {
  font-size: 1.6rem;
  margin-bottom: 10px;
}

.fecha {
  font-size: 1rem;
  opacity: 0.7;
  margin-bottom: 22px;
}

/* ===== FOTOS ===== */
.fotos {
  display: flex;
  justify-content: center;
  gap: 14px;
  flex-wrap: wrap;
  margin-bottom: 10px;
}

.fotos img {
  width: 90px;
  height: 90px;
  border-radius: 16px;
  object-fit: cover;
  border: 1px solid rgba(255, 211, 106, 0.5);
}

.extra {
  display: block;
  margin-top: 6px;
  font-size: 0.9rem;
  opacity: 0.7;
}

/* ===== BOTONES ===== */
.acciones {
  display: flex;
  justify-content: center;
  gap: 24px;
  margin-top: 26px;
}

.btn {
  padding: 12px 34px;
  border-radius: 40px;
  font-size: 0.85rem;
  letter-spacing: 2px;
  cursor: pointer;
  border: none;
}

.editar {
  background: #ffd36a;
  color: #120016;
}

.borrar {
  background: transparent;
  color: #ff8a80;
  border: 1px solid #ff8a80;
}

.cancelar {
  background: transparent;
  border: 1px solid #ffd36a;
  color: #ffd36a;
}

/* ===== OVERLAY ===== */
.overlay {
  position: fixed;
  inset: 0;
  z-index: 9999;
  background: rgba(0, 0, 0, 0.85);
  display: flex;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  padding: 80px 0;
}

/* ===== DIALOG ===== */
.dialog {
  width: 460px;
  background: #120016;
  border-radius: 26px;
  border: 1px solid rgba(255, 211, 106, 0.35);
  padding: 32px;
}

.dialog h3 {
  text-align: center;
  margin-bottom: 20px;
}

.dialog input,
.dialog textarea {
  width: 100%;
  margin-bottom: 14px;
  padding: 14px;
  font-size: 0.95rem;
  background: rgba(0, 0, 0, 0.4);
  border: 1px solid rgba(255, 211, 106, 0.3);
  color: white;
  border-radius: 10px;
}

/* ===== EDITAR FOTOS ===== */
.fotos-editar {
  text-align: center;
  margin: 20px 0;
}

.foto-edit {
  position: relative;
}

.foto-edit img {
  width: 80px;
  height: 80px;
  border-radius: 16px;
  object-fit: cover;
}

.foto-edit button {
  position: absolute;
  top: -8px;
  right: -8px;
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background: #ff5252;
  color: white;
  border: none;
  cursor: pointer;
  font-size: 0.75rem;
}

/* ===== BOTÓN SUBIR ===== */
.btn-subir-editar {
  display: inline-block;
  margin-top: 18px;
  font-size: 0.8rem;
  letter-spacing: 2px;
  padding: 10px 26px;
  border-radius: 30px;
  background: transparent;
  border: 1px dashed #ffd36a;
  color: #ffd36a;
  cursor: pointer;
}

/* ===== ACCIONES DIALOG ===== */
.dialog-acciones {
  display: flex;
  justify-content: space-between;
  margin-top: 25px;
}

</style>
