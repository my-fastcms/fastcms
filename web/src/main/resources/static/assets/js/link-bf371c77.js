import{u as c}from"./vue-router-c1461dfc.js";import{c as l}from"./index-0ad00899.js";import{H as p,_,f as d,ah as u,o as f,c as h,a as o,U as k,V as v,P as w,bh as y,bf as g}from"./@vue-6dabbe94.js";import{_ as b}from"./_plugin-vue_export-helper-c27b6911.js";import"./pinia-b6f74250.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./element-plus-91d6d961.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";const n=i=>(y("data-v-aed8fb0e"),i=i(),g(),i),L={class:"layout-padding layout-link-container"},x={class:"layout-padding-auto layout-padding-view"},C={class:"layout-link-warp"},I=n(()=>o("i",{class:"layout-link-icon iconfont icon-xingqiu"},null,-1)),S={class:"layout-link-msg"},V=n(()=>o("i",{class:"iconfont icon-lianjie"},null,-1)),$=n(()=>o("span",null,"立即前往体验",-1)),B=p({name:"layoutLinkView"}),N=p({...B,setup(i){const e=c(),t=_({title:"",isLink:""}),r=()=>{const{origin:s,pathname:a}=window.location;l(t.isLink)?window.open(t.isLink):window.open(`${s}${a}#${t.isLink}`)};return d(()=>e.path,()=>{t.title=e.meta.title,t.isLink=e.meta.isLink},{immediate:!0}),(s,a)=>{const m=u("el-button");return f(),h("div",L,[o("div",x,[o("div",C,[I,o("div",S,'页面 "'+k(s.$t(t.title))+'" 已在新窗口中打开',1),v(m,{class:"mt30",round:"",size:"default",onClick:r},{default:w(()=>[V,$]),_:1})])])])}}});const dt=b(N,[["__scopeId","data-v-aed8fb0e"]]);export{dt as default};
