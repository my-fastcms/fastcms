var c=Object.defineProperty;var o=Object.getOwnPropertySymbols;var u=Object.prototype.hasOwnProperty,d=Object.prototype.propertyIsEnumerable;var n=(s,e,t)=>e in s?c(s,e,{enumerable:!0,configurable:!0,writable:!0,value:t}):s[e]=t,l=(s,e)=>{for(var t in e||(e={}))u.call(e,t)&&n(s,t,e[t]);if(o)for(var t of o(e))d.call(e,t)&&n(s,t,e[t]);return s};import{_ as g,u as f}from"./index.6210ba4b.js";import{j as p,J as m,t as h,m as _,K as v,D as w,p as a}from"./vendor.7efd8838.js";const x={name:"pagesFilteringDetails",setup(){const s=f(),e=p({tagViewHeight:""}),t=m(()=>{let{isTagsview:i}=s.state.themeConfig.themeConfig,{isTagsViewCurrenFull:r}=s.state.tagsViewRoutes;return r?"30px":i?"114px":"80px"});return l({initTagViewHeight:t},h(e))}},V=a("div",{class:"layout-view-bg-white"},[a("div",{class:"w100 h100 flex"},[a("div",{class:"flex-margin color-primary"},"filtering-details \u6D4B\u8BD5\u754C\u9762")])],-1),B=[V];function y(s,e,t,i,r,C){return w(),_("div",{style:v({height:`calc(100vh - ${i.initTagViewHeight}`})},B,4)}var b=g(x,[["render",y]]);export{b as default};
