<script setup>
import { onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/user';
import { useListStore } from '@/stores/list';

const listStore = useListStore();
const memberStore = useMemberStore();

const { shareListRes } = storeToRefs(listStore);
const { shareList } = listStore;
const { userInfo } = storeToRefs(memberStore);

onMounted(() => {
  share();
});

const share = async () => {
  await shareList(userInfo.value.userId);
  if (shareListRes != []) {
    console.log(shareListRes);
  } else {
    console.log('아무것도 없다!!!');
  }
};
</script>

<template>
  <div>List share</div>
  <div v-if="shareListRes.length === 0">비어있어요</div>
  <template v-for="list in shareListRes" :key="list.listno">
    <li>{{ list.listName }}</li>
  </template>
</template>

<style scoped></style>
