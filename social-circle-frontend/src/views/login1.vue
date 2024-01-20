<template>
  <div class="container">
    <header>
      <button @click="goShouYe">×</button>
    </header>
    <main>
      <h1><span>{{loginType}}</span>登录</h1>
      <div class="info">


        <div class="userImg">
          <img :src="UserImgUrl" >
        </div>

        <div class="line">
          <div class="left">账号</div>
          <input v-model="usernameOrEmail" type="text" class="right" :placeholder="loginType">
        </div>

        <div class="line">
          <div class="left">密码</div>
          <input v-model="password" @focus="selectUserImg" type="text" class="right" placeholder="请填写密码">
        </div>

      </div>

      <div class="switch">
        <button @click="switchLoginType">{{ loginTypeText }}</button>
      </div>

      <div class="login">
        <button @click="login" :disabled="usernameOrEmail===''||password===''">登录</button>
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
import axios from "axios";

  export default {
    data(){
      return{
        UserImgUrl:'/src/assets/icon.png',
        loginType:'用户名',
        loginTypeText:'用邮箱登录',
        usernameOrEmail:'',
        password:'',
      }
    },
    methods:{
      goShouYe(){
        this.$router.push('/')
      },
      switchLoginType(){
        this.usernameOrEmail =''
        this.password = ''
        this.loginType = this.loginType === '用户名' ? '邮箱' : '用户名'
        this.loginTypeText = this.loginTypeText === '用邮箱登录' ? '用户名登录' : '用邮箱登录'
      },
      selectUserImg(){
        //当用户输入完成用户名/邮箱后，进行一次用户查询，替换上方微信头像为用户头像，如未查到，则保留微信头像
        if ((this.loginType ==='用户名')&&this.usernameOrEmail!==''){
          // this.$message.success("用户名登录："+this.usernameOrEmail)
          axios("/api/users/searchAvatarByUsername/"+this.usernameOrEmail).then(res =>{
            this.UserImgUrl = res.data.obj
          })
        }else {
          // this.$message.success("邮箱登录："+this.usernameOrEmail)
          axios("/api/users/searchAvatarByEmail/"+this.usernameOrEmail).then(res =>{
            this.UserImgUrl = res.data.obj
          })
        }
      },
      login(){
        //此处执行登录逻辑，同时根据loginType的类型执行对应的登录请求
        if (this.loginType==='用户名'){
          // 用户名密码登录
          // 检查输入框是否为空
          if (!this.usernameOrEmail || !this.password) {
            this.$message.error('用户名和密码不能为空');
            return;
          }
          let requestData = {};
          requestData = { username: this.usernameOrEmail, password: this.password };
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
        }else {
          //  邮箱登录
          // 检查输入框是否为空
          if (!this.usernameOrEmail || !this.password) {
            this.$message.error('邮箱和密码不能为空');
            return;
          }
          let requestData = {};
          requestData = { email: this.usernameOrEmail, password: this.password };
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
        }

      }
    }
  }
</script>

<style scoped>

</style>