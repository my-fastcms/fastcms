import{H as k,_ as F,j as R,i as $,ah as l,o as a,c as n,V as p,u as d,P as b,a1 as E,F as M,a8 as U,O as _,S as f,U as h,a3 as j,T as z}from"./@vue-6dabbe94.js";import{u as D,b as G,o as H}from"./vue-router-c1461dfc.js";import{h as O,u as P,o as q,L as S}from"./index-0ad00899.js";import{s as L}from"./pinia-b6f74250.js";import{_ as A}from"./_plugin-vue_export-helper-c27b6911.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./element-plus-91d6d961.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";const J={key:0,class:"layout-navbars-breadcrumb"},K={key:0,class:"layout-navbars-breadcrumb-span"},Q={key:1},W={key:2},X=["onClick"],Y=k({name:"layoutBreadcrumb"}),Z=k({...Y,setup(ee){const B=O(),I=P(),{themeConfig:s}=L(I),{routesList:g}=L(B),m=D(),v=G(),e=F({breadcrumbList:[],routeSplit:[],routeSplitFirst:"",routeSplitIndex:1}),V=R(()=>{u(m.path);const{layout:t,isBreadcrumb:r}=s.value;return t==="classic"||t==="transverse"?!1:!!r}),w=t=>{const{redirect:r,path:i}=t;r?v.push(r):v.push(i)},x=()=>{s.value.isCollapse=!s.value.isCollapse,N()},N=()=>{S.remove("themeConfig"),S.set("themeConfig",s.value)},y=t=>{t.forEach(r=>{e.routeSplit.forEach((i,C,c)=>{e.routeSplitFirst===r.path&&(e.routeSplitFirst+=`/${c[e.routeSplitIndex]}`,e.breadcrumbList.push(r),e.routeSplitIndex++,r.children&&y(r.children))})})},u=t=>{if(!s.value.isBreadcrumb)return!1;e.breadcrumbList=[g.value[0]],e.routeSplit=t.split("/"),e.routeSplit.shift(),e.routeSplitFirst=`/${e.routeSplit[0]}`,e.routeSplitIndex=1,y(g.value),(m.name==="home"||m.name==="notFound"&&e.breadcrumbList.length>1)&&e.breadcrumbList.shift(),e.breadcrumbList.length>0&&(e.breadcrumbList[e.breadcrumbList.length-1].meta.tagsViewName=q.setTagsViewNameI18n(m))};return $(()=>{u(m.path)}),H(t=>{u(t.path)}),(t,r)=>{const i=l("SvgIcon"),C=l("el-breadcrumb-item"),c=l("el-breadcrumb");return V.value?(a(),n("div",J,[p(i,{class:"layout-navbars-breadcrumb-icon",name:d(s).isCollapse?"ele-Expand":"ele-Fold",size:16,onClick:x},null,8,["name"]),p(c,{class:"layout-navbars-breadcrumb-hide"},{default:b(()=>[p(E,{name:"breadcrumb"},{default:b(()=>[(a(!0),n(M,null,U(e.breadcrumbList,(o,T)=>(a(),_(C,{key:o.meta.tagsViewName?o.meta.tagsViewName:o.meta.title},{default:b(()=>[T===e.breadcrumbList.length-1?(a(),n("span",K,[d(s).isBreadcrumbIcon?(a(),_(i,{key:0,name:o.meta.icon,class:"layout-navbars-breadcrumb-iconfont"},null,8,["name"])):f("",!0),o.meta.tagsViewName?(a(),n("div",W,h(o.meta.tagsViewName),1)):(a(),n("div",Q,h(t.$t(o.meta.title)),1))])):(a(),n("a",{key:1,onClick:j(te=>w(o),["prevent"])},[d(s).isBreadcrumbIcon?(a(),_(i,{key:0,name:o.meta.icon,class:"layout-navbars-breadcrumb-iconfont"},null,8,["name"])):f("",!0),z(h(t.$t(o.meta.title)),1)],8,X))]),_:2},1024))),128))]),_:1})]),_:1})])):f("",!0)}}});const Ue=A(Z,[["__scopeId","data-v-1b17d545"]]);export{Ue as default};
