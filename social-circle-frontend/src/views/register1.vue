<template>
  <div class="container">
    <header>
      <button @click="goShouYe">取消</button>
    </header>
    <main>
      <h1>用户注册</h1>
      <div class="info">

        <div class="userImg">
          <img :src="imgUrl" @click="uploadImg">
        </div>

        <div class="line">
          <div class="left">用户名</div>
          <input type="text" class="right" placeholder="何半仙">
        </div>

        <div class="line">
          <div class="left">密码</div>
          <input type="text" @input="selectUserImg" class="right" placeholder="请填写密码">
        </div>

        <div class="line">
          <div class="left">邮箱</div>
          <input type="text"  class="right" placeholder="请填写邮箱">
        </div>

        <div class="line VerificationCode">
          <div class="left">验证码</div>
          <input type="text" class="right" placeholder="请填写邮箱验证码">
          <button @click="getVerificationCode">获取验证码</button>
        </div>

      </div>

      <div class="tips">
        <input type="checkbox" @click="showTips">我已阅读并同意 <a>&lt;&lt;阿巴阿巴阿巴协议&gt;&gt;</a>
      </div>

      <div class="register">
        <button @click="register">注册</button>
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
// import '../assets/css/register.css';

export default {
  data(){
    return{
      imgUrl:'/src/assets/nullUser.png'
    }
  },
  methods:{
    goShouYe(){
      this.$router.push('/')
    },
    uploadImg(){
      //点击上传头像，替换上方默认图片url
      this.$message.success("上传头像")
    },
    register(){
      //此处执行注册逻辑，
      this.$message.success("注册")
    }
  }
}
</script>

<style scoped>
.container{
  max-width: 460px;
  margin: 0 auto;
  min-height: 98vh;
  position: absolute;
  left:0;
  right: 0;
  background-color: #ededed;
}

header button{
  font-size: 20px;
  border: none;
  color: #57be6a;
}
main{
  width: 100%;
  height: 60vh;
  margin-top: 100px;
  //background-color: gray;
}

main h1{
  font-size: 24px;
  font-weight: 500;
  margin-top: 10px;
  text-align: center;
}

main div.info{
  width: 100%;
  height: 400px;
  margin-top: 80px;
}
main div.userImg{
  width: 100%;
  height: 100px;
  margin-bottom: 30px;
}
main div.userImg img{
  display: block;
  width: 80px;
  height: 80px;
  margin: 0 auto;
  cursor: pointer;
}

main div.line{
  width: 100%;
  height: 50px;
  border: none;
  margin-top: 20px;
  border-bottom: 1px solid #999;
}
main div.line .left{
  float: left;
  line-height: 50px;
  height: 50px;
  padding-left: 20px;
  font-size: 18px;
}
main div.line input{
  display: block;
  float: right;
  width: 80%;
  line-height: 50px;
  height: 40px;
  margin-top: 5px;
  outline: none;
  font-size: 18px;
  border: none;
  background-color: transparent;
}

main div.line.VerificationCode input{
  width: 50%;
  float: left;
  margin-left: 40px;
}
main div.line.VerificationCode button{
  display: block;
  float: right;
  margin-right: 10px;
  height: 40px;
  border-radius: 5px;
  border: none;
  background-color: #07c160;
  color: #fff;padding: 0 10px;
}

main div.tips{
  width: 100%;
  height: 30px;
  color: #000;
  margin-top: 10px;
}
main div.tips input{
  font-size: 16px;
  font-weight: bold;
  border: none;
  cursor: pointer;
  margin-left: 20px;
}
main div.tips a{
  color: #667498;
  cursor: pointer;
}

main div.register{
  width: 100%;
  height: 80px;
  margin-top: 100px;
  //background-color: #fff;
}
main div.register button{
  display: block;
  width: 80%;
  height: 48px;
  background-color: #07c160;
  border-radius: 5px;
  border: none;
  font-weight: bold;
  color: #fff;
  font-size: 20px;
  margin: 0 auto;
  cursor: pointer;
}
main div.register button:hover{
  background-color: #07c160;
}

footer{
  width: 100%;
  height: 40px;
  position: absolute;
  bottom: 0;
  //background-color: #fff;
}
footer div.box{
  width: 200px;
  height: 30px;
  margin: 0 auto;
  line-height: 30px;
  text-align: center;
  color: #6c799b;
}
footer div.box div.left{
  width: 100px;
  float: left;
  border-right: 1px solid #959595;
  box-sizing: border-box;
  cursor: pointer;
}
footer div.box div.right{
  width: 100px;
  float: right;
  border-left: 1px solid #959595;
  box-sizing: border-box;
  cursor: pointer;
}
</style>