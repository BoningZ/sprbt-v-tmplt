<template>
  <Navi/>
  <div  class="form-div">
    <el-form ref="editForm" :model="editForm" :rules="editRules" label-position="left" label-width="100px" status-icon style="margin-top: 15px;">
      <br/>
      <el-row align="middle">
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item label="姓名" >
            <el-input v-model="name"   style="width: 200px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item label="工号" >
            <el-input v-model="aid" :disabled="true"  style="width: 200px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item align="center">
            <el-button type="primary" @click="submit" >提交</el-button>
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
  name: "AdminProfile",
  components:{Navi},
  data(){
    return{
      aid:"",
      name:"",
    }
  },
  created() {
    this.doQuery();
  },
  methods:{
    doQuery(){
      getProfile().then(res=>{
        this.aid=res.data.aid;
        this.name=res.data.name;
      })
    },
    submit(){
      submitProfile({"aid":this.aid,"name":this.name}).then(res=>{
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
    }
  }
}
</script>

