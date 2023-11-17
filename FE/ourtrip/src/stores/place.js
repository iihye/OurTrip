import { ref } from "vue";
import { defineStore } from "pinia";

export const usePlaceStore = defineStore("placeStore", () => {
  const placeList = ref([]);
  return { placeList };
});
