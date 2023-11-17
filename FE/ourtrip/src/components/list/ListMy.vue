<script setup>
import { onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/user';
import { useListStore } from '@/stores/list';

import ListItem from '@/components/list/ListItem.vue';

const listStore = useListStore();
const memberStore = useMemberStore();

const { myListRes } = storeToRefs(listStore);
const { myList } = listStore;
const { userInfo } = storeToRefs(memberStore);

onMounted(() => {
  my();
});

const my = async () => {
  console.log(myListRes.value);
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
  <div v-if="myListRes.length === 0">비어있어요</div>
  <ListItem v-for="list in myListRes" :key="list.listNo" :list="list"></ListItem>
</template>

<style scoped></style>
