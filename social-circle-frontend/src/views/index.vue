<template>
  <div>
    <!-- 朋友圈背景壁纸 -->
    <el-row class="background" :style="{ backgroundImage: 'url(' + user.backgroundImage + ')' }">
      <el-col :span="24">
        <!-- 用户头像放于背景右下角 -->
        <div class="user-info">
          <span class="user-nickname">{{ user.nickname }}</span>
          <img class="user-avatar" :src="user.avatar" alt="用户头像">
        </div>
        <!-- 相机图标，点击执行函数 -->
        <div class="camera-icon" @click="handleCameraClick">
          <img src="../assets/photo.png" alt="相机图标">
        </div>
      </el-col>
    </el-row>

    <!-- 背景图片容器 -->
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
              <img v-if="post.image" class="post-image" :src="post.image" @click="toggleFullscreen" alt="说说图片">
            </div>
            <div class="post-footer">
              <!-- 显示发布时间 -->
              <span class="post-time">{{ formatTime(post.time) }}</span>
              <!-- 点赞和评论按钮 -->
              <div class="post-actions">
                <i class="fa" :class="{ 'fa-heart': isLiked(post), 'fa-heart-o': !isLiked(post) }" @click="toggleLike(post)"></i>
<!--                {{ post.likeCount }}-->
                <el-button @click="showComments(post.postID)" text>评论</el-button>
              </div>
            </div>
            <!-- 显示点赞人名列表和评论列表 -->
            <div class="post-comments">
              <div v-if="post.likeUsernames && post.likeUsernames.length > 0" class="post-likes">
                点赞：{{ post.likeUsernames.join(', ') }}
              </div>
              <div v-if="post.comments && post.comments.length > 0" class="post-comments-list">
                <div v-for="(comment, index) in post.comments" :key="index" class="post-comment">
                  <span class="comment-author">{{ post.commenters[index] }}</span>：{{ comment }}
                </div>
              </div>
            </div>
            <!-- 评论输入框 -->
            <el-input v-show="post.showCommentInput" class="comment-input" v-model="newComment" placeholder="发表评论..." @keyup.enter="sendNewComment(newComment, post)" />
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

axios.defaults.baseURL = '/api';

export default {
  data() {
    return {
      user: {
        avatar: '',
        nickname: '',
        backgroundImage: '',
      },
      posts: [],
      showCommentInput: false,
      newComment: "",
      isFullscreen: false,
    };
  },
  created() {
    this.loadUserInfoAndBackgroundImage();
    // 发送请求获取朋友圈数据
  this.getAll();
  },
  methods: {
    getAll(){
      axios.get('/api/posts/all')  // 替换成实际的后端接口地址
          .then(response => {
            console.log(response.data.obj)
            this.posts = response.data.obj;
          })
          .catch(error => {
            console.error('Error fetching posts:', error);
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
        requestData = { userID: userID, postID: post.postID };
        axios({
          method: 'post',
          url: '/api/likes/remove',
          params: requestData  // 修正此行，应该使用 params
        }).then(response => {
          // 处理成功的逻辑，可能需要更新点赞列表等
          if (response.data.code===222){
            this.getAll()
            this.$message.success(response.data.msg)
          }
          post.likeUserIds = post.likeUserIds.filter(id => id !== userID);
          // 可能需要更新其他相关数据，具体根据后端返回的数据结构来处理
        }).catch(error => {
          console.error('Error unliking post:', error);
        });
      } else {
        // 如果未点赞，发送请求添加点赞
        requestData = { userID: userID, postID: post.postID };
        axios({
          method: 'post',
          url: '/api/likes/addLike',
          params: requestData  // 修正此行，应该使用 data
        }).then(response => {
          // 处理成功的逻辑，可能需要更新点赞列表等
          if (response.data.code===222){
            this.getAll()
            this.$message.success(response.data.msg)
          }
          post.likeUserIds.push(userID);
          // 可能需要更新其他相关数据，具体根据后端返回的数据结构来处理
        }).catch(error => {
          console.error('Error liking post:', error);
        });
      }
    },
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
    },
    sendNewComment(newComment, post) {
      this.$message.success(newComment);

      axios.post('/your-comment-api-endpoint', {
        postId: post.postID,
        userId: this.user.userId,
        content: newComment
      })
          .then(response => {
            // Assuming your API returns the updated post data after adding a comment
            post.comments = response.data.comments;
          })
          .catch(error => {
            console.error('Error adding comment:', error);
          });

      post.showCommentInput = false;
      this.newComment = '';
    },
    handleCameraClick() {
      this.$message.success("发布朋友圈");
      console.log('Camera icon clicked!');
      // 可以添加跳转逻辑或其他操作
      this.$router.push('/publish');
    },
    formatTime(time) {
      const options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
      };
      return new Intl.DateTimeFormat('zh-CN', options).format(new Date(time));
    },
    toggleFullscreen(event) {
      this.$message.error("图片查看功能-未完成");
    },
  }
};
</script>

<style scoped>

.background {
  position: relative;
  padding: 150px;
  background-size: cover;
}

.user-info {
  position: absolute;
  bottom: 0;
  right: 0;
  transform: translate(50%, 35%);
  text-align: right;
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
  width: 70px;
  height: 70px;
  margin-right: 10px;
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

img.post-avatar{
  margin-left: 10px;
  transform: rotate(0deg);
  transition: transform 0.5s ease-in-out;
}

img.post-avatar:hover{
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
  max-width: 100%;
  margin-top: 10px;
}

.post-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.post-time {
  color: #959595;
  font-size: 12px;
  line-height: 20px;
  font-weight: bold;
}

.post-comments {
  margin-top: 10px;
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
  color: #555;
}

.comment-author {
  font-weight: bold;
}

.comment-input {
  margin-top: 10px;
  width: 100%;
  border: 1px solid #ccc; /* 添加边框 */
  padding: 5px; /* 调整内边距 */
  border-radius: 5px; /* 添加圆角 */
}

.post-actions {
  margin-top: 10px;
}

/* Font Awesome 图标样式 */
.fa-heart {
  color: red;
}

.fa-heart-o {
  color: black;
}
</style>
