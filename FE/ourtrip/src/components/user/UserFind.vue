<script setup>
import { ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/user';

const router = useRouter();
const memberStore = useMemberStore();

const { isFind } = storeToRefs(memberStore);
const { userFind } = memberStore;

const findUser = ref({
  userId: '',
  userName: '',
});

const find = async () => {
  await userFind(findUser.value);
  if (isFind.value != '') {
    alert("비밀번호는 " + isFind.value + " 입니다");
    router.push({ name: 'user-login' });
  } else {
    alert("회원정보를 찾을 수 없습니다");
  }
}
</script>

<template>
  <div>
    <h1>user find</h1>
    <form>
    <input v-model="findUser.userId" placeholder="아이디" />
    <input v-model="findUser.userName" placeholder="이름" />
    <button type="button" @click="find">비밀번호 찾기</button>
  </form>
  </div>
</template>

<style scoped></style>
