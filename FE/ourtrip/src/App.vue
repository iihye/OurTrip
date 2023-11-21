<script setup>
import { RouterLink, RouterView } from 'vue-router';
import Nav from './components/common/Nav.vue';
import Footer from './components/common/Footer.vue';
</script>

<template>
  <div id="wrapper">
    <Nav />
    <div>
      <RouterView />
    </div>
  </div>

  <Footer></Footer>
</template>

<style scoped>
#wrapper {
  flex: 1;
  position: relative;
  padding-bottom: 150px;
}
Footer {
  height: 150px;
  position: relative;
  transform: translateY(0%);
  display: flex;
}
</style>
