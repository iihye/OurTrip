<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { usePlaceStore } from "@/stores/place";
import axios from "axios";
import { storeToRefs } from "pinia";

const router = useRouter();
const placeStore = usePlaceStore();
const { VITE_APP_UNSPLASH_API_URI, VITE_APP_UNSPLASH_KEY } = import.meta.env;

const { listInfo } = storeToRefs(placeStore);
const searchKeyword = ref("");
const searchList = ref([]);
const selectImageUrl = ref("");

const searchHandler = async () => {
  const url = VITE_APP_UNSPLASH_API_URI;
  const headers = {};
  const params = {
    query: searchKeyword.value,
    client_id: VITE_APP_UNSPLASH_KEY,
  };

  const response = await axios.get(url, { headers: headers, params: params });
  searchList.value = response.data.results;
};

const selectHandler = (image_url) => {
  selectImageUrl.value = image_url;
  console.log(selectImageUrl.value);
};

const nextButtonHandler = () => {
  listInfo.value = { ...listInfo.value, list_img: selectImageUrl.value };
  console.log(listInfo.value);
  router.push({ name: "place-open" });
};
</script>

<template>
  <div>PlaceCover</div>
  <form @submit.prevent="" @submit="searchHandler">
    <input type="text" v-model="searchKeyword" />
    <button>대표 이미지 검색</button>
  </form>
  <div v-for="item in searchList" :key="item.id">
    <img
      :src="item.urls.thumb"
      :alt="item.alt_description"
      @click="selectHandler(item.urls.thumb)"
    />
  </div>
  <button @click="nextButtonHandler">다음</button>
</template>

<style scoped></style>
