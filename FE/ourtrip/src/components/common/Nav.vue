<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useMenuStore } from '@/stores/menu';
import { storeToRefs } from 'pinia';

const root = ref(`${import.meta.env.VITE_APP_CLIENT_URI}`);
const router = useRouter();
const menuStore = useMenuStore();

const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;

const logout = () => {
  // console.log('로그아웃!!!!');
  changeMenuState();
  sessionStorage.removeItem('accessToken');
  sessionStorage.removeItem('refreshToken');
  router.push({ name: 'home' });
  window.location.reload();
};
</script>

<template>
  <nav class="navbar">
    <div class="navbar__logo">
      <i class="fab fa-accusoft"></i>
      <router-link :to="{ name: 'home' }">OurTrip</router-link>
    </div>

    <ul class="navbar__menu">
      <li class="nav-item">
        <router-link :to="{ name: 'place-location' }" class="nav-link">PLACELIST 만들기</router-link>
      </li>

      <li class="nav-item">
        <router-link :to="{ name: 'list-open' }" class="nav-link">공개 PLACELIST</router-link>
      </li>

      <!-- <li><a href="">Home</a></li> -->
      <template v-for="menu in menuList" :key="menu.routeName">
        <template v-if="menu.show">
          <template v-if="menu.routeName === 'user-logout'">
            <li class="nav-item">
              <router-link to="/" @click.prevent="logout" class="nav-link">{{
                menu.name
              }}</router-link>
            </li>
          </template>

          <template v-else>
            <li class="nav-item">
              <router-link :to="{ name: menu.routeName }" class="nav-link">{{
                menu.name
              }}</router-link>
            </li>
          </template>
        </template>
      </template>
    </ul>
  </nav>
</template>

<style scoped>
:root {
  --text-color: #f0f4f5;
  --background-color: #868686;
  --accent-color: #91684b;
}

body {
  margin: 0px;
  font-family: 'Open sans';
}

a {
  text-decoration: none;
  color: white;
}

.navbar {
  position: sticky;
  top: 0;
  /* width: 100% */
  left: 0;
  right: 0;
  display: flex;
  z-index: 100;
  justify-content: space-between;
  align-items: center;
  background: #5d5d5d;
  padding: 8px 12px;
  /* margin: 0 0 3rem 0; */
}

.navbar__logo {
  font-size: 24px;
  color: white;
}

.navbar__logo i {
  color: #d49466;
}

.navbar__menu {
  display: flex;
  list-style: none;
  padding-left: 0px;
}

.navbar__menu li {
  padding: 8px 12px;
}

.navbar__menu :hover {
  border-radius: 4px;
}

.navbar__icons {
  list-style: none;
  color: white;
  display: flex;
  padding-left: 0px;
}

.navbar__icons li {
  padding: 8px 12px;
}

.navbar__toogleBtn {
  display: none;
  position: absolute;
  right: 32px;
  font-size: 24px;
  color: #d49466;
}

@media screen and (max-width: 768px) {
  .navbar {
    flex-direction: column;
    align-items: flex-start;
    padding: 8px 24px;
  }

  .navbar__menu {
    display: none;
    flex-direction: column;
    align-items: center;
    width: 100%;
  }

  .navbar__menu li {
    width: 100%;
    text-align: center;
  }

  .navbar__icons {
    display: none;
    justify-content: center;
    width: 100%;
  }

  .navbar__toogleBtn {
    display: block;
  }

  .navbar__menu.active,
  .navbar__icons.active {
    display: flex;
  }
}
</style>
