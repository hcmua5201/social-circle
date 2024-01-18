<script >



export default {
  components:{

  }
}
</script>

<template>
  <div class="index">
    <div class="common-layout">
      <el-container>
        <el-header style="padding: 0">
          <h1> 你好</h1>>
        </el-header>
        <el-container>
          <el-aside width="200px" >
            <h1> 你好</h1>>
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