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
const { isAdd, isFind, findShareRes } = storeToRefs(shareStore);
const { addShare, findShare } = shareStore;
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

const param = ref({
  userId: '',
  listNo: listno.value,
});

const find = async () => {
  await findShare(param.value);
  // console.log(findShareRes.value);
  console.log(findShareRes.value);
  if (findShareRes.value.length > 0) {
    const sum = findShareRes.value.reduce((prev, item) => prev + item.status, 0);
    console.log(sum);
    if (sum == 0) {
      isCheckUserId.value = false;
    } else {
      isCheckUserId.value = true;
    }
  } else {
    isCheckUserId.value = false;
  }
  // if (isFind === true) {
  //   console.log('true야');
  //   isCheckUserId.value = true;
  // } else {
  //   console.log('false야');
  //   isCheckUserId.value = false;
  // }
};

const add = async (item) => {
  console.log('item ' + item);
  param.value.userId = item;
  console.log(param.value);
  await addShare(param.value);
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
    <v-text-field label="아이디 검색" v-model="param.userId" @blur="find" variant="underlined">
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
    <template v-for="list in findShareRes" :key="list.user_id">
      <div v-if="list.status == true">
        <h4>{{ list.user_id }}</h4>
        <button @click="add(list.user_id)">추가</button>
      </div>
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
