var d=Object.defineProperty;var n=Object.getOwnPropertySymbols;var l=Object.prototype.hasOwnProperty,u=Object.prototype.propertyIsEnumerable;var f=(a,t,e)=>t in a?d(a,t,{enumerable:!0,configurable:!0,writable:!0,value:e}):a[t]=e,m=(a,t)=>{for(var e in t||(t={}))l.call(t,e)&&f(a,e,t[e]);if(n)for(var e of n(t))u.call(t,e)&&f(a,e,t[e]);return a};import{i as g,M as h,j as p,J as v,o as w,P as _,t as L,a9 as x,w as c,m as y,p as I,v as $,K as b,n as k,D as B}from"./vendor.d2ed1f2c.js";import{_ as C,u as V}from"./index.a78abf2f.js";const j=g({name:"layoutIfameView",setup(){const a=h(),t=V(),e=p({iframeLoading:!0,iframeUrl:""}),s=()=>{e.iframeUrl=a.meta.isLink,k(()=>{e.iframeLoading=!0;const r=document.getElementById("iframe");if(!r)return!1;r.onload=()=>{e.iframeLoading=!1}})},o=v(()=>{let{isTagsview:r}=t.state.themeConfig.themeConfig,{isTagsViewCurrenFull:i}=t.state.tagsViewRoutes;return i?"0px":r?"83px":"49px"});return w(()=>{s()}),_(()=>a.path,()=>{s()}),m({setIframeHeight:o},L(e))}}),D=["src"];function R(a,t,e,s,o,r){const i=x("loading");return c((B(),y("div",{class:"layout-view-bg-white flex mt1",style:b({height:`calc(100vh - ${a.setIframeHeight}`,border:"none"})},[c(I("iframe",{src:a.iframeUrl,frameborder:"0",height:"100%",width:"100%",id:"iframe"},null,8,D),[[$,!a.iframeLoading]])],4)),[[i,a.iframeLoading]])}var E=C(j,[["render",R]]);export{E as default};
