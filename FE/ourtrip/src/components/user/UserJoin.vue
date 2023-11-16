<script setup>
import { ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/user';

const router = useRouter();
const memberStore = useMemberStore();

const { isJoin } = storeToRefs(memberStore);
const { userJoin } = memberStore;

const joinUser = ref({
  userId: '',
  userPw: '',
  userName: '',
});

const join = async () => {
  await userJoin(joinUser.value);
  if (isJoin.value) {
    alert('가입 성공!!');
    console.log('가입 성공!!!');
    router.push({ name: 'user-login' });
  } else {
    alert('가입 실패!!');
    console.log('가입 실패!!!');
    router.push({ name: 'user-join' });
  }
};
</script>

<template>
  <div>
    <h1>user join</h1>
    <form>
      <input v-model="joinUser.userId" placeholder="아이디" />
      <input v-model="joinUser.userPw" placeholder="비번" />
      <input v-model="joinUser.userName" placeholder="닉네임" />
      <button type="button" @click="join">회원가입</button>
    </form>
  </div>
</template>

<style scoped></style>
