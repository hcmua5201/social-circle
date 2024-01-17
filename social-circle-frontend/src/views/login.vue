<template>
  <div class="login-container">
    <h1>WelCome Login1</h1>
    <el-form ref="loginForm"  :model="loginForm" label-width="0">
      <el-form-item>
        <el-input v-model="loginForm.usernameOrEmail" placeholder="用户名/邮箱" @keydown.enter="login"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="loginForm.password" placeholder="密码" @keydown.enter="login"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login" style="width: 100%;">登录</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="goToRegister" plain style="width: 100%;">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.baseURL='/api'
export default {
  data() {
    return {
      loginForm: {
        usernameOrEmail: '',
        password: ''
      }
    };
  },
  methods: {
    login() {
      // 检查输入框是否为空
      if (!this.loginForm.usernameOrEmail || !this.loginForm.password) {
        this.$message.error('用户名和密码不能为空');
        return;
      }

      // 判断是邮箱还是密码登录
      let requestData = {};
      const lowerCaseInput = this.loginForm.usernameOrEmail.toLowerCase();
      if (lowerCaseInput.includes('@qq.com')) {
        // 邮箱登录
        requestData = { email: this.loginForm.usernameOrEmail, password: this.loginForm.password };

        axios({
          method:'post',
          url:'/api/users/loginByEmailAndPwd',
          params:{email:requestData.email,password:requestData.password}
        }).then((response)=>{
          // console.log(response.data.code)
          if (response.data.code===222){
            this.$message.success(response.data.msg)
            localStorage.setItem("login_info",JSON.stringify(response.data.obj))
            sessionStorage.setItem("userID",JSON.stringify(response.data.obj.userId))
            this.$router.push('/index');
          }else {
            this.$message.error(response.data.msg)
          }
        })
      } else {
        // 用户名密码登录
        requestData = { username: this.loginForm.usernameOrEmail, password: this.loginForm.password };
        axios({
          method:'post',
          url:'/api/users/loginByUserAndPwd',
          params:{username:requestData.username,password:requestData.password}
        }).then((response)=>{
          // console.log(response.data.code)
          if (response.data.code===222){
            this.$message.success(response.data.msg)
            localStorage.setItem("login_info",JSON.stringify(response.data.obj))
            sessionStorage.setItem("userID",JSON.stringify(response.data.obj.userId))
            this.$router.push('/index');
          }else {
            this.$message.error(response.data.msg)
          }
        })

      }
    },
    goToRegister() {
      // 跳转到注册页面，你需要在路由中配置/register
      this.$router.push('/register');
    }
  }
};
</script>

<style scoped>
.login-container {

  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
