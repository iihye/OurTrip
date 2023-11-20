<script setup>
import { RouterLink, RouterView } from 'vue-router';
import Nav from './components/common/Nav.vue';
import Footer from './components/common/Footer.vue';
</script>

<template>
  <div id="wrapper">
    <Nav />
    <br />
    <br />
    <br />
    <br />
    <br />
    <div>
      <RouterView />
    </div>
  </div>

  <!-- <footer id="footer">
    <h1>footer</h1>
  </footer> -->
  <Footer id="footer"></Footer>
</template>

<style scoped>
#wrapper {
  height: auto;
  min-height: 100%;
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
