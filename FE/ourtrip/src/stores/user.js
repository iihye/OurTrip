import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import {
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  userJoinApi,
  userCheckApi,
  userFindApi,
  userUpdateApi,
  userDeleteApi,
} from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

export const useMemberStore = defineStore("userStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const userInfo = ref(null);
  const isValidToken = ref(false);

  const isJoin = ref(false);
  const isCheck = ref(false);
  const isFind = ref("");
  const isUpdate = ref(false);
  const isDelete = ref(false);

  const userLogin = async (loginUser) => {
    await userConfirm(
      loginUser,
      (response) => {
        // console.log('login ok!!!!', response.status);
        // console.log("login ok!!!!", httpStatusCode.CREATE);
        if (response.status === httpStatusCode.CREATE) {
          let { data } = response;
          // console.log("data", data);
          let accessToken = data["access-token"];
          let refreshToken = data["refresh-token"];
          // console.log('accessToken', accessToken);
          // console.log('refreshToken', refreshToken);
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          sessionStorage.setItem("accessToken", accessToken);
          sessionStorage.setItem("refreshToken", refreshToken);
          // console.log('sessiontStorage에 담았다', isLogin.value);
        } else {
          // console.log('로그인 실패했다' + isLogin.value);
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
        }
      },
      (error) => {
        // console.log('login 실패!!!' + isLogin.value);
        // console.error(error);
        isLogin.value = false;
        isLoginError.value = true;
        isValidToken.value = false;
      }
    );
  };

  const getUserInfo = (token) => {
    let decodeToken = jwtDecode(token);
    // console.log('2. decodeToken', decodeToken);
    findById(
      decodeToken.userId,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          userInfo.value = response.data.userInfo;
          // console.log('3. getUserInfo data >> ', response.data);
        } else {
          console.log("유저 정보 없음!!!!");
        }
      },
      async (error) => {
        console.error(
          "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
          error.response.status
        );
        isValidToken.value = false;

        await tokenRegenerate();
      }
    );
  };

  const tokenRegenerate = async () => {
    console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("accessToken"));
    await tokenRegeneration(
      JSON.stringify(userInfo.value),
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let accessToken = response.data["access-token"];
          console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
          sessionStorage.setItem("accessToken", accessToken);
          isValidToken.value = true;
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          console.log("갱신 실패");
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await logout(
            userInfo.value.userid,
            (response) => {
              if (response.status === httpStatusCode.OK) {
                console.log("리프레시 토큰 제거 성공");
              } else {
                console.log("리프레시 토큰 제거 실패");
              }
              alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
              isLogin.value = false;
              userInfo.value = null;
              isValidToken.value = false;
              router.push({ name: "user-login" });
            },
            (error) => {
              console.error(error);
              isLogin.value = false;
              userInfo.value = null;
            }
          );
        }
      }
    );
  };

  const userLogout = async (userid) => {
    await logout(
      userid,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          isLogin.value = false;
          userInfo.value = null;
          isValidToken.value = false;
        } else {
          console.log("[error] user logout...");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const userJoin = async (joinUser) => {
    await userJoinApi(
      joinUser,
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          isJoin.value = true;
        } else {
          isJoin.value = false;
        }
      },
      (error) => {
        isJoin.value = false;
      }
    );
  };

  const userCheck = async (joinUser) => {
    await userCheckApi(
      joinUser,
      (response) => {
        // console.log(response.data.cnt);
        if (response.data.cnt === 0) {
          isCheck.value = true;
        } else {
          isCheck.value = false;
        }
      },
      (error) => {
        // console.log(error);
        isCheck.value = false;
      }
    );
  };

  const userFind = async (findUser) => {
    await userFindApi(
      findUser,
      (response) => {
        if (response.data.userPw != null) {
          isFind.value = response.data.userPw;
        } else {
          isFind.value = "";
        }
      },
      (error) => {
        console.log(error);
        isFind.value = "";
      }
    );
  };

  const userUpdate = async (updateUser) => {
    await userUpdateApi(
      updateUser,
      (response) => {
        if (response.data.message === "수정 성공") {
          isUpdate.value = true;
        } else {
          isUpdate.value = false;
        }
      },
      (error) => {
        console.log(error);
        isUpdate.value = false;
      }
    );
  };

  const userDelete = async (userid) => {
    await userDeleteApi(
      userid,
      (response) => {
        if (response.data.message === "삭제 성공") {
          isDelete.value = true;
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return {
    isLogin,
    isLoginError,
    isJoin,
    userInfo,
    isValidToken,
    userLogin,
    getUserInfo,
    tokenRegenerate,
    userLogout,
    userJoin,
    isCheck,
    userCheck,
    isFind,
    userFind,
    isUpdate,
    userUpdate,
    isDelete,
    userDelete,
  };
});
