import{u as v,b as q}from"./vue-router-c1461dfc.js";import{u as R}from"./vue-i18n-b3581196.js";import{S as s,L as n,i as x,f as L,g as w}from"./index-0ad00899.js";import{a as S}from"./formatTime-29ac8c52.js";import{L as U}from"./index-3434dcb9.js";import{a as i}from"./element-plus-91d6d961.js";import{H as _,e as C,_ as E,j,i as A,ah as B,o as b,c as D,V as M}from"./@vue-6dabbe94.js";import{_ as N}from"./_plugin-vue_export-helper-c27b6911.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./pinia-b6f74250.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";const O={class:"login-scan-container"},V=_({name:"loginScan"}),$=_({...V,setup(F){const{t:g}=R(),a=v(),c=q(),m=U(),h=C(null),r=E({sceneId:"",qrcodeUrl:""}),y=j(()=>S(new Date)),I=async e=>{if(e.data.userType!=1&&e.data.userType!=2){i.error("未知用户类型");return}e.data.userType===1&&e.data.hasRole==!1?i.error("请联系管理员授权"):T(e)},T=async e=>{var p,d,l,u;const o={username:e.data.username,photo:e.data.headImg===null||e.data.headImg==""?"/header.jpg":e.data.headImg,time:new Date().getTime(),hasRole:e.data.hasRole,version:e.data.version,userType:e.data.userType};s.set("token",e.data.token),s.set("tokenTtl",e.data.tokenTtl),n.set("token",e.data.token),n.set("tokenTtl",e.data.tokenTtl),n.set("userInfo",o),s.set("userInfo",o),e.data.userType===1?await x():e.data.userType===2&&await L();let t=y.value;if((p=a.query)!=null&&p.redirect)c.push({path:(d=a.query)==null?void 0:d.redirect,query:Object.keys((l=a.query)==null?void 0:l.params).length>0?JSON.parse((u=a.query)==null?void 0:u.params):""});else{const k=w[0].children[0].path||"/";c.push(k)}setTimeout(()=>{const f=g("message.signInText");i.success(`${t}，${f}`)},300)};return A(()=>{m.getLoginQrcode().then(e=>{r.qrcodeUrl=e.data.qrcodeUrl||null,r.sceneId=e.data.sceneId||null;let o=setInterval(()=>{m.getLoginUser(r.sceneId).then(t=>{clearInterval(o),I(t)}).catch(t=>{})},1e3)})}),(e,o)=>{const t=B("el-image");return b(),D("div",O,[M(t,{class:"login-scan-qrcode",ref_key:"qrcodeRef",ref:h,src:r.qrcodeUrl},null,8,["src"])])}}});const Le=N($,[["__scopeId","data-v-cf0ca5fe"]]);export{Le as default};
