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
  // console.log(myListRes.value);
  await myList(userInfo.value.userId);
  if (myListRes != []) {
    // console.log(myListRes);
  } else {
    console.log('아무것도 없다!!!');
  }
};
</script>

<template>
  <h1>내 PLACELIST</h1>
  <div class="empty-center" v-if="myListRes.length === 0">
    <font-awesome-icon :icon="['fas', 'list']" size="2xl" style="color: #787878;" class="empty-h1" />
    <h4>내 PLACELIST가 비어 있어요😥</h4>
  </div>
  <div class="list-container">
    <ListItem v-for="list in myListRes" :key="list.listNo" :list="list"></ListItem>
  </div>
</template>

<style scoped>
h1 {
  text-align: center;
  font-size: 36px;
  padding: 30px;
}

h4 {
  text-align: center;
  font-size: 24px;
  padding: 30px;
}
.empty-h1 {
  text-align: center;
  font-size: 36px;
  padding-top: 100px;
}
.empty-center{
  text-align: center;
}

.list-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between; 
  margin-left: 20%;
  margin-right: 20%;
}

.list-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin: 10px;
  width: calc(33.33% - 20px); 
  box-sizing: border-box;
}
</style>