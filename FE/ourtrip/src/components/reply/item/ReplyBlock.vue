<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import axios, { HttpStatusCode } from "axios";
import { useMemberStore } from "@/stores/user";

const { VITE_APP_SERVER_URI } = import.meta.env;
const props = defineProps({ item: Object, getReply: Function });

const memberStore = useMemberStore();
const { getUserInfo } = memberStore;
const { userInfo } = storeToRefs(memberStore);

onMounted(() => {
  fetch();
});

const fetch = async () => {
  await getUserInfo(sessionStorage.getItem("accessToken"));
};

const deleteReplyHandler = async (replyNo) => {
  const url = `${VITE_APP_SERVER_URI}/reply/delete`;
  const headers = {
    "Content-Type": "application/json",
  };
  const data = {
    replyNo: replyNo,
    userId: userInfo.value.userId,
  };
  try {
    await axios.delete(url, { headers, data });
  } catch (e) {
    if (e.response.status == HttpStatusCode.Unauthorized) {
      alert("본인의 댓글만 삭제하실 수 있습니다잉!!");
    }
  }
  props.getReply();
};

const addLikeHandler = async (replyNo) => {
  const url = `${VITE_APP_SERVER_URI}/reply/addlike/${replyNo}`;
  const response = await axios.post(url);
  console.log(response);
};

const cancelLikeHandler = async (replyNo) => {
  const url = `${VITE_APP_SERVER_URI}/reply/sublike/${replyNo}`;
  const response = await axios.post(url);
  console.log(response);
};
</script>

<template>
  <div>
    <div id="view_container">
      <div id="content_wrap">
        <div id="inner">
          <div id="box">
            <div id="user_name">{{ props.item.user_id }}</div>
            <div id="content">{{ props.item.reply_content }}</div>
          </div>
        </div>
        <div id="button_wrap">
          <button class="button">좋아요</button>
          <button class="button" @click="deleteReplyHandler(item.reply_no)">삭제</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#view_container {
  display: flex;
  flex-direction: row;
  padding: 10px 20px;
}

#content_wrap {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-left: 10px;
}

#inner {
  display: flex;
  flex-direction: row;
  align-items: flex-end;
}

#box {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  border: 0px;
  border-radius: 20px;
  background-color: #eff2f5;
  padding: 10px;
}
#user_name {
  font-size: 14px;
  font-weight: bold;
}
#content_box {
  font-size: 16px;
}

#button_wrap {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  padding-top: 5px;
}

.button {
  padding: 0 5px;
  border: none;
  background: none;
  color: #65676b;
  font-size: 12px;
  font-weight: bold;
  cursor: pointer;
}

.button:hover {
  text-decoration: underline;
}
</style>
