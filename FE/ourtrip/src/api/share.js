import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function addShareApi(param, success, fail) {
  await local.post(`share/add/`, param).then(success).catch(fail);
}

export { addShareApi };
