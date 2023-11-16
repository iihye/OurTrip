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

export { userConfirm, findById, tokenRegeneration, logout, userJoinApi };
