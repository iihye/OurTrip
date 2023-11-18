<script setup>
import { ref } from "vue";
import { usePlaceStore } from "@/stores/place";
import axios from "axios";
import { storeToRefs } from "pinia";

const placeStore = usePlaceStore();
const { VITE_APP_SERVER_URI } = import.meta.env;

const { listInfo } = storeToRefs(placeStore);

const selectIsOpen = ref(false);

const openButtonHandler = () => {
  selectIsOpen.value = true;
};

const saveButtonHandler = () => {
  listInfo.value = { ...listInfo.value, list_open: selectIsOpen.value };
  // console.log(listInfo.value);
  const listNo = registerList();
  registerPlace(listNo);
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
  return response;
};
const registerPlace = (listNo) => {
  console.log(listNo);
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
