<script setup>
defineProps({ list: Object });
</script>

<template>
  <div class="list-item">
    
    <div class="info-container">
      <router-link :to="{ name: 'list-detail', params: { listno: list.listNo } }" class="list-name">
        <img :src="list.listImg" :alt="list.listName" class="list-img">
        <div class="list-name"> {{ list.listName }}</div>
        <div class="list-id">{{ list.userId }}</div>
      </router-link>
    </div>

  </div>
</template>

<style scoped>
.list-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin: 10px;
  width: calc(33.33% - 20px);
  box-sizing: border-box;
}

.info-container {
  text-align: center;
}

.list-name {
  font-size: 24px;
  font-weight: bold;
  text-decoration: none;
  color: #333;
}

.list-id{
  font-size: 18px;
}

.list-img {
  width: 100%; /* 가로 폭을 100%로 설정 */
  height: auto; /* 세로 크기를 자동으로 조절하여 비율 유지 */
  object-fit: cover; /* 이미지를 가득 채우도록 설정 */
  border-radius: 8px; /* 이미지의 모서리를 둥글게 만듦 */
}

</style>
