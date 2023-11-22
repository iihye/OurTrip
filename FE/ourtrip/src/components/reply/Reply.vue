<script setup>
import axios from "axios";
import { ref } from "vue";
const props = defineProps({ listNo: Number });

const addReplyHandler = async () => {
  const url = `${VITE_APP_SERVER_URI}/reply/add/${listNo}`;
  const response = await axios.post(url);
};
const deleteReplyHandler = async () => {
  const url = `${VITE_APP_SERVER_URI}/reply/delete/${listNo}`;
  const response = await axios.delete(url);
};

const addLikeHandler = async (replyNo) => {
  const url = `${VITE_APP_SERVER_URI}/reply/addlike/${replyNo}`;
  const response = await axios.post(url);
};
const cancelLikeHandler = async (replyNo) => {
  const url = `${VITE_APP_SERVER_URI}/reply/sublike/${replyNo}`;
  const response = await axios.post(url);
};
</script>

<template>
  <h1>Reply</h1>
  <div>listNo {{ listNo }}</div>

  <div>
    <button @click="deleteReplyHandler"></button>
  </div>

  <form @submit.prevent="" @submit="addReplyHandler">
    <button type="submit">등록</button>
  </form>
  <button @click="addLikeHandler()">좋아요 증가</button>
  <button @click="cancelLikeHandler()">좋아요 감소</button>
</template>

<style scoped></style>
