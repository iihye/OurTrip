import { ref } from 'vue';
import { defineStore } from 'pinia';

export const usePlaceStore = defineStore('placeStore', () => {
  const listInfo = ref({});
  const saveStatus = ref('');
  return { listInfo, saveStatus };
});
