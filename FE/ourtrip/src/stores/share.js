import { ref } from 'vue';
import { defineStore } from 'pinia';
import { addShareApi } from '@/api/share';
import { httpStatusCode } from '@/util/http-status';

export const useShareStore = defineStore('shareStore', () => {
  const isAdd = ref(false);

  const addShare = async (param) => {
    console.log(param);
    await addShareApi(
      param,
      (response) => {
        console.log('성공');
      },
      async (error) => {
        console.log('[error] add shareList');
      }
    );
  };

  return { isAdd, addShare };
});
