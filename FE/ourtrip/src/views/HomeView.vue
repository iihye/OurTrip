<script setup>
import Footer from '@/components/common/Footer.vue';
</script>

<template>
  <!--container 1-->
  <container id="super-container">
    <div>
      <h1 id="super-text">
        나만의 PLACE를 찾아 <br />
        PLACELIST를 만들어보세요!
      </h1>
    </div>
    <div id="footer-btn-container">
      <v-btn id="footer-btn" size="x-large" variant="outlined" rounded="xl" width="400"> 시작하기 </v-btn>
    </div>
    <div>
      <img id="super-img1" src="@/assets/img/main1.png" alt="main-img1" width="700" />
    </div>
  </container>

  <div id="parent-container">
    <font-awesome-icon :icon="['fas', 'chevron-down']" style="color: #787878" />
  </div>

  <!--container 2-->
  <container id="main-container">
    <div id="main-flex-container">
      <div id="sub-container">
        <h1 id="sub-title">검색</h1>
        <h2 id="sub-content">
          검색으로 찾는, <br />
          나만의 PLACE
        </h2>
      </div>
      <div id="sub-img-container">
        <img src="@/assets/img/main2.png" alt="main-img2" />
      </div>
    </div>
  </container>

  <container id="main-container">
    <div id="main-flex-container">
      <div id="sub-container">
        <h1 id="sub-title">저장</h1>
        <h2 id="sub-content">
          알아보기 쉽게, <br />
          어울리는 커버 사진 찾기
        </h2>
      </div>
      <div id="sub-img-container">
        <img src="@/assets/img/main3.png" alt="main-img3" />
      </div>
    </div>
  </container>

  <container id="main-container">
    <div id="main-flex-container">
      <div id="sub-container">
        <h1 id="sub-title">공유</h1>
        <h2 id="sub-content">
          오직 친구에게만, <br />
          나만의 PLACELIST 보여주기
        </h2>
      </div>
      <div id="sub-img-container">
        <img src="@/assets/img/main4.png" alt="main-img4" />
      </div>
    </div>
  </container>

  <div id="parent-container">
    <font-awesome-icon :icon="['fas', 'chevron-down']" style="color: #787878" />
  </div>

  <!--container 3!-->
  <container id="foot-container">
    <div>
      <h1 id="foot-text">
        지금 바로, <br />
        PLACELIST를 만들어보세요!
      </h1>
    </div>
  </container>

  <!-- <Footer></Footer> -->
</template>

<style scoped>
.container {
  width: 70%;
  height: 200px;
  overflow: hidden;
}
.v-btn{
  font-size: 20px;
}
#main-img1 {
  max-width: 100%;
  height: auto !important;
  padding: 10px;
}

hr {
  width: 30%;
  margin: 0 auto;
}
#parent-container {
  padding-top: 30px;
  text-align: center; /* 부모 요소에 text-align: center;를 적용하여 가운데 정렬 */
}
#super-container {
  width: 70%;
  height: 200px;
  overflow: hidden;
  text-align: center;
  padding: 30px;
}
#super-text {
  width: 100%;
  text-align: center;
  font-size: 66px;
  line-height: 1.4;
  color: black;
}
#super-img1 {
  max-width: 100%;
  height: auto;
  padding: 10px;
}
#main-btn {
  text-align: center; /* 텍스트를 수평 가운데 정렬합니다. */
  width: 150px;
  font-size: 16px;
  margin: 0 auto; /* 가운데 정렬을 위한 마진 설정 */
}
#main-container {
  display: flex;
  justify-content: center; /* Optional: to center the content horizontally */
  padding: 100px;
}

#main-flex-container {
  display: flex;
  width: 100%;
}

#sub-container {
  padding-left: 10%;
  flex: 1; /* Takes up remaining space on the left */
}

#sub-img-container {
  padding-right: 10%;
}
#sub-img-container img {
  width: 400px;
  max-width: 100%;
  height: auto; /* 세로 크기를 자동으로 조절하여 비율 유지 */
}
#sub-title {
  width: 100%;
  text-align: left;
  font-size: 28px;
  line-height: 1.4;
  color: mediumpurple;
}
#sub-content {
  width: 100%;
  text-align: left;
  margin-bottom: 50px;
  font-size: 50px;
  line-height: 1.4;
  color: black;
}
#foot-container {
  width: 70%;
  height: 200px;
  overflow: hidden;
  text-align: center;
  padding: 30px;
  margin-bottom: 200px;
}
#footer-btn:hover{
  background-color: black;
  color: white;
}
#foot-text {
  width: 100%;
  text-align: center;
  padding-top: 100px;
  padding: 100px;
  font-size: 66px;
  line-height: 1.4;
  color: black;
}
#footer-btn-container {
  padding: 10px;
  margin: 2%;
  flex: 0 0 300px; /* Fixed width of 300px on the right */
}

/* #sub-container{
  padding:200px;
  flex: 1;
}
#sub-container2{
  background-color: rgb(234, 225, 255);
  padding:200px;
  flex: 1;
}

#sub-img{
  flex: 0 0 300px;
}
#main2{
  padding:100px;
  display: flex;
} */
</style>
