<script setup>
import { ref } from "vue";
import { useMenuStore } from "@/stores/menu";
import { storeToRefs } from "pinia";

const root = ref(`${import.meta.env.VITE_APP_CLIENT_URI}`);
const menuStore = useMenuStore();

const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;

const logout = () => {
  // console.log('로그아웃!!!!');
  changeMenuState();
  sessionStorage.removeItem("accessToken");
  sessionStorage.removeItem("refreshToken");
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
              <a class="nav-link" :href="`${root}/place/location`">만들기</a>
            </li>
            <li class="nav-item">
              <!-- Render content for when userId is not available -->
              <a class="nav-link" :href="`${root}/user/join`">공개 PLACELIST</a>
            </li>

            <template v-for="menu in menuList" :key="menu.routeName">
              <template v-if="menu.show">
                <template v-if="menu.routeName === 'user-logout'">
                  <li class="nav-item">
                    <router-link
                      to="/"
                      @click.prevent="logout"
                      class="nav-link"
                      >{{ menu.name }}</router-link
                    >
                  </li>
                </template>

                <template v-else>
                  <li class="nav-item">
                    <router-link
                      :to="{ name: menu.routeName }"
                      class="nav-link"
                      >{{ menu.name }}</router-link
                    >
                  </li>
                </template>
              </template>
            </template>
          </ul>
        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
