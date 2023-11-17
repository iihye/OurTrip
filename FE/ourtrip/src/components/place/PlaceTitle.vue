<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { usePlaceStore } from "@/stores/place";
import { storeToRefs } from "pinia";

const router = useRouter();
const placeStore = usePlaceStore();
const { listInfo } = storeToRefs(placeStore);

const title = ref("");
const nextButtonHandler = () => {
  listInfo.value = { ...listInfo.value, list_name: title.value };
  router.push({ name: "place-cover" });
};
</script>

<template>
  <div>place Title</div>
  <div v-for="place in listInfo.places" :key="place.id">
    {{ place.place_name }}
  </div>

  <form>
    <input type="text" v-model="title" />
  </form>

  <button @click="nextButtonHandler">다음</button>
</template>

<style scoped></style>
