<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import VKakaoMap from "../common/VKakaoMap.vue";
import { useMemberStore } from "@/stores/user";
import { useListStore } from "@/stores/list";
import { useShareStore } from "@/stores/share";
import { usePlaceStore } from "@/stores/place";
import Reply from "../reply/Reply.vue";

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
const userId = ref("");
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
    router.push({ name: "list-my" });
  } else {
    alert("삭제 오류");
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
  router.push({ name: "place-location" });
};

const message = ref("");

const searchParam = ref({
  userId: "",
  listNo: listno.value,
});

const addParam = ref({
  userId: "",
  listNo: listno.value,
});

const delParam = ref({
  userId: "",
  listNo: listno.value,
});

const find = async () => {
  if (
    searchParam.value.userId !== undefined &&
    searchParam.value.userId.length >= 2
  ) {
    message.value = "검색되었습니다☺️";
    await findShare(searchParam.value);

    if (findShareRes.value.length > 0) {
      const sum = findShareRes.value.reduce(
        (total, item) => total + item.status,
        0
      );
      isCheckUserId.value = sum > 0;
    } else {
      isCheckUserId.value = false;
      message.value = "";
    }
  } else {
    message.value = "두 글자 이상 검색해주세요😥";
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
  <h1>{{ listDetailInfo.listName }}</h1>
  <VKakaoMap
    id="map"
    :stations="placeArray"
    :selectStation="selectPlace"
  ></VKakaoMap>
  <!-- <template v-for="place in places" :key="place.placeNo">
    <li>{{ place.placeUrl }}</li>
    <li>{{ place.placeName }}</li>
    <li>{{ place.placePhone }}</li>
  </template> -->

  <Reply :listNo="listno"></Reply>
  <div v-if="userInfo !== null && userInfo.userId === listDetailInfo.userId">
    <div class="btn-container">
      <v-btn
        id="btn-handler"
        size="large"
        variant="flat"
        rounded="xl"
        color="black"
        @click="shareHandler()"
      >
        공유
      </v-btn>
      <v-btn
        id="btn-handler"
        size="large"
        variant="flat"
        rounded="xl"
        color="black"
        @click="modifyHandler(listno)"
      >
        수정
      </v-btn>
      <v-btn
        id="btn-handler"
        size="large"
        variant="flat"
        rounded="xl"
        color="black"
        @click="deleteHandler(listno)"
      >
        삭제
      </v-btn>
    </div>

    <div v-if="isShare" class="sharing-container">
      <div class="left-container">
        <h2>
          <font-awesome-icon
            :icon="['fas', 'share']"
            size=""
            style="color: #787878"
            class="empty-h1"
          /><br />어떤 사람과 공유할까요?
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
              <font-awesome-icon
                :icon="['fas', 'user']"
                style="color: #787878"
              />
            </template>
          </v-text-field>
        </div>

        <div class="empty-center" v-if="!isCheckUserId">
          <font-awesome-icon
            :icon="['fas', 'list']"
            size="2xl"
            style="color: #787878"
            class="empty-h1"
          />
          <h4>검색 결과가 없어요😥</h4>
        </div>

        <div class="list-container">
          <template v-for="list in findShareRes" :key="list.user_id">
            <div v-if="list.status == true" class="shared-user">
              <h4>{{ list.user_id }}</h4>
              <v-btn
                size="large"
                variant="flat"
                rounded="xl"
                color="black"
                @click="add(list.user_id)"
              >
                추가
              </v-btn>
            </div>
          </template>
        </div>
      </div>

      <div class="right-container">
        <h2>
          <font-awesome-icon
            :icon="['fas', 'list-ul']"
            size=""
            style="color: #787878"
            class="empty-h1"
          /><br />공유하고 있어요!
        </h2>
        <template v-for="item in findOurShareRes" :key="item">
          <div class="shared-user">
            <h4>{{ item }}</h4>
            <v-btn
              size="large"
              variant="flat"
              rounded="xl"
              color="black"
              @click="del(item)"
            >
              삭제
            </v-btn>
          </div>
        </template>
      </div>
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
  font-family: "Pretendard-Regular";
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
.btn-container {
  display: flex;
  justify-content: center;
}
.sharing-container {
  display: flex;
  justify-content: space-between;
  margin-left: 10rem;
  margin-right: 10rem;
  margin-top: 2rem;
}

.left-container {
  flex: 1;
  padding: 20px;
  border-right: 1px solid #ccc; /* Add a border between the two containers */
  margin-left: 2rem;
  margin-right: 1rem;
}

.right-container {
  flex: 1;
  padding: 20px;
  margin-left: 1rem;
  margin-right: 2rem;
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

#btn-handler {
  margin: 1rem;
}
</style>
