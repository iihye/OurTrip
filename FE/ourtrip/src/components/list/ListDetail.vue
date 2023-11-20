<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import { useListStore } from "@/stores/list";

const route = useRoute();
const router = useRouter();
const listStore = useListStore();
const { placeRes } = storeToRefs(listStore);
const { placeList } = listStore;
const { VITE_APP_SERVER_URI } = import.meta.env;

const listno = ref(route.params.listno);

onMounted(() => {
  getPlaceList();
});

const place = ref({});

const getPlaceList = async () => {
  await placeList(listno.value);
  console.log(placeRes.value);
  place.value = placeRes.value;
};

const deleteHandler = async (listNo) => {
  const url = `${VITE_APP_SERVER_URI}/list/delete/${listNo}`;
  const response = await axios.delete(url);
  if (response.status === 200) {
    router.push({ name: "list-my" });
  } else {
    alert("삭제 오류");
  }
};
</script>

<template>
  <h1>listname</h1>

  <template v-for="list in place" :key="list.placeNo">
    <li>{{ list.placeUrl }}</li>
    <li>{{ list.placeName }}</li>
    <li>{{ list.placePhone }}</li>
  </template>
  <button @click="deleteHandler(listno)">삭제</button>
</template>

<style scoped>
h1 {
  text-align: center;
  font-size: 36px;
  padding: 30px;
}
</style>
