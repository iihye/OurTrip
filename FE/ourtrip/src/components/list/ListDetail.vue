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

const { placeRes } = storeToRefs(listStore);
const { placeList } = listStore;

const memberStore = useMemberStore();
const shareStore = useShareStore();

const { findShareRes, findOurShareRes } = storeToRefs(shareStore);
const { addShare, findShare, findOurShare, delShare } = shareStore;

const { VITE_APP_SERVER_URI } = import.meta.env;

const listno = ref(route.params.listno);
const place = ref({});
const userId = ref('');
const isCheckUserId = ref(true);
const isFindOurShare = ref(true);

onMounted(() => {
  getPlaceList();
  findOur();
});

const getPlaceList = async () => {
  await placeList(listno.value);
  console.log(placeRes.value);
  place.value = placeRes.value;
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

const addParam = ref({
  userId: '',
  listNo: listno.value,
});

const delParam = ref({
  userId: '',
  listNo: listno.value,
});

const find = async () => {
    if (param.value.userId !== undefined && param.value.userId.length > 1) { // 1글자 이상 일 때만 검색
    await findShare(param.value);
    // console.log(findShareRes.value);

    if (findShareRes.value.length > 0) {
      const sum = findShareRes.value.reduce((total, item) => total + item.status, 0);
      isCheckUserId.value = sum > 0;
    } else {
      isCheckUserId.value = false;
    }
  }  
};

const findOur = async () => {
  await findOurShare(param.value);
  // console.log(findOurShareRes.value);
  isFindOurShare.value = findOurShareRes.value.length > 0 ? true : false;
}

const add = async (item) => {
  addParam.value.userId = item;
  console.log(addParam.value);
  await addShare(addParam.value);
  // await findShare(param.value);
  find();
  findOur();
};

const del = async (item) => {
  // console.log('item: ' + item);
  delParam.value.userId = item;
  // console.log('delParam: ' + delParam.value.userId);
  // console.log('delParam: ' + delParam.value.listNo);
  await delShare(delParam.value);
  find();
  findOur();
}
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

  <h1>공유한 사람 목록</h1>
  <template v-for="item in findOurShareRes" :key="item">
    <h4>{{ item }}</h4>
    <button @click="del(item)">삭제</button>
  </template>
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
