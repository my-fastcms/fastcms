var r=Object.defineProperty;var n=Object.getOwnPropertySymbols;var u=Object.prototype.hasOwnProperty,_=Object.prototype.propertyIsEnumerable;var o=(e,t,a)=>t in e?r(e,t,{enumerable:!0,configurable:!0,writable:!0,value:a}):e[t]=a,d=(e,t)=>{for(var a in t||(t={}))u.call(t,a)&&o(e,a,t[a]);if(n)for(var a of n(t))_.call(t,a)&&o(e,a,t[a]);return e};import{_ as p,e as c}from"./index.fabd0e58.js";import{j as m,O as l,k as f,t as v,m as h,p as s,y as x,B as Q,C as S,D as Y}from"./vendor.09ddcd37.js";const w={name:"chartHead",setup(){const e=m({time:{txt:"",fun:0}}),t=()=>{e.time.txt=c(new Date,"YYYY-mm-dd HH:MM:SS WWW QQQQ"),e.time.fun=window.setInterval(()=>{e.time.txt=c(new Date,"YYYY-mm-dd HH:MM:SS WWW QQQQ")},1e3)};return l(()=>{t()}),f(()=>{window.clearInterval(e.time.fun)}),d({},v(e))}},i=e=>(Q("data-v-2e5a2944"),e=e(),S(),e),B={class:"big-data-up mb15"},D={class:"up-left"},H=i(()=>s("i",{class:"el-icon-time mr5"},null,-1)),I=i(()=>s("div",{class:"up-center"},[s("span",null,"\u667A\u6167\u519C\u4E1A\u7CFB\u7EDF\u5E73\u53F0")],-1));function W(e,t,a,M,y,C){return Y(),h("div",B,[s("div",D,[H,s("span",null,x(e.time.txt),1)]),I])}var g=p(w,[["render",W],["__scopeId","data-v-2e5a2944"]]);export{g as default};
