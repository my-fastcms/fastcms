var d=Object.defineProperty;var n=Object.getOwnPropertySymbols;var l=Object.prototype.hasOwnProperty,u=Object.prototype.propertyIsEnumerable;var f=(t,a,e)=>a in t?d(t,a,{enumerable:!0,configurable:!0,writable:!0,value:e}):t[a]=e,m=(t,a)=>{for(var e in a||(a={}))l.call(a,e)&&f(t,e,a[e]);if(n)for(var e of n(a))u.call(a,e)&&f(t,e,a[e]);return t};import{i as g,M as h,j as p,J as v,o as w,P as _,t as L,a9 as x,w as c,m as y,p as I,v as $,K as k,n as B,D as C}from"./vendor.d2ed1f2c.js";import{_ as V,u as b}from"./index.2d3c5e63.js";const j=g({name:"layoutIfameView",setup(){const t=h(),a=b(),e=p({iframeLoading:!0,iframeUrl:""}),s=()=>{e.iframeUrl=t.meta.isLink,B(()=>{e.iframeLoading=!0;const r=document.getElementById("iframe");if(!r)return!1;r.onload=()=>{e.iframeLoading=!1}})},o=v(()=>{let{isTagsview:r}=a.state.themeConfig.themeConfig,{isTagsViewCurrenFull:i}=a.state.tagsViewRoutes;return i?"0px":r?"83px":"49px"});return w(()=>{s()}),_(()=>t.path,()=>{s()}),m({setIframeHeight:o},L(e))}}),D=["src"];function R(t,a,e,s,o,r){const i=x("loading");return c((C(),y("div",{class:"layout-view-bg-white flex mt1",style:k({height:`calc(100vh - ${t.setIframeHeight}`,border:"none"})},[c(I("iframe",{src:t.iframeUrl,frameborder:"0",height:"100%",width:"100%",id:"iframe"},null,8,D),[[$,!t.iframeLoading]])],4)),[[i,t.iframeLoading]])}var E=V(j,[["render",R]]);export{E as default};