import { ref } from "vue";
import { defineStore } from "pinia";

export const useMenuStore = defineStore("menuStore", () => {
  const menuList = ref([
    { name: "내 PLACELIST", show: false, routeName: "home" },
    { name: "공유 PLACELIST", show: false, routeName: "home" },
    { name: "로그인", show: true, routeName: "user-login" },
    { name: "회원가입", show: true, routeName: "user-join" },
    { name: "내정보", show: false, routeName: "mypage" },
    { name: "로그아웃", show: false, routeName: "user-logout" },
  ]);

  const changeMenuState = () => {
    // console.log('메뉴 바꿈!!!');
    menuList.value = menuList.value.map((item) => ({ ...item, show: !item.show }));
  };
  return {
    menuList,
    changeMenuState,
  };
});
