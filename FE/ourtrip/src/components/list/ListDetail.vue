<script setup>
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useRoute } from 'vue-router';
import { useListStore } from '@/stores/list';

const route = useRoute();
const listStore = useListStore();
const { detailRes } = storeToRefs(listStore);
const { detailList } = listStore;

const listno = ref(route.params.listno);

onMounted(() => {
  getList();
});

const place = ref({});

const getList = async () => {
  await detailList(listno.value);
  place.value = detailRes.value;
};
</script>

<template>
  <div>List detail</div>
  <span>{{ listno }}</span>
  <span>{{ place.placeName }}</span>

  <template v-for="list in place" :key="list.placeNo">
    <li>{{ list.placeNo }}</li>
    <li>{{ list.placeUrl }}</li>
    <li>{{ list.placeName }}</li>
    <li>{{ list.placePhone }}</li>
  </template>
</template>

<style scoped></style>
