<template>
  <div class="list-admin">
    <h2 class="titulo-principal">Galerías</h2>

    <div v-if="cargando" class="estado">Cargando galerías…</div>
    <div v-else-if="galerias.length === 0" class="estado">
      No hay galerías
    </div>

    <div class="grid">
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

    <!-- DIALOG ELIMINAR -->
    <div v-if="dialogEliminar" class="overlay">
      <div class="dialog">
        <p>
          ¿Eliminar <b>{{ galeriaSeleccionada.titulo }}</b>?
        </p>

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

    <!-- DIALOG EDITAR -->
    <div v-if="dialogEditar" class="overlay">
      <div class="dialog">
        <h3>Editar galería</h3>

        <input v-model="galeriaEditada.titulo" placeholder="Título" />
        <input v-model="galeriaEditada.fecha" placeholder="Fecha" />
        <textarea
          v-model="galeriaEditada.descripcion"
          placeholder="Descripción"
        ></textarea>

        <!-- FOTOS EDITAR -->
        <div class="fotos-editar">
          <div class="fotos">
            <div
              v-for="(foto, i) in galeriaEditada.fotos"
              :key="i"
              class="foto-edit"
            >
              <img :src="foto.imagen" />
              <button @click="eliminarFotoEditada(i)">✕</button>
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
      const res = await fetch(
        'https://api-secretodesacerdotisa.josevillar.com/galerias'
      )
      this.galerias = await res.json()
      this.cargando = false
    },

    abrirEliminar(g) {
      this.galeriaSeleccionada = g
      this.dialogEliminar = true
    },

    abrirEditar(g) {
      // copia profunda para no mutar directamente
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
          {
            method: 'POST',
            body: formData
          }
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
}

.titulo-principal {
  text-align: center;
  color: #ffd36a;
  letter-spacing: 4px;
  margin-bottom: 40px;
}

.estado {
  text-align: center;
  opacity: 0.7;
}

/* GRID */
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 30px;
}

/* CELDA */
.celda {
  background: rgba(255, 211, 106, 0.04);
  border: 1px solid rgba(255, 211, 106, 0.18);
  border-radius: 20px;
  padding: 26px;
  text-align: center;
}

.titulo-galeria {
  font-size: 1.05rem;
  margin-bottom: 6px;
}

.fecha {
  font-size: 0.8rem;
  opacity: 0.7;
  margin-bottom: 16px;
}

/* FOTOS */
.fotos {
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
}

.fotos img {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  object-fit: cover;
  border: 1px solid rgba(255, 211, 106, 0.45);
}

.extra {
  font-size: 0.75rem;
  opacity: 0.6;
  margin-top: 6px;
}

/* ACCIONES */
.acciones {
  display: flex;
  justify-content: center;
  gap: 14px;
  margin-top: 20px;
}

.btn {
  padding: 7px 20px;
  border-radius: 40px;
  font-size: 0.75rem;
  letter-spacing: 2px;
  border: none;
  cursor: pointer;
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

/* OVERLAY SCROLLABLE */
.overlay {
  position: fixed;
  inset: 0;
  z-index: 9999;
  background: rgba(0, 0, 0, 0.85);
  display: flex;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  padding: 60px 0;
}

/* DIALOG */
.dialog {
  width: 360px;
  background: #120016;
  border-radius: 22px;
  border: 1px solid rgba(255, 211, 106, 0.25);
  padding: 26px;
  margin-bottom: 60px;
}

.dialog input,
.dialog textarea {
  width: 100%;
  margin-bottom: 12px;
  padding: 10px;
  background: rgba(0, 0, 0, 0.4);
  border: 1px solid rgba(255, 211, 106, 0.25);
  color: white;
  border-radius: 8px;
}

/* EDITAR FOTOS */
.fotos-editar {
  text-align: center;
  margin: 15px 0;
}

.foto-edit {
  position: relative;
}

.foto-edit img {
  width: 58px;
  height: 58px;
  border-radius: 12px;
  object-fit: cover;
}

.foto-edit button {
  position: absolute;
  top: -6px;
  right: -6px;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background: #ff5252;
  color: white;
  border: none;
  font-size: 0.65rem;
  cursor: pointer;
}

.btn-subir-editar {
  display: inline-block;
  margin-top: 12px;
  font-size: 0.7rem;
  letter-spacing: 2px;
  padding: 6px 18px;
  border-radius: 30px;
  background: transparent;
  border: 1px dashed #ffd36a;
  color: #ffd36a;
  cursor: pointer;
}

.dialog-acciones {
  display: flex;
  justify-content: space-between;
}
</style>
