<script setup></script>

<template>
  <div>home</div>
  <!--container 1-->
  <container>
    <div id="main1">
      <h1>
        나만의 PLACE를 찾아 <br />
        PLACELIST를 만들어보세요!
      </h1>
      <img id="main-img1" src="@/assets/img/main1.png" alt="main-img1" width="700" />
    </div>
  </container>

  <!--container 2-->
  <container>
    <div id="sub-text">
      <h1>
        PLACE를 골라 <br />
        PLACELIST 속으로!
      </h1>
      <v-btn variant="outlined" rounded="xl"> 시작하기 </v-btn>
    </div>

    <v-card id="main-card" class="mx-auto" max-width="400">
      <v-card-item>
        <div class="mb-1">
          <h1>
            검색으로 찾는 <br />
            나만의 플레이스
          </h1>
          <img id="main-img1" src="@/assets/img/main2.png" alt="main-img1" width="300" />
        </div>
      </v-card-item>
    </v-card>
  </container>
</template>

<style scoped>
.container {
  width: 70%;
  height: 200px;
  overflow: hidden;
}
.mb-1 h1 {
  line-height: 1.4;
}
#main1 {
  text-align: center;
  padding: 30px;
}
#main-img1 {
  max-width: 100%;
  height: auto !important;
  padding: 10px;
}
#main-card {
  border: 2px solid #000;
  border-radius: 30px;
  padding: 20px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}
</style>
