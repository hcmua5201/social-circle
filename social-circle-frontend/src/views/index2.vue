<template>
  <div>
    <!-- 朋友圈背景壁纸 -->
    <el-row class="background" :style="{ backgroundImage: 'url(' + backgroundImage + ')' }">
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
        <el-col :span="24" v-for="post in posts" :key="post.id">
          <el-card class="post">
            <div slot="header">
              <img class="post-avatar" :src="post.author.avatar" alt="发布者头像">
              <!-- 在说说的内容下方显示发布说说的用户昵称 -->
              <span class="post-nickname">{{ post.author.nickname }}</span>
            </div>
            <div class="post-content">
              <p>{{ post.content }}</p>
              <img v-if="post.image" class="post-image" :src="post.image" alt="说说图片">
            </div>
            <div class="post-footer">
              <!-- 显示发布时间 -->
              <span class="post-time">{{ formatTime(post.time) }}</span>
              <!-- 点赞和评论按钮 -->
              <div class="post-actions">
                <i class="fa" :class="{ 'fa-heart': isLiked(post), 'fa-heart-o': !isLiked(post) }"
                   @click="toggleLike(post)"></i>
                {{ post.likes.length }}
                <el-button @click="showComments(post.id)" text>
                  评论
                </el-button>
              </div>
            </div>
            <!-- 显示点赞人名列表和评论列表 -->
            <div class="post-comments">
              <div v-if="post.likes.length > 0" class="post-likes">
                点赞：{{ post.likes.join(', ') }}
              </div>
              <div v-if="post.comments.length > 0" class="post-comments-list">
                <div v-for="(comment, index) in post.comments" :key="index" class="post-comment">
                  <span class="comment-author">{{ comment.author }}</span>：
                  {{ comment.content }}
                </div>
              </div>
            </div>
            <!-- 评论输入框 -->
            <el-input
                v-show="post.showCommentInput"
                class="comment-input"
                v-model="newComment"
                placeholder="发表评论..."
                @keyup.enter="sendNewComment(newComment,post)"
            />
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

axios.defaults.baseURL = '/api'; // 替换成你的后端API地址

export default {
  data() {
    return {
      user: {
        avatar: './src/assets/touxiang.jpg',
        nickname: '寒光'
      },
      backgroundImage: '', // 背景图片的路径变量
      posts: [],
      showCommentInput: false,    //默认不显示输入框
      newComment: ""       //评论内容
    };
  },
  created() {
    // 异步请求获取背景图片地址
    this.loadBackgroundImage();

    // 异步请求获取朋友圈说说数据
    this.loadFriendCircleData();
  },
  methods: {
    async loadBackgroundImage() {
      try {
        const userId = 1; //登录用户id
        const response = await axios.get('/api/users/background-image/' + userId);
        console.log(response.data)
        this.backgroundImage = response.data;
      } catch (error) {
        console.error('Failed to load background image:', error);
      }
    },
    async loadFriendCircleData() {
      try {
        const response = await axios.get('/api/friendCircleData'); // 替换成实际的 API 地址
        this.posts = response.data.posts;
      } catch (error) {
        console.error('Failed to load friend circle data:', error);
      }
    },
    isLiked(post) {
      // 检查用户是否已经点赞过该帖子
      return post.likes.includes(this.user.nickname);
    },
    toggleLike(post) {
      // 检查用户是否已经点赞过该帖子
      const index = post.likes.indexOf(this.user.nickname);

      if (index !== -1) {
        // 如果用户已经点赞，将其名字从点赞人列表中移除
        post.likes.splice(index, 1);
      } else {
        // 如果用户还未点赞，将其名字添加到点赞人列表中
        post.likes.push(this.user.nickname);
      }
    },
    showComments(postId) {
      // 实现显示评论的逻辑
      // 遍历所有帖子，设置对应帖子的 showCommentInput
      this.posts.forEach(post => {
        post.showCommentInput = post.id === postId && !post.showCommentInput;
      });

      // 聚焦评论输入框
      if (this.showCommentInput) {
        this.$nextTick(() => {
          const friendCircleRow = this.$refs.friendCircle;
          const commentInput = friendCircleRow.$el.querySelector('.comment-input');
          if (commentInput) {
            commentInput.focus();
          }
        });
      }
      // 设置对应帖子的 showCommentInput 为 true，其他帖子的为 false
      this.posts.forEach(post => {
        this.showCommentInput = post.id === postId;
      });
    },
    sendNewComment(newComment,post){
      this.$message.success(newComment)
      // 保存评论到对应帖子的 comments 数组
      post.comments.push({
        author: this.user.nickname,
        content: this.newComment,
      });
      // 隐藏评论输入框
      post.showCommentInput = false;
      // 清空评论内容
      this.newComment = '';
    },
    handleCameraClick() {
      // 在这里执行点击相机图标时的操作
      this.$message.success("发布盆友圈")
      console.log('Camera icon clicked!');
      // 可以添加跳转逻辑或其他操作
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
      return new Intl.DateTimeFormat('zh-CN', options).format(time);
    }
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
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 2px solid #fff; /* 添加边框 */
}

.user-nickname {
  margin-left: -100px;
  font-size: 10px;
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
  margin-top: 20px;
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
}

.post-content {
  flex-grow: 1;
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
  color: #888;
}

.post-comments {
  margin-top: 10px;
}

.post-likes,
.post-comments-list {
  margin-top: 5px;
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

