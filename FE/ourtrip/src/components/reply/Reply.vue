<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
const { VITE_APP_SERVER_URI } = import.meta.env;
const props = defineProps({ listNo: Number });
const replyContent = ref("");
const replys = ref([]);

const getReply = async () => {
  const url = `${VITE_APP_SERVER_URI}/reply/getReply/${props.listNo}`;
  const response = await axios.get(url);
  replys.value = response.data.list;
};

const addReplyHandler = async () => {
  const url = `${VITE_APP_SERVER_URI}/reply/regist`;
  const headers = {
    "Content-Type": "application/json",
  };

  const data = {
    listNo: props.listNo,
    userId: "test",
    replyContent: replyContent.value,
  };
  await axios.post(url, data, headers);
};

const deleteReplyHandler = async (replyNo) => {
  const url = `${VITE_APP_SERVER_URI}/reply/delete/${replyNo}`;
  const response = await axios.delete(url);
  console.log("delete");
  console.log(response);
};

const addLikeHandler = async (replyNo) => {
  const url = `${VITE_APP_SERVER_URI}/reply/addlike/${replyNo}`;
  const response = await axios.post(url);
};

const cancelLikeHandler = async (replyNo) => {
  const url = `${VITE_APP_SERVER_URI}/reply/sublike/${replyNo}`;
  const response = await axios.post(url);
};

onMounted(() => {
  getReply();
});
</script>

<template>
  <h1>Reply</h1>
  <div>listNo {{ listNo }}</div>
  <ul>
    <div v-for="item in replys">
      {
      {{ item }}
      }
      <button @click="deleteReplyHandler(item.reply_no)">삭제</button>
    </div>
  </ul>

  <form @submit.prevent="" @submit="addReplyHandler">
    <input type="text" v-model="replyContent" />
    <button type="submit">등록</button>
  </form>
  <button @click="addLikeHandler()">좋아요 증가</button>
  <button @click="cancelLikeHandler()">좋아요 감소</button>
</template>

<style scoped></style>
