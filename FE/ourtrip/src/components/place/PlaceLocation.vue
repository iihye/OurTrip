<script setup>
import { onMounted, ref } from 'vue';
import { useRouter, onBeforeRouteLeave } from 'vue-router';
import axios from 'axios';
import { usePlaceStore } from '@/stores/place';
import VKakaoMap from '../common/VKakaoMap.vue';
import { storeToRefs } from 'pinia';

const router = useRouter();
const placeStore = usePlaceStore();
const { VITE_APP_KAKAO_API_URI, VITE_APP_KAKAO_REST_KEY } = import.meta.env;

const { listInfo } = storeToRefs(placeStore);

const searchList = ref([]);
const selectList = ref([]);
const selectPlace = ref({});

// onBeforeRouteLeave((to, from) => {
//   console.log(to);
//   const answer = window.confirm('Do you really want to leave? you have unsaved changes!');
//   // cancel the navigation and stay on the same page
//   if (!answer) return false;
// });

const searchHandler = async (event) => {
  const url = VITE_APP_KAKAO_API_URI;
  const headers = {
    Authorization: `KakaoAK ${VITE_APP_KAKAO_REST_KEY}`,
  };
  const params = { query: event.target.value };

  const response = await axios.get(url, { headers: headers, params: params });
  searchList.value = response.data.documents;
};

const selectHandler = (item) => {
  console.log('com');
  const isNotExist = !selectList.value.includes(item);
  if (isNotExist) selectList.value = [...selectList.value, item];
};

const cancelHandler = (itemId) => {
  selectList.value = selectList.value.filter((item) => item.id !== itemId);
};
const nextButtonHandler = () => {
  listInfo.value = { ...listInfo.value, list_places: selectList.value };
  router.push({ name: 'place-title' });
};

onMounted(() => {
  if (listInfo.value.list_places !== undefined) {
    selectList.value = listInfo.value.list_places;
  }
});
</script>

<template>
  <body>
    <!-- <div
      id="full_bg"
      style="
        background-image: url('https://cdn.music-flo.com/image/v2/album/806/970/09/04/409970806_63647474_s.jpg?1667527798269/dims/resize/500/quality/90');
      "
    ></div> -->
    <form @submit.prevent="">
      <h3>가고 싶은 장소를 검색해주세요.</h3>
      <p>-> 검색 결과에서 원하는 장소를 선택해주세요</p>
      <div id="searchBox">
        <svg
          data-v-2885a6ec=""
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M14.3392 14.0667L18.2727 18.0001M15.75 10.875C15.75 13.5674 13.5674 15.75 10.875 15.75C8.18261 15.75 6 13.5674 6 10.875C6 8.18261 8.18261 6 10.875 6C13.5674 6 15.75 8.18261 15.75 10.875Z"
            stroke="white"
            stroke-opacity="0.3"
          ></path>
        </svg>
        <input placeholder="가고 싶은 장소를 검색해주세요." @input="searchHandler" />
      </div>
    </form>
    <div id="list_wrap">
      <div id="list_group">
        <div v-for="item in searchList" :key="item.id">
          <div id="list_item">
            <div>{{ item.place_name }}</div>
            <div>{{ item.address_name }}</div>
            <button @click="selectHandler(item)">선택</button>
          </div>

          <br />
        </div>
      </div>
    </div>

    <VKakaoMap id="map" :stations="selectList" :selectStation="selectPlace"></VKakaoMap>
    <h3>선택한 장소 목록</h3>
    <div>
      <div v-for="item in selectList" :key="item.id">
        <div>{{ item.place_name }}</div>
        <div>{{ item.address_name }}</div>
        <button @click="cancelHandler(item.id)">취소</button>
        <br />
        <br />
      </div>
    </div>

    <button @click="nextButtonHandler">다음</button>
  </body>
</template>

<style scoped>
body {
  height: 100%;
}
h3 {
  width: 100%;
  margin-bottom: 8px;
  font-size: 22px;
  font-weight: 600;
  line-height: 25px;
  color: #fff;
}
#searchBox {
  position: relative;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  width: 240px;
  padding: 6px 10px;
  background: hsla(0, 0%, 100%, 0.05);
  border-radius: 1000px;
}
input {
  width: 195px;
  font-size: 12px;
  color: #fff;
  background: none;
  outline: none;
}
#map {
  position: relative;
  width: 720px;
  height: 720px;
  margin-bottom: 25px;
  background-repeat: no-repeat;
  background-repeat-x: no-repeat;
  background-repeat-y: no-repeat;
  background-size: 100% 100%;

  border-top-left-radius: 30px;
  border-top-right-radius: 30px;
  border-bottom-right-radius: 30px;
  border-bottom-left-radius: 30px;

  outline-color: initial;
  outline-style: none;
  outline-width: initial;
}

#list_item {
  width: 340px;
  padding: 5px 0;
  margin-left: 16px;
  /* cursor: pointer; */
  padding: 9px 20px 9px 0;
}

/* #full_bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  -webkit-filter: blur(100px);
  filter: blur(100px);
  background-repeat: no-repeat;
  background-position: 50%;
  background-size: 100%;
  opacity: 0.2;
  will-change: transform;
  z-index: -200;
} */

#list_group {
  margin: 10px 13px 10px 0;
  background: hsla(0, 0%, 90%, 0.2);
  border-radius: 5px;
}

#list_wrap {
  width: 605px;
  margin-top: 20px;
}
</style>
