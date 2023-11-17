<script setup>
import { ref } from "vue";
import { usePlaceStore } from "@/stores/place";
import { storeToRefs } from "pinia";

const placeStore = usePlaceStore();
const { listInfo } = storeToRefs(placeStore);

const selectIsOpen = ref(false);

const openButtonHandler = () => {
  selectIsOpen.value = true;
};

const saveButtonHandler = () => {
  listInfo.value = { ...listInfo.value, list_open: selectIsOpen.value };
  console.log(listInfo.value);
  // list-my
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
