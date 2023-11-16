<script setup>
import { ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/user';
import { useMenuStore } from '@/stores/menu';

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { changeMenuState } = useMenuStore();

const loginUser = ref({
  userId: '',
  userPw: '',
});

const login = async () => {
  //   console.log('login ing!!!! !!!');
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem('accessToken');
  //   console.log('1. token', token);
  //   console.log('UserView에서 본 isLogin: ' + isLogin.value);
  if (isLogin.value) {
    alert('로그인 성공!!');
    // console.log('로그인 성공!!!');
    getUserInfo(token);
    changeMenuState();
    router.push('/');
  } else {
    alert('로그인 실패!!');
    router.push({ name: 'user-login' });
  }
};
</script>

<template>
  <h1>User Login</h1>
  <form>
    <input v-model="loginUser.userId" placeholder="아이디" />
    <input v-model="loginUser.userPw" placeholder="비번" />
    <button type="button" @click="login">로그인</button>
  </form>
</template>

<style scoped></style>
