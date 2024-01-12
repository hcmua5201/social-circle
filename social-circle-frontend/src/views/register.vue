<template>
  <div class="register-container">
    <el-form ref="registerForm" :model="registerForm" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="registerForm.username" placeholder="不超过10位"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="registerForm.nickname" placeholder="不超过15位"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="registerForm.password" placeholder="不少于5位" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="againpassword">
        <el-input type="password" v-model="registerForm.againpassword" placeholder="再次确认密码" show-password></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:8083/api/upload/image"
            :show-file-list="true"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
        >
          <el-button type="primary">点击上传头像</el-button>
        </el-upload>
        <el-image
            v-if="registerForm.avatar"
            style="width: 100px; height: 100px; margin-top: 10px;"
            :src="registerForm.avatar"
            fit="cover"
        ></el-image>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="registerForm.email" placeholder="请输入邮箱前缀">
          <template v-slot:append>
            <span>@qq.com</span>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="verificationCode">
        <el-button @click="getVerificationCode">获取验证码</el-button>
        <el-input v-model="registerForm.verificationCode" placeholder="请输入验证码"></el-input>

      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register">提交注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      registerForm: {
        username: '',
        nickname: '',
        password: '',
        againpassword: '',
        avatar: '',
        email: '',
        verificationCode: ''
      },
      fullEmail:''
    };
  },
  methods: {
    handleAvatarSuccess(response, file) {
      console.log(response)
      // 上传头像成功后的回调
      // 在实际项目中，你需要从后端接口返回的数据中获取头像的保存路径
      this.registerForm.avatar = response.obj; // 假设后端返回的数据中包含头像的url
    },
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
    getVerificationCode() {
      // 获取验证码的逻辑，这里是模拟的
      if (this.registerForm.email.includes('@qq.com')) {
         this.fullEmail = this.registerForm.email ;
        // 发送axios请求，携带参数fullEmail
        // 模拟成功后，你可以将后端返回的验证码保存到前端，然后进行倒计时等操作
        alert(this.fullEmail )
        alert('验证码已发送');
      } else {
        this.fullEmail = this.registerForm.email + '@qq.com';
        this.$message.error('请输入有效的邮箱');
      }
    },
    register() {
      // 注册按钮的逻辑，这里是模拟的
      if (!this.validateForm()) {
        return;
      }

      // 发送axios请求，携带注册表单数据
      // 在实际项目中，你需要根据后端接口的要求来配置axios请求
      // import axios from 'axios';
      // axios.post('/your-register-api', this.registerForm)
      //   .then(response => {
      //     // 处理注册成功的逻辑
      //   })
      //   .catch(error => {
      //     // 处理注册失败的逻辑
      //   });
    },
    validateForm() {
      // 校验表单数据，返回true表示验证通过，false表示验证失败
      if (!this.registerForm.username || this.registerForm.username.length > 10) {
        this.$message.error('请输入有效的用户名（不超过10位）');
        return false;
      }
      if (!this.registerForm.nickname || this.registerForm.nickname.length > 15) {
        this.$message.error('请输入有效的昵称（不超过15位）');
        return false;
      }
      if (!this.registerForm.password || this.registerForm.password.length < 5) {
        this.$message.error('密码不能少于5位');
        return false;
      }
      if (this.registerForm.password !== this.registerForm.againpassword) {
        this.$message.error('两次输入的密码不一致');
        return false;
      }
      if (!this.registerForm.email || !this.registerForm.email.includes('@qq.com')) {
        this.$message.error('请输入有效的QQ邮箱');
        return false;
      }
      if (!this.registerForm.verificationCode) {
        this.$message.error('请输入验证码');
        return false;
      }
      return true;
    }
  }
};
</script>

<style scoped>
.register-container {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.avatar-uploader {
  display: flex;
  align-items: center;
}
</style>
