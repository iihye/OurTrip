import { ref } from "vue";
import { defineStore } from "pinia";

export const usePlaceStore = defineStore("placeStore", () => {
  const listInfo = ref({});
  return { listInfo };
});
