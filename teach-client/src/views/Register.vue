<template>
  <body id="poster">
  <el-form class="login-container" label-position="left" label-width="0px">
  <div class="register">
    <h2>注册</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <el-label for="username">用户名</el-label>&nbsp;&nbsp;&nbsp;&nbsp;
        <el-input style="width: 75%" clearable v-model="username"  name="username" placeholder="请输入用户名"></el-input>
      </div>

      <div class="form-group">
        <h2></h2>
        <el-label for="username">姓名</el-label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-input style="width: 75%" clearable v-model="name"  name="name" placeholder="请输入姓名"></el-input>
      </div>

      <div class="form-group">
        <h2></h2>
        <label htmlFor="password">密码</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-input style="width: 75%"  clearable placeholder="请输入密码" v-model="password" show-password></el-input>
      </div>
      <div class="form-group">
        <h2></h2>
        <label htmlFor="password2">重复密码</label>&nbsp;
        <el-input style="width: 75%"  clearable placeholder="请输入重复密码" v-model="password2" show-password></el-input>
      </div>
      <div style="margin-top: 20px" id="register">
        <el-radio-group v-model="role" size="mini" >
          <el-radio label="ROLE_MEMBER" border>我是成员</el-radio>
          <el-radio label="ROLE_ADMIN" border>我是管理员</el-radio>
        </el-radio-group>
      </div>

      <div class="form-group"  v-show="(role==='ROLE_MEMBER')">
        <h2></h2>
        <el-label for="username">成员号</el-label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-input style="width: 75%" clearable v-model="mid"  name="mid" placeholder="请输入成员号"></el-input>
      </div>

      <div class="form-group"  v-show="(role==='ROLE_ADMIN')">
        <h2></h2>
        <el-label for="username">管理员号</el-label>&nbsp;
        <el-input style="width: 75%" clearable v-model="aid"  name="aid" placeholder="请输入管理员号"></el-input>
      </div>

      <div class="form-group"  v-show="(role==='ROLE_ADMIN')">
        <h2></h2>
        <el-label for="username">校验密码</el-label>&nbsp;
        <el-input style="width: 75%" clearable v-model="check"  name="check" placeholder="请输入校验密码"></el-input>
      </div>

      <div style="margin-top: 20px" class="form-group">
        <h2></h2>
        <el-button class="btn btn-primary" type="primary" @click='handleSubmit()' style="width: 50%;background: #409EFF;border: none">注册</el-button>
      </div>
    </form>
  </div>
  </el-form>
  </body>
</template>

<script>
import {register} from '@/service/genServ.js'
import router from "@/router";

export default {
  name: "RegisterPage",
  data(){
    return{
      username:'',
      password:'',
      password2:'',
      mid:'',
      name:'',
      aid:'',
      role:'ROLE_MEMBER',
      check:'',
    }
  },
  methods: {
    handleSubmit() {
      if(this.password!==this.password2){
        this.$message({
          message:  '口令不一致！',
          type: 'warning',
        })
      }if((this.role==='ROLE_MEMBER'&&(!this.name||!this.mid||!this.username||!this.password))||
          (this.role==='ROLE_ADMIN'&&(!this.name||!this.aid||!this.username||!this.password||!this.check))){
        this.$message({
          message:  '所有项目均为必填项！',
          type: 'warning',
        })
      }
        else{
        register({'username':this.username,'password':this.password,'role':this.role,'mid':this.mid,'aid':this.aid,'name':this.name,'check':this.check}).then(response=>{
          if (response.code === '0') {
            this.$message({
              message:  '成功，跳转到登录页面',
              type: 'success',
            })
            router.go(-1)
          } else {
            this.$message({
              message: response.msg,
              type: 'warning',
            })
          }
        })
      }
    }
  }
}
</script>

<style>
#poster{
  background:url("../assets/register.jpg") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}
body{
  margin: 0;
}
.login-container {
  border-radius: 30px;
  background-clip: padding-box;
  margin: 80px auto;
  width: 400px;
  padding: 35px 35px 50px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.login_title {
  margin: 0 auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
