var c=Object.defineProperty;var n=Object.getOwnPropertySymbols;var l=Object.prototype.hasOwnProperty,p=Object.prototype.propertyIsEnumerable;var o=(e,s,t)=>s in e?c(e,s,{enumerable:!0,configurable:!0,writable:!0,value:t}):e[s]=t,i=(e,s)=>{for(var t in s||(s={}))l.call(s,t)&&o(e,t,s[t]);if(n)for(var t of n(s))p.call(s,t)&&o(e,t,s[t]);return e};import{i as m,M as f,j as h,J as d,P as g,t as k,m as v,p as y,y as u,K as R,D as _}from"./vendor.63450691.js";import{_ as w,u as M}from"./index.56ec5e99.js";const $=m({name:"layoutLinkView",setup(){const e=f(),s=M(),t=h({currentRouteMeta:{}}),a=d(()=>{let{isTagsview:r}=s.state.themeConfig.themeConfig;return r?"114px":"80px"});return g(()=>e.path,()=>{t.currentRouteMeta=e.meta},{immediate:!0}),i({setLinkHeight:a},k(t))}}),L=["href"];function j(e,s,t,a,r,x){return _(),v("div",{class:"layout-view-bg-white flex layout-view-link",style:R({height:`calc(100vh - ${e.setLinkHeight}`})},[y("a",{href:e.currentRouteMeta.isLink,target:"_blank",rel:"opener",class:"flex-margin"},u(e.$t(e.currentRouteMeta.title))+"\uFF1A"+u(e.currentRouteMeta.isLink),9,L)],4)}var b=w($,[["render",j]]);export{b as default};