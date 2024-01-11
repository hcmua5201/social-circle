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
              <img v-if="post.image" class="post-image" ref="img" :src="post.image" @click="toggleFullscreen" alt="说说图片">
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
axios.defaults.baseURL='/api'
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
      newComment: "",       //评论内容
      isFullscreen: false,
    };
  },
  created() {
    // 模拟从数据库获取背景图片地址
    // 实际中使用异步请求从服务器获取
    this.loadBackgroundImage();

    // 模拟异步请求获取朋友圈说说数据
    // 实际中可以替换成从后端获取数据的逻辑
    setTimeout(() => {
      this.posts = [
        {
          id: 1,
          author: {
            avatar: './src/assets/touxiang.jpg',
            nickname: '寒光'
          },
          content: '这是一条朋友圈说说',
          image: '../src/assets/好吃的1.jpg',
          liked: false,
          comments: [],
          likes: ['小明', '小红'],
          time: new Date('2024-01-10T12:30:00')
        },
        {
          id: 2,
          author: {
            avatar: './src/assets/vue.svg',
            nickname: 'Vue'
          },
          content: '今天天气甚是不错，风儿在耳边喧嚣，云儿在天空飘荡。',
          image: '../src/assets/宇宙.png',
          liked: false,
          comments: [
            {author: '小花', content: '好好吃啊！'},
            {author: '小刚', content: '一起去吃吧！'}
          ],
          likes: ['小刚'],
          time: new Date()
        },
        // 可以添加更多的说说数据
      ];
    }, 1000); // 模拟异步请求的延时
  },
  methods: {
    async loadBackgroundImage() {
      try {
        // 模拟异步请求获取背景图片路径
        // const response = await axios.get('path/to/database-background-image'); // 替换成实际的 API 地址
        this.backgroundImage = './src/assets/宇宙.png';
      } catch (error) {
        console.error('Failed to load background image:', error);
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
    },
    toggleFullscreen(event) {
      this.$message.error("图片查看功能-未完成")
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
