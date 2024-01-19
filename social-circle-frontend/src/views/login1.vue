<template>
  <div class="container">
    <header>
      <button @click="goShouYe">×</button>
    </header>
    <main>
      <h1><span>{{loginType}}</span>登录</h1>
      <div class="info">


        <div class="userImg">
          <img src="../assets/icon.png" >
        </div>

        <div class="line">
          <div class="left">账号</div>
          <input type="text" class="right" :placeholder="loginType">
        </div>

        <div class="line">
          <div class="left">密码</div>
          <input type="text" @input="selectUserImg" class="right" placeholder="请填写密码">
        </div>

      </div>

      <div class="switch">
        <button @click="switchLoginType">用邮箱登录</button>
      </div>

      <div class="login">
        <button @click="login">登录</button>
      </div>
    </main>
    <footer>
      <div class="box">
        <div class="left">找回密码</div>
        <div class="right">更多选项</div>
      </div>
    </footer>
  </div>
</template>

<script>
import '../assets/css/login.css';

  export default {
    data(){
      return{
        loginType:'用户名'
        // loginType:'邮箱'
      }
    },
    methods:{
      goShouYe(){
        this.$router.push('/')
      },
      switchLoginType(){
        this.loginType = this.loginType === '用户名' ? '邮箱' : '用户名'
      },
      selectUserImg(){
        //当用户输入完成用户名/邮箱后，进行一次用户查询，替换上方微信头像为用户头像，如未查到，则保留微信头像
      },
      login(){
        //此处执行登录逻辑，同时根据loginType的类型执行对应的登录请求
        this.$message.success("登录")
      }
    }
  }
</script>

<style scoped>

</style>