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
  // console.log(selectImageUrl.value);
};

const nextButtonHandler = () => {
  listInfo.value = { ...listInfo.value, list_img: selectImageUrl.value };
  // console.log(listInfo.value);
  router.push({ name: "place-open" });
};
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

  <!--logo-->
  <h1>PLACELIST의 커버 이미지를 골라주세요</h1>

  <!--input-->
  <form class="form-container" @submit.prevent="" @submit="searchHandler">
    <div class="text-div">
      <button class="btn" size="large" variant="flat" rounded="xl">
        <font-awesome-icon
          :icon="['fas', 'magnifying-glass']"
          size="2xl"
          style="color: #646464"
        />
      </button>
      <input
        class="text-input"
        type="text"
        v-model="searchKeyword"
        variant="underlined"
        style="font-size: 1.5rem"
        placeholder="키워드 입력"
        @blur="searchHandler"
        message="키워드 입력 후 엔터를 눌러주세요"
        required
      />
      <span class="text-span"></span>
    </div>
  </form>

  <!--picture list-->
  <div class="list-container">
    <div v-for="item in searchList" :key="item.id">
      <img
        :src="item.urls.thumb"
        :alt="item.alt_description"
        @click="selectHandler(item.urls.thumb)"
      />
    </div>
  </div>

  <!--button-->
  <container class="btn-container">
    <div class="btn-handler">
      <v-btn
        class="btn"
        size="large"
        variant="flat"
        rounded="xl"
        @click="nextButtonHandler"
      >
        다음으로
      </v-btn>
    </div>
  </container>
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
  background-color: #7c7c7c;
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
  background-color: #3182f6;
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
  content: "";
  position: relative;
  top: 1.5rem;
  left: 50%;
  height: 2px;
  background-color: #e0e0e0;
  order: -1;
}
.c-stepper__item2::after {
  content: "";
  position: relative;
  top: 1.5rem;
  left: 50%;
  height: 2px;
  background-color: #e0e0e0;
  order: -1;
}
.c-stepper__item3::after {
  content: "";
  position: relative;
  top: 1.5rem;
  left: 50%;
  height: 2px;
  background-color: #e0e0e0;
  order: -1;
}
.form-container {
  display: flex;
  padding: 4rem 4rem 0 4rem;
}
.text-div {
  display: flex;
  align-items: center;
  width: 200px;
}
.text-input {
  font-size: 16px;
  color: #222222;
  width: 200px;
  border: none;
  border-bottom: solid #aaaaaa 1px;
  padding-bottom: 10px;
  padding-left: 10px;
  position: relative;
  background: none;
  z-index: 5;
}
.text-input::placeholder {
  color: #aaaaaa;
}
.text-input:focus {
  outline: none;
}
.text-span {
  display: block;
  position: absolute;
  bottom: 0;
  left: 0%; /* right로만 바꿔주면 오 - 왼 */
  background-color: #666;
  width: 0;
  height: 2px;
  border-radius: 2px;
  transition: 0.5s;
}
.text-input:focus ~ .text-span,
.text-input:valid ~ .text-span {
  width: 100%;
}
.list-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin-left: 20%;
  margin-right: 20%;
  margin-top: 4rem;
}
.list-container img {
  width: 240px;
  height: 180px;
  max-width: 100%;
  object-fit: cover;
  border-radius: 5px;
}
.btn-container {
  position: absolute;
  right: 0px;
  bottom: 0px;
  padding: 3rem;
}
.btn-handler {
  margin-left: auto;
  margin-right: 2rem;
}
.btn {
  background-color: #3182f6;
  color: white;
}
.btn:hover {
  background-color: #1b64da;
  color: white;
}
</style>
