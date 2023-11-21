<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { usePlaceStore } from "@/stores/place";
import axios from "axios";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";

const router = useRouter();
const memberStore = useMemberStore();
const placeStore = usePlaceStore();
const { VITE_APP_SERVER_URI } = import.meta.env;

const { userInfo } = storeToRefs(memberStore);
const { getUserInfo } = memberStore;
const { listInfo } = storeToRefs(placeStore);
const selectIsOpen = ref(false);

onMounted(() => {
  fetch();
});

const fetch = async () => {
  await getUserInfo(sessionStorage.getItem("accessToken"));
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
    "Content-Type": "application/json",
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
      "Content-Type": "application/json",
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
  router.push({ name: "list-my" });
};

const modifyButtonHandler = () => {
  const _deletePlaces = async (listNo) => {
    const url = `${VITE_APP_SERVER_URI}/place/delete/${listNo}`;
    await axios.delete(url);
  };

  const _modifyList = async () => {
    const url = `${VITE_APP_SERVER_URI}/list/modify`;
    const headers = {
      "Content-Type": "application/json",
    };
    const data = {
      listNo: listInfo.value.list_no,
      listName: listInfo.value.list_name,
      listImg: listInfo.value.list_img,
      listOpen: listInfo.value.listOpen,
      userId: listInfo.value.user_id,
    };
    await axios.put(url, data, headers);
  };

  _deletePlaces(listInfo.value.list_no);
  _modifyList();
  resetListInfo();
  router.push({ name: "list-my" });
};
</script>

<template>
  <div>place Auth Open</div>
  {{ listInfo }}
  <p>모두에게 공개할까요?</p>
  <br />
  <button @click="openButtonHandler">네! 공개할게요</button>
  <br />
  <button @click="closeButtonHandler">아니요! 저만 볼게요</button>
  <br />
  <br />

  <button v-if="listInfo.isModifyMode == undefined" @click="saveButtonHandler">
    저장하기
  </button>
  <button v-else="listInfo.isModifyMode == true" @click="modifyButtonHandler">
    수정하기
  </button>
</template>

<style scoped></style>
