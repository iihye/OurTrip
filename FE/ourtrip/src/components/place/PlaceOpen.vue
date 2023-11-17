<script setup>
import { usePlaceStore } from "@/stores/place";
import { storeToRefs } from "pinia";

const placeStore = usePlaceStore();
const { listInfo } = storeToRefs(placeStore);
</script>

<template>
  <div>place Auth Open</div>
  {{ listInfo }}
</template>

<style scoped></style>
