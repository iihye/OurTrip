import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function userConfirm(param, success, fail) {
  // console.log('---------userConfirm start----------');
  // console.log('param', param);
  await local.post(`/user/login`, param).then(success).catch(fail);
  // console.log('---------userConfirm finish----------');
}

async function findById(userid, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken');
  await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers['refreshToken'] = sessionStorage.getItem('refreshToken'); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

async function userJoinApi(param, success, fail) {
  // console.log('----------userJoin start----------');
  await local.post(`user/join`, param).then(success).catch(fail);
  // console.log('----------userJoin finish----------');
}

async function userCheckApi(param, success, fail) {
  // console.log("----------userCheck start----------");
  await local.post(`user/check`, param).then(success).catch(fail);
  // console.log("----------userCheck finish----------");
}

async function userFindApi(param, success, fail) {
  await local.post(`user/find`, param).then(success).catch(fail);
}

async function userUpdateApi(param, success, fail) {
  await local.post(`user/update`, param).then(success).catch(fail);
}

async function userDeleteApi(userid, success, fail) {
  await local.delete(`user/delete/${userid}`).then(success).catch(fail);
}

export {
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  userJoinApi,
  userCheckApi,
  userFindApi,
  userUpdateApi,
  userDeleteApi,
};
