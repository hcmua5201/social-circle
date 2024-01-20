<template>
  <div class="container">
    <div class="btnDiv">
      <button class="register" @click="goRegister">注册</button>
      <button class="login" @click="goLogin">登录</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {

    };
  },
  methods:{
    goRegister(){
      this.$router.push('/register');
    },
    goLogin(){
      this.$router.push('/login');
    }
  }
};
</script>


<style scoped>
.container{
  max-width: 500px;
  margin: 0 auto;
  min-height: 98vh;
  position: absolute;
  left:0;
  right: 0;
  background-image: url("../assets/BootImage.png");
  background-size: cover;
}

.container .btnDiv{
  width: 100%;
  height: 70px;
  //background-color: #ededed;
  position: absolute;
  bottom: 40px;
}

.container .btnDiv button{
  width: 40%;
  height: 46px;
  margin-top: 10px;
  position: absolute;
  border: none;
  cursor: pointer;

  box-sizing: border-box;
  font-weight: 700;
  font-size: 17px;
  text-align: center;
  text-decoration: none;
  line-height: 1.41176471;
  border-radius: 4px;
}
.container .btnDiv .register{
  background-color: #fff;
  left: 20px;
}
.container .btnDiv .login{
  background-color: #07c160;
  color: #fff;
  right: 20px;
}
</style>