<template>
    <div class="this-book block">
        <img :src="book.cover" />
        <div class="book-message">
            <p class="highlight score"> {{book.score}} </p>
            <p class="isbn">ISBN: {{book.ISBN}}</p>
            <p>
                <span class="book-name">{{book.book_name}}</span>
                <span v-if="book.subtitle"> · 
                    <span class="subtitle">{{book.subtitle}}</span>
                </span>
            </p>
            <p>作者：{{book.author_name}}
                <span v-if="book.author1_name"> | {{book.author1_name}}</span>
                <span v-if="book.author2_name"> | {{book.author2_name}}</span>
                <span v-if="book.author3_name"> | {{book.author3_name}}</span>
            </p>
            <p v-if="book.translator_name">译者：{{book.translator_name}}
                <span v-if="book.translator1_name"> | {{book.translator1_name}}</span>
                <span v-if="book.translator2_name"> | {{book.translator2_name}}</span>
                <span v-if="book.translator3_name"> | {{book.translator3_name}}</span>
            </p>
            <p>版本：第{{book.version}}版</p>
            <p>语言：{{book.language_name}}</p>
            <p>出版：{{book.press_name}} </p>
            <p class="logo">
                <button v-for="logo in book.logo">{{book.logos}}</button>
            </p>
        </div>
        <p class="num">
            销量 <span>{{book.saled}}</span> 册 |
            库存 <span>{{book.inventory}}</span> 册
            <span class="highlight">￥{{book.price}}</span>
        </p>
    </div>
</template>

<script>
export default {
  props: ["book"],
  data() {
    return {};
  },
  methods: {},
  mounted(){
      console.log(this.book)
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
img {
  margin: 0;
}

.isbn{
    color:#666;
}

.this-book {
  position: absolute;
  background:#000;
  z-index: 10000;
  left:0;
  right:0;
  top:0;
  bottom:0;
  margin:auto;
  padding: 20px;
  width:440px;
  height:240px;
  opacity:.92;
  border-radius:8px;
}

.this-book img {
  width: 140px;
  border-radius:5px;
  z-index:10000;
}

.this-book button {
  float: none;
}

.book-message {
  display: inline-block;
  font-size: 15px;
  vertical-align:top;
  margin-left:10px;
  width:270px;
  height:200px;
}

.book-message p {
  margin-bottom: 10px;
  height:20px;
  line-height:20px;
  overflow:hidden;
  text-overflow:ellipsis;
  white-space:nowrap;
}

.book-message .score{
  height:25px;
  line-height:25px;
}

.this-book .highlight {
  font-size: 25px;
  margin: 10px;
}

.score {
  position: absolute;
  right: 0;
  top:15px;
}

.book-name {
  font-size: 20px;
  cursor: pointer;
}

.logo button {
  font-size: 10px;
  margin: 0 2px;
  cursor: auto;
}

.book-fun {
  padding-bottom: 10px;
}

.book-fun span {
  display: inline-block;
  width: 60px;
  text-align: center;
  cursor: pointer;
  color: cadetblue;
}

.shoucang {
  font-size: 24px;
}
.gouwuche {
  font-size: 28px;
}

.book-fun span:hover {
  color: #000;
}

.book-fun span:active {
  color: cadetblue;
}

.num{
    margin-top:10px;
}

.num span{
    color:orange;
    font-size:19px;
}

.subtitle{
    color:#666;
}
</style>