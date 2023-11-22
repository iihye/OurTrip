<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import ReplyBlock from "../../components/reply/item/ReplyBlock.vue";

const { VITE_APP_SERVER_URI } = import.meta.env;
const props = defineProps({ listNo: Number });
const replyContent = ref("");
const replys = ref([]);

const getReply = async () => {
  const url = `${VITE_APP_SERVER_URI}/reply/getReply/${props.listNo}`;
  const response = await axios.get(url);
  replys.value = response.data.list;
};

const addReplyHandler = async (event) => {
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
  replyContent.value = "";
  getReply();
};

onMounted(() => {
  getReply();
});

const item = { userName: "jam", content: "test" };
</script>

<template>
  <button @click="addLikeHandler()">좋아요 증가</button>
  <br />
  <button @click="cancelLikeHandler()">좋아요 감소</button>

  <div>
    <div v-for="item in replys" :key="item.reply_no">
      <ReplyBlock :item="item" :getReply="getReply"></ReplyBlock>
    </div>
    <div id="input_container">
      <form id="input_form" @submit.prevent="" @submit="addReplyHandler">
        <input
          id="input"
          type="text"
          v-model="replyContent"
          placeholder="댓글을 입력하세요..."
        />
        <p id="input_description">글을 게시하려면 Enter 키를 누르세요.</p>
        <button type="submit" hidden>등록</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
#input_container {
  display: flex;
  flex-direction: row;
  padding: 20px;
}

#input_form {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-left: 10px;
}

#input {
  width: 500px;
  height: 40px;
  border: 0px;
  border-radius: 25px;
  background-color: #eff2f5;
  padding: 10px;
  outline-width: 0;
}
#input_description {
  padding-top: 5px;
  font-size: 12px;
}
</style>
