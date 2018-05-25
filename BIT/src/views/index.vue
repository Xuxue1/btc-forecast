<template>
  <div class="index-wrapper">
    <div class="main-content">
      <div class="content-top">
        <span class="logo-word">预言家 Prophet</span>
        <span class="content-fqa" @click="dialogFaqVisible = true">FAQ</span>
      </div>
      <div class="content-intro">
        <div class="intro-word">
          <p class="intro-title">玩法说明:</p>
          <p class="intro-step">1、每小时开盘一次，预测最近整点时间与其下一个小时的比特币价格对比，涨或者跌（如现在是22:30，预测24点比23点的比特币价格涨跌）。</p>
          <p class="intro-step">2、比特币价格以Binance为准，BTC/USDT交易对，以整点时从Binance获取到的价格为准。</p>
          <p class="intro-step">3、预测正确的预言家瓜分全部的NAS，按每人付出的NAS所占比例分配。若没有人预测正确，则所有人的NAS原路返回。</p>
          <p class="intro-step">4、奖励将在开盘1分钟之内结算并发到用户预测时使用的账户，返还奖励时会扣除1%的矿工费。</p>
        </div>
        <div class="price-wrapper">
          <div class="price-item">
            <p class="price-item-1">${{open_price}}</p>
            <p class="price-item-2">最近一次开盘价格</p>
            <p class="price-item-3">{{open_time}}</p>
          </div>
          <div class="price-item">
            <p class="price-item-1">
              ${{close_price}}<i v-if="open_price<close_price" class="el-icon-back" style="transform:rotate(90deg)"></i><i v-if="open_price>close_price" class="el-icon-back" style="transform:rotate(-90deg)"></i>
            </p>
            <p class="price-item-2">当前价格</p>
            <p class="price-item-3">{{now_time}}</p>
          </div>
          <div class="price-item" style="width:26%">
            <p class="price-item-1">{{residue_time}}</p>
            <p class="price-item-2">距下次开盘时间</p>
            <p class="price-item-3">{{next_open_time}}</p>
          </div>
        </div>
      </div>
      <div class="chang">
        <el-button-group>
          <el-button :type="status==1?'primary':'button'" @click="switchStatus">{{open_time}}场</br><span class="small">进行中，停止预测</span>
          </el-button>
          <el-button :type="status==2?'primary':'button'" @click="switchStatus">{{next_open_time}}场</br><span
            class="small">未开始，可预测</span></el-button>
        </el-button-group>
      </div>


      <div v-if="status===1" class="yuce">
        <p>预测{{next_open_time}}比{{open_time}}的涨跌</p>
        <p>该场次预测已于{{open_time}}截止，请参加下一场</p>
      </div>

      <div v-if="status===2" class="yuce">
        <p>预测{{next_next_open_time}}比{{next_open_time}}的涨跌</p>
        <p>该场次正在预测!</p>
      </div>


      <div class="zhang-die">
        <div class="bilv-wrapper">
          <div :class="['bilv-zhang',zhang_rate==100?'all-zhang':'']" :style="'width:'+zhang_rate+'%'">
            <span class="bilv-zhang-word">看涨</span>
            <div class="bilv">{{zhang_total}}:{{die_total}}</div>
          </div>
          <div :class="['bilv-die',zhang_rate==100?'all-die':'']" :style="'width:'+die_rate+'%'">
            <span class="bilv-die-word">看跌</span>
          </div>
        </div>
        <div class="bilv-box">
          <div class="zhang-box">
            <div class="half">
              <p class="boxTitle">看涨</p>
              <el-row class="boxTop">
                <el-col :span="12">
                  <p class="money">{{zhang_total.toFixed(4)}}
                  <p>
                  <p class="explain">累计预测NAS</p>
                </el-col>
                <el-col :span="12">
                  <p class="money">{{(die_total+zhang_total).toFixed(4)}}<span v-if="zhang_all_income_rate!=='0%'">+{{zhang_all_income_rate}}</span>
                  <p>
                  <p class="explain">预计可获得NAS</p>
                </el-col>
              </el-row>
            </div>
            <el-row class="boxBtm">
              <el-col :span="12">
                <p class="money">{{zhang_my.toFixed(4)}}</p>
                <p class="explain">我预测的NAS</p>
              </el-col>
              <el-col :span="12">
                <p class="money">{{zhang_income.toFixed(4)}}<span v-if="zhang_my_income_rate!=='0%'">+{{zhang_my_income_rate}}</span></p>
                <p class="explain">预计可获得NAS</p>
              </el-col>
            </el-row>
            <el-row class="btn-wrapper">
              <button v-if="status==1" class="yijiezhi">已截止</button>
              <button v-if="status==2" class="xiazhu" @click="zhang_xiazhu">预测</button>
            </el-row>
          </div>
          <div class="die-box">
            <div class="half">
              <p class="boxTitle">看跌</p>
              <el-row class="boxTop">
                <el-col :span="12">
                  <p class="money">{{die_total.toFixed(4)}}
                  <p>
                  <p class="explain">累计预测NAS</p>
                </el-col>
                <el-col :span="12">
                  <p class="money">{{(zhang_total+die_total).toFixed(4)}}<span v-if="die_all_income_rate!=='0%'">+{{die_all_income_rate}}</span>
                  <p>
                  <p class="explain">预计可获得NAS</p>
                </el-col>
              </el-row>
            </div>
            <el-row class="boxBtm">
              <el-col :span="12">
                <p class="money">{{die_my.toFixed(4)}}</p>
                <p class="explain">我预测的NAS</p>
              </el-col>
              <el-col :span="12">
                <p class="money">{{die_income.toFixed(4)}}<span v-if="die_my_income_rate!='0%'">+{{die_my_income_rate}}</span></p>
                <p class="explain">预计可获得的NAS</p>
              </el-col>
            </el-row>
            <el-row class="btn-wrapper">
              <button v-if="status==1" class="yijiezhi">已截止</button>
              <button v-if="status==2" class="xiazhu" @click="die_xiazhu">预测</button>
            </el-row>
          </div>
        </div>
      </div>
      <el-row class="clear">
        <div class="tab-wrapper">
          <el-tabs v-model="activeName2" @tab-click="handleClick">
            <el-tab-pane label="历史涨跌" name="first">
              <p class="tab-tips">只显示最近24小时涨跌</p>
              <div>
               <p class="tab1-wrapper">
                  <span class="tab1-time">场次</span>
                  <span class="tab1-money">价格</span>
                  <span class="tab1-zhang-die">涨跌</span>
                  <span class="tab1-shouyi">收益率</span>
                </p>
                <p class="tab1-wrapper" v-for="item in data1" :key="item.time">
                  <span class="tab1-time">{{item.time}}</span>
                  <span class="tab1-money">{{item.money}}</span>
                  <span class="tab1-zhang-die">{{item.zhangDie}}
                    <i v-if="item.zhangDie == '涨'" class="el-icon-back color-zhang"
                        style="transform:rotate(90deg)"></i>
                    <i v-if="item.zhangDie == '跌'" class="el-icon-back color-die"
                        style="transform:rotate(-90deg)"></i>
                  </span>
                  <span class="tab1-shouyi">{{item.shouyi}}</span>
                </p>
              </div>
            </el-tab-pane>
            <el-tab-pane label="今日预测记录" name="second">
              <p class="tab-tips">点击预测金额可查询该笔交易进度</p>
              <div style="margin-top:20px">
                <div>
                  <p class="tab2-wrapper">
                    <span class="tab1-time">场次</span>
                    <span class="tab-link">地址</span>
                    <span class="tab1-kan">涨跌</span>
                    <span class="tab1-zhu">预测金额</span>
                    <span class="tab1-shouyiText" >收益</span>
                  </p>
                </div>
                <div class="tab1-wrapper" v-for="item in data2" :key="item.time">
                  <p class="tab2-wrapper">
                    <span class="tab1-time">{{item.time}}</span>
                    <span class="tab-link">{{item.link}}</span>
                    <span class="tab1-kan">{{item.kan}}</span>
                    <a class="tab1-zhu color-zhang" :href=base+item.txHash>{{item.zhu}}</a>
                    <span class="tab1-shouyiText" v-if="item.shouyiState ==1">未开盘</span>
                    <span class="tab1-shouyiText" v-if="item.shouyiState ==2">0</span>
                    <span class="tab1-shouyiText" v-if="item.shouyiState ==3">{{item.shouyiMoney}}</span>
                  </p>
                </div>
              </div>
              <el-pagination
                class="marginTop30"
                @current-change="handleCurrentChange1"
                :current-page="currentPage1"
                :page-size="10"
                layout="total, prev, pager, next, jumper"
                :total="total1">
              </el-pagination>
            </el-tab-pane>
            <el-tab-pane label="我的预测记录" name="third">
              <p class="tab-tips">点击预测金额可查询该笔交易进度</p>
              <div style="margin-top:20px">
                <div>
                  <p class="tab2-wrapper">
                    <span class="tab1-time">场次</span>
                    <span class="tab-link">地址</span>
                    <span class="tab1-kan">涨跌</span>
                    <span class="tab1-zhu">预测金额</span>
                    <span class="tab1-shouyiText" >收益</span>
                  </p>
                </div>
                <div class="tab1-wrapper" v-for="item in data3" :key="item.time">
                  <p class="tab2-wrapper">
                    <span class="tab1-time">{{item.time}}</span>
                    <span class="tab-link">{{item.link}}</span>
                    <span class="tab1-kan">{{item.kan}}</span>
                    <a class="tab1-zhu color-zhang" :href=base+item.txHash>{{item.zhu}}</a>
                    <span class="tab1-shouyiText" v-if="item.shouyiState ==1">未开盘</span>
                    <span class="tab1-shouyiText" v-if="item.shouyiState ==2">0</span>
                    <span class="tab1-shouyiText" v-if="item.shouyiState ==3">{{item.shouyiMoney}}</span>
                  </p>
                </div>
              </div>
              <el-pagination
                class="marginTop30"
                @current-change="handleCurrentChange2"
                :current-page="currentPage2"
                :page-size="10"
                layout="total, prev, pager, next, jumper"
                :total="total2">
              </el-pagination>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-row>
    </div>

    <!-- 下注弹框 -->
    <el-dialog
      class="dialogReset"
      :title="RaiseOrFall=='rise'?'看涨':'看跌'"
      :visible.sync="dialogVisible">
      <div>
        <p class="paddingBtm20">请选择预测的NAS</p>
        <div :class="RaiseOrFall" style="padding-right:15px;">
          <div class="paddingLeft15">
            <el-slider v-model="value2" :format-tooltip="formatTooltip" :show-tooltip="false"></el-slider>
          </div>
          <p class="clear paddingBtm20 textAlignCenter">
            <span class="floatL paddingLeft15">0</span>
            <span :class="RaiseOrFall=='rise'?'color-zhang':'color-die'" style="font-size:25px">{{value1}}</span>
            <span class="floatR">10</span></p>
        </div>
        <p class="paddingBtm20">预计收益</p>
        <p v-if="RaiseOrFall==='rise'" :class="RaiseOrFall+'Money'"><span>{{zhang_shouyi}}</span></p>
        <p v-if="RaiseOrFall==='fall'" :class="RaiseOrFall+'Money'"><span>{{die_shouyi}}</span></p>
      </div>
      <span slot="footer" class="dialog-footer">
        <button :class="RaiseOrFall+'Btn'" @click="submit" :disabled="value2==0">预测，去支付</button>
      </span>
    </el-dialog>

    <!-- faq弹框  -->
    <dialog-faq :visible="dialogFaqVisible" @close="closeDialog"></dialog-faq>

    <!--请使用Chrome浏览器，并安装星云钱包插件，插件安装教程-->
    <el-dialog title="提示" :visible.sync="dialogNotice" class="dialogFaq" center>
        <div style="padding-top:50px">
           <p class="shuoP shuoT" style="padding-left:0">请使用Chrome浏览器，并安装星云钱包插件，插件安装教程：<a class="color-zhang" href="https://github.com/ChengOrangeJu/WebExtensionWallet" target="_blank">https://github.com/ChengOrangeJu/WebExtensionWallet</a> </p>
        </div>
        <div slot="footer" class="dialog-footer iKnow" style="margin-bottom:20px;">
            <button :class="RaiseOrFall=='rise'?'bgcolor-zhang':'bgcolor-die'" @click="dialogNotice = false">我知道了</button>
        </div>
    </el-dialog>


    <!-- 下注成功提示 -->
    <el-dialog title="提示" :visible.sync="pushsuccess" center width="30%">
        <div style="padding-top:15px">
           <p style="text-align:center;">{{txt}}</p>
        </div>
        <div slot="footer" class="dialog-footer iKnow" style="margin-bottom:20px;">
            <button :class="RaiseOrFall=='rise'?'bgcolor-zhang':'bgcolor-die'" @click="pushsuccess = false">我知道了</button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
  import api from '../fetch/api'

  export default {
    name: 'Hello',
    components: {
        dialogFaq: () => import("@/components/dialogFaq")
    },
    data() {
      return {
        pushsuccess:false,    //下注成功提示
        RaiseOrFall: 'rise',   //涨或者跌 控制弹框头部
        dialogVisible: false, //弹窗是否显示
        dialogFaqVisible:false,  //弹框faq
        currentPage1: 1,
        total1: 0,
        currentPage2: 1,
        total2: 0,
        value1: 0,   //滑块
        value2: 0,
        zhang_total:0,
        zhang_rate:50,
        die_rate:50,
        die_total:0,
        die_income:0,
        base:"https://explorer.nebulas.io/#/tx/",
        zhang_income:0,
        zhang_my:0,
        die_my:0,
        activeName2: 'first',
        status: 1,
        data1: [],
        data2: [],
        data3:[],
        time: '',
        nebPay: new NebPay(),
        open_price: '',
        close_price: '',
        open_time: '',
        now_time: '',
        open_time_long: '',
        residue_time: '',
        next_open_time: '',
        next_next_open_time:'',
        next_date: '',
        //app_address: 'n1oV3XbFfFixRGQjfHgwTGKbr62mrPWgM2j',
        app_address:'n1fY9873TTzh5HBWzC9bcNFevKaH8eBWQCS',
        address:'',
        all_data:[],
        my_data:[],
        now_data:{},
        next_data:{},
        dialogNotice:false,      //判断是否是谷歌浏览器 或者 有无安装钱包插件 的  弹框
        show_message:'',
        zhang_all_income_rate:'0%',
        zhang_my_income_rate:'0%',
        die_my_income_rate:'0%',
        die_all_income_rate:'0%',
        page_size1:1,
        page_size2:1,
        page_num1:1,
        page_num:2,
        timer:"",
        txt:"预测成功，祝您好运"
      }
    },
    created() {

      this.getWallectInfo();
      this.refrash_recent();
      this.refrash_history_data();
      this.refrash_all_data(1);
      this.refrash_my_data(1);
    },
    mounted(){
      let v = this;
      v.requestKline().then( time =>{
        if(v.residue_time === '00:00'){
          v.residue_time = '59:59';
        }else{
          let now = new Date();
          let next_date = new Date(time + 60 * 60 * 1000)
          v.next_date = next_date;
          let  seconds = (next_date.getTime() - now.getTime()) / 1000
          let hour = parseInt(seconds / 60);
          let s = parseInt(seconds % 60);
          v.residue_time = v.formate_number(hour) + ":" + v.formate_number(s);
        }
      });
      setInterval(()=>{
        if(v.timer!==""){
          clearInterval(v.timer)
        }
        v.requestKline().then( time =>{
          v.timer = setInterval(() => {
            if(v.residue_time === '00:00'){
              v.residue_time = '59:59';
            }else{
              let now = new Date();
              let next_date = new Date(time + 60 * 60 * 1000)
              v.next_date = next_date;
              let  seconds = (next_date.getTime() - now.getTime()) / 1000
              let hour = parseInt(seconds / 60);
              let s = parseInt(seconds % 60);
              v.residue_time = v.formate_number(hour) + ":" + v.formate_number(s);
            }
          },1000);
        });
      },6000);
    },
    methods: {
      handle_data_change(){
        let v = this;
        let data;
        this.zhang_total = 0;
        this.die_total = 0;
        this.zhang_my = 0;
        this.die_my = 0;
        this.zhang_all_income_rate = '0%';
        this.die_my_income_rate = '0%';
        this.die_all_income_rate = '0%';
        this.zhang_my_income_rate = '0%';
        this.zhang_income = 0;
        this.die_income = 0;
        if(this.status===1) {
          data = v.now_data;
        }else{
          data = v.next_data;
        }
        if(data && data.data){
          this.zhang_total = data.data.rise_amount;
          this.die_total = data.data.fall_amount;
          data.data.rise.forEach(r=>{
            if(r.address === v.address){
              v.zhang_my = r.value
            }
          });
          data.data.fall.forEach(r=>{
            if(r.address === v.address){
              v.die_my = r.value
            }
          })
        }
        if(v.zhang_total!==0){
          v.zhang_total = parseFloat((v.zhang_total/nas_rate).toFixed(3))
        }
        if(v.die_my!==0){
          v.die_my = parseFloat((v.die_my/nas_rate).toFixed(3))
        }
        if(v.zhang_my !==0){
          v.zhang_my = parseFloat((v.zhang_my/nas_rate).toFixed(3))
        }
        if(v.die_total !==0){
          v.die_total = parseFloat((v.die_total/nas_rate).toFixed(3))
        }

        if(v.zhang_total===0 && v.die_total===0){
          v.zhang_rate = 50;
          v.die_rate = 50;
        }

        if(v.zhang_total===0 && v.die_total!==0){
          v.zhang_rate = 0;
          v.die_rate = 100;
        }

        if(v.zhang_total!==0 && v.die_total===0){
          v.zhang_rate = 100;
          v.die_rate = 0;
        }

        if(v.zhang_total!==0 && v.die_total!==0){
          v.zhang_rate = parseInt(100 * v.zhang_total/(v.zhang_total+v.die_total));
          v.die_rate = 100-v.zhang_rate;
          v.die_all_income_rate = parseInt((v.zhang_total/v.die_total)*100)+'%';
          v.zhang_all_income_rate = parseInt((v.die_total/v.zhang_total)*100)+'%';
        }

        if(v.zhang_my!==0){
          v.zhang_income = (this.zhang_my/this.zhang_total)*(this.die_total+this.zhang_total);
          v.zhang_my_income_rate = v.zhang_all_income_rate
        }

        if(v.die_my !==0){
          this.die_income = (this.die_my/this.die_total)*(this.die_total+this.zhang_total);
          v.die_my_income_rate = v.die_all_income_rate
        }
      },
      refrash_recent(){
        let v = this;
        let date = new Date();
        date.setSeconds(0);
        date.setMilliseconds(0);
        date.setMinutes(0);
        let callFunction = "allRecord";
        let dappAddress = this.app_address;
        let callArgs = [date.getTime(), date.getTime()+60*60*1000];
        this.nebPay.simulateCall(dappAddress, "0", callFunction, JSON.stringify(callArgs), {
          callback: response=>{
              let result = JSON.parse(response.result);
              result.forEach(d=>{
                console.log(date.getTime());
                if(d.time === date.getTime()){
                  v.now_data = d;
                }else{
                  v.next_data = d;
                }
              });
              v.handle_data_change();
          }
        });
      },
      refrash_history_data(){
        let v = this;
        let date = new Date();
        date.setSeconds(0);
        date.setMilliseconds(0);
        date.setMinutes(0);
        let start_time = date.getTime()-60*60*1000*25;
        let end_time = date.getTime()-60*60*1000;
        let callFunction = "allRecord";
        let dappAddress = this.app_address;
        let callArgs = [start_time, end_time];
        this.nebPay.simulateCall(dappAddress, "0", callFunction, JSON.stringify(callArgs), {
          callback: response=>{
            console.log(JSON.stringify(response));
            let result = JSON.parse(response.result);
            result.forEach(r=>{
              let data = {};
              let date = new Date(r.time);
              data.time = v.formate_number(date.getHours())+":"+v.formate_number(date.getSeconds())
              data.money = r.data.close_price;
              data.t = r.time;
              if(r.data.close_price>r.data.open_price){
                data.zhangDie = '涨';
                if(r.data.rise_amount!==0 && r.data.fall_amount!==0){
                  data.shouyi = ((r.data.fall_amount/r.data.rise_amount)*100).toFixed(2)+"%"
                }else{
                  data.shouyi = '0%'
                }
              }else{
                data.zhangDie = '跌';
                if(r.data.rise_amount!==0 && r.data.fall_amount!==0){
                  data.shouyi = ((r.data.rise_amount/r.data.fall_amount)*100).toFixed(2)+"%"
                }else{
                  data.shouyi = '0%'
                }
              }
              v.data1.push(data)
              v.data1.sort((a,b)=>b.t-a.t);
            })

          }
        });
      },
      refrash_all_data(page_num){
        let v = this;
        let date = new Date();
        date.setSeconds(0);
        date.setMilliseconds(0);
        date.setMinutes(0);
        let start_time = date.getTime()-60*60*1000*25;
        let end_time = date.getTime()+60*60*1000;
        let callFunction = "allRecord";
        let dappAddress = this.app_address;
        let callArgs = [start_time, end_time];
        let all_data = [];
        this.nebPay.simulateCall(dappAddress, "0", callFunction, JSON.stringify(callArgs), {
          callback: response=>{
            let result = JSON.parse(response.result);
            result.forEach(r=>{
              let date = new Date(r.time);
              let time = v.formate_number(date.getHours())+":"+v.formate_number(date.getSeconds())
              r.data.rise.forEach(rise=>{
                  let data ={};
                  if(r.data.status === 0){
                    data.shouyiState = 1
                  }else{
                    if(rise.value === rise.get){
                      data.shouyiState = 2;
                    }else if(rise.value!==undefined && rise.get!==undefined && rise.get>rise.value){
                      data.shouyiState = 3;
                      data.shouyiMoney = parseInt(100*(rise.get-rise.value)/rise.value)+'%';
                    }else if(rise.get===undefined){
                      data.shouyiState = 3;
                      data.shouyiMoney = "-100%"
                    }
                  }
                  data.t = r.time;
                  data.txHash = rise.fromHash;
                  data.link = rise.address;
                  data.time = time;
                  data.kan = '看涨';
                  data.zhu = (rise.value/nas_rate).toFixed(3);
                  all_data.push(data)
              });
              r.data.fall.forEach(fall=>{
                let data ={};
                data.link = fall.address;
                data.time = time;
                data.kan = '看跌';
                data.zhu = (fall.value/nas_rate).toFixed(3);
                data.txHash = fall.fromHash;
                data.t = r.time;
                if(r.data.status === 0){
                  data.shouyiState = 1
                }else{
                  if(fall.value === fall.get){
                    data.shouyiState = 2;
                  }else if(fall.value!==undefined && fall.get!==undefined && fall.get>fall.value){
                    data.shouyiState = 3;
                    data.shouyiMoney = parseInt(100*(fall.get-fall.value)/fall.value)+'%';
                  }else if(fall.get===undefined){
                    data.shouyiState = 3;
                    data.shouyiMoney = "-100%"
                  }
                }
                all_data.push(data)
              })
            });
            v.total1 = all_data.length;
            all_data.sort((a,b)=>b.t-a.t);
            let start = (page_num-1)*10;
            let end = start+10;
            v.data2 = [];
            while(start<end && start<all_data.length){
              v.data2.push(all_data[start])
              start+=1;
            }
          }
        });

      },
      refrash_my_data(page_num){
        let v = this;
        let callFunction = "myRecord";
        let dappAddress = this.app_address;
        let callArgs = [10, page_num];
        v.data3 = [];
        this.nebPay.simulateCall(dappAddress, "0", callFunction, JSON.stringify(callArgs), {
          callback: response=>{
            let result = JSON.parse(response.result);
            v.total2 = result.total;
            result.record.forEach(r=>{
              let date = new Date(r.time);
              let time = v.formate_number(date.getFullYear())+"-"+v.formate_number(date.getMonth())+"-"+v.formate_number(date.getDate())+" "+v.formate_number(date.getHours())+":"+v.formate_number(date.getSeconds())
              r.data.rise.forEach(rise=>{
                let data ={};
                data.link = rise.address;
                data.time = time;
                data.kan = '看涨';
                data.t = r.time;
                data.zhu = (rise.value/nas_rate).toFixed(3);
                data.txHash = rise.fromHash;
                if(r.data.status === 0){
                  data.shouyiState = 1
                }else{
                  if(rise.value === rise.get){
                    data.shouyiState = 2;
                  }else if(rise.value!==undefined && rise.get!==undefined && rise.get>rise.value){
                    data.shouyiState = 3;
                    data.shouyiMoney = parseInt(100*(rise.get-rise.value)/rise.value)+'%';
                  }else if(rise.get===undefined){
                    data.shouyiState = 3;
                    data.shouyiMoney = '-100%'
                  }
                }
                if(rise.address === v.address){
                  v.data3.push(data);
                  v.data3.sort((a,b)=>b.t-a.t)
                }
              });
              r.data.fall.forEach(fall=>{
                let data ={};
                data.link = fall.address;
                data.time = time;
                data.kan = '看跌';
                data.zhu = (fall.value/nas_rate).toFixed(3);
                data.txHash = fall.fromHash;
                data.t = r.time;
                if(r.data.status === 0){
                  data.shouyiState = 1
                }else{
                  if(fall.value === fall.get){
                    data.shouyiState = 2;
                  }else if(fall.value!==undefined && fall.get!==undefined && fall.get>fall.value){
                    data.shouyiState = 3;
                    data.shouyiMoney = parseInt(100*(fall.get-fall.value)/fall.value)+'%';
                  }else if(fall.get===undefined){
                    data.shouyiState = 3;
                    data.shouyiMoney = '-100%';
                  }
                }
                if(fall.address === v.address){
                  v.data3.push(data);
                  v.data3.sort((a,b)=>b.t-a.t)
                }
              })
            });
          }
        });

      },
      handleCurrentChange1(val) {
        this.refrash_all_data(parseInt(val))
      },
      handleCurrentChange2(val) {
        this.refrash_my_data(parseInt(val))
      },
      closeDialog(){
          this.dialogFaqVisible = false;
      },
      formatTooltip(val) {
        this.value1 = val / 10;
        return val / 10;
      },
      handleClick(val) {
        console.log(val)
      },
      switchStatus() {
        if (this.status === 1) {
          this.status = 2
        } else {
          this.status = 1
        }
        this.handle_data_change();
      },
      requestKline() {
        return new Promise((resolve, reject)=>{
          api.api('get', '/api/btc/kline').then(res => {
            if (res.length > 0) {
              let recentPrice = res[res.length - 1];
              let time = recentPrice[0];
              let open = parseFloat(recentPrice[1]);
              let close = parseFloat(recentPrice[4]);
              this.open_price = open.toFixed(2);
              this.close_price = close.toFixed(2);
              let date = new Date(time);
              this.open_time = this.formate_number(date.getHours()) + ":" + this.formate_number(date.getMinutes())
              let now = new Date()
              this.now_time = this.formate_number(now.getHours()) + ":" + this.formate_number(now.getMinutes())
              let next_date = new Date(time + 60 * 60 * 1000)
              this.next_date = next_date;
              let seconds = (next_date.getTime() - now.getTime()) / 1000
              let hour = parseInt(seconds / 60);
              let s = parseInt(seconds % 60);
              console.log(next_date,this.residue_time)
              this.next_open_time = this.formate_number(next_date.getHours()) + ":" + this.formate_number(next_date.getMinutes());

              let next_next_date = new Date(next_date.getTime()+60*60*1000);
              this.next_next_open_time = this.formate_number(next_next_date.getHours()) + ":" + this.formate_number(next_next_date.getMinutes())
              resolve(time);
            }
          })
        })
      },
      submit() {
        this.dialogVisible = false;
        let callFunction = "bid";
        let dappAddress = this.app_address;
        let callArgs = [this.RaiseOrFall, this.next_date.getTime()];
        console.log(JSON.stringify(callArgs));
        let amount = this.value1;
        this.nebPay.call(dappAddress, amount, callFunction, JSON.stringify(callArgs), {
          qrcode: {
            showQRCode: false
          },
          callback: this.submitCallback
        });
        console.log("submit")
      },
      submitCallback(resp) {
        console.log("test "+resp.txhash);
        this.getStatus(resp.txhash)
      },
      getStatus(txhash){
        let v = this;
        neb.api.getTransactionReceipt(txhash)
          .then(o => {
            console.log(JSON.stringify(o));
            if(o.status === 2){
              window.setTimeout(()=>this.getStatus(txhash),3000)
            }else if(o.status === 1){
              v.refrash_all_data(1);
              v.refrash_my_data(1);
              v.pushsuccess = true;
            }else if(o.status === 0){
              v.txt = "预测失败 "+o.execute_result;
              v.pushsuccess = true;
            }
          })
          .catch(function (o) {
            console.log(o)
          })
      },
      formate_number(number) {
        if (parseInt(number) >= 0 && parseInt(number) < 10){
          return "0"+number;
        }
        else return number + "";
      },
      getWallectInfo() {
        let v = this;
        window.addEventListener('message', function (e) {
          if (e.data && e.data.data) {
            if (e.data.data.account) {
              v.address = e.data.data.account
            }
          }
        });
        window.postMessage({
          "target": "contentscript",
          "data": {},
          "method": "getAccount",
        }, "*");
      },
      zhang_xiazhu(){
        this.RaiseOrFall='rise'
        if(typeof(webExtensionWallet) === "undefined" || navigator.userAgent.indexOf("Chrome") <= -1){
          console.log("not installed");
          this.dialogNotice = true;
        }else{
          this.dialogVisible=true;
        }
      },
      die_xiazhu(){
        this.RaiseOrFall='fall';
        if(typeof(webExtensionWallet) === "undefined" || navigator.userAgent.indexOf("Chrome") <= -1){
          this.dialogNotice = true;
        }else{
          this.dialogVisible=true;
        }
      }
    },
    computed:{
      zhang_shouyi: function () {
        let v = (this.value1/(this.zhang_total+this.value1))*this.die_total;
        if(isNaN(v)|| v === 0){
          return this.value1.toFixed(4)
        }else{
          return (v+this.value1).toFixed(4);
        }
      },
      die_shouyi:function () {
        let v = (this.value1/(this.die_total+this.value1))*this.zhang_total;
        if(isNaN(v)|| v === 0){
          return this.value1.toFixed(4)
        }else{
          return (v+this.value1).toFixed(4);
        }
      }
    }
  }
</script>
