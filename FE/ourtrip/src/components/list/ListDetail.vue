<script setup>
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import { useMemberStore } from '@/stores/user';
import { useListStore } from '@/stores/list';
import { useShareStore } from '@/stores/share';

const route = useRoute();
const router = useRouter();
const listStore = useListStore();
const memberStore = useMemberStore();
const shareStore = useShareStore();

const { detailRes } = storeToRefs(listStore);
const { detailList } = listStore;
const { checkIdRes } = storeToRefs(memberStore);
const { checkUserId } = memberStore;
const { isAdd } = storeToRefs(shareStore);
const { addShare } = shareStore;
const { VITE_APP_SERVER_URI } = import.meta.env;

const listno = ref(route.params.listno);
const place = ref({});
const userId = ref('');
const isCheckUserId = ref(true);

onMounted(() => {
  getList();
});

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

const check = async () => {
  await checkUserId(userId.value);
  // console.log(checkIdRes);
  if (checkIdRes.value.length > 0) {
    isCheckUserId.value = true;
  } else {
    isCheckUserId.value = false;
  }
};

const add = async (item) => {
  const param = ref({
    userId: item,
    listNo: listno.value,
  });
  console.log(param.value);
  // await addShare(param);
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
    <v-text-field label="아이디 검색" v-model="userId" @blur="check" variant="underlined">
      <template v-slot:prepend-inner>
        <font-awesome-icon :icon="['fas', 'user']" style="color: #787878" />
      </template>
    </v-text-field>
  </div>

  <div class="empty-center" v-if="!isCheckUserId">
    <font-awesome-icon :icon="['fas', 'list']" size="2xl" style="color: #787878" class="empty-h1" />
    <h4>검색 결과가 없어요😥</h4>
  </div>
  <div class="list-container">
    <template v-for="list in checkIdRes" :key="list.userName">
      <h4>{{ list.userId }}</h4>
      <button @click="add(list.userId)">추가</button>
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
}
.empty-center {
  text-align: center;
}

.list-container {
  margin-left: 20%;
  margin-right: 20%;
}
</style>
