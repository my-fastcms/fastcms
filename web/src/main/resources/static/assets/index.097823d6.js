var B=Object.defineProperty;var h=Object.getOwnPropertySymbols;var C=Object.prototype.hasOwnProperty,_=Object.prototype.propertyIsEnumerable;var m=(t,u,e)=>u in t?B(t,u,{enumerable:!0,configurable:!0,writable:!0,value:e}):t[u]=e,E=(t,u)=>{for(var e in u||(u={}))C.call(u,e)&&m(t,e,u[e]);if(h)for(var e of h(u))_.call(u,e)&&m(t,e,u[e]);return t};import{_ as D,u as b,r as L,S as w,c as I,e as P}from"./index.6b1e96b3.js";import{j as v,J as f,o as R,t as k,l as F,m as j,z as a,A as g,D as x}from"./vendor.35fa38cf.js";const y={name:"limitsFrontEndPage",setup(){const t=b(),u=v({val:"",userAuth:""}),e=f(()=>t.state.userInfos.userInfos.authPageList),o=f(()=>t.state.userInfos.userInfos.authBtnList),d=()=>{u.userAuth=t.state.userInfos.userInfos.authPageList[0]},A=async()=>{L();let s=[],n=[],r=["admin"],i=["btn.add","btn.del","btn.edit","btn.link"],l=["test"],p=["btn.add","btn.link"];u.userAuth==="admin"?(s=r,n=i):(s=l,n=p);const c={userName:u.userAuth,photo:u.userAuth==="admin"?"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1813762643,1914315241&fm=26&gp=0.jpg":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=317673774,2961727727&fm=26&gp=0.jpg",time:new Date().getTime(),authPageList:s,authBtnList:n};w.set("userInfo",c),t.dispatch("userInfos/setUserInfos",c),await I(),P()};return R(()=>{d()}),E({getAuthPageList:e,getAuthBtnList:o,onRadioChange:A},k(u))}};function S(t,u,e,o,d,A){const s=F("el-alert"),n=F("el-radio-button"),r=F("el-radio-group"),i=F("el-card");return x(),j("div",null,[a(s,{title:"\u6E29\u99A8\u63D0\u793A\uFF1A\u6B64\u6743\u9650\u9875\u9762\u4EE3\u7801\u53CA\u6548\u679C\u53EA\u4F5C\u4E3A\u6F14\u793A\u4F7F\u7528\uFF0C\u82E5\u51FA\u73B0\u4E0D\u53EF\u9006\u8F6C\u7684bug\uFF0C\u8BF7\u5C1D\u8BD5 `F5` \u5237\u65B0\u9875\u9762\u3002\u82E5\u5B9E\u9645\u9879\u76EE\u4E2D\u975E\u8981\u5B9E\u73B0\u6B64\u7528\u6237\u6743\u9650\u5207\u6362\u529F\u80FD\uFF0C\r\n      \u8BF7\u5728\u5207\u6362\u65B9\u6CD5 `onRadioChange` \u6700\u540E\u9762\u6DFB\u52A0\u5237\u65B0\u4EE3\u7801 `window.location.reload()`\u3002 \u8BF7\u6CE8\u610F\uFF1A\u6309\u94AE\u6743\u9650\u9875\u9762\u4E2D\u7684\u6F14\u793A2\uFF08\u6307\u4EE4\u6A21\u5F0F\uFF09\u3001\u6F14\u793A3\uFF08\u51FD\u6570\u6A21\u5F0F\uFF09\r\n      \u5207\u6362\u7528\u6237\u65F6\u65E0\u6CD5\u52A8\u6001\u6F14\u793A\uFF0C\u60F3\u8981\u52A8\u6001\u6F14\u793A\uFF0C\u8BF7\u6309 `F5` \u6216\u8005\u6DFB\u52A0 `window.location.reload()`\u3002",type:"warning",closable:!1}),a(s,{title:`\u5F53\u524D\u7528\u6237\u9875\u9762\u6743\u9650\uFF1A[${o.getAuthPageList}]\uFF0C\u5F53\u524D\u7528\u6237\u6309\u94AE\u6743\u9650\uFF1A[${o.getAuthBtnList}]`,type:"success",closable:!1,class:"mt15"},null,8,["title"]),a(i,{shadow:"hover",header:"\u5207\u6362\u7528\u6237\u6F14\u793A\uFF0C\u524D\u7AEF\u63A7\u5236\u4E0D\u540C\u7528\u6237\u663E\u793A\u4E0D\u540C\u9875\u9762\u3001\u6309\u94AE\u6743\u9650",class:"mt15"},{default:g(()=>[a(r,{modelValue:t.userAuth,"onUpdate:modelValue":u[0]||(u[0]=l=>t.userAuth=l),size:"small",onChange:o.onRadioChange},{default:g(()=>[a(n,{label:"admin"}),a(n,{label:"test"})]),_:1},8,["modelValue","onChange"])]),_:1})])}var $=D(y,[["render",S]]);export{$ as default};