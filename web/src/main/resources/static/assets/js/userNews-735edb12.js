import{H as l,_ as u,ah as p,o as e,c as n,a as s,U as t,S as i,F as v,a8 as w,O as b}from"./@vue-6dabbe94.js";import{_ as h}from"./_plugin-vue_export-helper-c27b6911.js";const g={class:"layout-navbars-breadcrumb-user-news"},k={class:"head-box"},y={class:"head-box-title"},f={class:"content-box"},x={class:"content-box-msg"},C={class:"content-box-time"},B=l({name:"layoutBreadcrumbUserNews"}),L=l({...B,setup(N){const o=u({newsList:[{label:"关于版本发布的通知",value:"Fastcms是基于SpringBoot前后端分离技术，且具有插件化架构的CMS系统，系统具有高扩展性，易维护性，可以快速搭建网站，微信小程序，是开发微信营销插件的基石！",time:"2023-06-12"}]}),r=()=>{o.newsList=[]},d=()=>{window.open("https://gitee.com/xjd2020/fastcms.git")};return(a,S)=>{const _=p("el-empty");return e(),n("div",g,[s("div",k,[s("div",y,t(a.$t("message.user.newTitle")),1),o.newsList.length>0?(e(),n("div",{key:0,class:"head-box-btn",onClick:r},t(a.$t("message.user.newBtn")),1)):i("",!0)]),s("div",f,[o.newsList.length>0?(e(!0),n(v,{key:0},w(o.newsList,(c,m)=>(e(),n("div",{class:"content-box-item",key:m},[s("div",null,t(c.label),1),s("div",x,t(c.value),1),s("div",C,t(c.time),1)]))),128)):(e(),b(_,{key:1,description:a.$t("message.user.newDesc")},null,8,["description"]))]),o.newsList.length>0?(e(),n("div",{key:0,class:"foot-box",onClick:d},t(a.$t("message.user.newGo")),1)):i("",!0)])}}});const G=h(L,[["__scopeId","data-v-caa9e6c7"]]);export{G as default};
