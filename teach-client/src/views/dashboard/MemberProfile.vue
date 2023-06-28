<template>
  <Navi/>
  <div  class="form-div">
    <el-form ref="editForm" :model="editForm" :rules="editRules" label-position="left" label-width="50px" status-icon style="margin-top: 15px;">
      <br/>
      <el-row align="middle">
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item label="姓名" >
            <el-input v-model="name" maxlength="20" show-word-limit style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item label="学号" >
            <el-input v-model="mid" :disabled="true"  maxlength="20"  style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item align="center">
            <el-button type="primary" @click="submit">提交</el-button>
          </el-form-item>
        </el-col>
      </el-row>


    </el-form>
  </div>
</template>

<script>
import {getProfile,submitProfile} from "@/service/genServ";
import Navi from '@/components/Navi'
export default {
  name: "MemberProfile",
  components:{Navi},
  data(){
    return{
      mid:"",
      name:"",
    }
  },
  created() {
    this.doQuery();
  },
  methods:{
    doQuery(){
      getProfile().then(res=>{
        this.mid=res.data.mid;
        this.name=res.data.name;
      })
    },
    submit(){
      submitProfile({"mid":this.mid,"name":this.name}).then(res=>{
        if(res.code==='0'){
          this.$message({
            message:  '成功',
            type: 'success',
          })
        }else{
          this.$message({
            message:  res.msg,
            type: 'warning',
          })
        }
      })
    },

  }
}
</script>

