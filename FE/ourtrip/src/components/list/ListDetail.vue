<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const listno = ref(route.params.listno);

onMounted(() => {
  getList();
});

const getList = () => {
  //   console.log('getList를 실행하였습니다');
};
</script>

<template>
  <div>List detail</div>
  <span>{{ listno }}</span>
</template>

<style scoped></style>
