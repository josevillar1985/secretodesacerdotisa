<template>
  <section class="admin">
    <h1>Panel de administración</h1>

    <p>
      Bienvenido/a.
      Desde aquí podrás gestionar el contenido.
    </p>

    <button @click="logout">
      Cerrar sesión
    </button>
  </section>
</template>

<script>
export default {
  name: 'AdminView',
  methods: {
    logout() {
      localStorage.removeItem('admin')
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
.admin {
  min-height: 100vh;
  padding: 160px 10%;
  background: linear-gradient(#120016, #0b000d);
  color: var(--color-gold);
  text-align: center;
}

.admin h1 {
  font-family: var(--font-title);
  letter-spacing: 4px;
  margin-bottom: 20px;
}

.admin button {
  margin-top: 30px;
  padding: 12px 30px;
  background: rgba(255, 211, 106, 0.2);
  border: none;
  color: var(--color-gold);
  cursor: pointer;
}

</style>
