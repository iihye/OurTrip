<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();

const goback = async () => {
  router.go(-1);
};
</script>

<template>
  <div class="wrapper">
    <div class="content">
      <font-awesome-icon :icon="['fas', 'triangle-exclamation']" size="2xl" style="color: red" />
      <h1>404 Error</h1>
      <h2>요청하신 페이지를 찾을 수 없습니다</h2>
      <h2>입력하신 주소가 정확한지 다시 한번 확인해주세요</h2>
      <br />
      <br />
      <v-btn class="btn" size="large" variant="flat" rounded="xl" @click="goback"> 이전 페이지로 돌아가기 </v-btn>
    </div>
  </div>
</template>

<style scoped>
.wrapper {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  /* min-height: 100vh - 118px; */
}
.content {
  display: block;
  position: absolute;
  text-align: center;
}

.btn {
  background-color: #3182f6;
  color: white;
}
.btn:hover {
  background-color: #1b64da;
  color: white;
}
</style>
