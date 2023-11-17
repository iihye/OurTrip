import { ref } from 'vue';
// import { useRouter } from 'vue-router';
import { defineStore } from 'pinia';
import { myListApi, shareListApi, openListApi } from '@/api/list';
import { httpStatusCode } from '@/util/http-status';

export const useListStore = defineStore('listStore', () => {
  //   const router = useRouter();

  const myListRes = ref([]);
  const shareListRes = ref([]);
  const openListRes = ref([]);

  const openList = async () => {
    console.log('openList를 실행합니다~~');
    await openListApi(
      (response) => {
        console.log('response');
        console.log(response.data.list);
        if (response.status === httpStatusCode.OK) {
          openListRes.value = response.data.list;
        }
      },
      async (error) => {
        console.log(error);
      }
    );
  };

  return { myListRes, shareListRes, openListRes, openList };
});
