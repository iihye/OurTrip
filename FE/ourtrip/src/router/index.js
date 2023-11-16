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
    // {
    //   path:"/mypage",
    //   name:"mypage",
    //   component:"",
    //   children:[
    //     {
    //       path:"info"
    //       name:"mypage-info"
    //     }
    //   ]
    // }
  ],
});

export default router;
