<template>
<div class="body">
 <div class="body-content">
   <user-consume @close="close" :begin="time1" :end="time2" :userConsume="userConsume" v-if="userconsume"></user-consume>
   <book-saled @close="close" :begin="time1" :end="time2" :bookSaled="bookSaled"  v-if="booksaled"></book-saled>
   <div class="admfun">
     <button @click="lookBookSaled" v-if="adm">销量统计</button>
     <button  @click="lookUserConsume" v-if="adm">用户消费统计</button>
   </div>
   <div class="filter" >
     <input v-if="adm" type="text" placeholder="username" v-model="onlyUser"/>
     <input type="text" placeholder="2000-01-01 12:00:00" v-model="time1"/>
     <input type="text" placeholder="2020-01-01 12:00:00" v-model="time2"/>
     <button v-if="!adm" @click="orderStatistics">统计</button>
   </div>
   <div class="content" v-if="!statistics">
     <div v-for="order in orders"
         v-if="((order.time>=time1 && order.time<=time2) 
         || (order.time<=time2 && time1=='') 
         || (order.time>=time1 &&time2==''))
         && (onlyUser=='' || (onlyUser!='' && order.username==onlyUser))" 
         class="order" >
          <p class="order-id">{{order.username}} | {{order.time}}</p>
          <div v-for="eorder in order.eorders"
               class="book">
            <img :src="'data:image/jpg;base64, '+eorder.book.bookimage" />
            <div class="message">
              <p class="ISBN">ISBN: {{eorder.book.isbn}}</p>
              <p>
                <span class="book-name">{{eorder.book.name}}</span>
                <span v-if="eorder.book.subtitle" class="subtitle"> · {{eorder.book.subtitle}}</span>
              </p>
              <p>第 {{eorder.book.version}} 版 · {{eorder.book.language}} 版</p>
              <p>
                <span class="price highlight">￥ {{eorder.book.price}}</span>
                &emsp;购买 <span class="highlight">{{eorder.book_num}}</span> 册
              </p>
            </div>
          </div>
          <p class="money highlight">￥ {{order.money}}</p>
     </div>
   </div>
   <div class="content" v-if="statistics">
     <div class="total">
       <span @click="close" class="iconfont close">&#xe658;</span>
       <div>
         <p >用户 <span class="number">{{orderSta.username}}</span></p>
         <p>在
           <span  class="number" v-if="orderSta.time1!=''">{{orderSta.begin}}</span>
           <span class="number" v-if="orderSta.time1==''">注册</span>
           —
           <span class="number" v-if="orderSta.time2!=''">{{orderSta.end}}</span>
           <span class="number" v-if="orderSta.time2==''">至今</span>
           时间段里
         </p><br />
         <p>购书 <span class="number">{{orderSta.totalTime}} </span>次</p>
         <p>累计购书 <span class="number">{{orderSta.totalBooknum}}</span> 册</p>
         <p>共计消费 <span class="highlight">￥{{orderSta.totalMoney}}</span></p>
       </div>
       <div>
         <span>于 <span class="number">{{orderSta.maxTime}}</span></span>
         <span>达单笔最大交易金额 <span class="highlight"> ￥{{orderSta.maxMoney}}</span></span>
       </div>
     </div>
     <div class="detail" v-for="order in orderSta.orders"
          :class="{max: order.time==orderSta.maxTime}">
       <p style="width:350px">订单日期：<span>{{order.time}}</span></p>
       <p style="width:200px">购书 <span class="highlight">{{order.booknum}}</span> 册</p>
       <p>消费 <span class="highlight">￥{{order.money}}</span></p>
     </div>
     <div class="tail"></div>
   </div>
  </div>
</div>
</template>



<script>

import UserConsume from './UserConsume'
import BookSaled from './BookSaled'

export default {
  components:{
    UserConsume,
    BookSaled,
  },
  data(){
    return {
      orders:[],
      time1:"2000-01-01 00:00:00",
      time2:"2020-01-01 00:00:00",
      orderSta:{},
      statistics:false,
      server:this.GLOBAL.server,
      adm:false,
      onlyUser:"",
      booksaled:false,
      userconsume:false,
      userConsume:{},
      bookSaled:{},
    }
  },
  methods:{
      lookUserConsume:function(){
        var getUrl=this.server+"/adm/userConsume?begin="+this.time1+
        "&end="+this.time2;
        this.$http.get(getUrl).then((res)=>{
          console.log(res.data);
          this.userConsume=res.data;
          this.userconsume=true;
        })
      },
      lookBookSaled:function(){
        var getUrl=this.server+"/adm/bookSaled?begin="+this.time1+
        "&end="+this.time2;
        this.$http.get(getUrl).then((res)=>{
          console.log(res.data);
          this.bookSaled=res.data;
          this.booksaled=true;
        })
      },
      getAllOrderList:function(){
        this.$http.get(this.server+"/adm/lookAllOrder").then((res)=>{
            console.log(res);
            this.orders=res.data;
        })
      },
      getOrderList:function(){
        this.$http.get(this.server+"/user/getPersonalOrder").then((res)=>{
          console.log(res);
            this.orders=res.data;
        })
      },
      orderStatistics:function(){
        var getUrl=this.server+"/user/getPersonalOrderStatistics?begin="
        +this.time1+"&end="+this.time2;
        this.$http.get(getUrl).then((res)=>{
          console.log(res);
          this.orderSta=res.data;
          this.statistics=true;
        })
      },
      close:function(){
        this.statistics=false;
        this.userconsume=false;
        this.booksaled=false;
      }
  },
  mounted(){
    this.$http.get(this.server+"/userType").then((res)=>{
      console.log(res);
        this.adm=res.data==2;
        if(res.data==0){
          this.$router.push({path:"/"});
        }
        if(this.adm){
          this.getAllOrderList();
        }else{
          this.getOrderList();
        }
    })
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.body-content{
  margin-top:500px;
  box-shadow: 0 0 5px 0;
  position:relative;
  width:890px;
  overflow:hidden;
  border-radius: 10px;
  margin: 0 auto;
  background:rgb(239, 240, 234);
}

.admfun{
  position:absolute;
  top:20px;
  width:520px;
  left:100px;
  z-index:400;
}
.admfun button{
  margin-right:20px;
}

.filter{
  position:absolute;
  top:20px;
  width:520px;
  right:40px;
  z-index:400;
}

.filter input{
  width:150px;
  height:30px;
  border-radius:8px;
  background:rgb(239, 240, 234);
  box-shadow: 0 0 5px 0;
  margin-right:15px;
}

.filter input:hover{
  color:rgb(12, 195, 250);
}

.filter button{
  width:70px;
  height:30px;
  vertical-align: top;
  border-radius:8px;
  background:rgb(12, 195, 250);
  box-shadow: 0 0 5px 0;
}

.filter button:hover{
  background:rgb(81, 140, 158);
}
.filter button:active{
  opacity: .3;
}

.content{
  padding:0 40px;
  width:900px;
  height:1180px;
  z-index:1000;
  overflow:auto;
  margin-top:80px;
}

.book img{
  width:80px;
}

.order{
  float:left;
  position:absolute;
  width:400px;
  margin:3px;
  position:relative;
  overflow:hidden;
  border-radius:10px;
  box-shadow: 0 0 20px 0;
  opacity:.9;
  z-index:1000;
}

.order-id{
  height:30px;
  line-height:30px;
  color:#666;
  padding:10px 10px 0 10px;
}

.money{
   box-shadow: #000 0 20px 0;
   padding: 10px;
   text-align:right;
}

.book{
  margin:1px;
  width:397px;
  height:114px;
  box-shadow: 0 0 10px 0;
  border-radius: 5px;
  overflow: hidden;
}

.message{
  display:inline-block;
  vertical-align:top;
  padding:10px;
}

.message p{
  margin-bottom:6px;
  width:280px;
  overflow:hidden;
  text-overflow:ellipsis;
  white-space:nowrap
}

.book-name{
  font-size:17px;
}

.subtitle{
  color:#666;
}

.ISBN{
  color:#666;
}

.total{
  box-shadow: 0 0 20px 0;
  margin-top:10px;
  width:700px;
  height:100px;
  opacity: .95;
  padding:5px 0;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  overflow:hidden;
  text-align:center;
  position: relative;
}

.total p{
  height:30px;
  line-height:30px;
  text-align:center;
  display:inline-block;
  padding:0 10px ;
}

.number{
  color:orange;
  font-size:17px;
}

.detail{
  box-shadow: 0 0 5px 0;
  width:700px;
  margin:5px 0;
  overflow:hidden;
  padding:30px 0;
  margin-bottom:1px;
}

.detail:hover{
  box-shadow: 0 0 50px 0;
}

.detail p{
  display:inline-block;
  padding-left:10px;
}
.detail .highlight{
  font-size:16px;
}

.tail{
  height:60px;
  width:700px;
  opacity:.95;
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
  box-shadow: 0 0 20px 0;
}

.max{
  background:rgb(12, 195, 250);
}


.close{
  position:absolute;
  right:15px;
  top:15px;
  font-size:20px;
  cursor:pointer;
  color:rgb(12, 195, 250);
}

</style>
