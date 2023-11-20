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

const { listDetailRes, placeRes } = storeToRefs(listStore);
const { listDetail, placeList } = listStore;

const memberStore = useMemberStore();
const shareStore = useShareStore();

const { findShareRes } = storeToRefs(shareStore);
const { addShare, findShare } = shareStore;

const { VITE_APP_SERVER_URI } = import.meta.env;

const listno = ref(route.params.listno);
const places = ref([]);
const listInfo = ref({});
const userId = ref('');
const isCheckUserId = ref(true);

onMounted(() => {
  getListDetail();
  getPlaceList();
});

const getListDetail = async () => {
  await listDetail(listno.value);
  console.log(listDetailRes.value);
  listInfo.value = listDetailRes.value;
  console.log(listInfo);
};

const getPlaceList = async () => {
  await placeList(listno.value);
  places.value = placeRes.value;
  console.log(places.value);
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

const modifyHandler = () => {
  // 온마운트시 listdetail get
  // places와 함께 listInfo pinia 저장
  router.push({ name: 'place-location' });
};
const param = ref({
  userId: '',
  listNo: listno.value,
});

const addParam = ref({
  userId: '',
  listNo: listno.value,
});

const find = async () => {
  await findShare(param.value);
  // console.log(findShareRes.value);
  if (findShareRes.value.length > 0) {
    const sum = findShareRes.value.reduce((total, item) => total + item.status, 0);
    isCheckUserId.value = sum > 0;
  } else {
    isCheckUserId.value = false;
  }
};

const add = async (item) => {
  addParam.value.userId = item;
  await addShare(addParam.value);
  await findShare(param.value);
};
</script>

<template>
  <h1>{{ listInfo.listName }}</h1>

  <template v-for="place in places" :key="place.placeNo">
    <li>{{ place.placeUrl }}</li>
    <li>{{ place.placeName }}</li>
    <li>{{ place.placePhone }}</li>
  </template>
  <button @click="deleteHandler(listno)">삭제</button>
  <button @click="modifyHandler(listno)">수정</button>

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
