<script setup>
import axios from 'axios';
import { storeToRefs } from 'pinia';
import { ref, onMounted } from 'vue';
import { useMemberStore } from '@/stores/user';

import ReplyBlock from '../../components/reply/item/ReplyBlock.vue';

const { VITE_APP_SERVER_URI } = import.meta.env;
const props = defineProps({ listNo: Number });
const replyContent = ref('');
const replys = ref([]);

const memberStore = useMemberStore();
const { getUserInfo } = memberStore;
const { userInfo } = storeToRefs(memberStore);

onMounted(() => {
  fetch();
});

const fetch = async () => {
  await getUserInfo(sessionStorage.getItem('accessToken'));
};

const displayedAt = (item) => {
  const createdAt = new Date(item[0], item[1] - 1, item[2], item[3], item[4], item[5]);
  const milliSeconds = new Date() - createdAt;
  const seconds = milliSeconds / 1000;
  if (seconds < 60) return `방금 전`;
  const minutes = seconds / 60;
  if (minutes < 60) return `${Math.floor(minutes)}분 전`;
  const hours = minutes / 60;
  if (hours < 24) return `${Math.floor(hours)}시간 전`;
  const days = hours / 24;
  if (days < 7) return `${Math.floor(days)}일 전`;
  const weeks = days / 7;
  if (weeks < 5) return `${Math.floor(weeks)}주 전`;
  const months = days / 30;
  if (months < 12) return `${Math.floor(months)}개월 전`;
  const years = days / 365;
  return `${Math.floor(years)}년 전`;
};

const getReply = async () => {
  const url = `${VITE_APP_SERVER_URI}/reply/getReply`;
  const headers = {
    'Content-Type': 'application/json',
  };

  const data = {
    listNo: props.listNo,
    userId: 'test',
  };

  const response = await axios.post(url, data, headers);
  replys.value = response.data.list.map((item) => {
    return { ...item, reply_computed: displayedAt(item.reply_datetime) };
  });
};

const addReplyHandler = async () => {
  const url = `${VITE_APP_SERVER_URI}/reply/regist`;
  const headers = {
    'Content-Type': 'application/json',
  };

  const data = {
    listNo: props.listNo,
    userId: userInfo.value.userId,
    replyContent: replyContent.value,
  };
  await axios.post(url, data, headers);
  replyContent.value = '';
  await getReply();
  const mySpace = document.getElementById('main_container');
  mySpace.scrollTop = mySpace.scrollHeight;
};

onMounted(() => {
  getReply();
});

setInterval(() => getReply(), 3000);
</script>

<template>
  <div>
    <div id="main_container">
      <div v-for="item in replys" :key="item.reply_no">
        <ReplyBlock :item="item" :getReply="getReply"></ReplyBlock>
      </div>
    </div>
    <div id="input_container">
      <form id="input_form" @submit.prevent="" @submit="addReplyHandler">
        <input id="input" type="text" v-model="replyContent" placeholder="댓글을 입력하세요..." />
        <p id="input_description">글을 게시하려면 Enter 키를 누르세요.</p>
        <button type="submit" hidden>등록</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
#main_container {
  overflow-y: auto;
  height: 86%;
  width: 100%;
}
#input_container {
  display: flex;
  flex-direction: row;
}

#input_form {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-left: 10px;
  margin-top: 1rem;
}

#input {
  /* width: 500px; */
  width: 260px;
  height: 40px;
  border: 0px;
  border-radius: 25px;
  background-color: #eff2f5;
  outline-width: 0;
  padding-left: 10px;
}
#input_description {
  font-size: 12px;
  padding-top: 3px;
  padding-left: 10px;
}
</style>
