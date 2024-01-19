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
import '../assets/css/register.css';

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

</style>