<script setup>
import { onMounted, ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/user';
import { useMenuStore } from '@/stores/menu';

const router = useRouter();
const memberStore = useMemberStore();
const menuStore = useMenuStore();

const { userInfo, isUpdate, isDelete } = storeToRefs(memberStore);
const { getUserInfo, userUpdate, userDelete } = memberStore;
const { changeMenuState } = menuStore;

const updateUser = ref({
  userId: '',
  userPw: '',
  userName: '',
});

const userPwMessage = ref('');
const isPwCheck = ref(false);
const isVisible = ref(false);

onMounted(() => {
  fetch();
});

const fetch = async () => {
  await getUserInfo(sessionStorage.getItem('accessToken'));
  updateUser.value.userId = userInfo.value.userId;
  updateUser.value.userName = userInfo.value.userName;
};

const update = async () => {
  if (updateUser.value.userPw === '') {
    alert('앗! 비밀번호를 입력해주세요😥');
    return;
  }

  if (updateUser.value.userName === '') {
    alert('앗! 이름을 입력해주세요😥');
    return;
  }

  if (!isPwCheck.value) {
    alert('사용할 수 없는 비밀번호입니다😥');
    return;
  }

  await userUpdate(updateUser.value);
  if (isUpdate.value) {
    alert('회원정보를 수정했어요☺️');
    await getUserInfo(sessionStorage.getItem('accessToken'));
    router.push({ name: 'home' });
  } else {
    alert('앗! 회원정보를 수정할 수 없어요😥');
    // router.push({ name: 'user-join' });
  }
};

const unjoin = async () => {
  await userDelete(updateUser.value.userId);
  if (isDelete.value) {
    alert('회원탈퇴가 처리되었습니다😭');
    changeMenuState(); // logout 처리
    sessionStorage.removeItem('accessTkoen');
    sessionStorage.removeItem('refreshToken');
    router.push({ name: 'home' });
  } else {
    alert('앗! 회원탈퇴를 처리할 수 없어요😥');
  }
};

const pwCheck = async () => {
  if (
    updateUser.value.userPw !== undefined &&
    updateUser.value.userPw.length >= 4 &&
    updateUser.value.userPw.length <= 30
  ) {
    userPwMessage.value = '사용할 수 있는 비밀번호예요☺️';
    isPwCheck.value = true;
  } else {
    userPwMessage.value = '앗! 비밀번호를 4자 이상 30자 이하로 설정해주세요😥';
    isPwCheck.value = false;
  }
};

const visible = () => {
  isVisible.value = !isVisible.value;
};
</script>

<template>
  <div>
    <h1>마이페이지</h1>

    <form class="form">
      <v-container>
        <div class="form-wrapper">
          <v-text-field label="아이디" v-model="updateUser.userId" variant="underlined" readonly>
            <template v-slot:prepend-inner>
              <font-awesome-icon :icon="['fas', 'user']" style="color: #787878" />
            </template>
          </v-text-field>
        </div>

        <div class="form-wrapper">
          <v-text-field
            label="비밀번호"
            v-model="updateUser.userPw"
            variant="underlined"
            :type="isVisible ? 'text' : 'password'"
            @keydown="pwCheck"
            :messages="userPwMessage"
            style="ime-mode: inactive"
          >
            <template v-slot:prepend-inner>
              <font-awesome-icon :icon="['fas', 'lock']" style="color: #787878" />
            </template>
            <template v-slot:append-inner>
              <div v-if="!isVisible" @click="visible">
                <font-awesome-icon :icon="['fas', 'eye']" style="color: #787878" />
              </div>
              <div v-if="isVisible" @click="visible">
                <font-awesome-icon :icon="['fas', 'eye-slash']" style="color: #787878" />
              </div>
            </template>
          </v-text-field>
        </div>

        <div class="form-wrapper">
          <v-text-field label="닉네임" v-model="updateUser.userName" variant="underlined" style="ime-mode: active">
            <template v-slot:prepend-inner>
              <font-awesome-icon :icon="['fas', 'signature']" style="color: #787878" />
            </template>
          </v-text-field>
        </div>

        <div class="footer-btn-container">
          <v-btn class="custom-btn" size="x-large" variant="flat" color="black" rounded="xl" @click="update">
            정보수정
          </v-btn>
        </div>

        <div class="footer-btn-container">
          <v-btn class="custom-btn" size="x-large" variant="outlined" rounded="xl" @click="unjoin"> 회원탈퇴 </v-btn>
        </div>
      </v-container>
    </form>
  </div>
</template>

<style scoped>
h1 {
  text-align: center;
  font-size: 36px;
  padding: 30px;
}
.form {
  padding: 30px;
  padding-left: 40%;
  padding-right: 40%;
}
.form-wrapper {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
}
.footer-btn-container {
  display: flex;
  justify-content: center;
  margin-top: 16px;
}
.custom-btn {
  width: 400px;
}
</style>
