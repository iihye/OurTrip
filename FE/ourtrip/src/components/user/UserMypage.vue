<script setup>
import { onMounted, ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/user';
import { isModelListener } from "@vue/shared";

const router = useRouter();
const memberStore = useMemberStore();

const { userInfo, isUpdate, isDelete } = storeToRefs(memberStore);
const { getUserInfo, userUpdate, userDelete } = memberStore;

const updateUser = ref({
  userId: '',
  userPw: '',
  userName: '',
});

onMounted(() => {
    fetch();
});

const fetch = async () => {
    await getUserInfo(sessionStorage.getItem("accessToken"));
    updateUser.value.userId = userInfo.value.userId;
    updateUser.value.userName = userInfo.value.userName;
};

const update = async () => {
    if (updateUser.value.userPw === '') {
        alert("비밀번호를 입력해주세요");
        return;
    }

    await userUpdate(updateUser.value);
    if (isUpdate.value) {
        alert('수정 성공!!');
        await getUserInfo(sessionStorage.getItem("accessToken"));
        router.push({ name: 'home' });
    } else {
        alert('수정 실패!!');
        // router.push({ name: 'user-join' });
    }
};

const unjoin = async () => {
    await userDelete(updateUser.value);
    if (isDelete.value) {
        alert("삭제 성공!!");
        router.push({ name: 'home' });
    } else {
        alert("삭제 실패!!");
    }
};

</script>

<template>
  <div>
    <h1>Mypage</h1>
    <form>
      <input v-model="updateUser.userId" placeholder="아이디" readonly/>
      <br/>
      <input v-model="updateUser.userPw" placeholder="비번" />
      <br/>
      <input v-model="updateUser.userName" placeholder="닉네임" />
      <br/>
      <button type="button" @click="update">회원수정</button>
    </form>
    <button type="button" @click="unjoin">회원탈퇴</button>
  </div>
</template>

<style scoped></style>
