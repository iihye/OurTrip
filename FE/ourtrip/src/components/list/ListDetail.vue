<script setup>
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import VKakaoMap from '../common/VKakaoMap.vue';
import { useMemberStore } from '@/stores/user';
import { useListStore } from '@/stores/list';
import { useShareStore } from '@/stores/share';
import { usePlaceStore } from '@/stores/place';
import Reply from '../reply/Reply.vue';

const route = useRoute();
const router = useRouter();

const memberStore = useMemberStore();
const listStore = useListStore();
const placeStore = usePlaceStore();
const shareStore = useShareStore();

const { userInfo } = storeToRefs(memberStore);
const { listDetailRes, placeRes } = storeToRefs(listStore);
const { listInfo } = storeToRefs(placeStore);
const { findShareRes, findOurShareRes } = storeToRefs(shareStore);

const { listDetail, placeList } = listStore;
const { addShare, findShare, findOurShare, delShare } = shareStore;

const { VITE_APP_SERVER_URI } = import.meta.env;

const listno = ref(route.params.listno);
const places = ref([]);
const listDetailInfo = ref({});
const userId = ref('');
const placeArray = ref([]);
const selectPlace = ref({});

const isCheckUserId = ref(true);
const isFindOurShare = ref(true);
const isShare = ref(false);
const isModifyMode = ref(false);

onMounted(() => {
  getListDetail();
  getPlaceList();
  findOur();
});

// console.log(location.pathname);
history.replaceState({}, null, location.pathname);

const getListDetail = async () => {
  await listDetail(listno.value);
  listDetailInfo.value = listDetailRes.value;
};

const getPlaceList = async () => {
  await placeList(listno.value);
  places.value = placeRes.value;
  placeArray.value = places.value.map((item) => {
    return {
      address_name: item.placeAddressName,
      id: item.placeId,
      phone: item.placePhone,
      place_name: item.placeName,
      place_url: item.placeUrl,
      road_address_name: item.placeRoadAddressName,
      x: item.placeX,
      y: item.placeY,
    };
  });
};

const shareHandler = async () => {
  isShare.value = !isShare.value;
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
  isModifyMode.value = true;
  listInfo.value = {
    isModifyMode: isModifyMode.value,
    list_img: listDetailInfo.value.listImg,
    list_name: listDetailInfo.value.listName,
    list_open: listDetailInfo.value.listOpen,
    user_id: listDetailInfo.value.userId,
    list_no: listDetailInfo.value.listNo,
    list_places: places.value.map((item) => {
      return {
        address_name: item.placeAddressName,
        id: item.placeId,
        phone: item.placePhone,
        place_name: item.placeName,
        place_url: item.placeUrl,
        road_address_name: item.placeRoadAddressName,
        x: item.placeX,
        y: item.placeY,
      };
    }),
  };
  router.push({ name: 'place-location' });
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
    message.value = '검색되었습니다☺️';
    await findShare(searchParam.value);

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
  isFindOurShare.value = findOurShareRes.value.length > 0 ? true : false;
};

const add = async (item) => {
  addParam.value.userId = item;
  await addShare(addParam.value);
  find();
  findOur();
};

const del = async (item) => {
  delParam.value.userId = item;
  await delShare(delParam.value);
  find();
  findOur();
};
</script>

<template>
  <!--logo-->
  <h1>{{ listDetailInfo.listName }}</h1>

  <!--search result list-->
  <div id="main-contain">
    <div>
      <h4>선택한 PLACE 목록</h4>
      <div id="list-container">
        <div id="list_items" v-for="place in places" :key="place.placeNo">
          <div id="list_item">
            <font-awesome-icon :icon="['fas', 'location-dot']" style="color: #1b64da" />
            <div id="list_text">
              <div>{{ place.placeName }}</div>
              <div>{{ place.placeAddressName }}</div>
            </div>
            <!-- <button></button> -->
          </div>
        </div>
      </div>
    </div>

    <!-- map -->
    <div>
      <VKakaoMap id="map" :stations="placeArray" :selectStation="selectPlace"></VKakaoMap>
    </div>

    <!-- Reply -->
    <Reply :listNo="listno"></Reply>
  </div>

  <!--------------------------------------------------------------------------------->

  <!-- <template >
    <li>{{ place.placeUrl }}</li>
    <li></li>
    <li>{{ place.placePhone }}</li>
  </template> -->

  <div v-if="userInfo !== null && userInfo.userId === listDetailInfo.userId">
    <div class="btn-containers">
      <v-btn class="btn" size="large" variant="flat" rounded="xl" @click="shareHandler()"> 공유 </v-btn>
      <v-btn class="btn" size="large" variant="flat" rounded="xl" @click="modifyHandler(listno)"> 수정 </v-btn>
      <v-btn class="btn" size="large" variant="flat" rounded="xl" @click="deleteHandler(listno)"> 삭제 </v-btn>
    </div>

    <div v-if="isShare" class="sharing-container">
      <div class="left-container">
        <h2>
          <font-awesome-icon :icon="['fas', 'share']" size="" style="color: #787878" class="empty-h1" /><br />어떤
          사람과 공유할까요?
        </h2>
        <div class="form-wrapper">
          <v-text-field
            label="아이디 검색"
            v-model="searchParam.userId"
            @keydown="find"
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
          <h5>검색 결과가 없어요😥</h5>
        </div>

        <div id="list-container">
          <div id="share_items" v-for="list in findShareRes" :key="list.user_id">
            <div id="share_item" v-if="list.status == true" class="shared-user">
              <div id="share_text">
                <v-btn id="share_btn" class="btn" size="large" variant="flat" rounded="xl" @click="add(list.user_id)">
                  추가
                </v-btn>
                <div>{{ list.user_id }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="right-container">
        <h2>
          <font-awesome-icon :icon="['fas', 'list-ul']" size="" style="color: #787878" class="empty-h1" /><br />공유하고
          있어요!
        </h2>

        <div id="list-container">
          <div id="share_items" v-for="item in findOurShareRes" :key="item">
            <div id="share_item" class="shared-user">
              <div id="share_text">
                <v-btn
                  id="share_btn"
                  class="btn"
                  size="large"
                  variant="flat"
                  rounded="xl"
                  color="black"
                  @click="del(item)"
                >
                  삭제
                </v-btn>
                <div>{{ item }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#main-contain {
  display: flex;
  height: 600px;
  padding-top: 1rem;
  padding-bottom: 1rem;
}

#main-contain > div {
}

#main-contain > div:nth-child(1) {
  flex: 0 0 300px; /* 고정된 너비 240px */
  margin-left: 2rem;
}
#main-contain > div:nth-child(3) {
  flex: 0 0 300px; /* 고정된 너비 240px */
  margin-right: 2rem;
}

#main-contain > div:nth-child(2) {
  display: flex;
  justify-content: center; /* 수평 가운데에 정렬 */
  flex: 1.2;
}

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
h4 {
  font-size: 20px;
  color: #1b64da;
  text-align: center;
}
.form-wrapper {
  display: flex;
  align-items: center;
  margin-bottom: 2rem;
}
.empty-center {
  text-align: center;
  font-size: 18px;
  margin-top: 2rem;
}
.btn-container {
  position: absolute;
  right: 0px;
  top: 0px;
  padding: 3rem;
}
.btn-containers {
  position: absolute;
  right: 0px;
  top: 0px;
  padding: 3rem;
}
.btn-handler {
  margin-left: auto;
  margin-right: 2rem;
}
.btn {
  background-color: #3182f6;
  color: white;
}
.btn:hover {
  background-color: #1b64da;
  color: white;
}
.sharing-container {
  display: flex;
  min-height: 500px;
  justify-content: space-between;
  margin: 2rem 10rem 10rem 10rem;
}

.left-container {
  flex: 1;
  padding: 20px;
  margin-left: 2rem;
  margin-right: 1rem;
  padding-right: 1rem;
}

.right-container {
  flex: 1;
  padding: 20px;
  margin-left: 1rem;
  margin-right: 2rem;
  border-left: 1px solid #ccc; /* Add a border between the two containers */
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

#share-container {
  overflow-y: auto;
  height: 1000px;
  width: 100%;
}

#map {
  width: 98%; /* Make the width 100% */
  height: auto;
  position: relative;
  margin-bottom: 25px;
  background-repeat: no-repeat;
  background-repeat-x: no-repeat;
  background-repeat-y: no-repeat;
  background-size: 100% 100%;

  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
  border-bottom-left-radius: 15px;

  outline-color: initial;
  outline-style: none;
  outline-width: initial;
}

#btn-handler {
  margin: 1rem;
}

#list-container {
  overflow-y: auto;
  height: 500px;
  width: 100%;
}

#list_items {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 260px;
  padding: 5px 0;
  margin-left: 16px;
  /* cursor: pointer; */
  padding: 9px 9px 9px 0;
}
#list_item {
  display: flex;
  align-items: center;
  width: 100%;
  border-bottom: 1px solid #d8d8d8;
}

#list_text {
  display: flex;
  text-align: left;
  flex-direction: column;
  margin-left: 1rem;
}

#share_items {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 300px;
  margin-left: 16px;
  /* cursor: pointer; */
}
#share_item {
  display: flex;
  align-items: center;
  width: 100%;
}

#share_text {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-left: 1rem;
}

#share_btn {
  width: 20px;
  height: 30px;
  padding: 0;
  margin-right: 2rem;
}
</style>
