var c=Object.defineProperty;var n=Object.getOwnPropertySymbols;var l=Object.prototype.hasOwnProperty,f=Object.prototype.propertyIsEnumerable;var o=(e,a,t)=>a in e?c(e,a,{enumerable:!0,configurable:!0,writable:!0,value:t}):e[a]=t,i=(e,a)=>{for(var t in a||(a={}))l.call(a,t)&&o(e,t,a[t]);if(n)for(var t of n(a))f.call(a,t)&&o(e,t,a[t]);return e};import{i as p,M as m,j as h,J as d,P as g,t as k,m as v,p as y,y as u,K as R,D as _}from"./vendor.35fa38cf.js";import{_ as w,u as M}from"./index.6b1e96b3.js";const $=p({name:"layoutLinkView",setup(){const e=m(),a=M(),t=h({currentRouteMeta:{}}),s=d(()=>{let{isTagsview:r}=a.state.themeConfig.themeConfig;return r?"114px":"80px"});return g(()=>e.path,()=>{t.currentRouteMeta=e.meta},{immediate:!0}),i({setLinkHeight:s},k(t))}}),L=["href"];function b(e,a,t,s,r,j){return _(),v("div",{class:"layout-view-bg-white flex layout-view-link",style:R({height:`calc(100vh - ${e.setLinkHeight}`})},[y("a",{href:e.currentRouteMeta.isLink,target:"_blank",rel:"opener",class:"flex-margin"},u(e.$t(e.currentRouteMeta.title))+"\uFF1A"+u(e.currentRouteMeta.isLink),9,L)],4)}var S=w($,[["render",b]]);export{S as default};
