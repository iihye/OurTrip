<script setup>
import { onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useListStore } from '@/stores/list';

const listStore = useListStore();

const { openListRes } = storeToRefs(listStore);
const { openList } = listStore;

onMounted(() => {
  open();
});

const open = async () => {
  await openList();
  if (openListRes != []) {
    console.log(openListRes);
  } else {
    console.log('아무것도 없다!!!');
  }
};
</script>

<template>
  <div>List open</div>
  <div v-if="openListRes.length === 0">비어있어요</div>
  <template v-for="list in openListRes" :key="list.listno">
    <li>{{ list.listName }}</li>
  </template>
</template>

<style scoped></style>
