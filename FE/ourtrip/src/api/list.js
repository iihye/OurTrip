import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function myListApi(param, success, fail) {
  console.log('-----------myListApi start-----------');
  await local.get(`/list/my/${userId}`).then(success).catch(fail);
  console.log('-----------myListApi start-----------');
}

async function shareListApi(param, success, fail) {
  console.log('-----------shareListApi start-----------');
  await local.get(`/list/share/${userId}`).then(success).catch(fail);
  console.log('-----------shareListApi start-----------');
}

async function openListApi(param, success, fail) {
  console.log('-----------openListApi start-----------');
  await local.get(`/list/open`).then(success).catch(fail);
  console.log('-----------openListApi start-----------');
}

export { myListApi, shareListApi, openListApi };
