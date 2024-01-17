<template>
<!--  动态发布页面-->

  <!--  页面总容器-->
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
        <img id="img_preview1" style="" @onclick="uploadbut(1)" src="../assets/insertimg.png">
        <img id="img_preview2" style=" display: none;" @onclick="uploadbut(2)" src="../assets/insertimg.png">
        <img id="img_preview3" style=" display: none;" @onclick="uploadbut(3)" src="../assets/insertimg.png">
        <img id="img_preview4" style=" display: none;" @onclick="uploadbut(4)" src="../assets/insertimg.png">
        <img id="img_preview5" style=" display: none;" @onclick="uploadbut(5)" src="../assets/insertimg.png">
        <img id="img_preview6" style=" display: none;" @onclick="uploadbut(6)" src="../assets/insertimg.png">
        <img id="img_preview7" style=" display: none;" @onclick="uploadbut(7)" src="../assets/insertimg.png">
        <img id="img_preview8" style=" display: none;" @onclick="uploadbut(8)" src="../assets/insertimg.png">
        <img id="img_preview9" style=" display: none;" @onclick="uploadbut(9)" src="../assets/insertimg.png">
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data(){

  },
  methods:{
    backIndex(){
      this.$router.push('/index')
    },
    publish(){

      //此处api搁置，由于不精确，经常抽风返回错误地址
      // axios.get("https://api.vvhan.com/api/getIpInfo").then(res =>{
      //   console.log(res.data.info)
      //   console.log(res.data.info.country)
      //   console.log(res.data.info.prov)
      //   console.log(res.data.info.city)
      //   this.$message.success("发布于："+res.data.info.country+" "+res.data.info.prov+"省 "+res.data.info.city+" ")
      // })

      // 接入IP138额度查询API,更稳定，但是付费，省着点调😭
      axios({
        url:'http://api.ipshudi.com/ip/',
        method:'get',
        params:{
          token:'c065a105a93db1662ed6d2842a1ac1f3'
        }
      }).then(res =>{
        console.log(res.data.data)
        const address = res.data.data
        this.$message.success("发布于："+address[0]+" "+address[1]+"省 "+address[2])
      })
      // this.$message.success("发布成功")
      this.$router.push('/index')
    }
  },
  beforeRouteEnter(to, from, next) {

    // 添加背景色 margin:0;padding:0是为了解决vue四周有白边的问题

    document.querySelector('body').setAttribute('style','margin:0;padding:0')

    next()

  }
}
</script>

<style scoped>
*{
  margin: 0;
  padding: 0;
}
.container{
  background-color: #fff;
  max-width: 450px;
  margin: 0 auto;
  min-height: 90vh;
}

.container .top{
  border-bottom: 1px solid #BCBCBC;
  width: 94%;
  margin: 0 auto;
  height: 100%;
}

.container .top .header{
  width: 100%;
  height: 40px;
}

.container .top .header .left{
  width: 40px;
  height: 100%;
  float: left;
  margin-left: -1px;
  margin-top: 8px;
  color: #000000;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
}

.container .top .header .right{
  width: 40px;
  height: 100%;
  float: right;
  margin-right: -1px;
  margin-top: 8px;
  color: #32b431;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
}
.container .top .header .right:hover{
  background-color: #999;
}

.container .top textarea.text{
  display: block;
  width: 96%;
  margin-top: 34px;
  border: none;
  color: #232323;
  font-size: 20px;
  outline: none;
  text-indent: 1em;
  min-height: 100px;
}

.container .bottom{
  width: 100%;
}
.container .bottom #picInput{
  width: 90%;
  margin: 12px auto 0;
}

.container .bottom #picInput img{
  width: 7.5rem;
  height: 7.5rem;
}
</style>