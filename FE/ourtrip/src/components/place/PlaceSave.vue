<script setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';
import { usePlaceStore } from '@/stores/place';

const placeStore = usePlaceStore();
const { saveStatus } = storeToRefs(placeStore);

const router = useRouter();
onMounted(() => {
  alert('PLACELIST가 ' + saveStatus.value + '되었습니다☺');
  router.push({ name: 'list-my' });
});
</script>

<template>
  <div>
    <h1>vue 저장 페이지</h1>
  </div>
</template>

<style scoped></style>
