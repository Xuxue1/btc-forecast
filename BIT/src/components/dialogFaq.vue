<template>
  <el-dialog
    title="FAQ"
    class="dialogFaq"
    :visible="visible"
    :before-close="handleClose">
    <div class="dialogFaq-content">
      <p class="shuoP shuoT"><span class="shuoM">1、</span>预言家 Prophet是什么？</p>
      <p class="shuoP">预言家基于比特币K线，收集整合各方面的信息，从而对比特币未来一段时间内的走势产生判断。预言家可以基于自己掌握的信息买入未来事件的结果，一旦买入的结果与未来事件结果一致，便可获得高额回报。当然，若不一致也会造成损失，请慎重评估风险。</p>
      <p class="shuoP shuoT"><span class="shuoM">2、</span>为什么用区块链做预测？</p>
      <p class="shuoP">传统的预测市场有很多的痛点，比如黑箱操作、封闭不透明、维护成本高等，而区块链具有的智能合约、不可篡改特性可以很好的解决这些问题。由于我们还无法做到完全的去中心化结果，故只能暂时以最大的交易市场Biannce（https://www.binance.com/）的结果为准。</p>
      <p class="shuoP shuoT"><span class="shuoM">3、</span>如何知道你们是否有作弊？</p>
      <p class="shuoP">以下是智能合约地址，所有代码公开、透明，任何人都可以查看：<a class="color-zhang" href="https://explorer.nebulas.io/#/address/n1fY9873TTzh5HBWzC9bcNFevKaH8eBWQCS" target="_blank">https://explorer.nebulas.io/#/address/n1fY9873TTzh5HBWzC9bcNFevKaH8eBWQCS</a></p>
      <p class="shuoP shuoT"><span class="shuoM">4、</span>提示我没有安装插件怎么办？</p>
      <p class="shuoP">该应用是基于星云链智能合约进行开发的，故需要星云钱包才能运行。如没有安装星云钱包插件，则可以通过以下教程进行安装：<a class="color-zhang" href="https://github.com/ChengOrangeJu/WebExtensionWallet" target="_blank">https://github.com/ChengOrangeJu/WebExtensionWallet</a></p>
      <p class="shuoP shuoT"><span class="shuoM">5、</span>获得奖励后，为什么查不到给我的转账记录？</p>
      <p class="shuoP">由于星云浏览器暂时无法查看智能合约的转账记录，所以获得的奖励是在浏览器里查不到记录的，但是奖励是会到账的，请注意钱包余额的变化。</p>
      <p class="shuoP shuoT"><span class="shuoM">6、</span>如何联系我们？</p>
      <p class="shuoP">如遇到问题可随时给我们发邮件，我们收到后会第一时间处理：mnbvcxz321v@163.com。</p>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
    }
  },
  methods:{
    handleClose(){
      this.$emit('close')
    }
  },
  props:['visible']
}
</script>
