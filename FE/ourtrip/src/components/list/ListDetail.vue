<script setup>
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import { useListStore } from '@/stores/list';

const route = useRoute();
const router = useRouter();
const listStore = useListStore();

const { detailRes, findIdRes } = storeToRefs(listStore);
const { detailList, findId } = listStore;
const { VITE_APP_SERVER_URI } = import.meta.env;

const listno = ref(route.params.listno);

onMounted(() => {
  getList();
});

const place = ref({});
const userId = ref('');

const getList = async () => {
  await detailList(listno.value);
  place.value = detailRes.value;
};

const deleteHandler = async (listNo) => {
  const url = `${VITE_APP_SERVER_URI}/list/delete/${listNo}`;
  const response = await axios.delete(url);
  if (response.status === 200) {
    router.push({ name: 'list-my' });
  } else {
    alert('삭제 오류');
  }
};

const findIdCheck = async () => {
  console.log(findIdRes.value);
  await userCheck(findId.value);
  if (findIdRes != []) {
    console.log(findIdRes);
  } else {
    console.log('아무것도 없다!!!');
  }
};
</script>

<template>
  <h1>listname</h1>

  <template v-for="list in place" :key="list.placeNo">
    <li>{{ list.placeUrl }}</li>
    <li>{{ list.placeName }}</li>
    <li>{{ list.placePhone }}</li>
  </template>
  <button @click="deleteHandler(listno)">삭제</button>

  <h1>공유하기</h1>
  <div class="form-wrapper">
    <v-text-field label="아이디 검색" v-model="userId" @blur="findIdCheck" variant="underlined">
      <template v-slot:prepend-inner>
        <font-awesome-icon :icon="['fas', 'user']" style="color: #787878" />
      </template>
    </v-text-field>
    <template v-for="list in findIdRes" :key="list.userName">
      <li>{{ list.userName }}</li>
    </template>
  </div>
</template>

<style scoped>
h1 {
  text-align: center;
  font-size: 36px;
  padding: 30px;
}
.form-wrapper {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
}
</style>
