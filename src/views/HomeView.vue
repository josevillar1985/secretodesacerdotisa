<template>
  <div>
    <HeroComponent />

    <SeccionLecturas />

    <SeccionRitual />

    <SeccionProceso />

    <FooterComponent />
  </div>

</template>

<script>
import FooterComponent from '@/components/FooterComponent.vue';
import HeroComponent from '@/components/HeroComponent.vue'
import SeccionLecturas from '@/components/SeccionLecturas.vue';
import SeccionProceso from '@/components/SeccionProceso.vue';
import SeccionRitual from '@/components/SeccionRitual.vue';

export default {
  components: { HeroComponent, SeccionLecturas, SeccionRitual,SeccionProceso, FooterComponent },
}
</script>
