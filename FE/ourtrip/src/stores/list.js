import { ref } from 'vue';
import { defineStore } from 'pinia';
import {
  myListApi,
  shareListApi,
  openListApi,
  listDetailApi,
  placesApi,
  findIdApi,
} from '@/api/list';
import { httpStatusCode } from '@/util/http-status';

export const useListStore = defineStore('listStore', () => {
  const myListRes = ref([]);
  const shareListRes = ref([]);
  const openListRes = ref([]);
  const listDetailRes = ref({});
  const placeRes = ref([]);
  const findIdRes = ref([]);

  const listDetail = async (listno) => {
    await listDetailApi(
      listno,
      (response) => {
        // console.log(response);
        if (response.status === httpStatusCode.OK) {
          listDetailRes.value = response.data;
        }
      },
      async (error) => {
        console.log('[error] loading listDetail...');
      }
    );
  };

  const placeList = async (listno) => {
    await placesApi(
      listno,
      (response) => {
        // console.log('response');
        // console.log(response.data.list);
        if (response.status === httpStatusCode.OK) {
          placeRes.value = response.data;
        }
      },
      async (error) => {
        // console.log(error);
        console.log('[error] loading placeList...');
      }
    );
  };

  const myList = async (userId) => {
    await myListApi(
      userId,
      (response) => {
        // console.log('response');
        // console.log(response.data.list);
        if (response.status === httpStatusCode.OK) {
          myListRes.value = response.data.list;
        }
      },
      async (error) => {
        // console.log(error);
        console.log('[error] loading myList...');
      }
    );
  };

  const shareList = async (userId) => {
    await shareListApi(
      userId,
      (response) => {
        // console.log('response');
        // console.log(response.data.list);
        if (response.status === httpStatusCode.OK) {
          shareListRes.value = response.data.list;
        }
      },
      async (error) => {
        // console.log(error);
        console.log('[error] loading shareList...');
      }
    );
  };

  const openList = async () => {
    await openListApi(
      (response) => {
        // console.log('response');
        // console.log(response.data.list);
        if (response.status === httpStatusCode.OK) {
          openListRes.value = response.data.list;
        }
      },
      async (error) => {
        // console.log(error);
        console.log('[error] loading openList...');
      }
    );
  };

  const findId = async () => {
    await findIdApi(
      (response) => {
        // console.log('response');
        // console.log(response.data.list);
        if (response.status === httpStatusCode.OK) {
          findIdRes.value = response.data.list;
        }
      },
      async (error) => {
        // console.log(error);
        console.log('[error] loading findId...');
      }
    );
  };

  return {
    myListRes,
    shareListRes,
    openListRes,
    listDetailRes,
    placeRes,
    findIdRes,
    myList,
    shareList,
    openList,
    listDetail,
    placeList,
    findId,
  };
});
