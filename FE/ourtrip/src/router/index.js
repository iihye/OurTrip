import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import UserView from '../views/UserView.vue';
import MypageView from '../views/MypageView.vue';
import PlaceView from '@/views/PlaceView.vue';
import ListView from '../views/ListView.vue';

import UserJoin from '@/components/user/UserJoin.vue';
import UserLogin from '@/components/user/UserLogin.vue';
import UserFind from '@/components/user/UserFind.vue';
import UserMypage from '@/components/user/UserMypage.vue';

import PlaceLocation from '@/components/place/PlaceLocation.vue';
import PlaceTitle from '@/components/place/PlaceTitle.vue';
import PlaceCover from '@/components/place/PlaceCover.vue';
import PlaceOpen from '@/components/place/PlaceOpen.vue';

import ListMy from '@/components/list/ListMy.vue';
import ListShare from '@/components/list/ListShare.vue';
import ListOpen from '@/components/list/ListOpen.vue';
import ListDetail from '@/components/list/ListDetail.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/user',
      name: 'user',
      component: UserView,
      redirect: { name: 'user-login' },
      children: [
        {
          path: 'join',
          name: 'user-join',
          component: UserJoin,
        },
        {
          path: 'login',
          name: 'user-login',
          component: UserLogin,
        },
        {
          path: 'find',
          name: 'user-find',
          component: UserFind,
        },
      ],
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MypageView,
      redirect: { name: 'mypage-info' },
      children: [
        {
          path: 'info',
          name: 'mypage-info',
          component: UserMypage,
        },
      ],
    },
    {
      path: '/place',
      name: 'place',
      component: PlaceView,
      redirect: { name: 'place-location' },
      children: [
        {
          path: 'location',
          name: 'place-location',
          component: PlaceLocation,
        },
        {
          path: 'title',
          name: 'place-title',
          component: PlaceTitle,
        },
        {
          path: 'cover',
          name: 'place-cover',
          component: PlaceCover,
        },
        {
          path: 'open',
          name: 'place-open',
          component: PlaceOpen,
        },
      ],
    },
    {
      path: '/list',
      name: 'list',
      component: ListView,
      redirect: { name: 'list-open' },
      children: [
        {
          path: 'my',
          name: 'list-my',
          component: ListMy,
        },
        {
          path: 'our',
          name: 'list-share',
          component: ListShare,
        },
        {
          path: 'open',
          name: 'list-open',
          component: ListOpen,
        },
        {
          path: 'detail/:listno',
          name: 'list-detail',
          component: ListDetail,
        },
      ],
    },
  ],
});

export default router;
