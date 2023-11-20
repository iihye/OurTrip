import { ref } from 'vue';
import { defineStore } from 'pinia';
import { addShareApi, findShareApi } from '@/api/share';
import { httpStatusCode } from '@/util/http-status';

export const useShareStore = defineStore('shareStore', () => {
  const isAdd = ref(false);
  const isFind = ref(false);
  const findShareRes = ref([]);

  const addShare = async (param) => {
    console.log(param);
    await addShareApi(
      param,
      (response) => {
        console.log('성공');
        // if (findShareRes.value != []) {
        //   findShareRes.value = response.data.list;
        //   console.log(findShareRes.value.status);
        //   isAdd = true;

        //   console.log(isAdd.value);
        // } else {
        //   isAdd = false;
        // }
      },
      async (error) => {
        console.log('[error] add shareList');
        isAdd = false;
      }
    );
  };

  const findShare = async (param) => {
    // console.log('param: ' + param);
    await findShareApi(
      param,
      (response) => {
        // console.log(response.data.list);
        findShareRes.value = response.data.list;
      },
      async (error) => {
        console.log('[error] find shareList');
        // isFind = false;
      }
    );
    // console.log(isFind.value);
  };

  return { isAdd, addShare, isFind, findShareRes, findShare };
});
