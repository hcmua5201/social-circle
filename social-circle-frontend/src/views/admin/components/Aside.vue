<script setup>


import {Document, Setting} from "@element-plus/icons-vue";
</script>

<template>



  <el-col :span="19">
    <el-menu
        style="height: 100vh"
        active-text-color="#ffd04b"
        background-color="#545c64"
        class="el-menu-vertical-demo"
        default-active="2"
        text-color="#fff"
        router
    >
        <el-menu-item-group >
          <el-menu-item index="/index">首页</el-menu-item>
        </el-menu-item-group>
      <el-menu-item index="/2" >
        <el-icon><document /></el-icon>
        <span>用户管理</span>
      </el-menu-item>
      <el-menu-item index="/q">
        <el-icon><setting /></el-icon>
        <span>qqq</span>
      </el-menu-item>
    </el-menu>
  </el-col>
</template>

<style scoped>

</style>
