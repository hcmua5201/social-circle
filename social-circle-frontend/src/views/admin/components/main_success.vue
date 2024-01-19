<template>
  <div class="main_success">
    <h1>Welcome, {{ userInfo.nickname }}</h1>
    <p>Current Time: {{ currentTime }}</p>
  </div>
</template>

<script>
import axios from "axios";
import {ElNotification} from "element-plus";
import {h} from "vue";

export default {
  data() {
    return {
      userInfo: JSON.parse(localStorage.getItem('login_info')),
      currentTime: ''
    };
  },
  methods: {
    getIpInfo(){
      axios({
        method:"get",
        url:'https://api.vvhan.com/api/getIpInfo',
    }).then((response)=>{
        console.log(response.data.info)
        ElNotification({
          title: 'Title',
          message: h('i', { style: 'color: teal' }, "欢迎您！来自运营商为"+response.data.info.lsp+"的用户"),
          offset: 200,
        })
      })
    },
    getCurrentTime() {
      const now = new Date();
      const hours = now.getHours().toString().padStart(2, '0');
      const minutes = now.getMinutes().toString().padStart(2, '0');
      const seconds = now.getSeconds().toString().padStart(2, '0');
      return `${hours}:${minutes}:${seconds}`;
    }
  },
  created() {
    this.getIpInfo()
    const id = sessionStorage.getItem('userID');
    if (id==null){
      this.$message.error("尚未登录，请登录")
      this.$router.push('/')
    }
  },
  mounted() {
    this.currentTime = this.getCurrentTime();

    setInterval(() => {
      this.currentTime = this.getCurrentTime();
    }, 1000);
  }
};
</script>

<style scoped>
.main_success {
  text-align: center;
  margin-top: 20px;
  font-size: 64px;
}
</style>
