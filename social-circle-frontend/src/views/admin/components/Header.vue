

<template>
  <el-menu
      class="el-menu-demo"
      mode="horizontal"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
  >
    <span style="font-size: 25px; top: 10px;bottom:0;position: absolute;left: 25px; color: #5cd2a9">朋友圈后台管理平台</span>
    <el-button  @click="logout" style="position: fixed;left: 85%;top: 15px;color: #4ad5a5" text>退出</el-button>
  </el-menu>
</template>

<script>


export default {
  methods:{
    logout(){
      this.$confirm("此操作永久删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
        this.$router.push('/')
        sessionStorage.removeItem('userID');
        localStorage.removeItem("login_info")
        this.$message.success("退出成功！")
      }).catch(() => {
        this.$message.info("取消操作");
      });
    }
  }
}
</script>

<style scoped>

</style>