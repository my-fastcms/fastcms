var h=Object.defineProperty;var i=Object.getOwnPropertySymbols;var f=Object.prototype.hasOwnProperty,C=Object.prototype.propertyIsEnumerable;var c=(a,e,t)=>e in a?h(a,e,{enumerable:!0,configurable:!0,writable:!0,value:t}):a[e]=t,d=(a,e)=>{for(var t in e||(e={}))f.call(e,t)&&c(a,t,e[t]);if(i)for(var t of i(e))C.call(e,t)&&c(a,t,e[t]);return a};import{g as w}from"./client.991fed2a.js";import{_ as D}from"./index.8302dcda.js";import{j as z,o as v,t as A,l as r,m as S,z as o,A as u,D as E,p as N,x}from"./vendor.d2ed1f2c.js";const j={name:"articleCommentManager",components:{},setup(){const a=z({tableData:{data:[],total:0,loading:!1,param:{pageNum:1,pageSize:10}}}),e=()=>{w(a.tableData.param).then(l=>{a.tableData.data=l.data.records,a.tableData.total=l.data.total}).catch(()=>{})},t=l=>{a.tableData.param.pageSize=l},p=l=>{a.tableData.param.pageNum=l};return v(()=>{e()}),d({onHandleSizeChange:t,onHandleCurrentChange:p,initTableData:e},A(a))}},y={class:"mb15"},F=x("\u67E5\u8BE2");function H(a,e,t,p,l,k){const m=r("el-input"),g=r("el-button"),n=r("el-table-column"),b=r("el-table"),B=r("el-pagination"),_=r("el-card");return E(),S("div",null,[o(_,{shadow:"hover"},{default:u(()=>[N("div",y,[o(m,{size:"small",placeholder:"\u8BF7\u8F93\u5165\u8BC4\u8BBA\u5185\u5BB9","prefix-icon":"el-icon-search",style:{"max-width":"180px"},class:"ml10"}),o(g,{size:"small",type:"primary",class:"ml10"},{default:u(()=>[F]),_:1})]),o(b,{data:a.tableData.data,stripe:"",style:{width:"100%"}},{default:u(()=>[o(n,{prop:"id",label:"ID","show-overflow-tooltip":""}),o(n,{prop:"content",label:"\u8BC4\u8BBA\u5185\u5BB9","show-overflow-tooltip":""}),o(n,{prop:"parentComment",label:"\u56DE\u590D\u8BC4\u8BBA","show-overflow-tooltip":""}),o(n,{prop:"author",label:"\u8BC4\u8BBA\u4EBA","show-overflow-tooltip":""}),o(n,{prop:"articleTitle",label:"\u8BC4\u8BBA\u6587\u7AE0","show-overflow-tooltip":""}),o(n,{prop:"status",label:"\u72B6\u6001","show-overflow-tooltip":""}),o(n,{prop:"created",label:"\u521B\u5EFA\u65F6\u95F4","show-overflow-tooltip":""})]),_:1},8,["data"]),o(B,{onSizeChange:p.onHandleSizeChange,onCurrentChange:p.onHandleCurrentChange,class:"mt15","pager-count":5,"page-sizes":[10,20,30],"current-page":a.tableData.param.pageNum,"onUpdate:current-page":e[0]||(e[0]=s=>a.tableData.param.pageNum=s),background:"","page-size":a.tableData.param.pageSize,"onUpdate:page-size":e[1]||(e[1]=s=>a.tableData.param.pageSize=s),layout:"total, sizes, prev, pager, next, jumper",total:a.tableData.total},null,8,["onSizeChange","onCurrentChange","current-page","page-size","total"])]),_:1})])}var $=D(j,[["render",H]]);export{$ as default};
