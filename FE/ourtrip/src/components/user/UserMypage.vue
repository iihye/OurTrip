<script setup>
import { onMounted, ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/user';

const router = useRouter();
const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);
const { getUserInfo } = memberStore;

const joinUser = ref({
  userId: '',
  userPw: '',
  userName: '',
});

onMounted(() => {
    fetch();
});

const fetch = async () => {
    await getUserInfo(sessionStorage.getItem("accessToken"));
    joinUser.value.userId = userInfo.value.userId;
    joinUser.value.userName = userInfo.value.userName;  
}

const update = async () => {
  await userUpdate(joinUser.value);
  if (isUpdate.value) {
    alert('수정 성공!!');
    // router.push({ name: 'user-login' });
  } else {
    alert('수정 실패!!');
    // router.push({ name: 'user-join' });
  }
};

</script>

<template>
  <div>
    <h1>Mypage</h1>
    <form>
      <input v-model="joinUser.userId" placeholder="아이디"/>
      <br/>
      <input v-model="joinUser.userPw" placeholder="비번" />
      <br/>
      <input v-model="joinUser.userName" placeholder="닉네임" />
      <br/>
      <button type="button" @click="update">회원수정</button>
    </form>
  </div>
</template>

<style scoped></style>
