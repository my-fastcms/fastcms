var c=Object.defineProperty;var n=Object.getOwnPropertySymbols;var d=Object.prototype.hasOwnProperty,u=Object.prototype.propertyIsEnumerable;var f=(a,t,e)=>t in a?c(a,t,{enumerable:!0,configurable:!0,writable:!0,value:e}):a[t]=e,m=(a,t)=>{for(var e in t||(t={}))d.call(t,e)&&f(a,e,t[e]);if(n)for(var e of n(t))u.call(t,e)&&f(a,e,t[e]);return a};import{i as g,M as h,j as p,J as v,o as w,P as _,t as L,aa as b,w as l,m as x,p as y,v as I,K as $,n as k,D as B}from"./vendor.7efd8838.js";import{_ as C,u as V}from"./index.6210ba4b.js";const j=g({name:"layoutIfameView",setup(){const a=h(),t=V(),e=p({iframeLoading:!0,iframeUrl:""}),s=()=>{e.iframeUrl=a.meta.isLink,k(()=>{e.iframeLoading=!0;const r=document.getElementById("iframe");if(!r)return!1;r.onload=()=>{e.iframeLoading=!1}})},o=v(()=>{let{isTagsview:r}=t.state.themeConfig.themeConfig,{isTagsViewCurrenFull:i}=t.state.tagsViewRoutes;return i?"0px":r?"83px":"49px"});return w(()=>{s()}),_(()=>a.path,()=>{s()}),m({setIframeHeight:o},L(e))}}),D=["src"];function R(a,t,e,s,o,r){const i=b("loading");return l((B(),x("div",{class:"layout-view-bg-white flex mt1",style:$({height:`calc(100vh - ${a.setIframeHeight}`,border:"none"})},[l(y("iframe",{src:a.iframeUrl,frameborder:"0",height:"100%",width:"100%",id:"iframe"},null,8,D),[[I,!a.iframeLoading]])],4)),[[i,a.iframeLoading]])}var E=C(j,[["render",R]]);export{E as default};
