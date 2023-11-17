<script setup>
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
      <v-btn size="x-large" variant="outlined" rounded="xl" width="400"> 시작하기 </v-btn>
    </div>
    <div id="super-img1" >
      <img src="@/assets/img/main1.png" alt="main-img1" width="700" />
    </div>
  </container>

  <!--container 2-->
  <container id="main-container">
    <div id="main-flex-container">
      <div id="sub-container">
        <h1 id="sub-title">
          검색
        </h1>
        <h2 id="sub-content">
          검색으로 찾는, <br/>
          나만의 PLACE
        </h2>
      </div>
      <div id="sub-img-container">
        <img src="@/assets/img/main2.png" alt="main-img2" width="400" />
      </div>
    </div>
  </container>

  <container id="main-container">
    <div id="main-flex-container">
      <div id="sub-container">
        <h1 id="sub-title">
          저장
        </h1>
        <h2 id="sub-content">
          알아보기 쉽게, <br/>
          어울리는 커버 사진 찾기
        </h2>
      </div>
      <div id="sub-img-container">
        <img src="@/assets/img/main3.png" alt="main-img3" width="400" />
      </div>
    </div>
  </container>

  <container id="main-container">
    <div id="main-flex-container">
      <div id="sub-container">
        <h1 id="sub-title">
          공유
        </h1>
        <h2 id="sub-content">
          오직 친구에게만, <br/>
          나만의 PLACELIST 보여주기
        </h2>
      </div>
      <div id="sub-img-container">
        <img src="@/assets/img/main4.png" alt="main-img4" width="400" />
      </div>
    </div>
  </container>


  <!--container 3!-->
  <container id="foot-container">
    <div>
      <h1 id="foot-text">
        지금 바로, <br />
        PLACELIST를 만들어보세요!
      </h1>
    </div>
  </container>
</template>

<style scoped>
#super-container {
  width: 70%;
  height: 200px;
  overflow: hidden;
  text-align: center;
  padding: 30px;
}
#super-text{
  width: 100%;
  text-align: center;
  font-size: 66px;
  line-height: 1.4;
  color: black;
}
#super-img1 {
  max-width: 100%;
  height: auto !important;
  padding: 10px;
}
#main-btn{
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
  flex: 0 0 300px; /* Fixed width of 300px on the right */
}
#sub-title{
  width: 100%;
  text-align: left;
  font-size: 28px;
  line-height: 1.4;
  color: mediumpurple;
}
#sub-content{
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
#foot-text{
  width: 100%;
  text-align: center;
  padding-top: 100px;
  padding : 100px;
  font-size: 66px;
  line-height: 1.4;
  color: black;
}
#footer-btn-container {
  padding: 10px;
  margin : 2%;
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
