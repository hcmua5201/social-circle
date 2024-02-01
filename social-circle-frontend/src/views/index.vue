<template>
  <div class="container">
    <!-- 朋友圈背景壁纸 -->
    <div class="top">
      <!-- 侧边菜单栏 -->
      <el-drawer v-model="sidebarVisible"
                 size="200px"
                 direction="ltr"
                 style="background-color: rgb(84, 92, 100)"
                 class="sidebar-drawer">
        <el-col :span="24">

          <el-menu
              default-active="2"
              class="el-menu-vertical-demo"
              background-color="#545c64"
              text-color="#fff"
              active-text-color="#ffd04b"
              router
          >
            <!--            <h1 style="text-align: center">你好</h1>-->

            <img :src="user.avatar" class="userImg">
            <span>{{ user.nickname }}</span>


            <el-sub-menu index="1">
              <template #title>
                <el-icon>
                  <location/>
                </el-icon>
                <span>管理员操作</span>
              </template>
              <el-menu-item-group title="管理员">
                <el-menu-item index="/admin/main_success">后台管理</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-menu-item @click="ShowDialogVisible">
              <el-icon>
                <document/>
              </el-icon>
              <span>个人中心</span>
            </el-menu-item>
            <el-menu-item @click="logout">
              <el-icon>
                <setting/>
              </el-icon>
              <span>退出登录</span>
            </el-menu-item>
          </el-menu>
        </el-col>
      </el-drawer>

      <el-dialog
          v-model="dialogVisible"
          title="个人信息"
          width="50%"
      >

        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          修改
        </el-button>
      </span>
        </template>
      </el-dialog>

      <el-row class="background" :style="{ backgroundImage: 'url(' + user.backgroundImage + ')' }">
        <el-col :span="24">
          <!-- 用户头像放于背景右下角 -->
          <div class="user-info">
            <span class="user-nickname">{{ user.nickname }}</span>
            <img class="user-avatar" :src="user.avatar" alt="用户头像">
          </div>

          <!-- 侧边栏触发按钮 -->
          <div class="sidebar-trigger" @click="toggleSidebar">
            <el-icon>
              <medal/>
            </el-icon>
          </div>

          <!-- 相机图标，点击执行函数 -->
          <div class="camera-icon" @click="handleCameraClick">
            <img src="../assets/photo.png" alt="相机de图标">
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 动态容器 -->
    <div class="bottom">
      <div class="background-image-container">
        <!-- 朋友圈发布内容 -->
        <el-row class="friend-circle" ref="friendCircle">
          <el-col :span="24" v-for="post in posts" :key="post.postID">
            <el-card class="post">
              <div slot="header">
                <img class="post-avatar" :src="post.author.avatar" alt="发布者头像">
                <!-- 在说说的内容下方显示发布说说的用户昵称 -->
                <span class="post-nickname">{{ post.author.nickname }}</span>
              </div>
              <div class="post-content">
                <p>{{ post.content }}</p>
                <img v-for="(image, index) in post.images" :key="index" class="post-image" :src="image" @click="toggleFullscreen" alt="说说图片">
              </div>
              <div class="post-footer">
                <div class="left">
                  <!-- 显示发布时间 -->
                  <span class="post-time">🕑： {{ formatTime(post.time) }}</span><br>
                  <span class="post-address">🏙️： {{ post.address }}</span>
                </div>
                <div class="right">
                  <!-- 点赞和评论按钮 -->
                  <div class="post-actions">
                    <i class="fa" :class="{ 'fa-heart': isLiked(post), 'fa-heart-o': !isLiked(post) }"
                       @click="toggleLike(post)"></i>
                    <el-button @click="showComments(post.postID)" text>评论</el-button>
                  </div>
                </div>
              </div>
              <!-- 显示点赞人名列表和评论列表 -->
              <div class="post-comments">
                <div v-if="post.likeUsernames && post.likeUsernames.length > 0" class="post-likes">
                  点赞：{{ post.likeUsernames.join(', ') }}
                </div>
                <div v-if="post.comments && post.comments.length > 0" class="post-comments-list">
                  <div v-for="(comment, index) in post.comments" :key="index" class="post-comment">
                    <span class="comment-author">{{ post.commenters[index] }}</span>：<span
                      @click="showMore(index,comment)">{{ comment }}</span>
                    <div class="moreMenu" v-show="index === this.selectedCommentId">
                      <p @click="copyComment(comment)">复制</p>
                      <p>删除</p>
                      <p>回复</p>
                    </div>
                  </div>
                </div>
              </div>
              <!-- 评论输入框 -->
              <el-input v-show="post.showCommentInput" class="comment-input" v-model="newComment"
                        placeholder="发表评论..." @keyup.enter="sendNewComment(newComment, post)"/>
              <!-- 提交评论按钮 -->
              <el-button v-show="post.showCommentInput" class="comment-submit" @click="sendNewComment(newComment, post)"
                         type="success">提交
              </el-button>

            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
    <div id="goTop" @click="goTop"> ⬆</div>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';
import {Document, Location, Medal, Minus, Monitor, Setting} from "@element-plus/icons-vue";

axios.defaults.baseURL = '/api';

export default {
  components: {Medal, Monitor, Minus, Setting, Document, Location},
  data() {
    return {
      user: {
        avatar: '',
        nickname: '',
        backgroundImage: '',
      },
      posts: [],
      sidebarVisible: false,
      showCommentInput: false,
      newComment: "",
      isFullscreen: false,
      moreMenuStatus: false,
      selectedCommentId: -1,
      dialogVisible:false
    };
  },
  created() {

    this.loadUserInfoAndBackgroundImage();
    // 发送请求获取朋友圈数据
    this.getAll();
    //多执行几次，保证页面数据显示正确
    this.upDateComment();
    this.upDateComment();
    this.upDateComment();
  },
  methods: {
    ShowDialogVisible(){
     this.dialogVisible=!this.dialogVisible;
    },
    toggleSidebar() {
      this.sidebarVisible = !this.sidebarVisible;
    },
    showMore(index, comment) {
      this.$message.info("点击评论：" + comment)
      this.selectedCommentId = index;
      console.log(comment)
    },
    goTop() {
      window.scrollTo(0, 0);
    },
    copyComment(comment) {
      this.$message.success("已复制评论: " + comment)
      navigator.clipboard.writeText(comment);
      this.selectedCommentId = -1;
    },
    getAll() {
      axios.get('/api/posts/all')  // 替换成实际的后端接口地址
          .then(response => {
            console.log(response.data.obj)
                this.posts = response.data.obj.map(post => {
                  // 将post.image分割成数组，存储到post.images中
                  post.images = post.image ? post.image.split(',') : [];
                  return post;
                });
          })
          .catch(error => {
            console.error('查询接口出错:', error);
          });
    },
    async upDateComment() {
      axios({
        method: 'get',
        url: '/api/comments/UpdateComment'
      }).then((response) => {
        console.log(response.data)
        this.updateCommentsData(response.data.obj);
      })
    },
    async updateCommentsData(newCommentsData) {
      // 遍历帖子列表
      this.posts.forEach(post => {
        // 找到当前帖子的评论数据
        const commentsForPost = newCommentsData.filter(comment => comment.postID === post.postID);

        // 更新帖子的评论数据
        post.comments = commentsForPost.map(comment => comment.content);
        post.commenters = commentsForPost.map(comment => comment.nickname);
      });
    },
    loadUserInfoAndBackgroundImage() {
      const logininfo = localStorage.getItem("login_info");
      const useriD = sessionStorage.getItem("userID");
      const login_info = JSON.parse(logininfo);
      const userID = JSON.parse(useriD);
      this.user.backgroundImage = login_info.backgroundImagePath;
      this.user.avatar = login_info.avatar;
      this.user.nickname = login_info.nickname;
    },
    isLiked(post) {
      return post.likeUsernames && post.likeUsernames.includes(this.user.nickname);
    },
    toggleLike(post) {
      const logininfo = localStorage.getItem("login_info");
      const useriD = sessionStorage.getItem("userID");
      const login_info = JSON.parse(logininfo);
      const userID = JSON.parse(useriD);

      // 判断当前用户是否已经点赞
      const hasLiked = post.likeUserIds.includes(userID);
      let requestData = {};

      if (hasLiked) {
        // 如果已经点赞，发送请求取消点赞
        requestData = {userID: userID, postID: post.postID};
        axios({
          method: 'post',
          url: '/api/likes/remove',
          params: requestData  // 修正此行，应该使用 params
        }).then(response => {
          // 处理成功的逻辑，可能需要更新点赞列表等
          if (response.data.code === 222) {
            this.getAll()
            this.upDateComment();
            this.upDateComment();
            this.upDateComment();
            this.$message.success(response.data.msg)
          }
          post.likeUserIds = post.likeUserIds.filter(id => id !== userID);
          // 可能需要更新其他相关数据，具体根据后端返回的数据结构来处理
        }).catch(error => {
          console.error('Error unliking post:', error);
        });
      } else {
        // 如果未点赞，发送请求添加点赞
        requestData = {userID: userID, postID: post.postID};
        axios({
          method: 'post',
          url: '/api/likes/addLike',
          params: requestData  // 修正此行，应该使用 data
        }).then(response => {
          // 处理成功的逻辑，可能需要更新点赞列表等
          if (response.data.code === 222) {
            this.getAll()
            this.upDateComment();
            this.upDateComment();
            this.upDateComment();
            this.$message.success(response.data.msg)
          }
          post.likeUserIds.push(userID);
          // 可能需要更新其他相关数据，具体根据后端返回的数据结构来处理
        }).catch(error => {
          console.error('Error liking post:', error);
        });
      }
    },  //点赞功能
    showComments(postId) {
      this.posts.forEach(post => {
        post.showCommentInput = post.postID === postId && !post.showCommentInput;
      });

      if (this.showCommentInput) {
        this.$nextTick(() => {
          const friendCircleRow = this.$refs.friendCircle;
          const commentInput = friendCircleRow.$el.querySelector('.comment-input');
          if (commentInput) {
            commentInput.focus();
          }
        });
      }
      this.posts.forEach(post => {
        this.showCommentInput = post.postID === postId;
      });
    }, //点击评论按钮触发评论输入框显示
    sendNewComment(newComment, post) {
      const useriD = sessionStorage.getItem("userID");
      const userID = JSON.parse(useriD);
      // this.$message.success("消息"+newComment+"用户"+userID+"帖子"+post.postID);
      let requestData = {};

      if (newComment.length >= 50) {
        this.$message.warning("评论内容不能超过50字")
      } else if (newComment === '') {
        this.$message.error("评论内容不能为空")
      } else if (newComment !== '') {
        // this.$message.warning("评论内容通过")
        requestData = {userID: userID, postID: post.postID, content: newComment};

        axios({
          method: 'post',
          url: '/api/comments/add',
          params: requestData
        }).then((response) => {
          console.log(response.data)
          if (response.data.code === 222) {
            this.getAll();
            this.upDateComment();
            this.upDateComment();
            this.upDateComment();
            this.$message.success(response.data.msg)
          } else {
            this.$message.error(response.data.msg)
          }
        })
        post.showCommentInput = false;
        this.newComment = '';
      }
    },   //评论功能
    handleCameraClick() {
      // this.$message.success("发布朋友圈");
      console.log('Camera icon clicked!');
      // 可以添加跳转逻辑或其他操作
      this.$router.push('/publish');
    },
    formatTime(time) {
      return moment(time).format('YYYY-MM-DD HH:mm:ss');
    },
    toggleFullscreen(event) {
      this.$message.error("图片查看功能-未完成");
    },
    logout() {
      this.$confirm("将退出该账号，是否继续？", "提示", {type: "info"}).then(() => {
        this.$router.push('/')
        sessionStorage.removeItem('userID');
        localStorage.removeItem("login_info")
        this.$message.success("退出成功！")
      }).catch(() => {
        this.$message.info("取消操作");
      });
    }
  }
};
</script>

<style scoped>
.container {
  max-width: 450px;
  margin: 0 auto;
  min-height: 80%;
  position: absolute;
  left: 0;
  right: 0;
}

.moreMenu {
  display: block;
  width: 60px;
  height: 64px;
  position: absolute;
  background-color: #f3f3f5;
  right: 30px;
  border-radius: 5px;
  /* 设置投影的颜色 */
  box-shadow: 0 0 10px 2px rgba(0, 0, 0, 0.2);
}

.moreMenu p {
  font-size: 16px;
  line-height: 20px;
  margin: 0 auto;
  color: #000;
  text-align: center;
  border: 1px solid #fff;
}

div.top {
  height: 332px;
}

#goTop {
  position: fixed;
  bottom: 10px;
  right: 10px;
  cursor: pointer;
  color: #fff;
  background: deepskyblue;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  font-size: 16px;
  z-index: 1000;
}

.background {
  padding: 150px;
  background-size: cover;
  display: block;
  height: 100%;
}

.user-info {
  position: absolute;
  bottom: 0;
  right: 0;
  transform: translate(50%, 35%);
  text-align: right;
}

.sidebar-trigger {
  position: absolute;
  top: 10px; /* 调整上边距 */
  left: 10px; /* 调整左边距 */
  font-size: 24px;
  color: #fff;
  cursor: pointer;
  z-index: 1001; /* Make sure it is above the sidebar */
}

/* 侧边菜单栏样式 */
.sidebar-drawer {
  height: 100%;
  z-index: 999; /* 确保侧边栏在顶层 */
  overflow-y: auto;
  background-color: #090723;
}

.sidebar-drawer img.userImg {
  display: block;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin: 0 auto;
  border: 4px solid #fff;
}

.sidebar-drawer img.userImg:hover {
  border: 4px solid skyblue;
}

.sidebar-drawer span {
  display: block;
  margin-top: 10px;
  width: 100%;
  text-align: center;
}

.camera-icon {
  position: absolute;
  top: -80px;
  right: -80px;
  cursor: pointer;
  /* 调整相机图标大小，可以根据需要设置宽高 */
  /* 或者使用 transform 缩小图标 */
  transform: scale(0.15);
}

.user-avatar {
  width: 60px;
  height: 60px;
  margin-right: 52px;
  margin-top: 16px;
  border: 3px solid #fff; /* 添加边框 */
}

.user-nickname {
  margin-left: -140px;
  color: #000;
  font-size: 20px;
  font-weight: bold;
  padding-right: 12px;
}

img.post-avatar {
  margin-left: 10px;
  transform: rotate(0deg);
  transition: transform 0.5s ease-in-out;
}

img.post-avatar:hover {
  transform: rotate(180deg);
  transition: transform 0.5s ease-in-out;
}

.background-image-container {
  background-size: cover;
  background-position: center; /* 或者调整为你希望的位置 */
  background-repeat: no-repeat;
  background-color: #fff; /* 背景色，防止背景图片不完整时显示为黑色 */
  width: 100%; /* 背景图片容器宽度为100% */
  height: 100vh; /* 背景图片容器高度为100%视口高度 */
}

.friend-circle {
  margin-top: 44px;
}

.post {
  display: flex;
  flex-direction: column;
  align-items: stretch;
}

.post-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.post-nickname {
  margin-left: 10px;

  color: #546993;
  max-width: 300px;
  font-size: 18px;
  font-weight: bold;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  line-height: 30px;
  cursor: pointer;
  display: inline-block;
}

.post-content {
  color: #090909;
  font-size: 20px;
  font-weight: bold;
}

.post-image {
  max-width: 30%;
  margin: 2px;
  margin-top: 10px;
}

.post-footer {
  justify-content: space-between;
  align-items: center;
}

.post-footer .left, .post-footer .right {
  width: 50%;
  float: left;
}

.post-time {
  color: #959595;
  font-size: 12px;
  line-height: 20px;
  font-weight: bold;
}

.post-address {
  color: #959595;
  font-size: 12px;
  line-height: 20px;
  font-weight: bold;
}

.post-comments {
  margin-top: 70px;
  background-color: #f3f3f5;
}

.post-likes,
.post-comments-list {
  margin-top: 5px;
  color: #546993;
  font-size: 15px;
  font-weight: bold;
  cursor: pointer;
  line-height: 20px;
}

.post-comment {
  margin-top: 5px;
  color: #090909;
  border-top: 1px solid #e5e5e5;
}

.comment-author {
  font-weight: bold;
  color: #546993;
}

.comment-input {
  margin-top: 10px;
  width: calc(100% - 80px); /* 调整输入框宽度，留出按钮的宽度 */
  border-radius: 5px;
  outline: none;
  float: left; /* 将输入框浮动到左边 */
}

.comment-submit {
  margin-top: 10px;
  margin-left: 10px;
  float: left; /* 将按钮浮动到左边 */
}


.post-actions {
  margin-top: 10px;
  width: 80px;
  margin-left: 120px;
}

/* Font Awesome 图标样式 */
.fa-heart {
  color: red;
}

.fa-heart-o {
  color: black;
}

</style>
