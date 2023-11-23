<script setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
onMounted(() => {
  alert('PLACELIST가 생성되었습니다☺');
  router.push({ name: 'list-my' });
});
</script>

<template>
  <div>
    <h1>vue 저장 페이지</h1>
  </div>
</template>

<style scoped></style>
