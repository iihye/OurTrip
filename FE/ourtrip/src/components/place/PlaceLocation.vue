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
  <!--stepper-->
  <div>
    <ol class="c-stepper">
      <li class="c-stepper__item1">
        <h3 class="c-stepper__title">PLACE 고르기</h3>
      </li>
      <li class="c-stepper__item2">
        <h3 class="c-stepper__title">PLACELIST 이름 짓기</h3>
      </li>
      <li class="c-stepper__item3">
        <h3 class="c-stepper__title">커버 이미지 고르기</h3>
      </li>
      <li class="c-stepper__item4">
        <h3 class="c-stepper__title">공개 여부 결정하기</h3>
      </li>
    </ol>
  </div>

  <body>
    <div
      id="full_bg"
      style="
        background-image: url('https://cdn.music-flo.com/image/v2/album/806/970/09/04/409970806_63647474_s.jpg?1667527798269/dims/resize/500/quality/90');
      "
    ></div>
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
h1 {
  text-align: left;
  font-size: 36px;
  padding: 4rem 4rem 0 4rem;
}
h3 {
  margin-top: 1rem;
}
.c-stepper {
  display: flex;
  flex-wrap: wrap;
  margin-top: 2rem;
  margin: 2rem 30rem 2rem 30rem;
  padding: 0;
}
.c-stepper__item1 {
  flex: 1;
  display: flex;
  flex-direction: column;
  text-align: center;
}
.c-stepper__item2 {
  flex: 1;
  display: flex;
  flex-direction: column;
  text-align: center;
}
.c-stepper__item3 {
  flex: 1;
  display: flex;
  flex-direction: column;
  text-align: center;
}
.c-stepper__item4 {
  flex: 1;
  display: flex;
  flex-direction: column;
  text-align: center;
}
.c-stepper__item1:before {
  --size: 3rem;
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" height="1.25em" viewBox="0 0 384 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path style="fill:rgb(255,255,255)" d="M215.7 499.2C267 435 384 279.4 384 192C384 86 298 0 192 0S0 86 0 192c0 87.4 117 243 168.3 307.2c12.3 15.3 35.1 15.3 47.4 0zM192 128a64 64 0 1 1 0 128 64 64 0 1 1 0-128z"/></svg>');
  position: relative;
  z-index: 1;
  display: flex; /* Use flexbox */
  align-items: center; /* Center vertically */
  justify-content: center; /* Center horizontally */
  width: var(--size);
  height: var(--size);
  border-radius: 50%;
  margin: 0 auto;
  padding-top: 0.3rem;
  background-color: #3182f6;
}
.c-stepper__item2:before {
  --size: 3rem;
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" height="1.25em" viewBox="0 0 448 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path style="fill:rgb(255,255,255)" d="M254 52.8C249.3 40.3 237.3 32 224 32s-25.3 8.3-30 20.8L57.8 416H32c-17.7 0-32 14.3-32 32s14.3 32 32 32h96c17.7 0 32-14.3 32-32s-14.3-32-32-32h-1.8l18-48H303.8l18 48H320c-17.7 0-32 14.3-32 32s14.3 32 32 32h96c17.7 0 32-14.3 32-32s-14.3-32-32-32H390.2L254 52.8zM279.8 304H168.2L224 155.1 279.8 304z"/></svg>');
  position: relative;
  z-index: 1;
  display: flex; /* Use flexbox */
  align-items: center; /* Center vertically */
  justify-content: center; /* Center horizontally */
  width: var(--size);
  height: var(--size);
  border-radius: 50%;
  margin: 0 auto;
  padding-top: 0.3rem;
  background-color: #7c7c7c;
}
.c-stepper__item3:before {
  --size: 3rem;
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" height="1.25em" viewBox="0 0 512 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path style="fill:rgb(255,255,255)" d="M448 80c8.8 0 16 7.2 16 16V415.8l-5-6.5-136-176c-4.5-5.9-11.6-9.3-19-9.3s-14.4 3.4-19 9.3L202 340.7l-30.5-42.7C167 291.7 159.8 288 152 288s-15 3.7-19.5 10.1l-80 112L48 416.3l0-.3V96c0-8.8 7.2-16 16-16H448zM64 32C28.7 32 0 60.7 0 96V416c0 35.3 28.7 64 64 64H448c35.3 0 64-28.7 64-64V96c0-35.3-28.7-64-64-64H64zm80 192a48 48 0 1 0 0-96 48 48 0 1 0 0 96z"/></svg>');
  position: relative;
  z-index: 1;
  display: flex; /* Use flexbox */
  align-items: center; /* Center vertically */
  justify-content: center; /* Center horizontally */
  width: var(--size);
  height: var(--size);
  border-radius: 50%;
  margin: 0 auto;
  padding-top: 0.3rem;
  background-color: #7c7c7c;
}
.c-stepper__item4:before {
  --size: 3rem;
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" height="1.25em" viewBox="0 0 448 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path style="fill:rgb(255,255,255)" d="M144 144v48H304V144c0-44.2-35.8-80-80-80s-80 35.8-80 80zM80 192V144C80 64.5 144.5 0 224 0s144 64.5 144 144v48h16c35.3 0 64 28.7 64 64V448c0 35.3-28.7 64-64 64H64c-35.3 0-64-28.7-64-64V256c0-35.3 28.7-64 64-64H80z"/></svg>');
  position: relative;
  z-index: 1;
  display: flex; /* Use flexbox */
  align-items: center; /* Center vertically */
  justify-content: center; /* Center horizontally */
  width: var(--size);
  height: var(--size);
  border-radius: 50%;
  margin: 0 auto;
  padding-top: 0.3rem;
  background-color: #7c7c7c;
}
.c-stepper__item1::after {
  content: '';
  position: relative;
  top: 1.5rem;
  left: 50%;
  height: 2px;
  background-color: #e0e0e0;
  order: -1;
}
.c-stepper__item2::after {
  content: '';
  position: relative;
  top: 1.5rem;
  left: 50%;
  height: 2px;
  background-color: #e0e0e0;
  order: -1;
}
.c-stepper__item3::after {
  content: '';
  position: relative;
  top: 1.5rem;
  left: 50%;
  height: 2px;
  background-color: #e0e0e0;
  order: -1;
}
body {
  background-color: #333;
  color: #fff;
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

#full_bg {
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
}

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
