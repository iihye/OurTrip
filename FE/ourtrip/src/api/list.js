import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function myListApi(userId, success, fail) {
  // console.log('-----------myListApi start-----------');
  await local.get(`/list/my/${userId}`).then(success).catch(fail);
  // console.log('-----------myListApi start-----------');
}

async function shareListApi(userId, success, fail) {
  // console.log('-----------shareListApi start-----------');
  await local.get(`/list/share/${userId}`).then(success).catch(fail);
  // console.log('-----------shareListApi start-----------');
}

async function openListApi(success, fail) {
  // console.log('-----------openListApi start-----------');
  await local.get(`/list/open`).then(success).catch(fail);
  // console.log('-----------openListApi start-----------');
}

async function listDetailApi(listno, success, fail) {
  // console.log('-----------listDetailApi start-----------');
  await local.get(`/list/detail/${listno}`).then(success).catch(fail);
  // console.log('-----------listDetailApi start-----------');
}

async function placesApi(listno, success, fail) {
  // console.log('-----------placesApi start-----------');
  await local.get(`/list/places/${listno}`).then(success).catch(fail);
  // console.log('-----------placesApi start-----------');
}

async function findIdApi(findId, success, fail) {
  await local.get(`/list/find/${findId}`).then(success).catch(fail);
}

export { myListApi, shareListApi, openListApi, listDetailApi, placesApi, findIdApi };
