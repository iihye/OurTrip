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
  if (findUser.value.userId === '') {
    alert('아이디를 입력해주세요');
    return;
  }

  if (findUser.value.userName === '') {
    alert('닉네임을 입력해주세요');
    return;
  }

  await userFind(findUser.value);
  if (isFind.value != '') {
    alert('비밀번호는 ' + isFind.value + ' 입니다☺️');
    router.push({ name: 'user-login' });
  } else {
    alert('회원정보를 찾을 수 없습니다😥');
  }
};
</script>

<template>
  <div>
    <h1>비밀번호 찾기</h1>

    <form class="form">
      <v-container>
        <div class="form-wrapper">
          <v-text-field label="아이디" v-model="findUser.userId" variant="underlined">
            <template v-slot:prepend-inner>
              <font-awesome-icon :icon="['fas', 'user']" style="color: #787878" />
            </template>
          </v-text-field>
        </div>

        <div class="form-wrapper">
          <v-text-field label="닉네임" v-model="findUser.userName" variant="underlined">
            <template v-slot:prepend-inner>
              <font-awesome-icon :icon="['fas', 'signature']" style="color: #787878" />
            </template>
          </v-text-field>
        </div>

        <div class="footer-btn-container">
          <v-btn class="custom-btn" size="x-large" variant="flat" color="black" rounded="xl" @click="find">
            비밀번호 찾기
          </v-btn>
        </div>
      </v-container>
    </form>

    <!-- <h1>user find</h1>
    <form>
    <input v-model="findUser.userId" placeholder="아이디" />
    <input v-model="findUser.userName" placeholder="이름" />
    <button type="button" @click="find">비밀번호 찾기</button>
  </form> -->
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
