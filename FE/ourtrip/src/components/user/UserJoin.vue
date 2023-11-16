<script setup>
import { ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/user';

const router = useRouter();
const memberStore = useMemberStore();

const { isJoin, isCheck } = storeToRefs(memberStore);
const { userJoin, userCheck } = memberStore;

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

const check = async () => {
  await userCheck(joinUser.value);
  if (!isCheck.value) {
    console.log('등록 가능');
  } else {
    console.log('등록 불가');
  }
};
</script>

<template>
  <div>
    <h1>user join</h1>
    <form>
      <input v-model="joinUser.userId" placeholder="아이디" @blur="check" />
      <span v-if="!isCheck">사용할 수 없는 아이디입니다</span>
      <input v-model="joinUser.userPw" placeholder="비번" />
      <input v-model="joinUser.userName" placeholder="닉네임" />
      <button type="button" @click="join">회원가입</button>
    </form>
  </div>
</template>

<style scoped></style>
