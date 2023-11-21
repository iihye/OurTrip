<script setup>
import { onMounted, ref } from "vue";
import { useRouter, onBeforeRouteLeave } from "vue-router";
import axios from "axios";
import { usePlaceStore } from "@/stores/place";
import VKakaoMap from "../common/VKakaoMap.vue";
import { storeToRefs } from "pinia";

const router = useRouter();
const placeStore = usePlaceStore();
const { VITE_APP_KAKAO_API_URI, VITE_APP_KAKAO_REST_KEY } = import.meta.env;

const { listInfo } = storeToRefs(placeStore);
const searchKeyword = ref("");
const searchList = ref([]);
const selectList = ref([]);
const selectPlace = ref({});

// onBeforeRouteLeave((to, from) => {
//   console.log(to);
//   const answer = window.confirm('Do you really want to leave? you have unsaved changes!');
//   // cancel the navigation and stay on the same page
//   if (!answer) return false;
// });

const searchHandler = async () => {
  const url = VITE_APP_KAKAO_API_URI;
  const headers = {
    Authorization: `KakaoAK ${VITE_APP_KAKAO_REST_KEY}`,
  };
  const params = { query: searchKeyword.value };

  const response = await axios.get(url, { headers: headers, params: params });
  searchList.value = response.data.documents;
};

const selectHandler = (item) => {
  const isNotExist = !selectList.value.includes(item);
  if (isNotExist) selectList.value = [...selectList.value, item];
};

const cancelHandler = (itemId) => {
  selectList.value = selectList.value.filter((item) => item.id !== itemId);
};
const nextButtonHandler = () => {
  listInfo.value = { ...listInfo.value, list_places: selectList.value };
  router.push({ name: "place-title" });
};

onMounted(() => {
  if (listInfo.value.list_places !== undefined) {
    selectList.value = listInfo.value.list_places;
  }
});
</script>

<template>
  <div>place location</div>
  <form @submit.prevent="" @submit="searchHandler">
    <p>가고 싶은 장소를 검색해주세요.</p>
    <p>-> 검색 결과에서 원하는 장소를 선택해주세요</p>
    <input v-model="searchKeyword" />
    <button>검색</button>
  </form>
  <ul>
    <div v-for="item in searchList" :key="item.id" @click="selectHandler(item)">
      <li>{{ item.place_name }}</li>
      <li>{{ item.address_name }}</li>
      <br />
    </div>
  </ul>
  <VKakaoMap :stations="selectList" :selectStation="selectPlace"></VKakaoMap>
  <h3>선택한 장소 목록</h3>
  <ul>
    <div v-for="item in selectList" :key="item.id">
      <li>{{ item.place_name }}</li>
      <li>{{ item.address_name }}</li>
      <button @click="cancelHandler(item.id)">취소</button>
      <br />
      <br />
    </div>
  </ul>

  <button @click="nextButtonHandler">다음</button>
</template>

<style scoped></style>
