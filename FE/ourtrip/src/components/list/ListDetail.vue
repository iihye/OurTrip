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

const { userInfo } = storeToRefs(memberStore);
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

const message = ref('');

const searchParam = ref({
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
  if (searchParam.value.userId !== undefined && searchParam.value.userId.length >= 2) {
    // 2글자 이상 일 때만 검색
    message.value = '검색되었습니다☺️';
    await findShare(searchParam.value);
    // console.log(findShareRes.value);

    if (findShareRes.value.length > 0) {
      const sum = findShareRes.value.reduce((total, item) => total + item.status, 0);
      isCheckUserId.value = sum > 0;
    } else {
      isCheckUserId.value = false;
      message.value = '';
    }
  } else {
    message.value = '두 글자 이상 검색해주세요😥';
  }
};

const findOur = async () => {
  await findOurShare(searchParam.value);
  // console.log(findOurShareRes.value);
  isFindOurShare.value = findOurShareRes.value.length > 0 ? true : false;
};

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

  <div v-if="userInfo.userId === listDetailInfo.userId" class="sharing-container">
    <div class="left-container">
      <h2>
        <font-awesome-icon :icon="['fas', 'share']" size="" style="color: #787878" class="empty-h1" /><br />어떤 사람과
        공유할까요?
      </h2>
      <div class="form-wrapper">
        <v-text-field
          label="아이디 검색"
          v-model="searchParam.userId"
          @blur="find"
          variant="underlined"
          :messages="message"
        >
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
          <div v-if="list.status == true" class="shared-user">
            <h4>{{ list.user_id }}</h4>
            <v-btn size="large" variant="flat" rounded="xl" color="black" @click="add(list.user_id)"> 추가 </v-btn>
          </div>
        </template>
      </div>
    </div>

    <div class="right-container">
      <h2>
        <font-awesome-icon :icon="['fas', 'list-ul']" size="" style="color: #787878" class="empty-h1" /><br />공유하고
        있어요!
      </h2>
      <template v-for="item in findOurShareRes" :key="item">
        <div class="shared-user">
          <h4>{{ item }}</h4>
          <v-btn size="large" variant="flat" rounded="xl" color="black" @click="del(item)"> 삭제 </v-btn>
        </div>
      </template>
    </div>
  </div>
</template>

<style scoped>
h1 {
  text-align: center;
  font-size: 36px;
  padding: 30px;
}

h2 {
  font-family: 'Pretendard-Regular';
  text-align: center;
  font-size: 28px;
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
}
.sharing-container {
  display: flex;
  justify-content: space-between;
  margin-left: 10rem;
  margin-right: 10rem;
}

.left-container {
  flex: 1.5;
  padding: 20px;
  border-right: 1px solid #ccc; /* Add a border between the two containers */
  margin-left: 10rem;
  margin-right: 10rem;
}

.right-container {
  flex: 1;
  padding: 20px;
  padding-left: 10rem;
  padding-right: 10rem;
}

.shared-user {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  margin-left: 2rem;
}

.shared-user h4 {
  margin-right: 4rem;
  font-size: 20px;
}

.v-btn {
  font-size: 18px;
}
</style>
