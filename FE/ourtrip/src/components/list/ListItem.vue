<script setup>
defineProps({ list: Object });
</script>

<template>
  <div>
    <router-link :to="{ name: 'list-detail', params: { listno: list.listNo } }">
      {{ list.listName }}
    </router-link>
    <div>{{ list.listNo }}</div>
  </div>
</template>

<style scoped></style>
