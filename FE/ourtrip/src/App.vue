<script setup>
import { RouterLink, RouterView } from 'vue-router';
import Nav from './components/common/Nav.vue';

</script>

<template>
  <div id="wrapper">
    <Nav />
    <RouterView />
  </div>
  
  
  <!-- <footer id="footer">
    <h1>footer</h1>
  </footer> -->
  <!-- <Footer id="footer"></Footer> -->
</template>

<style scoped>
#app {
  min-height: 100%;
  position: relative;
  padding-bottom: 170px;
}
</style>
