<template>
  <!-- 动态发布页面 -->
  <!-- 页面总容器 -->
  <div class="container">
    <div class="top">
      <div class="header">
        <div class="left" @click="backIndex">取消</div>
        <div class="right" @click="publish">发布</div>
      </div>
      <textarea class="text" placeholder="这一刻的想法..."></textarea>
    </div>

    <div class="bottom">
      <div id="picInput">
        <el-upload
            class="avatar-uploader"
            :http-request="upload"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            multiple
        >
          <img
              v-if="images.length < 9"
              class="avatar default-avatar"
              src="../assets/insertimg.png"
          />
          <img
              v-for="(image, index) in images"
              :key="index"
              :src="image.url"
              class="avatar"
              @click="viewImage(image.url)"
          />
        </el-upload>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      images: [],
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
              this.$message.info("头像已存在，但不影响使用，");
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
        this.$message.error("上传头像图片只能是 JPG 或 PNG 格式！");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB！");
      }

      return isJPG && isLt2M;
    },
    getAddress() {
      axios({
        method: "get",
        url: "https://api.vvhan.com/api/getIpInfo",
      }).then((response) => {
        console.log(response.data.info);
      });
    },
    backIndex() {
      this.$router.push("/index");
    },
    publish() {
      console.log(this.images);
      console.log(this.images.length);
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
}

.container .bottom {
  width: 100%;
}

.container .bottom #picInput {
  width: 100%;
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.container .bottom #picInput img {
  width: calc(33.33% - 10px);
  height: auto;
  margin-bottom: 10px;
}
</style>
