<script >



import Header from "../components/Header.vue";
import Aside from "../components/Aside.vue";

export default {
  components:{
    Aside,
    Header

  }
}
</script>

<template>
  <div class="index">
    <div class="common-layout">
      <el-container>
        <el-header style="padding: 0">
          <Header></Header>
        </el-header>
        <el-container>
          <el-aside width="200px" >
           <Aside></Aside>
          </el-aside>
          <el-main class="main">
            <router-view></router-view>
          </el-main>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<style scoped>
.index{
  position: fixed;
  width: 100%;
  height: 100vh;
  top: 0;
  left: 0;
}
</style>