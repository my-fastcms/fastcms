var f=Object.defineProperty;var r=Object.getOwnPropertySymbols;var A=Object.prototype.hasOwnProperty,v=Object.prototype.propertyIsEnumerable;var p=(e,t,s)=>t in e?f(e,t,{enumerable:!0,configurable:!0,writable:!0,value:s}):e[t]=s,_=(e,t)=>{for(var s in t||(t={}))A.call(t,s)&&p(e,s,t[s]);if(r)for(var s of r(t))v.call(t,s)&&p(e,s,t[s]);return e};import{_ as D}from"./index.fabd0e58.js";import{j as F,t as E,l as u,m as k,z as o,A as a,D as n,Q as l,L as h,x}from"./vendor.09ddcd37.js";const b={name:"pagesSteps",setup(){const e=F({stepsActive:1});return _({onNextSteps:()=>{e.stepsActive++>2&&(e.stepsActive=1)}},E(e))}},N={class:"steps-container"},y=x("\u4E0B\u4E00\u6B65");function S(e,t,s,d,T,j){const c=u("el-step"),m=u("el-steps"),i=u("el-result"),B=u("el-button"),C=u("el-card");return n(),k("div",N,[o(C,{shadow:"hover",header:"element-plus \u6B65\u9AA4\u6761"},{default:a(()=>[o(m,{active:e.stepsActive},{default:a(()=>[o(c,{title:"\u7B2C\u4E00\u6B65",icon:"iconfont icon-0_round_solid"}),o(c,{title:"\u7B2C\u4E8C\u6B65",icon:"iconfont icon-2_round_solid"}),o(c,{title:"\u7B2C\u4E09\u6B65",icon:"iconfont icon-3_round_solid"})]),_:1},8,["active"]),e.stepsActive===1?(n(),l(i,{key:0,icon:"error",title:"\u6210\u529F\u63D0\u793A",subTitle:"\u8BF7\u6839\u636E\u63D0\u793A\u8FDB\u884C\u64CD\u4F5C"})):e.stepsActive===2?(n(),l(i,{key:1,icon:"warning",title:"\u8B66\u544A\u63D0\u793A",subTitle:"\u8BF7\u6839\u636E\u63D0\u793A\u8FDB\u884C\u64CD\u4F5C"})):e.stepsActive===3?(n(),l(i,{key:2,icon:"success",title:"\u9519\u8BEF\u63D0\u793A",subTitle:"\u8BF7\u6839\u636E\u63D0\u793A\u8FDB\u884C\u64CD\u4F5C"})):h("",!0),o(B,{onClick:d.onNextSteps,size:"small",class:"mt15",type:"primary",icon:"iconfont icon-step"},{default:a(()=>[y]),_:1},8,["onClick"])]),_:1})])}var g=D(b,[["render",S]]);export{g as default};
