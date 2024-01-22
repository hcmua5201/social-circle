<template>
  <!-- 动态发布页面 -->
  <!-- 页面总容器 -->
  <div class="container">
    <div class="top">
      <div class="header">
        <div class="left" @click="backIndex">取消</div>
        <div class="right" @click="publish">发布</div>
      </div>
      <textarea v-model="this.content" class="text" placeholder="这一刻的想法..."></textarea>
    </div>

    <div class="bottom">
      <div id="picInput">
        <el-upload
            class="avatar-uploader"
            list-type="picture"
            :http-request="upload"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            multiple
        >
          <img
              v-for="(image, index) in images"
              :key="index"
              :src="image.url"
              class="avatar"
              @click="viewImage(image.url)"
          />

          <img
              v-if="images.length < 9"
              class="avatar default-avatar"
              src="../assets/insertimg.png"
          />
        </el-upload>
      </div>
    </div>
    <span>🏙️地点:{{this.nowIpAddress}}</span>
    <el-button @click="updateAddress" text>重新定位</el-button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      content:'',
      images: [],
      nowIpAddress:''
    };
  },
  created() {
    this.getAddress();
  },

  methods: {
    upload(file) {
      if (this.images.length >= 9) {
        this.$message.warning("最多只能上传 9 张图片");
        return false; // 取消上传
      }

      const formData = new FormData();
      formData.append("smfile", file.file);
      axios.defaults.baseURL = "/sms";
      axios
          .post("/api/v2/upload", formData, {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: "a1GaFmvrVYXdCzFM4AG5In2rjkwaAvFQ",
            },
          })
          .then((res) => {
            if (res.data.success === true) {
              this.$message.success("上传成功");
              this.images.push({ url: res.data.data.url });
            } else {
              this.$message.info("图片已存在，但不影响使用，");
              this.images.push({ url: res.data.images });
            }
          });
    },
    beforeAvatarUpload(file) {
      if (this.images.length >= 9) {
        this.$message.warning("最多只能上传 9 张图片");
        return false; // 取消上传
      }

      const isJPG = file.type === "image/jpeg" || file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG 或 PNG 格式！");
      }
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 2MB！");
      }

      return isJPG && isLt2M;
    },
    getAddress() {
      axios({
        method: "get",
        url: "https://api.vvhan.com/api/getIpInfo",
      }).then((response) => {
        console.log(response.data);
        if (response.data.success === true){
          const country = response.data.info.country
          const prov = response.data.info.prov
          const city = response.data.info.city
          const address = country+'-'+prov+'-'+city
          this.nowIpAddress=address
          localStorage.setItem("login_address",JSON.stringify(address))
          console.log(address)
        }else {
          this.$message.error("当前登录状态ip异常,发表说说可能会有问题，请检查网络后重试")
          this.$router.push("/index");
        }
      });
    },
    updateAddress(){
      axios({
        method: "get",
        url: "https://api.vvhan.com/api/getIpInfo",
      }).then((response) => {
        console.log(response.data);
        if (response.data.success === true){
          const country = response.data.info.country
          const prov = response.data.info.prov
          const city = response.data.info.city
          if (prov==='-'||city==='-'){
             this.$message.info("请注意，非科学上网会影响定位呦")
          }
          const address = country+'-'+prov+'-'+city
          this.nowIpAddress=address
          localStorage.removeItem("login_address")
          localStorage.setItem("login_address",JSON.stringify(address))
          this.$message.success("定位已更新")
          console.log(address)
        }else {
          this.$message.error("当前登录状态ip异常,发表说说可能会有问题，请检查网络后重试")
          this.$router.push("/index");
        }
      });
    },
    backIndex() {
      this.$router.push("/index");
    },
    getCurrentTime() {
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0');
      const day = String(now.getDate()).padStart(2, '0');
      const hours = String(now.getHours()).padStart(2, '0');
      const minutes = String(now.getMinutes()).padStart(2, '0');
      const seconds = String(now.getSeconds()).padStart(2, '0');

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    publish() {
      const useriD=sessionStorage.getItem("userID")
      const userID = JSON.parse(useriD);
      const imageUrls = this.images.map((image) => image.url).join(',');
      // 获取当前时间并格式化
      const currentTime = this.getCurrentTime();
      const address=localStorage.getItem("login_address");
      const login_address = JSON.parse(address);
      let requestData = {};
      requestData = { userID: userID, content: this.content,image:imageUrls,time:currentTime,address:login_address };
      axios.defaults.baseURL='/api'
      axios({
        method:'post',
        url:'/api/posts/add',
        params:requestData
      }).then((response)=>{
        console.log(response.data)
        if (response.data.code===222){
          this.$message.success(response.data.msg)
          this.$router.push("/index");
        }else {
          this.$message.error(response.data.msg)
        }
      })
      // this.$router.push("/index");
    },
    viewImage(imageUrl) {
      // 处理点击图片预览的逻辑
      console.log("View Image: ", imageUrl);
    },
  },
  beforeRouteEnter(to, from, next) {
    // 添加背景色 margin:0;padding:0是为了解决vue四周有白边的问题
    document.querySelector("body").setAttribute("style", "margin:0;padding:0");
    next();
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.container {
  background-color: #fff;
  max-width: 500px;
  margin: 0 auto;
  min-height: 90vh;
  padding: 10px;
}

.avatar {
  width: calc(33.33% - 10px);
  height: auto;
  object-fit: cover;
  cursor: pointer;
  margin: 5px;
}

.default-avatar {
  background-image: url("../assets/insertimg.png");
  background-size: cover;
}

.container .top {
  border-bottom: 1px solid #bcbcbc;
  width: 94%;
  margin: 0 auto;
  height: 100%;
}

.container .top .header {
  width: 100%;
  height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.container .top .header .left {
  color: #000000;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
}

.container .top .header .right {
  color: #32b431;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
}

.container .top .header .right:hover {
  background-color: #999;
}

.container .top textarea.text {
  display: block;
  width: 100%;
  margin-top: 10px;
  border: none;
  color: #232323;
  font-size: 20px;
  outline: none;
  text-indent: 1em;
  min-height: 100px;
  max-width: 100%;
  min-width: 100%;
}

.container .bottom {
  width: 100%;
}

.container .bottom #picInput {
  width: 100%;
  margin-top: 10px;
}

>>>.avatar-uploader .el-upload--picture {
  display: block;
}

.container .bottom #picInput img {
  width: 30%;
  height: auto;
  margin-top: 10px;
  margin-left: 10px;
}
</style>
