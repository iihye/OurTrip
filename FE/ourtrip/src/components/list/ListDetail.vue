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
  <h1>listname</h1>

  <template v-for="list in place" :key="list.placeNo">
    <li>{{ list.placeUrl }}</li>
    <li>{{ list.placeName }}</li>
    <li>{{ list.placePhone }}</li>
  </template>
</template>

<style scoped>
h1 {
  text-align: center;
  font-size: 36px;
  padding: 30px;
}
</style>
