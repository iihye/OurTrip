<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { usePlaceStore } from "@/stores/place";
import axios from "axios";
import { storeToRefs } from "pinia";

const router = useRouter();
const placeStore = usePlaceStore();
const { VITE_APP_SERVER_URI } = import.meta.env;

const { listInfo } = storeToRefs(placeStore);

const selectIsOpen = ref(false);

const openButtonHandler = () => {
  selectIsOpen.value = true;
};

const saveButtonHandler = async () => {
  listInfo.value = { ...listInfo.value, list_open: selectIsOpen.value };
  const listNo = await registerList();
  registerPlace(listNo);
  router.push({ name: "list-my" });
};

const registerList = async () => {
  const list_info = listInfo.value;
  const url = `${VITE_APP_SERVER_URI}/list/register`;
  const headers = {
    "Content-Type": "application/json",
  };
  const data = {
    listName: list_info.list_name,
    listImg: list_info.list_img,
    listOpen: list_info.list_open,
    userId: "test",
  };

  const response = await axios.post(url, data, headers);
  return response.data.listNo;
};

const registerPlace = async (listNo) => {
  const list_places = listInfo.value.list_places;
  const places = list_places.map((place_info) => {
    return {
      placeName: place_info.address_name,
      placeUrl: place_info.place_url,
      placeAddressName: place_info.address_name,
      placeRoadAddressName: place_info.road_address_name,
      placePhone: place_info.phone,
      placeX: place_info.x,
      placeY: place_info.y,
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
</script>

<template>
  <div>place Auth Open</div>
  {{ listInfo }}
  <p>모두에게 공개할까요?</p>
  <br />
  <button @click="openButtonHandler">네! 공개할게요</button>
  <br />
  <button>아니요! 저만 볼게요</button>
  <br />
  <br />
  <button @click="saveButtonHandler">저장하기</button>
</template>

<style scoped></style>
