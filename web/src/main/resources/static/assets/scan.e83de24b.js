var C=Object.defineProperty;var _=Object.getOwnPropertySymbols;var k=Object.prototype.hasOwnProperty,B=Object.prototype.propertyIsEnumerable;var I=(a,t,s)=>t in a?C(a,t,{enumerable:!0,configurable:!0,writable:!0,value:s}):a[t]=s,h=(a,t)=>{for(var s in t||(t={}))k.call(t,s)&&I(a,s,t[s]);if(_)for(var s of _(t))B.call(t,s)&&I(a,s,t[s]);return a};import{i as x,H as U,M as b,a0 as j,j as w,o as S,t as $,l as E,m as F,z as L,b as d,D as M,G as A}from"./vendor.07d41a1e.js";import{_ as D,u as N,L as i,b as y,c as O,d as z}from"./index.5f776db4.js";import{a as G,b as H}from"./index.08f00681.js";const J=x({name:"login11",props:{domain:{type:String,default:()=>""}},setup(a){const{t}=U(),{proxy:s}=A(),c=N(),o=b(),u=j(),n=w({sceneId:null,qrcodeUrl:""}),v=async e=>{if(e.data.userType!=1&&e.data.userType!=2){d.error("\u672A\u77E5\u7528\u6237\u7C7B\u578B");return}e.data.userType===1&&e.data.hasRole==!1?d.error("\u8BF7\u8054\u7CFB\u7BA1\u7406\u5458\u6388\u6743"):q(e)},q=async e=>{var l,p,g,m;const r={username:e.data.username,photo:e.data.headImg===null?"/header.jpg":e.data.headImg,time:new Date().getTime(),hasRole:e.data.hasRole,version:e.data.version,userType:e.data.userType};i.set("token",e.data.token),i.set("tokenTtl",e.data.tokenTtl),i.set("userInfo",r),c.dispatch("userInfos/setUserInfos",r),c.state.themeConfig.themeConfig.isRequestRoutes?e.data.userType===1?await O():e.data.userType===2&&await y():await y();let T=currentTime.value;if((l=o.query)==null?void 0:l.redirect)console.log(o.query),u.push({path:(p=o.query)==null?void 0:p.redirect,query:Object.keys((g=o.query)==null?void 0:g.params).length>0?JSON.parse((m=o.query)==null?void 0:m.params):""});else{const R=z[0].children[0].path||"/";u.push(R)}setTimeout(()=>{n.loading.signIn=!0;const f=t("message.signInText");d.success(`${T}\uFF0C${f}`),s.mittBus.emit("onSignInClick")},300)};return S(()=>{G().then(e=>{n.qrcodeUrl=e.data.qrcodeUrl||null,n.sceneId=e.data.sceneId||null,console.log("sceneId:"+n.sceneId),console.log("qrcodeUrl:"+n.qrcodeUrl),setInterval(()=>{H(n.sceneId).then(r=>{clearInterval(),v(r)}).catch(()=>{console.log("=====no login user find=====")})},1e3)})}),h({},$(n))}}),P={class:"login-scan-container"};function Q(a,t,s,c,o,u){const n=E("el-image");return M(),F("div",P,[L(n,{class:"login-scan-qrcode",ref:"qrcodeRef",src:a.qrcodeUrl},null,8,["src"])])}var Y=D(J,[["render",Q],["__scopeId","data-v-4abd23dc"]]);export{Y as default};
