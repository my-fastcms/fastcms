import{u as x}from"./vue-i18n-b3581196.js";import{s as C}from"./pinia-b6f74250.js";import{u as w,L as m}from"./index-a6ee13d3.js";import{H as c,_ as y,j as k,i as L,ah as n,o as U,c as V,V as a,P as i,a as t,U as o,T as r}from"./@vue-6dabbe94.js";import{_ as N}from"./_plugin-vue_export-helper-c27b6911.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./vue-router-c1461dfc.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./element-plus-91d6d961.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";const G={class:"upgrade-dialog"},$={class:"upgrade-title"},B={class:"upgrade-title-warp"},E={class:"upgrade-title-warp-txt"},H={class:"upgrade-title-warp-version"},O={class:"upgrade-content"},j={class:"mt5"},z={class:"upgrade-content-desc mt5"},A={class:"upgrade-btn"},I=c({name:"layoutUpgrade"}),D=c({...I,setup(M){const{t:p}=x(),_=w(),{themeConfig:u}=C(_),e=y({isUpgrade:!1,version:"2.0.0",isLoading:!1,btnTxt:""}),g=k(()=>u.value),f=()=>{e.isUpgrade=!1},v=()=>{e.isLoading=!0,e.btnTxt=p("message.upgrade.btnTwoLoading"),setTimeout(()=>{m.clear(),window.location.reload(),m.set("version",e.version)},2e3)};return L(()=>{setTimeout(()=>{e.btnTxt=p("message.upgrade.btnTwo")},200)}),(s,d)=>{const h=n("el-link"),l=n("el-button"),T=n("el-dialog");return U(),V("div",G,[a(T,{modelValue:e.isUpgrade,"onUpdate:modelValue":d[0]||(d[0]=b=>e.isUpgrade=b),width:"300px","destroy-on-close":"","show-close":!1,"close-on-click-modal":!1,"close-on-press-escape":!1},{default:i(()=>[t("div",$,[t("div",B,[t("span",E,o(s.$t("message.upgrade.title")),1),t("span",H,"v"+o(e.version),1)])]),t("div",O,[r(o(g.value.globalTitle)+" "+o(s.$t("message.upgrade.msg"))+" ",1),t("div",j,[a(h,{type:"primary",class:"font12",href:"https://gitee.com/xjd2020/fastcms/blob/master/CHANGELOG.md",target:"_black"},{default:i(()=>[r(" CHANGELOG.md ")]),_:1})]),t("div",z,o(s.$t("message.upgrade.desc")),1)]),t("div",A,[a(l,{round:"",size:"default",type:"info",text:"",onClick:f},{default:i(()=>[r(o(s.$t("message.upgrade.btnOne")),1)]),_:1}),a(l,{type:"primary",round:"",size:"default",onClick:v,loading:e.isLoading},{default:i(()=>[r(o(e.btnTxt),1)]),_:1},8,["loading"])])]),_:1},8,["modelValue"])])}}});const we=N(D,[["__scopeId","data-v-d332c199"]]);export{we as default};