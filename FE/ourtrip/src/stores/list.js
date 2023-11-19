import { ref } from "vue";
import { defineStore } from "pinia";
import { myListApi, shareListApi, openListApi, detailApi } from "@/api/list";
import { httpStatusCode } from "@/util/http-status";

export const useListStore = defineStore("listStore", () => {
  const myListRes = ref([]);
  const shareListRes = ref([]);
  const openListRes = ref([]);
  const detailRes = ref([]);

  const detailList = async (listno) => {
    await detailApi(
      listno,
      (response) => {
        // console.log('response');
        // console.log(response.data.list);
        if (response.status === httpStatusCode.OK) {
          detailRes.value = response.data.list;
        }
      },
      async (error) => {
        // console.log(error);
        console.log("[error] loading detail...");
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
        console.log("[error] loading myList...");
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
        console.log("[error] loading shareList...");
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
        console.log("[error] loading openList...");
      }
    );
  };

  return {
    myListRes,
    shareListRes,
    openListRes,
    detailRes,
    myList,
    shareList,
    openList,
    detailList,
  };
});
