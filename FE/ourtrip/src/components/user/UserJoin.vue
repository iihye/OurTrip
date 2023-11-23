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
const userIdMessage = ref('');
const userPwMessage = ref('');
const isPwCheck = ref(false);
const isVisible = ref(false);

const join = async () => {
  if (joinUser.value.userId === '') {
    alert('아이디를 입력해주세요');
    return;
  }

  if (joinUser.value.userPw === '') {
    alert('비밀번호를 입력해주세요');
    return;
  }

  if (joinUser.value.userName === '') {
    alert('이름을 입력해주세요');
    return;
  }

  if (!isCheck.value) {
    alert('사용할 수 없는 아이디입니다😥');
    return;
  }

  if (!isPwCheck.value) {
    alert('사용할 수 없는 비밀번호입니다😥');
    return;
  }

  await userJoin(joinUser.value);
  if (isJoin.value) {
    alert(joinUser.value.userName + '님 환영합니다☺️');
    router.push({ name: 'user-login' });
  } else {
    alert('회원가입에 실패했어요 관리자에게 문의하세요!');
    router.push({ name: 'user-join' });
  }
};

const idCheck = async () => {
  if (joinUser.value.userId !== undefined && joinUser.value.userId.length >= 4 && joinUser.value.userId.length <= 16) {
    await userCheck(joinUser.value);
    if (isCheck.value) {
      userIdMessage.value = '사용할 수 있는 아이디예요☺️';
      // console.log('등록 가능');
    } else {
      userIdMessage.value = '앗! 이미 사용 중이거나 사용할 수 없는 아이디예요😥';
      // console.log('등록 불가');
    }
  } else {
    userIdMessage.value = '앗! 아이디를 4자 이상 16자 이하로 설정해주세요😥';
  }
};

const pwCheck = async () => {
  if (joinUser.value.userPw !== undefined && joinUser.value.userPw.length >= 4 && joinUser.value.userPw.length <= 30) {
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
    <h1>회원가입</h1>

    <form class="form">
      <v-container>
        <div class="form-wrapper">
          <v-text-field
            label="아이디"
            v-model="joinUser.userId"
            @blur="idCheck"
            variant="underlined"
            :messages="userIdMessage === '' ? '4~16자 아이디를 입력해주세요😊' : userIdMessage"
            style="ime-mode: inactive"
          >
            <template v-slot:prepend-inner>
              <font-awesome-icon :icon="['fas', 'user']" style="color: #787878" />
            </template>
          </v-text-field>
        </div>

        <div class="form-wrapper">
          <v-text-field
            label="비밀번호"
            v-model="joinUser.userPw"
            variant="underlined"
            :type="isVisible ? 'text' : 'password'"
            @blur="pwCheck"
            :messages="userPwMessage === '' ? '4~30자 비밀번호를 입력해주세요😊' : userPwMessage"
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
          <v-text-field label="닉네임" v-model="joinUser.userName" variant="underlined">
            <template v-slot:prepend-inner>
              <font-awesome-icon :icon="['fas', 'signature']" style="color: #787878" />
            </template>
          </v-text-field>
        </div>

        <div class="footer-btn-container">
          <v-btn class="custom-btn" size="x-large" variant="flat" color="black" rounded="xl" @click="join">
            가입하기
          </v-btn>
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
