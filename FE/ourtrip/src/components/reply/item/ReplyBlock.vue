<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import axios, { HttpStatusCode } from "axios";
import { useMemberStore } from "@/stores/user";

const { VITE_APP_SERVER_URI, VITE_APP_CLIENT_URI } = import.meta.env;
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
  const url = `${VITE_APP_SERVER_URI}/reply/addLike`;
  const headers = {
    "Content-Type": "application/json",
  };
  const data = {
    replyNo: replyNo,
    userId: userInfo.value.userId,
  };
  try {
    await axios.post(url, data, headers);
  } catch (e) {
    if (e.response.status == HttpStatusCode.Forbidden) {
      alert("좋아요는 한번만 할 수 있습니다잉!!");
    }
  }
  props.getReply();
};

const cancelLikeHandler = async (replyNo) => {
  const url = `${VITE_APP_SERVER_URI}/reply/subLike`;
  const headers = {
    "Content-Type": "application/json",
  };
  const data = {
    replyNo: replyNo,
    userId: userInfo.value.userId,
  };
  try {
    await axios.post(url, data, headers);
  } catch (e) {
    if (e.response.status == HttpStatusCode.Forbidden) {
      alert("취소는 한번만 할 수 있습니다잉!!");
    }
  }
  props.getReply();
};
const likeSvg = `${VITE_APP_CLIENT_URI}/like.svg`;
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
          <img v-if="props.item.reply_like >= 1" height="18" :src="likeSvg" alt="like" />
        </div>
        <div id="button_wrap">
          <button
            v-if="props.item.reply_like >= 1"
            class="button"
            @click="cancelLikeHandler(item.reply_no)"
          >
            좋아요 취소
          </button>
          <button
            v-if="props.item.reply_like == 0"
            class="button"
            @click="addLikeHandler(item.reply_no)"
          >
            좋아요
          </button>
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
