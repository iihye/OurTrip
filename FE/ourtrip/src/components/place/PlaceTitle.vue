<script setup>
import { usePlaceStore } from "@/stores/place";
import { storeToRefs } from "pinia";

const placeStore = usePlaceStore();
const { placeList } = storeToRefs(placeStore);
</script>

<template>
  <div>place Title</div>
  <div v-for="place in placeList" :key="place.id">{{ place.place_name }}</div>
</template>

<style scoped></style>
