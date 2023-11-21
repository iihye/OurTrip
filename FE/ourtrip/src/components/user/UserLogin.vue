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

const isVisible = ref(false);

const login = async () => {
  if (loginUser.value.userId === '') {
    alert('앗! 아이디를 입력해주세요😥');
    return;
  }

  if (loginUser.value.userPw === '') {
    alert('앗! 비밀번호를 입력해주세요😥');
    return;
  }

  await userLogin(loginUser.value);
  let token = sessionStorage.getItem('accessToken');
  //   console.log('token', token);
  if (isLogin.value) {
    alert('반가워요! OURTRIP과 PLACELIST를 만들어보세요✈️');
    getUserInfo(token);
    changeMenuState();
    router.push('/');
  } else {
    alert('앗! 가입하지 않은 아이디이거나 비밀번호가 올바르지 않아요😥');
    router.push({ name: 'user-login' });
  }
};

const find = async () => {
  router.push({ name: 'user-find' });
};

const visible = () => {
  isVisible.value = !isVisible.value;
};
</script>

<template>
  <div>
    <h1>로그인</h1>

    <form class="form">
      <v-container>
        <div class="form-wrapper">
          <v-text-field label="아이디" v-model="loginUser.userId" variant="underlined" style="ime-mode: inactive">
            <template v-slot:prepend-inner>
              <font-awesome-icon :icon="['fas', 'user']" style="color: #787878" />
            </template>
          </v-text-field>
        </div>

        <div class="form-wrapper">
          <v-text-field
            label="비밀번호"
            v-model="loginUser.userPw"
            variant="underlined"
            :type="isVisible ? 'text' : 'password'"
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

        <div class="footer-btn-container">
          <v-btn class="custom-btn" size="x-large" variant="flat" color="black" rounded="xl" @click="login">
            로그인
          </v-btn>
        </div>

        <div class="footer-btn-container">
          <v-btn class="custom-btn" size="x-large" variant="outlined" rounded="xl" @click="find">
            비밀번호 찾기
            <!-- <router-link :to="{name: 'user-find'}">비밀번호 찾기</router-link> -->
          </v-btn>
        </div>
      </v-container>
    </form>
  </div>

  <!-- <h1>User Login</h1>
  <form>
    <input v-model="loginUser.userId" placeholder="아이디" />
    <input v-model="loginUser.userPw" placeholder="비번" />
    <button type="button" @click="login">로그인</button>
  </form> -->
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
