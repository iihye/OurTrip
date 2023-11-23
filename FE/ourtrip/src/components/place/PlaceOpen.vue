<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, onBeforeRouteLeave } from 'vue-router';
import { usePlaceStore } from '@/stores/place';
import axios from 'axios';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/user';

const router = useRouter();
const memberStore = useMemberStore();
const placeStore = usePlaceStore();
const { VITE_APP_SERVER_URI } = import.meta.env;

const { userInfo } = storeToRefs(memberStore);
const { getUserInfo } = memberStore;
const { listInfo } = storeToRefs(placeStore);
const selectIsOpen = ref(false);

onBeforeRouteLeave((to, from) => {
  console.log('to: ' + to.path);
  console.log('from: ' + from.path);
  if (to.path !== '/place/save' && to.path !== '/place/cover') {
    const answer = window.confirm('Do you really want to leave? you have unsaved changes!');
    // cancel the navigation and stay on the same page
    if (!answer) return false;
    listInfo.value = {};
  }
});

onMounted(() => {
  fetch();
});

const fetch = async () => {
  await getUserInfo(sessionStorage.getItem('accessToken'));
};

const registerPlace = async (listNo) => {
  const list_places = listInfo.value.list_places;
  const places = list_places.map((place_info) => {
    return {
      placeName: place_info.place_name,
      placeUrl: place_info.place_url,
      placeAddressName: place_info.address_name,
      placeRoadAddressName: place_info.road_address_name,
      placePhone: place_info.phone,
      placeX: place_info.x,
      placeY: place_info.y,
      placeId: place_info.id,
      listNo,
    };
  });
  const url = `${VITE_APP_SERVER_URI}/place/register`;
  const headers = {
    'Content-Type': 'application/json',
  };
  const data = places;
  await axios.post(url, data, headers);
};

const resetListInfo = () => {
  listInfo.value = {};
};

const openButtonHandler = () => {
  selectIsOpen.value = true;
};

const closeButtonHandler = () => {
  selectIsOpen.value = false;
};

const saveButtonHandler = async () => {
  const _registerList = async () => {
    const list_info = listInfo.value;
    const url = `${VITE_APP_SERVER_URI}/list/register`;
    const headers = {
      'Content-Type': 'application/json',
    };
    const data = {
      listName: list_info.list_name,
      listImg: list_info.list_img,
      listOpen: list_info.list_open,
      userId: userInfo.value.userId,
    };

    const response = await axios.post(url, data, headers);
    return response.data.listNo;
  };

  listInfo.value = { ...listInfo.value, list_open: selectIsOpen.value };
  const listNo = await _registerList();
  registerPlace(listNo);
  resetListInfo();
  router.push({ name: 'place-save' });
};

const modifyButtonHandler = () => {
  const listNo = listInfo.value.list_no;
  const _deletePlaces = async (listNo) => {
    const url = `${VITE_APP_SERVER_URI}/place/delete/${listNo}`;
    await axios.delete(url);
  };

  const _modifyList = async () => {
    const url = `${VITE_APP_SERVER_URI}/list/modify`;
    const headers = {
      'Content-Type': 'application/json',
    };
    const data = {
      listNo: listInfo.value.list_no,
      listName: listInfo.value.list_name,
      listImg: listInfo.value.list_img,
      listOpen: listInfo.value.list_open,
      userId: listInfo.value.user_id,
    };
    await axios.put(url, data, headers);
  };
  listInfo.value = { ...listInfo.value, list_open: selectIsOpen.value };
  _deletePlaces(listNo);
  _modifyList();
  registerPlace(listNo);
  resetListInfo();
  router.push({ name: 'place-save' });
};

const leftButtonHandler = () => {
  router.push({ name: 'place-cover' });
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
  <h1>'{{ listInfo.list_name }}' PLACELIST를 모두에게 공개할까요?</h1>

  <!--button-->
  <container class="btnleft-container">
    <div class="btnleft-handler">
      <v-btn class="btn" size="large" variant="flat" rounded="xl" @click="leftButtonHandler"> 이전으로 </v-btn>
    </div>
  </container>

  <!--button-->
  <container class="btn-container">
    <div class="btn-handler">
      <v-btn
        v-if="listInfo.isModifyMode == undefined"
        class="btn"
        size="large"
        variant="flat"
        rounded="xl"
        @click="saveButtonHandler"
      >
        저장하기
      </v-btn>
      <v-btn
        v-else="listInfo.isModifyMode == true"
        class="btn"
        size="large"
        variant="flat"
        rounded="xl"
        @click="modifyButtonHandler"
      >
        수정하기
      </v-btn>
    </div>
  </container>

  <!--button-->
  <div class="select-container">
    <v-btn
      class="select-btn"
      size="large"
      variant="flat"
      rounded="xl"
      @click="openButtonHandler"
      :class="{ selected: selectIsOpen }"
    >
      네! 공개할게요
    </v-btn>
    <v-btn
      class="select-btn"
      size="large"
      variant="flat"
      rounded="xl"
      @click="closeButtonHandler"
      :class="{ selected: !selectIsOpen }"
    >
      아니요! 저만 볼게요
    </v-btn>
  </div>
</template>

<style scoped>
body {
  max-height: 100%;
}
h1 {
  text-align: left;
  font-size: 36px;
  padding: 1rem 4rem 0 4rem;
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
  background-color: #3182f6;
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
.select-container {
  display: flex;
  height: 50%;
  flex-direction: center;
  justify-content: center;
  align-items: center;
  padding: 1rem 4rem 2rem 4rem;
}
.select-btn {
  margin-left: 2rem;
  background-color: #e0e0e0;
  color: white;
}
.select-btn.selected {
  background-color: #1b64da;
  color: white;
}
.btn-container {
  position: absolute;
  right: 0px;
  top: 0px;
  padding: 3rem;
}
.btn-handler {
  margin-left: auto;
  margin-right: 2rem;
}
.btnleft-container {
  position: absolute;
  left: 0px;
  top: 0px;
  padding: 3rem;
}
.btnleft-handler {
  margin-left: 2rem;
  margin-right: auto;
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
