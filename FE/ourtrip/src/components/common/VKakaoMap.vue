<script setup>
import { ref, watch, onMounted } from "vue";
import markerSvg from "../../assets/img/marker.svg?url";

const { VITE_APP_KAKAO_JS_KEY, VITE_APP_CLIENT_URI } = import.meta.env;
var map;
const positions = ref([]);
const markers = ref([]);
const overlays = ref([]);

const props = defineProps({ stations: Array, selectStation: Object });

watch(
  () => props.stations.value,
  () => {
    positions.value = [];
    initMap();
    props.stations.forEach((station) => {
      let obj = {};
      obj.latlng = new kakao.maps.LatLng(station.y, station.x);
      obj.title = station.place_name;

      positions.value.push(obj);
    });
    loadMarkers();
  },
  { deep: true }
);

watch(
  () => props.selectStation.value,
  () => {
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(props.selectStation.lat, props.selectStation.lng);

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
  },
  { deep: true }
);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${VITE_APP_KAKAO_JS_KEY}&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(36.3398, 127.394),
    level: 7,
  };
  map = new kakao.maps.Map(container, options);

  // loadMarkers();
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커 이미지를 생성합니다
  // const imgSrc = require("@/assets/map/marker.svg");
  const imgSrc = markerSvg;
  // 마커 이미지의 이미지 크기 입니다
  const imgSize = new kakao.maps.Size(24, 35);
  const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map,
      position: position.latlng,
      title: position.title,
      clickable: true,
      image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);

    kakao.maps.event.addListener(marker, "click", function () {
      new kakao.maps.InfoWindow({
        content: `<div class="wrap">
                  <div class="info">
                      <div class="title">
                          ${position.title}
                      </div>"
                      <div class="body">
                          <div class="img">
                              <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70">
                        </div>
                          <div class="desc">
                              <div class="ellipsis">제주특별자치도 제주시 첨단로 242</div>
                              <div class="jibun ellipsis">(우) 63309 (지번) 영평동 2181</div>
                              <div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">홈페이지</a></div>
                          </div>
                      </div>
                  </div>
              </div>`,
        map: map,
        position: marker.getPosition(),
        removable: true,
      });
    });
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <div id="map"></div>
</template>

<style>
#map {
  width: 720px;
  height: 720px;
}

/* 오버레이를 위한 css */
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -133px;
  margin-bottom: -78px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 3px 10px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}

.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 0px 0 0 90px;
  height: 90px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 0px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: 53px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
</style>
