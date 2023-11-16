import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/user",
      name: "user",
      component: UserView,
      redirect: { name: "" },
      children: [
        {
          path: "join",
          name: "user-join",
          component: "",
        },
        {
          path: "login",
          name: "user-login",
          component: "",
        },
        {
          path: "find",
          name: "user-find",
          component: "",
        },
      ],
    },
    {
      path: "/mypage",
      name: "mypage",
      component: "",
      redirect: { name: "" },
      children: [
        {
          path: "info",
          name: "mypage-info",
          component: "",
        },
      ],
    },
    {
      path: "place",
      name: "place",
      component: "",
      redirect: { name: "" },
      children: [
        {
          path: "location",
          name: "place-location",
          component: "",
        },
        {
          path: "title",
          name: "place-title",
          component: "",
        },
        {
          path: "cover",
          name: "place-cover",
          component: "",
        },
        {
          path: "open",
          name: "place-open",
          component: "",
        },
      ],
    },
    {
      path: "list",
      name: "list",
      component: "",
      redirect: { name: "" },
      children: [
        {
          path: "my",
          name: "list-my",
          component: "",
        },
        {
          path: "our",
          name: "list-our",
          component: "",
        },
        {
          path: "open",
          name: "list-open",
          component: "",
        },
        {
          path: "detail/:place_id",
          name: "list-detail",
          component: "",
        },
      ],
    },
  ],
});

export default router;
