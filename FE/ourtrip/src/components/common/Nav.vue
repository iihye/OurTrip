<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const root = ref(`${import.meta.env.VITE_APP_CLIENT_URI}`);
const userId = localStorage.getItem("userId");

const router = useRouter();

const logout = () => {
  localStorage.removeItem("userId");
  router.replace({ name: "home" });
  window.location.reload();
};
</script>

<template>
  <nav>
    <div class="container" style="width: 100%">
      <div id="navb">
        <!-- 로고 -->
        <div id="navb-logo" class="justify-content-center">
          <a class="navbar-brand" :href="`${root}/`">
            <!-- <img
              src="@/assets/img/logo.png"
              :href="`${root}/`"
              style="width: 100px"
              alt="logo image"
            /> -->
          </a>
        </div>

        <div id="navb-login">
          <ul class="navbar-nav">
            <li class="nav-item">
              <!-- Render content for when userId is not available -->
              <a class="nav-link" :href="`${root}/user/login`">만들기</a>
            </li>
            <li class="nav-item">
              <!-- Render content for when userId is not available -->
              <a class="nav-link" :href="`${root}/user/join`">공개PLACELIST</a>
            </li>

            <li class="nav-item">
              <!-- Render content for when userId is available -->
              <a class="nav-link" :href="`${root}/user/mypage`">내 PLACELIST</a>
            </li>
            <li class="nav-item">
              <!-- Render content for when userId is available -->
              <a class="nav-link" :href="`${root}/user/mypage`"
                >공유 PLACELIST</a
              >
            </li>
          </ul>
        </div>

        <!-- 로그인 정보 -->
        <div id="navb-login">
          <ul class="navbar-nav">
            <div v-if="!userId">
              <li class="nav-item">
                <!-- Render content for when userId is not available -->
                <a class="nav-link" :href="`${root}/user/login`">로그인</a>
              </li>
              <li class="nav-item">
                <!-- Render content for when userId is not available -->
                <a class="nav-link" :href="`${root}/user/join`">회원가입</a>
              </li>
            </div>
            <div v-else>
              <li class="nav-item">
                <!-- Render content for when userId is available -->
                <a class="nav-link" :href="`${root}/user/mypage`"
                  >{{ userId }} 님 반가워요!</a
                >
              </li>
              <li class="nav-item">
                <!-- Render content for when userId is available -->
                <a class="nav-link" @click="logout">로그아웃</a>
              </li>
            </div>
          </ul>
        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
