var l=Object.defineProperty;var n=Object.getOwnPropertySymbols;var c=Object.prototype.hasOwnProperty,u=Object.prototype.propertyIsEnumerable;var f=(a,t,e)=>t in a?l(a,t,{enumerable:!0,configurable:!0,writable:!0,value:e}):a[t]=e,m=(a,t)=>{for(var e in t||(t={}))c.call(t,e)&&f(a,e,t[e]);if(n)for(var e of n(t))u.call(t,e)&&f(a,e,t[e]);return a};import{i as g,M as h,j as p,J as v,o as w,P as _,t as L,aa as x,w as d,m as y,p as I,v as $,K as k,n as B,D as C}from"./vendor.7efd8838.js";import{_ as V,u as b}from"./index.996e2d4a.js";const j=g({name:"layoutIfameView",setup(){const a=h(),t=b(),e=p({iframeLoading:!0,iframeUrl:""}),s=()=>{e.iframeUrl=a.meta.isLink,B(()=>{e.iframeLoading=!0;const r=document.getElementById("iframe");if(!r)return!1;r.onload=()=>{e.iframeLoading=!1}})},o=v(()=>{let{isTagsview:r}=t.state.themeConfig.themeConfig,{isTagsViewCurrenFull:i}=t.state.tagsViewRoutes;return i?"0px":r?"83px":"49px"});return w(()=>{s()}),_(()=>a.path,()=>{s()}),m({setIframeHeight:o},L(e))}}),D=["src"];function R(a,t,e,s,o,r){const i=x("loading");return d((C(),y("div",{class:"layout-view-bg-white flex mt1",style:k({height:`calc(100vh - ${a.setIframeHeight}`,border:"none"})},[d(I("iframe",{src:a.iframeUrl,frameborder:"0",height:"100%",width:"100%",id:"iframe"},null,8,D),[[$,!a.iframeLoading]])],4)),[[i,a.iframeLoading]])}var E=V(j,[["render",R]]);export{E as default};
