<script setup>
import { onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/user';
import { useListStore } from '@/stores/list';

const listStore = useListStore();
const memberStore = useMemberStore();

const { myListRes } = storeToRefs(listStore);
const { myList } = listStore;
const { userInfo } = storeToRefs(memberStore);

onMounted(() => {
  my();
});

const my = async () => {
  await myList(userInfo.value.userId);
  if (myListRes != []) {
    console.log(myListRes);
  } else {
    console.log('아무것도 없다!!!');
  }
};
</script>

<template>
  <div>List my</div>
  <template v-for="list in myListRes" :key="list.listno">
    <li>{{ list.listName }}</li>
  </template>
</template>

<style scoped></style>
