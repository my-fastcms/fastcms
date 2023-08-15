import{i as l}from"./countup.js-350bca8d.js";import{a as E}from"./formatTime-29ac8c52.js";import{s as T}from"./pinia-b6f74250.js";import{s as R,b as j,a as H}from"./index-0ad00899.js";import{H as I,_ as U,j as q,i as J,ba as K,f as M,ah as c,o as _,c as f,V as o,P as s,a as t,u as m,U as a,F as y,a8 as x,n as O,O as P,K as C,J as G,bh as Q,bf as W}from"./@vue-6dabbe94.js";import{_ as X}from"./_plugin-vue_export-helper-c27b6911.js";import"./vue-router-c1461dfc.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./element-plus-91d6d961.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";function Y(){return{getIndexData(){return R({url:"/admin/index/data",method:"get"})}}}const N=[{title:"今日新增文章",titleNum:"1123",tip:"文章总数",tipNum:"911",color:"#F95959",iconColor:"#F86C6B",icon:"iconfont icon-jinridaiban"},{title:"今日浏览量",titleNum:"1223",tip:"总浏览量",tipNum:"911",color:"#FEBB50",iconColor:"#FDC566",icon:"iconfont icon-shenqingkaiban"},{title:"今日新增订单",titleNum:"1243",tip:"订单总数",tipNum:"611",color:"#8595F4",iconColor:"#92A1F4",icon:"iconfont icon-AIshiyanshi"}],D=[{time1:"2021-11-03",time2:"12:20:30",title:"命名",label:"正式命名为 fastcms"},{time1:"2022-03-01",time2:"00:00:00",title:"版本",label:"发布第一个版本V0.0.1"}],F=p=>(Q("data-v-c2cc1a75"),p=p(),W(),p),Z={class:"home-container"},tt={class:"home-card-item home-card-first"},et={class:"flex-margin flex"},it=["src"],ot={class:"home-card-first-right ml15"},st={class:"flex-margin"},at={class:"home-card-first-right-title"},nt={class:"home-card-first-right-msg mt5"},lt={class:"home-card-item-flex"},ct={class:"home-card-item-title pb3"},mt=["id"],rt={class:"home-card-item-tip pb3"},dt=["id"],_t=["href"],pt={class:"home-dynamic"},ht={class:"home-dynamic-item-left"},ut={class:"home-dynamic-item-left-time1 mb5"},ft={class:"home-dynamic-item-left-time2"},gt=F(()=>t("div",{class:"home-dynamic-item-line"},[t("i",{class:"iconfont icon-fangkuang"})],-1)),bt={class:"home-dynamic-item-right"},vt={class:"home-dynamic-item-right-title mb5"},wt=F(()=>t("i",{class:"el-icon-s-comment"},null,-1)),yt={class:"home-dynamic-item-right-label"},xt=I({name:"home"}),Ct=I({...xt,setup(p){const S=Y(),k=j(),L=H(),{userInfos:h}=T(k),u=U({topCardItemList:N,activitiesList:D,articleListData:[],myCharts:[]}),V=q(()=>E(new Date)),g=()=>{O(()=>{for(let i=0;i<u.myCharts.length;i++)u.myCharts[i].resize()})},A=()=>{window.addEventListener("resize",g)},B=()=>{S.getIndexData().then(i=>{let r=i.data.articleStatData,n=i.data.orderStatData;new l("titleNum1",0).update(r.todayCount),new l("titleNum2",0).update(r.todayViewCount),new l("titleNum3",0).update(n.todayCount),new l("tipNum1",0).update(r.totalCount),new l("tipNum2",0).update(r.totalViewCount),new l("tipNum3",0).update(n),u.articleListData=i.data.newArticleList})};return J(()=>{B(),A()}),K(()=>{g()}),M(()=>L.isTagsViewCurrenFull,()=>{g()}),(i,r)=>{const n=c("el-col"),b=c("el-row"),v=c("el-table-column"),z=c("el-table"),w=c("el-card"),$=c("el-scrollbar");return _(),f("div",Z,[o(b,{gutter:15},{default:s(()=>[o(n,{sm:6,class:"mb15"},{default:s(()=>[t("div",tt,[t("div",et,[t("img",{src:m(h).photo},null,8,it),t("div",ot,[t("div",st,[t("div",at,a(V.value)+"，"+a(m(h).username===""?"游客":m(h).username)+"！ ",1),t("div",nt,a(m(h).username==="admin"?"超级管理":""),1)])])])])]),_:1}),(_(!0),f(y,null,x(m(N),(e,d)=>(_(),P(n,{sm:6,class:"mb15",key:d},{default:s(()=>[t("div",{class:"home-card-item home-card-item-box",style:C({background:e.color})},[t("div",lt,[t("div",ct,a(e.title),1),t("div",{class:"home-card-item-title-num pb6",id:`titleNum${d+1}`},null,8,mt),t("div",rt,a(e.tip),1),t("div",{class:"home-card-item-tip-num",id:`tipNum${d+1}`},null,8,dt)]),t("i",{class:G(e.icon),style:C({color:e.iconColor})},null,6)],4)]),_:2},1024))),128))]),_:1}),o(b,{gutter:15},{default:s(()=>[o(n,{xs:24,sm:14,md:14,lg:16,xl:16,class:"home-warning-media"},{default:s(()=>[o(w,{shadow:"hover",style:{height:"450px"},header:i.$t("message.card.title3"),class:"home-warning-card"},{default:s(()=>[o(z,{data:u.articleListData,style:{width:"100%",height:"450px"},stripe:""},{default:s(()=>[o(v,{prop:"title","min-width":"80%",label:i.$t("message.table.th1")},{default:s(e=>[t("a",{href:e.row.url,target:"_blank"},a(e.row.title),9,_t)]),_:1},8,["label"]),o(v,{prop:"viewCount","min-width":"20%",label:i.$t("message.table.th2")},null,8,["label"])]),_:1},8,["data"])]),_:1},8,["header"])]),_:1}),o(n,{xs:24,sm:10,md:10,lg:8,xl:8,class:"home-dynamic-media"},{default:s(()=>[o(w,{shadow:"hover",style:{height:"450px"},header:i.$t("message.card.title4")},{default:s(()=>[t("div",pt,[o($,null,{default:s(()=>[(_(!0),f(y,null,x(m(D),(e,d)=>(_(),f("div",{class:"home-dynamic-item",key:d},[t("div",ht,[t("div",ut,a(e.time1),1),t("div",ft,a(e.time2),1)]),gt,t("div",bt,[t("div",vt,[wt,t("span",null,a(e.title),1)]),t("div",yt,a(e.label),1)])]))),128))]),_:1})])]),_:1},8,["header"])]),_:1})]),_:1})])}}});const le=X(Ct,[["__scopeId","data-v-c2cc1a75"]]);export{le as default};
