import { ref } from 'vue';
import { defineStore } from 'pinia';
import { addShareApi, findShareApi } from '@/api/share';
import { httpStatusCode } from '@/util/http-status';

export const useShareStore = defineStore('shareStore', () => {
  const findShareRes = ref([]);

  const addShare = async (param) => {
    // console.log(param);
    await addShareApi(
      param,
      (response) => {
        // console.log('성공')
      },
      async (error) => {
        console.log('[error] add shareList');
      }
    );
  };

  const findShare = async (param) => {
    // console.log(param);
    await findShareApi(
      param,
      (response) => {
        findShareRes.value = response.data.list;
      },
      async (error) => {
        console.log('[error] find shareList');
      }
    );
  };

  return { addShare, findShareRes, findShare };
});
