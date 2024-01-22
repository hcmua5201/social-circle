<template>
  <div class="container">
    <header>
      <button @click="goShouYe">取消</button>
    </header>
    <main>
      <h1>用户注册</h1>
      <div class="info">

        <div class="userImg">
          <el-upload
              class="avatar-uploader"
              :http-request="upload"
              :show-file-list="false"
              :before-upload="beforeAvatarUpload"
          >
            <img :src="this.registerForm.avatar" class="avatar" />
          </el-upload>
        </div>

        <div class="line">
          <div class="left">用户名</div>
          <input v-model="this.registerForm.username" type="text" class="right" placeholder="用户名">
        </div>

        <div class="line">
          <div class="left">密码</div>
          <input v-model="this.registerForm.password" type="text" @input="selectUserImg" class="right" placeholder="请填写密码">
        </div>

        <div class="line">
          <div class="left">邮箱</div>
          <input v-model="this.registerForm.email" type="text"  class="right" placeholder="请填写邮箱">
        </div>

        <div class="line VerificationCode">
          <div class="left">验证码</div>
          <input v-model="this.registerForm.verificationCode" type="text" class="right" placeholder="请填写邮箱验证码">
          <button @click="getVerificationCode" :disabled="verificationCodeBtnDisabled">{{ verificationCodeBtnText }}</button>
        </div>

      </div>

      <div class="tips">
        <input type="checkbox" v-model="checked">我已阅读并同意 <a @click="showTips">&lt;&lt;阿巴阿巴阿巴协议&gt;&gt;</a>
      </div>

      <div class="register">
        <button @click="register" :disabled="!checked">注册</button>
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

import axios from "axios";

export default {
  data(){
    return {
      registerForm: {
        avatar: '/src/assets/nullUser.png',
        username: '',
        password: '',
        email: '',
        verificationCode: ''
      },
      fullEmail: '',
      verificationCodeBtnDisabled: false,
      verificationCodeBtnText: '获取验证码',
      verificationCodeTimeout: null,
      checked:false
    }
  },
  methods:{
    goShouYe(){
      this.$router.push('/')
    },
    showTips(){
      this.$message.info('根据阿巴阿巴协议规定，你是🐖！！！')
    },
    upload(file) {
      // this.beforeAvatarUpload()
      const formData = new FormData()
      formData.append('smfile', file.file)
      axios.defaults.baseURL='/sms'
      axios.post('/api/v2/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': 'a1GaFmvrVYXdCzFM4AG5In2rjkwaAvFQ'
        }
      }).then((res)=>{
        console.log(res.data)
        if (res.data.success === true){
          this.$message.success("上传成功")
          this.avatar=res.data.data.url
        }else {
          this.$message.info("头像已存在，但不影响使用，")
          this.registerForm.avatar=res.data.images
        }

      })
    },  //文件上传
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 或 PNG 格式！');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB！');
      }

      return isJPG && isLt2M;
    },
    getVerificationCode(){
      if (this.registerForm.email.includes('@qq.com')) {
        this.fullEmail = this.registerForm.email ;
        // 发送axios请求，携带参数fullEmail
        // 模拟成功后，你可以将后端返回的验证码保存到前端，然后进行倒计时等操作
        axios.defaults.baseURL='/api'
        axios({
          method:'get',
          url:"/email/sendEmail/" + this.fullEmail,
        }).then((response)=>{
          // console.log(response.data)
          if (response.data.code===222){
            // 设置按钮不可点击
            this.verificationCodeBtnDisabled = true;
            // 设置验证码过期倒计时
            let countdown = 60;
            this.verificationCodeBtnText = `${countdown}秒后重试`;
            this.verificationCodeTimeout = setInterval(() => {
              countdown--;
              this.verificationCodeBtnText = `${countdown}秒后重试`;

              if (countdown <= 0) {
                // 倒计时结束，恢复按钮状态
                clearInterval(this.verificationCodeTimeout);
                this.verificationCodeBtnDisabled = false;
                this.verificationCodeBtnText = '获取验证码';
              }
            }, 1000);
            this.$message.success(response.data.msg)
          }else {
            this.$message.error(response.data.msg)
          }
        })
      } else {
        this.fullEmail = this.registerForm.email + '@qq.com';
        axios.defaults.baseURL='/api'
        axios({
          method:'get',
          url:"/email/sendEmail/" + this.fullEmail,
        }).then((response)=>{
          // console.log(response.data)
          if (response.data.code===222){
            // 设置按钮不可点击
            this.verificationCodeBtnDisabled = true;
            // 设置验证码过期倒计时
            let countdown = 60;
            this.verificationCodeBtnText = `${countdown}秒后重试`;
            this.verificationCodeTimeout = setInterval(() => {
              countdown--;
              this.verificationCodeBtnText = `${countdown}秒后重试`;

              if (countdown <= 0) {
                // 倒计时结束，恢复按钮状态
                clearInterval(this.verificationCodeTimeout);
                this.verificationCodeBtnDisabled = false;
                this.verificationCodeBtnText = '获取验证码';
              }
            }, 1000);
            this.$message.success(response.data.msg)
          }else {
            this.$message.error(response.data.msg)
          }
        })
      }
    },
    register(){
      //此处执行注册逻辑，
      if (this.validateForm()) {
        if (this.registerForm.email.includes('@qq.com')) {
          this.fullEmail = this.registerForm.email;
        }else {
          this.fullEmail = this.registerForm.email + '@qq.com';
        }
        this.registerForm.email = this.fullEmail
        // 先封装数据
        const registerData = Object.assign({}, this.registerForm);
        //先判断验证码正确不
        axios({
          method:'post',
          url:'/email/QueryVerificationCode',
          data:{
            "email":registerData.email,"math":registerData.verificationCode
          }
        }).then((response)=>{
          console.log(response.data)
          if (response.data.code===444){
            this.$message.error(response.data.msg)
          }else {
            //验证码正确，注册,删除验证码的键
            delete registerData.verificationCode;
            axios({
              method:'post',
              url:'/api/users/add',
              data:registerData
            }).then((response)=>{
              console.log(response.data)
              if (response.data.code===222){
                this.registerForm=''
                this.$message.success(response.data.msg)
                //注册成功后应跳转进入首页
                this.goShouYe();
              }else {
                this.$message.error(response.data.msg)
              }
            })
          }
        })

      }
    },
    validateForm() {
      // 校验表单数据，返回true表示验证通过，false表示验证失败
      if (!this.registerForm.username || this.registerForm.username.length > 10) {
        this.$message.error('请输入有效的用户名（不超过10位）');
        return false;
      }
      if (!this.registerForm.password || this.registerForm.password.length < 5) {
        this.$message.error('密码不能少于5位');
        return false;
      }
      if (!this.registerForm.verificationCode) {
        this.$message.error('请输入验证码');
        return false;
      }
      return true;
    },
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
  height: 80vh;
  margin-top: 8%;
}

main h1{
  font-size: 24px;
  font-weight: 500;
  //margin-top: 10px;
  text-align: center;
  text-indent: 0;
}

main div.info{
  width: 100%;
  height: 400px;
  //margin-top: 60px;
}
main div.userImg{
  width: 100%;
  height: 100px; /* 调整高度为你需要的值 */
  display: flex;
  align-items: center;
  justify-content: center;
}
main div.userImg img{
  display: block;
  width: 80px;
  height: 80px;
  cursor: pointer;
}

main div.line{
  width: 100%;
  height: 40px;
  border: none;
  margin-top: 20px;
  border-bottom: 1px solid lightgray;
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
  margin-left: 3%;
}
main div.line.VerificationCode button{
  display: block;
  float: right;
  margin-right: 10px;
  height: 40px;
  border-radius: 5px;
  border: none;
  background-color: #07c160;
  color: #fff;
  padding: 0 10px;
}

main div.tips{
  width: 100%;
  height: 30px;
  color: #000;
  margin-top: -30px;
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
  height: 76px;
  margin-top: 80px;
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
main div.register button:disabled{
  background-color: #7ad7a6;
}

main div.register button:hover{
  background-color: #07c160;
}

footer{
  width: 100%;
  height: 4vh;
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