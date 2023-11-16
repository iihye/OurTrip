<script setup>
import { ref } from "vue";
import axios from "axios";
const { VITE_APP_KAKAO_API_URI, VITE_APP_KAKAO_REST_KEY } = import.meta.env;

const searchKeyword = ref("");
const searchList = ref([]);
const searchHandeler = async () => {
  const url = VITE_APP_KAKAO_API_URI;
  const headers = {
    Authorization: `KakaoAK ${VITE_APP_KAKAO_REST_KEY}`,
  };
  const params = { query: searchKeyword.value };

  const response = await axios.get(url, { headers: headers, params: params });
  searchList.value = response.data.documents;
};
</script>

<template>
  <div>place location</div>
  <form action="" @submit.prevent="" @submit="searchHandeler">
    <input v-model="searchKeyword" />
    <button>검색</button>
  </form>
  <ul>
    <div v-for="item in searchList" :key="item.id">
      <li>{{ item.place_name }}</li>
      <li>{{ item.address_name }}</li>
      <br />
    </div>
  </ul>
</template>

<style scoped></style>
